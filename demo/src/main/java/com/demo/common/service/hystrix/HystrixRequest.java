package com.demo.common.service.hystrix;

import com.netflix.hystrix.*;

public class HystrixRequest extends HystrixCommand<String> {
    private final String name;
    private String groupName;
    private long startTime;
    private long staticStartTIme;

    public HystrixRequest(String name, String groupName, long beginTime) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                /* 使用HystrixThreadPoolKey工厂定义线程池名称 如不设置,Hystrix会为每一个CommandGroup建立一个线程池, 如果这里设置固定值, 则所有的CommandGroup都使用同一个线城池*/
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(groupName))
                /* 线程池属性 */
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                /* 线程池大小 默认10*/
                                .withCoreSize(20)
                                /* 线程池队列大小 默认-1 如果超了就直接执行降级策略:getFallback*/
                                .withMaxQueueSize(100)
                )
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                /* 设置fallback的最大并发数 如果超了就直接抛异常*/
                                .withFallbackIsolationSemaphoreMaxConcurrentRequests(200)
                                /* 设置启用超时时间 默认true*/
                                .withExecutionTimeoutEnabled(true)
                                /* 设置超时时间 默认1000ms*/
                                .withExecutionTimeoutInMilliseconds(1000)
                                /* 设置启用断路器(熔断器) 默认true*/
                                .withCircuitBreakerEnabled(true)
                                /* 设置错误百分比，超过该值打开断路器 默认50*/
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                /* 设置10s中内最少的请求量，大于该值断路器配置才会生效 默认20*/
                                .withCircuitBreakerRequestVolumeThreshold(5)
                                /* 短路器打开后多长时间尝试关闭（Half open）默认5s*/
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )
        );

        /*
         * groupName : 用来实现依赖隔离, 不同group的请求分配不同的资源,同一group的资源可以使用同一资源(比如线程池及其配置)
         * name      : 用来实现熔断器的隔离, 不同name的熔断互不影响(比如namea的请求被熔断不会影响nameb的请求继续)
         * */

        this.name = name;
        this.groupName = groupName;
        this.startTime = System.currentTimeMillis();
        this.staticStartTIme = beginTime;
    }

    private long getThreadTotal() {
        return System.currentTimeMillis() - startTime;
    }

    private long getTotal() {
        return System.currentTimeMillis() - staticStartTIme;
    }

    private void printTime(String msg) {
        System.out.println(msg + "|  " + groupName + " * " + name + " * " + String.format("%-16s", Thread.currentThread().getName()) + " : "
                + " 执行时长:<" + getTotal() + ">" + "\t线程执行时长:<" + getThreadTotal() + "> ");
    }

    @Override
    protected String run() {
        printTime("RUN    ->   ");

        for (int i = 0; i < 6; i++) {
            try {
                // printTime("DOING       ");
                Thread.sleep(200);
            } catch (Exception ignore) {

            }
        }

        printTime("SUCCESS     ");
        return "  Hello group: [" + groupName + "],  name: [" + name + "]";
    }

    // 使用Fallback() 提供降级策略 如果不实现getFallback则调用父类(抛异常, onError可捕获)
    // 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
    // HystrixBadRequestException用在非法参数或非系统故障异常等不应触发回退逻辑的场景。
    @Override
    protected String getFallback() {
        printTime("* Fallback *");
        return "jump into fall                 * " + String.format("%-16s", Thread.currentThread().getName());
    }

    // 重写getCacheKey方法,实现区分不同请求的逻辑, 如果调用到getFallback则不会缓存 返回结果必须相同
    @Override
    protected String getCacheKey() {
        return null;
        //        return groupName + "_" + name;
        //        return  UUID.randomUUID().toString();
        //        return groupName + "_" + "qwertyu";
        //        return name + "_" + "qwertyu";
    }
}
