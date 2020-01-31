package com.demo.common.service.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class HystrixRequest extends HystrixCommand<String> {
    private final String name;
    private String groupName;
    private long startTime;
    private long staticStartTIme;

    public HystrixRequest(String name, String groupName, long beginTime) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                /* 配置信号量隔离方式(默认(上面的)采用线程池隔离), 此时 发起请求的线程和真实执行的线程是同一个线程*/
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                /* 设置使用信号量隔离策略 */
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                /* 设置信号量隔离时的最大并发请求数 如果超了就直接执行降级策略:getFallback*/
                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(100)
                                /* 设置fallback的最大并发数 如果超了就直接抛异常*/
                                .withFallbackIsolationSemaphoreMaxConcurrentRequests(200)
                                /* 设置启用超时时间 默认true*/
                                .withExecutionTimeoutEnabled(true)
                                /* 设置超时时间 默认1000ms*/
                                .withExecutionTimeoutInMilliseconds(5000)
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

        System.out.println("  构造       " + groupName + " * " + name + " *                *  " + staticStartTIme + " < " + startTime);

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

        try{
            Integer groupNumber = Integer.valueOf(groupName.substring(groupName.length() - 3));
            System.out.println(groupNumber);
        }catch(Exception ignore){

        }

        for (int i = 0; i < 8; i++) {
            try {
                printTime("DOING       ");
                Thread.sleep(1000);
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
