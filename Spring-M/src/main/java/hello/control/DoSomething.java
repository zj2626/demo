package hello.control;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import hello.annotation.MovieRecommender;
import hello.annotation.SimpleMovieLister;
import hello.service.DoHSomething;
import hello.service.DoWithAnnotation;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class DoSomething {
    /*  不需要@Autowired因为设置了default-autowire="byName" 但是需要setter方法 */
    private RedisTemplate<String, Object> throttlingRedisTemplate;
    private DoHSomething doHSomething;
    private DoWithAnnotation doWithAnnotation;

    private SimpleMovieLister simpleMovieLister;
    private MovieRecommender movieRecommender2;

    public DoSomething() {
        System.out.println("构造造 DoSomething");
    }

    public void setThrottlingRedisTemplate(RedisTemplate<String, Object> throttlingRedisTemplate) {
        this.throttlingRedisTemplate = throttlingRedisTemplate;
    }

    public void setSimpleMovieLister(SimpleMovieLister simpleMovieLister) {
        this.simpleMovieLister = simpleMovieLister;
    }

    public void setDoHSomething(DoHSomething doHSomething) {
        this.doHSomething = doHSomething;
    }

    public void setDoWithAnnotation(DoWithAnnotation doWithAnnotation) {
        this.doWithAnnotation = doWithAnnotation;
    }

    public void setMovieRecommender2(MovieRecommender movieRecommender2) {
        this.movieRecommender2 = movieRecommender2;
    }

    public boolean dosome(Boolean same) {
        System.out.println("do redis throttlingRedisTemplate: " + (throttlingRedisTemplate != null));
        System.out.println("do redis simpleMovieLister: " + (simpleMovieLister != null));
        System.out.println("do redis movieRecommender2: " + (movieRecommender2 != null));
        System.out.println("do redis doHSomething: " + (doHSomething != null));
        System.out.println("do redis doWithAnnotation: " + (doWithAnnotation != null));

        if (null != throttlingRedisTemplate) {
            try {
                throttlingRedisTemplate.opsForValue().set("a", "B");
                System.out.println("---------------------------");

                throttlingRedisTemplate.execute(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(RedisConnection connection) throws DataAccessException {
                        connection.openPipeline();

                        connection.zCount("route_pulling_queue_00".getBytes(), 0, 1552036878499L);

                        List<Object> result = connection.closePipeline();
                        System.out.println("_______");
                        System.out.println(result);
                        return null;
                    }
                });

                throttlingRedisTemplate.execute(new SessionCallback<String>() {
                    @Override
                    public <K, V> String execute(RedisOperations<K, V> operations) throws DataAccessException {
                        RedisOperations<String, Object> redisOperations = (RedisOperations<String, Object>) operations;
                        Long result = redisOperations.opsForZSet().count("route_pulling_queue_00", 0, 1552036878499L);
                        System.out.println("_______");
                        System.out.println(result);
                        return null;
                    }
                });

                System.out.println("getting message >>>> begin");
                String thing = doHSomething.sayHello("spring-m");
                System.out.println("getting message >>>> end");
                System.out.println("get message >>>> " + thing);

                System.out.println(">>>>>>>>>>>");
                System.out.println(">>>>>>>>>>>");
                System.out.println(">>>>>>>>>>>");

                System.out.println("wait for getting Future, 卡在这里等待获得异步返回结果");
                Future<String> future = RpcContext.getContext().getFuture();
                if (future != null) {
                    thing = future.get();
                    System.out.println("finally get message >>>> " + thing + " from Future");
                }

                System.out.println(">>>>>>>>>>>");
                System.out.println(">>>>>>>>>>>");
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
                for (int n = 0; n < 1000; n++) { // 改为1001则下面的测试则会调用接口而不是使用缓存 (缓存的最大量是1000)
                    String pre = null;
                    for (int i = 0; i < 10; i++) {
                        String result = doWithAnnotation.sayFuck(String.valueOf(n));
                        System.out.print(n + " ");
                        if (pre != null && !pre.equals(result)) {
                            System.err.println("n=" + n + " ERROR: " + result);
                        }
                        pre = result;
                    }
                    System.out.println("\n" + pre);
                }

                System.out.println("测试LRU有移除最开始的一个缓存项");
                doWithAnnotation.sayFuck("0");

                System.out.println("test dubbo request");
                System.out.println(doHSomething.sayShit("0-0-0-0"));

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
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" +
                    "  getting test kafka");

            String thing2 = doHSomething.sayFuckToKafka("abc");
            System.out.println("\n调用发消息结束 >>>> end " + thing2);

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true){
//                        doHSomething.sayFuckToKafka("def");
//                    }
//                }
//            }).start();


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
