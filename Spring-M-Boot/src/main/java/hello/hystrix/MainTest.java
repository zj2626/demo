package hello.hystrix;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MainTest {
    /**
     * 同步调用使用
     */
    @Test
    public void test() {
        HystrixRequest hystrixRequest = new HystrixRequest("aka", "shitGroupNameBitch");
        String result = hystrixRequest.execute();
        System.out.println("HystrixThread > " + result);
        System.out.println("MainThread    > thread:" + Thread.currentThread().getName());

        System.out.println("\n###");
        hystrixRequest = new HystrixRequest("zzz", "shitGroupNameBitch2");
        result = hystrixRequest.execute();
        System.out.println("HystrixThread > " + result);
        System.out.println("MainThread    > thread:" + Thread.currentThread().getName());
    }

    /**
     * 请求缓存 Request-Cache
     */
    @Test
    public void test2() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("zzzfuck", "shitGroupNameBitch001");
            hystrixRequest.execute();
            System.out.println(hystrixRequest.isResponseFromCache());

            HystrixRequest hystrixRequest2 = new HystrixRequest("zzzbitc", "shitGroupNameBitch002");
            hystrixRequest2.execute();
            System.out.println(hystrixRequest2.isResponseFromCache());

            HystrixRequest hystrixRequest3 = new HystrixRequest("zzzbitc", "shitGroupNameBitch003");
            hystrixRequest3.execute();
            System.out.println(hystrixRequest3.isResponseFromCache());

            HystrixRequest hystrixRequest4 = new HystrixRequest("zzzshit", "shitGroupNameBitch003");
            hystrixRequest4.execute();
            System.out.println(hystrixRequest4.isResponseFromCache());
        } finally {
            context.shutdown();
        }

        System.out.println("##########################");
        context = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest4 = new HystrixRequest("zzzshit", "shitGroupNameBitch003");
            hystrixRequest4.execute();
            System.out.println(hystrixRequest4.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }

    /**
     * 异步调用使用
     */
    @Test
    public void test3() {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("gggc", "ewwwwww");
            Future<String> future = hystrixRequest.queue();
            System.out.println("#######0");
            for (int i = 1; i <= 6; i++) {
                try {
                    System.out.println("###" + i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("#######卡住 等待线程执行完毕");
            String result = future.get(5000, TimeUnit.SECONDS);
            System.out.println("HystrixThread > " + result);
//            System.out.println("MainThread    > thread:" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册异步事件回调执行
     */
    @Test
    public void test4() {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("ewww", "weeeee");
            System.out.println("#######1");
            Observable<String> fs = hystrixRequest.observe();
            System.out.println("#######2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("#######3");
            // 注册异步事件回调执行
            fs.subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted > execute onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    // 当产生异常时回调
                    System.err.println("onError > " + e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onNext(String v) {
                    // 获取结果后回调
                    System.out.println("onNext > " + v);
                }
            });
            System.out.println("#######4");
            for (int i = 1; i <= 8; i++) {
                try {
                    System.out.println("###" + i);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
