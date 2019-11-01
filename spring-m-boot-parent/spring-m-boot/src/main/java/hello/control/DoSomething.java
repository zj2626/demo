package hello.control;

import hello.annotation.MovieRecommender;
import hello.annotation.SimpleMovieLister;
import hello.hystrix.HystrixUtil;
import hello.request.ExterfaceInvokeIOHttpSender;
import hello.service.DoHSomething;
import hello.service.DoSendKafka;
import hello.service.DoWithAnnotation;
import hello.util.MQTTUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class DoSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSomething.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");

    @Autowired
    private MQTTUtil mqtt;

    @Autowired
    private Jedis jedis;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Reference(group = "${dubbo.consumer.group}")
    private DoHSomething doHSomething;

    @Reference(group = "${dubbo.consumer.group}")
    private DoSendKafka doSendKafka;

    @Reference(group = "${dubbo.consumer.group}", cache = "true")
    private DoWithAnnotation doWithAnnotation;

    @Autowired
    private SimpleMovieLister simpleMovieLister;

    @Autowired
    private MovieRecommender movieRecommender2;

    @Autowired
    private ExterfaceInvokeIOHttpSender exterfaceInvokeIOHttpSender;

    public DoSomething() {
        logger.info("构造造 DoSomething");
    }

    public boolean dodubbo() {
        doMakeLog();

        System.out.println("do  doHSomething: " + (doHSomething != null));
        System.out.println("do  doWithAnnotation: " + (doWithAnnotation != null));

        try {
            RpcContext rpcContext = RpcContext.getContext();

            System.out.println(rpcContext == null);

            //################################## 同步
            System.out.println("\n同步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");

            rpcContext.set("aa", "a1");
            rpcContext.setAttachment("bb", "b1");
            rpcContext.set(Constants.NAME, "zj2626");
            rpcContext.setUrl(URL.valueOf("www.baidu.com"));

            for (int i = 0; i < 3; i++) {
                String res = doHSomething.remoteToDubboSync("user " + i);
                System.out.println("同步请求: " + res);
            }
            System.out.println("同步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");

            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide2 = rpcContext.isConsumerSide();
            String serverIP2 = rpcContext.getRemoteHost();
            URL rpcContextUrl2 = rpcContext.getUrl();
            System.out.println("-------------- a");

            //################################## 异步
            System.out.println("\n异步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");

            rpcContext.set("cc", "c1");
            rpcContext.setAttachment("dd", "d1");
            rpcContext.set(Constants.NAME, "zj2626");
            rpcContext.setUrl(URL.valueOf("www.baidu.com"));

            doHSomething.remoteToDubboAsync("user " + 0);
            doHSomething.remoteToDubboAsync("user " + 1);
            System.out.println("异步请求 doing...");

            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide3 = rpcContext.isConsumerSide();
            String serverIP3 = rpcContext.getRemoteHost();
            URL rpcContextUrl3 = rpcContext.getUrl();
            System.out.println("-------------- b");

            System.out.println("wait for getting Future, 卡在这里等待获得异步返回结果");
            Future<String> future = rpcContext.getFuture();
            if (future != null) {
                String thing = future.get();
                System.out.println("\n异步请求 " + thing);
            }
            System.out.println("异步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");

            boolean isConsumerSide4 = rpcContext.isConsumerSide();
            String serverIP4 = rpcContext.getRemoteHost();
            URL rpcContextUrl4 = rpcContext.getUrl();
            System.out.println("-------------- c");

            System.out.println("RPC a -> " + serverIP2 + " > " + isConsumerSide2 + " > " + rpcContextUrl2);
            System.out.println("RPC b -> " + serverIP3 + " > " + isConsumerSide3 + " > " + rpcContextUrl3);
            System.out.println("RPC c -> " + serverIP4 + " > " + isConsumerSide4 + " > " + rpcContextUrl4);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dodubbo2() {
        //        System.out.println("\n回声测试开始 >>>>>>>>>>>>>");
        //        EchoService echoService = (EchoService) doWithAnnotation;
        //        String status = (String) echoService.$echo("OK\n");
        //        System.out.println(status);
        //        System.out.println("\n回声测试完毕 <<<<<<<<<<<<<");

        System.out.println("测试缓存 缓存的最大量是1000");
        // 改为1001则下面的测试则会调用接口而不是使用缓存 (缓存的最大量是1000)
        for (int n = 0; n < 1000; n++) {
            int i;
            // 每个相同参数调用2次,第二次会直接走缓存不会调用到外部服务
            for (i = 0; i < 2; i++) {
                String result = doWithAnnotation.sayFuck(String.valueOf(n));
                System.out.printf("第%d个 第%d次 获得结果: %s\n", n + 1, i + 1, result);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("测试LRU有移除最开始的一个缓存项 执行时间: " + LocalDateTime.now());
        doWithAnnotation.sayFuck(String.valueOf(0));
        doWithAnnotation.sayFuck(String.valueOf(1));

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

    public boolean dokafka(String name) {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test kafka");

            String thing2 = doSendKafka.remoteToKafka(name);
            System.out.println("\n调用发消息结束 >>>> end " + thing2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void kafkaCustomProducer(String name) {
        doSendKafka.kafkaCustomProducer(name);
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

    public boolean domqtt(String topic, String message, int qos) {
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test domqtt");

            mqtt.publish(topic, message, qos);
            System.out.println("\n调用发消息结束 >>>> end ");
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
