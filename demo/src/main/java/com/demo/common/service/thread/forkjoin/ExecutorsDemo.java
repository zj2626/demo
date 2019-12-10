package com.demo.common.service.thread.forkjoin;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

public class ExecutorsDemo extends MyExcutor {
    private long[] numbers = LongStream.rangeClosed(1, 10_000_000).toArray();

    /**
     * 求和
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        int size = 20;
        int pipe = numbers.length / size;

        excutorPool = new ExcutorPoolDemo(this);
        for (int i = 0; i < size; i++) {
            excutorPool.execute(Params.builder().size(1).data(numbers).from(i * pipe).to((i + 1) * pipe).build());
        }

        int result = 0;
        List<Future> futureList = excutorPool.getFutureList();
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