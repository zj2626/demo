package hello.hystrix;

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
    }

    @Override
    protected String run() {
        System.out.println("RUN---> " + Thread.currentThread().getName() + " * " + groupName);

        // 处理 延迟
//        for (int i = 1; i <= 3; i++) {
//            try {
//                Thread.sleep(1000);
//                System.out.println("--------RUN-- " + groupName + " " + i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        return "thread:" + Thread.currentThread().getName() + ", Hello " + name;
    }

    // 使用Fallback() 提供降级策略
    @Override
    protected String getFallback() {
        System.out.println("jump to getFallback >>>>>> ");
        return new FallbackViaNetwork(name, groupName).execute();
    }

    //重写getCacheKey方法,实现区分不同请求的逻辑
//    @Override
//    protected String getCacheKey() {
//        return String.valueOf(groupName);
//    }

    private static class FallbackViaNetwork extends HystrixCommand<String> {
        private final String name;
        private String groupName;

        protected FallbackViaNetwork(String name, String groupName) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                    // 使用不同的线程池做隔离，防止上层线程池跑满，影响降级逻辑.
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FuckWorldPool"))
            );
            this.name = name;
            this.groupName = groupName;
        }

        @Override
        protected String run() {
            System.out.println("FallbackViaNetwork RUN---> " + Thread.currentThread().getName() + " * " + groupName);
            return "thread:" + Thread.currentThread().getName() + ", Fallback " + name;
        }
    }
}
