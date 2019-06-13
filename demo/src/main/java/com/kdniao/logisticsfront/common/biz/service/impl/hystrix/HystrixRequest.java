package com.kdniao.logisticsfront.common.biz.service.impl.hystrix;

import com.netflix.hystrix.*;

public class HystrixRequest extends HystrixCommand<String> {
    private final String name;
    private String groupName;

    public HystrixRequest(String name, String groupName) {
        // 1.最少配置:指定命令组名(CommandGroup)
//        super(HystrixCommandGroupKey.Factory.asKey(groupName));

        // 2.配置依赖超时时间
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                .withExecutionIsolationThreadTimeoutInMilliseconds(4000))
//        );

        // 3.依赖命名:CommandKey
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                /*HystrixCommandKey工厂定义依赖名称*/
//                .andCommandKey(
//                        HystrixCommandKey.Factory.asKey(name))
//        );

        // 4.依赖分组:CommandGroup
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName)));

        // 5.线程池/信号:ThreadPoolKey
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
//                /* 使用HystrixThreadPoolKey工厂定义线程池名称*/
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));

        // 6.信号量隔离:SEMAPHORE
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                /* 配置信号量隔离方式,默认采用线程池隔离 */
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE))
        );

        // Demo
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
//                .andCommandKey(HystrixCommandKey.Factory.asKey(name))
//                .andCommandPropertiesDefaults(
//                        HystrixCommandProperties.Setter()
//                                .withExecutionTimeoutEnabled(false)
//                                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
//                                .withExecutionIsolationSemaphoreMaxConcurrentRequests(10000)
//                                .withCircuitBreakerErrorThresholdPercentage(50)
//                                .withCircuitBreakerRequestVolumeThreshold(2)
//                                .withCircuitBreakerSleepWindowInMilliseconds(5000))
//        );

        this.name = name;
        this.groupName = groupName;

        System.out.println("构造 " + groupName + " * " + name);

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
     *
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

    * */


    @Override
    protected String run() {
        System.out.println("RUN---> " + Thread.currentThread().getName() + " * " + groupName + " * " + name);

        // 处理 延迟
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
                System.out.println("running-- " + groupName + " " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "thread:" + Thread.currentThread().getName() + ", Hello " + name;
    }

    // 使用Fallback() 提供降级策略
    @Override
    protected String getFallback() {
        System.out.println("getFallback");
        return "jump to getFallback >>>>>> " + Thread.currentThread().getName();
    }

    //重写getCacheKey方法,实现区分不同请求的逻辑
//    @Override
//    protected String getCacheKey() {
//        return groupName + "_" + name;
//    }
}
