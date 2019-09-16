package hello.control;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import hello.annotation.MovieRecommender;
import hello.annotation.SimpleMovieLister;
import hello.hystrix.HystrixUtil;
import hello.request.ExterfaceInvokeIOHttpSender;
import hello.service.DoHSomething;
import hello.service.DoWithAnnotation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

@Service
public class DoSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSomething.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");
    
    @Autowired
    private Jedis jedis;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DoHSomething doHSomething;
    @Autowired
    private DoWithAnnotation doWithAnnotation;
    @Autowired
    private SimpleMovieLister simpleMovieLister;
    @Autowired
    private MovieRecommender movieRecommender2;
    @Autowired
    private ExterfaceInvokeIOHttpSender exterfaceInvokeIOHttpSender;
    
    public DoSomething() {
        System.out.println("构造造 DoSomething");
    }
    
    public boolean dodubbo() {
        doMakeLog();
        
        System.out.println("do  doHSomething: " + (doHSomething != null));
        System.out.println("do  doWithAnnotation: " + (doWithAnnotation != null));
        
        try {
            RpcContext rpcContext = RpcContext.getContext();
            
            rpcContext.set("aa", "bb");
//            rpcContext.set(Constants.REQUESTID_KEY, UUID.randomUUID().toString());
            rpcContext.setAttachment("cc", "dd");
            rpcContext.setUrl(URL.valueOf("www.baidu.com"));
            
            System.out.println(rpcContext.getUrl());
            boolean isConsumerSide = rpcContext.isConsumerSide();
            String serverIP = rpcContext.getRemoteHost();
            String requestId = ""/*(String) RpcContext.getContext().get(Constants.REQUESTID_KEY)*/;
            String timestamp = "";
            System.out.println("-------------- 1");
            
            //################################## 同步
            System.out.println("\n同步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");
            for (int i = 0; i < 10; i++) {
                String res = doHSomething.remoteToDubboSync("user " + i);
                System.out.println("同步请求: " + res);
            }
            System.out.println("同步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");
            
            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide2 = rpcContext.isConsumerSide();
            String serverIP2 = rpcContext.getRemoteHost();
            String requestId2 = ""/*(String) RpcContext.getContext().get(Constants.REQUESTID_KEY)*/;
            String timestamp2 = rpcContext.getUrl().getParameter("timestamp");
            System.out.println("-------------- 2");
            
            //################################## 异步
            System.out.println("\n异步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");
            doHSomething.remoteToDubboAsync("user " + 0);
            doHSomething.remoteToDubboAsync("user " + 1);
            System.out.println("异步请求 doing...");
            
            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide3 = rpcContext.isConsumerSide();
            String serverIP3 = rpcContext.getRemoteHost();
            String requestId3 = ""/*(String) RpcContext.getContext().get(Constants.REQUESTID_KEY)*/;
            String timestamp3 = rpcContext.getUrl().getParameter("timestamp");
            System.out.println("-------------- 3");
            
            System.out.println("wait for getting Future, 卡在这里等待获得异步返回结果");
            Future<String> future = rpcContext.getFuture();
            if (future != null) {
                String thing = future.get();
                System.out.println("\n异步请求 " + thing);
            }
            System.out.println("异步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");
            
            boolean isConsumerSide4 = rpcContext.isConsumerSide();
            String serverIP4 = rpcContext.getRemoteHost();
            String requestId4 = ""/*(String) RpcContext.getContext().get(Constants.REQUESTID_KEY)*/;
            String timestamp4 = rpcContext.getUrl().getParameter("timestamp");
            
            System.out.println("RPC 1 -> " + requestId  + " > " + serverIP  + " > " + isConsumerSide  + " > " + timestamp);
            System.out.println("RPC 2 -> " + requestId2 + " > " + serverIP2 + " > " + isConsumerSide2 + " > " + timestamp2);
            System.out.println("RPC 3 -> " + requestId3 + " > " + serverIP3 + " > " + isConsumerSide3 + " > " + timestamp3);
            System.out.println("RPC 4 -> " + requestId4 + " > " + serverIP4 + " > " + isConsumerSide4 + " > " + timestamp4);
            System.out.println("-------------- 4");
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean dodubbo2(Boolean same) {
        System.out.println(">>>>>>>>>>>");
        
        String msg;
        String info;
        if (null != same && !same) {
            info = new Random(123).toString();
            msg = doWithAnnotation.sayFuck(info);
        } else {
            info = "0";
            msg = doWithAnnotation.sayFuck(info);
        }
        System.out.println("doWithAnnotation get message >>>> " + msg + " from " + info);
        
        System.out.println("\n回声测试开始");
        EchoService echoService = (EchoService) doWithAnnotation;
        String status = (String) echoService.$echo("OK\n");
        System.out.println(status);
        System.out.println("\n回声测试完毕");
        
        System.out.println("测试缓存 缓存的最大量是1000");
        for (int n = 0; n < 2; n++) { // 改为1001则下面的测试则会调用接口而不是使用缓存 (缓存的最大量是1000)
            String pre = null;
            for (int i = 0; i < 10; i++) {
                String result = doWithAnnotation.sayFuck(String.valueOf(n));
                System.out.println(n);
                if (pre != null && !pre.equals(result)) {
                    System.err.println("n=" + n + " ERROR: " + result);
                }
                pre = result;
            }
            System.out.println(pre);
        }
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("测试LRU有移除最开始的一个缓存项");
        doWithAnnotation.sayFuck("0");
        
        return true;
    }
    
    public boolean doredis() {
        System.out.println("do redis redisTemplate: " + (redisTemplate != null));
        System.out.println("do redis jedis: " + (jedis != null));
        
        if (null != redisTemplate) {
            try {
                redisTemplate.opsForValue().set("a", "B");
                System.out.println("---------------------------");
                jedis.zadd("queue_00", 1, "a");
                jedis.zadd("queue_00", 2, "b");
                jedis.zadd("queue_00", 3, "c");
                jedis.zadd("queue_00", 3, "d");
                System.out.println("---------------------------");
                redisTemplate.execute(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();
                        
                        connection.zCount("queue_00".getBytes(), 0, 1552036878499L);
                        
                        List<Object> result = connection.closePipeline();
                        System.out.println("_______");
                        System.out.println(result);
                        return null;
                    }
                });
                
                redisTemplate.execute(new SessionCallback<String>() {
                    @Override
                    public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                        RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
                        Long result = redisOperations.opsForZSet().count("queue_00", 0, 1552036878499L);
                        System.out.println("_______");
                        System.out.println(result);
                        return null;
                    }
                });
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        return false;
    }
    
    public boolean dokafka() {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test kafka");
            
            String thing2 = doHSomething.remoteToKafka("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean dorabbitmq() {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test rabbitmq");
            
            String thing2 = doHSomething.remoteToRabbitmq("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean doactivemq() {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test activemq");
            
            String thing2 = doHSomething.remoteToActivemq("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /*httpRequest*/
    public String doHttpRequest(String message) {
        System.out.println("do simpleMovieLister: " + (simpleMovieLister != null));
        System.out.println("do movieRecommender2: " + (movieRecommender2 != null));
        
        message = StringUtils.isNotEmpty(message) ? message : "defultFuck";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("message", message);
        String response = null;
        try {
            response = exterfaceInvokeIOHttpSender.sendGet(params, "/message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response);
        
        return response;
    }
    
    /*hystrix httpRequest*/
    public String doHystrixHttpRequest(String message) {
        message = StringUtils.isNotEmpty(message) ? message : "defultFuck";
        
        String response = HystrixUtil.send(message);
        System.out.println("HystrixThread > " + response);
        System.out.println("MainThread    > " + Thread.currentThread().getName());
        
        return response;
    }
    
    private void doMakeLog() {
        /*测试logback*/

//        long len = 1024;
//        for (int i = 0; i < len; i++) {
//            logger.error("m" + i);
//        }
        
        logger.error("[logger] 这是一个错误信息");
        
        /*level="INFO"*/
        logger2.debug("[logger2] sm.test");
        logger2.info("[logger2] sm.test");
        logger2.error("[logger2] sm.test");
        
        /*level="ERROR"*/
        logger3.debug("[logger3] sm.err"); // 日志级别是ERROR 所以info信息保存
        logger3.info("[logger3] sm.err"); // 日志级别是ERROR 所以info信息保存
        logger3.error("[logger3] sm.err");
        
        /*level="DEBUG"*/
        logger4.debug("[logger4] sm.web");
        logger4.info("[logger4] sm.web");
        logger4.error("[logger4] sm.web");
        
        /* TRACE < DEBUG < INFO < WARN < ERROR */
        logger.trace("Trace Message!");
        logger.debug("Debug Message!");
        logger.info("Info Message!");
        logger.warn("Warn Message!");
        logger.error("Error Message!");
    }
    
    public void testError() {
        doMakeLog();
        
        int k = 200 / 0;
        
        logger.info("DONE");
    }
}
