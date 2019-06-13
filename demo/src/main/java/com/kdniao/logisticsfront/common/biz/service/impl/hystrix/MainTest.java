package com.kdniao.logisticsfront.common.biz.service.impl.hystrix;

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
        System.out.println("Result > " + result);

        System.out.println("\n###");

        HystrixRequest hystrixRequest2 = new HystrixRequest("zzz", "fuckGroupNameBitch2");
        String result2 = hystrixRequest2.execute();
        System.out.println("Result > " + result2);
    }

    /**
     * 请求缓存 Request-Cache 需要继承getCacheKey方法
     */
    @Test
    public void test2() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001");
            hystrixRequest.execute();
            System.out.println(hystrixRequest.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest2 = new HystrixRequest("zzzbitc", "bGroupNameBitch002");
            hystrixRequest2.execute();
            System.out.println(hystrixRequest2.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest3 = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001");
            hystrixRequest3.execute();
            System.out.println(hystrixRequest3.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest4 = new HystrixRequest("zzzshit", "dGroupNameBitch003");
            hystrixRequest4.execute();
            System.out.println(hystrixRequest4.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest5 = new HystrixRequest("zzzfuckYYYY", "aGroupNameBitch001");
            hystrixRequest5.execute();
            System.out.println(hystrixRequest5.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest6 = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001");
            hystrixRequest6.execute();
            System.out.println(hystrixRequest6.isResponseFromCache() + "\n");
        } finally {
            context.shutdown();
        }

        System.out.println("##########################");
        HystrixRequestContext context2 = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001");
            hystrixRequest.execute();
            System.out.println(hystrixRequest.isResponseFromCache() + "\n");
        } finally {
            context2.shutdown();
        }
    }

    /**
     * 异步调用使用 ????
     */
    @Test
    public void test3() {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("aName", "aGroup");
            Future<String> future = hystrixRequest.queue();
            System.out.println("#######0");
//            for (int i = 1; i <= 2; i++) {
//                try {
//                    System.out.println("###" + i);
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            System.out.println("#######卡住 等待线程执行完毕");
            String result = future.get(5000, TimeUnit.SECONDS);
            System.out.println("Result > " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册异步事件回调执行 ????
     */
    @Test
    public void test4() {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("aName", "aGroup");
            System.out.println("#######1");
            Observable<String> fs = hystrixRequest.observe();
            System.out.println("#######2");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
                    System.out.println("<<<" + i);
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
