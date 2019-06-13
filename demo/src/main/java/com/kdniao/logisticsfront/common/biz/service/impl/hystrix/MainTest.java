package com.kdniao.logisticsfront.common.biz.service.impl.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 断路器 Hystrix
 */
public class MainTest {
    private static long beginTime = System.currentTimeMillis();

    /**
     * 同步调用使用
     */
    @Test
    public void test() throws InterruptedException {
        HystrixRequest hystrixRequest = new HystrixRequest("aka", "shitGroupNameBitch", beginTime);
        String result = hystrixRequest.execute();
        System.out.println("Result > " + result);

        System.out.println("\n###");

        HystrixRequest hystrixRequest2 = new HystrixRequest("zzz", "fuckGroupNameBitch2-", beginTime);
        String result2 = hystrixRequest2.execute();
        System.out.println("Result > " + result2);

        end();
    }

    /**
     * 同步调用使用 多线程
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testMoreLine() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10); // 一共10个线程
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureTasks.add(service.submit(new MyExecutor("n" + i, "g" + i, beginTime)));
        }

        for (Future<?> futureTask : futureTasks) {
            futureTask.get();
        }
        service.shutdown();
    }

    /*********************************/

    /**
     * 异步调用使用
     */
    @Test
    public void test3() throws InterruptedException {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("aName", "aGroup", beginTime);
            Future<String> future = hystrixRequest.queue();
            System.out.println("#######0");
            for (int i = 1; i <= 3; i++) {
                try {
                    System.out.println("#######" + i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("#######卡住 等待线程执行完毕");
            String result = future.get(5000, TimeUnit.SECONDS);
            System.out.println("\n###");
            System.out.println("Result > " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        end();
    }

    /**
     * 异步调用使用 多线程
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncMoreLine() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(10); // 一共10个线程
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futureTasks.add(service.submit(new MyExecutorAsync("n" + i, "g" + i, beginTime)));
        }

        for (Future<?> futureTask : futureTasks) {
            futureTask.get();
        }
        service.shutdown();
    }

    /**
     * 异步调用使用 多线程 测试断路器效果
     * 前提: 这里设置26s以内的有请求都超时, 26s以后的都不超时
     * 测试结果:
     * 1. 根据设置的错误百分比,从5s以后getFallback开始,断路器打开,并在5s之内的所有调用都直接getFallback(线程内部的时间统计为0秒左右)
     * 2. 5s之后断路器关闭, 下次请求依然是超时,所以断路器打开,后续同上
     * 3. 当时间过了26s以后, 请求都不超时 等最后一次断路器关闭以后就不会再直接getFallback了
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testAsyncMoreLineAboutCircuitBreaker() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(100); // 一共100个线程
        List<Future<?>> futureTasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i < 10) {
                futureTasks.add(service.submit(new MyExecutorAsync("n0" /*+ String.format("%03d", i)*/, "g0", beginTime)));
            } else {
                futureTasks.add(service.submit(new MyExecutorAsync("n1" , "g0", beginTime)));
            }
            Thread.sleep(1000);
        }

        for (Future<?> futureTask : futureTasks) {
            futureTask.get();
        }
        service.shutdown();
    }

    /*********************************/

    /**
     * 注册异步回调执行 observe
     * <p>
     * 执行observe时候就执行Hystrix, 然后执行subscribe可以订阅并获取其响应信息
     */
    @Test
    public void test4() throws InterruptedException {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("aName", "aGroup", beginTime);
            System.out.println("#######0");
            Observable<String> fs = hystrixRequest.observe();
            for (int i = 1; i <= 4; i++) {
                try {
                    System.out.println("#######" + i);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("#######7");

            // 注册异步事件回调执行
            fs.subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted >>> execute onCompleted\n");
                }

                @Override
                public void onError(Throwable e) {
                    // 当产生异常时回调 (抛出异常时候才执行)
                    System.err.println("onError >>> " + e.getMessage());
                }

                @Override
                public void onNext(String v) {
                    // 获取结果后回调
                    System.out.println("onResult >>> " + v);
                }


            });
            System.out.println("#######8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        end();
    }

    /**
     * 注册异步回调执行 toObservable
     * <p>
     * 返回Observable对象, 当执行订阅的时候才执行hystrix命令并返回其响应
     */
    @Test
    public void test5() throws InterruptedException {
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("aName", "aGroup", beginTime);
            System.out.println("#######0");
            Observable<String> fs = hystrixRequest.toObservable();
            for (int i = 1; i <= 4; i++) {
                try {
                    System.out.println("#######" + i);
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("#######7");

            // 注册异步事件回调执行
            fs.subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {
                    System.out.println("onCompleted >>> execute onCompleted\n");
                }

                @Override
                public void onError(Throwable e) {
                    // 当产生异常时回调 (抛出异常时候才执行)
                    System.err.println("onError >>> " + e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onNext(String v) {
                    // 获取结果后回调
                    System.out.println("onResult >>> " + v);
                }


            });
            System.out.println("#######8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        end();
    }

    /*********************************/

    /**
     * 请求缓存 Request-Cache 需要重写getCacheKey方法 缓存取决于HystrixCommandGroupKey和getCacheKey()的返回值
     */
    @Test
    public void test2() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001", beginTime);
            String result = hystrixRequest.execute();
            System.out.println("Result1 > " + result + " | " + hystrixRequest.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest2 = new HystrixRequest("zzzbitc", "bGroupNameBitch002", beginTime);
            String result2 = hystrixRequest2.execute();
            System.out.println("Result2 > " + result2 + " | " + hystrixRequest2.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest3 = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001", beginTime);
            String result3 = hystrixRequest3.execute();
            System.out.println("Result3 > " + result3 + " | " + hystrixRequest3.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest4 = new HystrixRequest("zzzshit", "dGroupNameBitch003", beginTime);
            String result4 = hystrixRequest4.execute();
            System.out.println("Result4 > " + result4 + " | " + hystrixRequest4.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest5 = new HystrixRequest("zzzfuckYYYY", "aGroupNameBitch001", beginTime);
            String result5 = hystrixRequest5.execute();
            System.out.println("Result5 > " + result5 + " | " + hystrixRequest5.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest6 = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch999", beginTime);
            String result6 = hystrixRequest6.execute();
            System.out.println("Result6 > " + result6 + " | " + hystrixRequest6.isResponseFromCache() + "\n");

            HystrixRequest hystrixRequest7 = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001", beginTime);
            String result7 = hystrixRequest7.execute();
            System.out.println("Result7 > " + result7 + " | " + hystrixRequest7.isResponseFromCache() + "\n");
        } finally {
            context.shutdown();
        }

        System.out.println("##########################");
        HystrixRequestContext context2 = HystrixRequestContext.initializeContext();
        try {
            HystrixRequest hystrixRequest = new HystrixRequest("zzzfuckIIII", "aGroupNameBitch001", beginTime);
            String result = hystrixRequest.execute();
            System.out.println("Result > " + result + " | " + hystrixRequest.isResponseFromCache() + "\n");
        } finally {
            context2.shutdown();
        }
    }

    private void end() throws InterruptedException {
        System.out.println("\n######E");
        Thread.sleep(3000);
        System.out.println("\n#########E : " + System.currentTimeMillis());
    }
}
