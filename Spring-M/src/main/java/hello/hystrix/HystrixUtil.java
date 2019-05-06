package hello.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import hello.request.ExterfaceInvokeIOHttpSender;

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

            System.out.println(null != exterfaceInvokeIOHttpSender);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("message", message);

            String response = exterfaceInvokeIOHttpSender.sendGet(params, "/message");
            System.out.println("RUN---> " + response);

            if(null == response){
                throw new Exception("请求异常");
            }

            return response;
        }

        // 使用Fallback() 提供降级策略
        @Override
        protected String getFallback() {
            System.out.println("jump to getFallback >>>>>> E");

            return "Fallback";
        }
    }
}
