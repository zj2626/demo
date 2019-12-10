package com.demo.common.service.thread.forkjoin;

import com.demo.common.service.thread.abs.ForkJoinPoolDemo;
import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

public class ForkJoinDemo extends MyExcutor {
    private long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();

    /**
     * 求和  https://blog.csdn.net/m0_37542889/article/details/92640903
     * <p>
     * * ForkJoinPool
     * * ForkJoinTask
     * * ForkJoinWorkerThread
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int size = 20;
        int pipe = numbers.length / size;

        forkJoinPool = new ForkJoinPoolDemo(this);
        for (int i = 0; i < size; i++) {
            forkJoinPool.execute(Params.builder().size(1).data(numbers).from(i * pipe).to((i + 1) * pipe).build());
        }
        int result = 0;
        List<Future> futureList = forkJoinPool.getFutureList();
        for (Future future : futureList) {
            result = (int) future.get();
        }
        System.out.println("求和 " + result);
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        Params param = (Params) parameter.get("requestParam");
        long[] line = (long[]) param.getData();
        System.out.println(Thread.currentThread().getName() + " => " + param.getFrom() + " " + param.getTo());
        int sum = 0;
        for (int i = param.getFrom(); i < param.getTo(); i++) {
            sum += line[i];
        }
        return sum;
    }
}
