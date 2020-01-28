package hello.control;

import hello.annotation.MovieRecommender;
import hello.annotation.SimpleMovieLister;
import hello.hystrix.HystrixUtil;
import hello.request.ExterfaceInvokeIOHttpSender;
import hello.service.DoHSomething;
import hello.service.DoSendKafka;
import hello.service.DoWithAnnotation;
import hello.service.model.KafkaRequest;
import hello.service.model.RedisRequest;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class DoSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSomething.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");

    @Autowired
    private SimpleMovieLister simpleMovieLister;

    @Autowired
    private MovieRecommender movieRecommender2;

    public DoSomething() {
        logger.info("构造造 DoSomething");
    }

    private void doMakeLog() {
        System.out.println("do simpleMovieLister: " + (simpleMovieLister != null));
        System.out.println("do movieRecommender2: " + (movieRecommender2 != null));

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
