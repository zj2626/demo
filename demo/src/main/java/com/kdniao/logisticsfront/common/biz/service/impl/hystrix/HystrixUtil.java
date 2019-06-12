package com.kdniao.logisticsfront.common.biz.service.impl.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.HashMap;
import java.util.Map;

public class HystrixUtil {
    private static String name = "defultName";
    private static String groupName = "defultGroup";

    public static String send(String message) {
        return new HystrixNetwork(name, groupName, message).execute();
    }

    private static class HystrixNetwork extends HystrixCommand<String> {
        private final String name;
        private String groupName;
        private String message;

        private ExterfaceInvokeIOHttpSender exterfaceInvokeIOHttpSender;

        protected HystrixNetwork(String name, String groupName, String message) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupName))
                    .andCommandKey(HystrixCommandKey.Factory.asKey(name))
                    .andCommandPropertiesDefaults(
                            HystrixCommandProperties.Setter()
                                    .withExecutionTimeoutEnabled(false)
                                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                    .withExecutionIsolationSemaphoreMaxConcurrentRequests(10000)
                                    .withCircuitBreakerErrorThresholdPercentage(50)
                                    .withCircuitBreakerRequestVolumeThreshold(2)
                                    .withCircuitBreakerSleepWindowInMilliseconds(5000))
            );
            this.name = name;
            this.groupName = groupName;
            this.message = message;

            try {
                this.exterfaceInvokeIOHttpSender = new ExterfaceInvokeIOHttpSender();
                this.exterfaceInvokeIOHttpSender.setHostname("http://127.0.0.1:8073");
                this.exterfaceInvokeIOHttpSender.afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String run() throws Exception {
            System.out.println("RUN---> " + Thread.currentThread().getName() + " * " + groupName);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("message", message);
            String response = null;
            try {
                response = exterfaceInvokeIOHttpSender.sendGet(params, "/message");
            } catch (Exception e) {
                System.err.println("RUN--->ER " + response);
                throw e;
            }
            System.out.println("RUN---> " + response);

            return response;
        }

        // 使用Fallback() 提供降级策略
        @Override
        protected String getFallback() {
            System.out.println("jump to getFallback");

            return "Fallback";
        }
    }
}
