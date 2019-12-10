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
    private long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

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
        forkJoinPool = new ForkJoinPoolDemo(this);
        forkJoinPool.execute(Params.builder().size(1).data(numbers).from(0).to(5).build());
        forkJoinPool.execute(Params.builder().size(1).data(numbers).from(5).to(10).build());
        forkJoinPool.execute(Params.builder().size(1).data(numbers).from(10).to(15).build());
        forkJoinPool.execute(Params.builder().size(1).data(numbers).from(15).to(20).build());
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
        int sum = 0;
        for (int i = param.getFrom(); i < param.getTo(); i++) {
            sum += line[i];
        }
        return sum;
    }
}
