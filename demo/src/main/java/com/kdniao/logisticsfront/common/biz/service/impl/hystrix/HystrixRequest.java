package com.kdniao.logisticsfront.common.biz.service.impl.hystrix;

import com.netflix.hystrix.*;

public class HystrixRequest extends HystrixCommand<String> {
    private final String name;
    private String groupName;
    private long startTime;
    private long staticStartTIme;

    public HystrixRequest(String name, String groupName, long beginTime) {
        // 1.最少配置:指定命令组名(CommandGroup)
//        super(HystrixCommandGroupKey.Factory.asKey(groupName));

        // 2.配置依赖超时时间
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                .withExecutionIsolationThreadTimeoutInMilliseconds(2000)
//                )
//        );

        // 3.依赖分组:CommandGroup   CommandGroup是每个命令最少配置的必选参数
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName)));

        // 4.依赖命名:CommandKey
        // 每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
        // Hystrix提供了两种依赖隔离方式：线程池隔离 和 信号量隔离
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                /*HystrixCommandKey工厂定义依赖名称*/
//                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
//        );

        // 5.线程池/信号:ThreadPoolKey
        // 当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分.
        // 使用线程池的缺点主要是增加了计算的开销。每一个依赖调用都会涉及到队列，调度，上下文切换，而这些操作都有可能在不同的线程中执行。
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
//                /* 使用HystrixThreadPoolKey工厂定义线程池名称 如不设置,Hystrix会为每一个CommandGroup建立一个线程池, 如果这里设置固定值, 则所有的CommandGroup都使用同一个线城池*/
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("qwqeqe"))
//                /* 线程池属性 */
//                .andThreadPoolPropertiesDefaults(
//                        HystrixThreadPoolProperties.Setter()
//                                /* 线程池大小 默认10*/
//                                .withCoreSize(200)
//                                /* 线程池队列大小 默认-1 如果超了就直接执行降级策略:getFallback*/
//                                .withMaxQueueSize(5)
//                )
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                /* 设置fallback的最大并发数 如果超了就直接抛异常*/
//                                .withFallbackIsolationSemaphoreMaxConcurrentRequests(200)
//                                /* 设置启用超时时间 默认true*/
//                                .withExecutionTimeoutEnabled(true)
//                                /* 设置超时时间 默认1000ms*/
//                                .withExecutionTimeoutInMilliseconds(1000)
//                                /* 设置启用断路器(熔断器) 默认true*/
//                                .withCircuitBreakerEnabled(true)
//                                /* 设置错误百分比，超过该值打开断路器 默认50*/
//                                .withCircuitBreakerErrorThresholdPercentage(50)
//                                /* 设置10s中内最少的请求量，大于该值断路器配置才会生效 默认20*/
//                                .withCircuitBreakerRequestVolumeThreshold(5)
//                                /* 短路器打开后多长时间尝试关闭（Half open）默认5s*/
//                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
//                )
//        );

        // 6.信号量隔离:SEMAPHORE (使用信号量则必须等待执行完成以后才能返回调用方, 如果使用线程则不需要等待因为是开启的另一个线程)
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

        // 线程池隔离缺点是带来一定的开销，但不会阻塞请求线程，适合于于IO密集型的任务
        // 信号量隔离使用用户请求线程，没有格外线程切换开销，使用与执行时间和执行逻辑都比较短的本地计算。比如CPU密集型的任务

        this.name = name;
        this.groupName = groupName;
        this.startTime = System.currentTimeMillis();
        this.staticStartTIme = beginTime;

        System.out.println("  构造 " + groupName + " * " + name);

    }

    /*
    * 1):Command 配置
            //使用命令调用隔离方式,默认:采用线程隔离,ExecutionIsolationStrategy.THREAD
            private final HystrixProperty<ExecutionIsolationStrategy> executionIsolationStrategy;
            //使用线程隔离时，调用超时时间，默认:1秒
            private final HystrixProperty<Integer> executionIsolationThreadTimeoutInMilliseconds;
            //线程池的key,用于决定命令在哪个线程池执行
            private final HystrixProperty<String> executionIsolationThreadPoolKeyOverride;
            //使用信号量隔离时，命令调用最大的并发数,默认:10
            private final HystrixProperty<Integer> executionIsolationSemaphoreMaxConcurrentRequests;
            //使用信号量隔离时，命令fallback(降级)调用最大的并发数,默认:10
            private final HystrixProperty<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests;
            //是否开启fallback降级策略 默认:true
            private final HystrixProperty<Boolean> fallbackEnabled;
            // 使用线程隔离时，是否对命令执行超时的线程调用中断（Thread.interrupt()）操作.默认:true
            private final HystrixProperty<Boolean> executionIsolationThreadInterruptOnTimeout;
            // 统计滚动的时间窗口,默认:5000毫秒circuitBreakerSleepWindowInMilliseconds
            private final HystrixProperty<Integer> metricsRollingStatisticalWindowInMilliseconds;
            // 统计窗口的Buckets的数量,默认:10个,每秒一个Buckets统计
            private final HystrixProperty<Integer> metricsRollingStatisticalWindowBuckets; // number of buckets in the statisticalWindow
            //是否开启监控统计功能,默认:true
            private final HystrixProperty<Boolean> metricsRollingPercentileEnabled;
            // 是否开启请求日志,默认:true
            private final HystrixProperty<Boolean> requestLogEnabled;
            //是否开启请求缓存,默认:true
            private final HystrixProperty<Boolean> requestCacheEnabled; // Whether request caching is enabled.
     *
     *
     * 2):Circuit Breaker配置
            // 熔断器在整个统计时间内是否开启的阀值，默认20秒。也就是10秒钟内至少请求20次，熔断器才发挥起作用
            private final HystrixProperty<Integer> circuitBreakerRequestVolumeThreshold;
            //熔断器默认工作时间,默认:5秒.熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
            private final HystrixProperty<Integer> circuitBreakerSleepWindowInMilliseconds;
            //是否启用熔断器,默认true. 启动
            private final HystrixProperty<Boolean> circuitBreakerEnabled;
            //默认:50%。当出错率超过50%后熔断器启动.
            private final HystrixProperty<Integer> circuitBreakerErrorThresholdPercentage;
            //是否强制开启熔断器阻断所有请求,默认:false,不开启
            private final HystrixProperty<Boolean> circuitBreakerForceOpen;
            //是否允许熔断器忽略错误,默认false, 不开启
            private final HystrixProperty<Boolean> circuitBreakerForceClosed;*
     *
     * extends HystrixCollapser
     * 3):命令合并(Collapser)配置
            //请求合并是允许的最大请求数,默认: Integer.MAX_VALUE
            private final HystrixProperty<Integer> maxRequestsInBatch;
            //批处理过程中每个命令延迟的时间,默认:10毫秒
            private final HystrixProperty<Integer> timerDelayInMilliseconds;
            //批处理过程中是否开启请求缓存,默认:开启
            private final HystrixProperty<Boolean> requestCacheEnabled;
     *
     *
     * 4):命令合并(Collapser)配置
            // 配置线程池大小,默认值10个.
            // 建议值:请求高峰时99.5%的平均响应时间 + 向上预留一些即可
            HystrixThreadPoolProperties.Setter().withCoreSize(int value)
            // 配置线程值等待队列长度,默认值:-1
            // 建议值:-1表示不等待直接拒绝,测试表明线程池使用直接决绝策略+ 合适大小的非回缩线程池效率最高.所以不建议修改此值。
            // 当使用非回缩线程池时，queueSizeRejectionThreshold,keepAliveTimeMinutes 参数无效
            HystrixThreadPoolProperties.Setter().withMaxQueueSize(int value)
    *
    * */

    private String getTime() {
        return " 线程执行时间:<" + (System.currentTimeMillis() - startTime) + "> ";
    }


    private String getTotalTime() {
        return " 执行总时间:<" + getTotal() + ">";
    }

    private long getTotal() {
        return System.currentTimeMillis() - staticStartTIme;
    }

    @Override
    protected String run() {
        System.out.println("  RUN STA -> " + Thread.currentThread().getName() + " * " + groupName + " * " + name + " : " + getTotalTime());

        // 处理 延迟 1
//        for (int i = 0; i < 15; i++) {
//            try {
//                Thread.sleep(200); // 150 200 500 1000
//                System.out.println("  running -> " + groupName + " " + i + " : " + getTotalTime() + getTime());
//
//            } catch (Exception e) {
//                System.err.println("  " + e.getMessage() + " : " + getTotalTime() + getTime());
//            }
//        }

        // 处理 延迟 2
        // Integer groupNumber = Integer.valueOf(groupName.substring(groupName.length() - 3));
        if (getTotal() < 26000) {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(600);
                } catch (Exception ignore) {

                }
            }
        }

        return "  Hello group:" + groupName + ", name:" + name;
    }

    // 使用Fallback() 提供降级策略 如果不实现getFallback则调用父类(抛异常, onError可捕获)
    // 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
    // HystrixBadRequestException用在非法参数或非系统故障异常等不应触发回退逻辑的场景。
    @Override
    protected String getFallback() {
        System.out.println(" getFallback " + Thread.currentThread().getName() + " : " + getTotalTime() + getTime());
        return "jump into fall >----> " + Thread.currentThread().getName();
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
