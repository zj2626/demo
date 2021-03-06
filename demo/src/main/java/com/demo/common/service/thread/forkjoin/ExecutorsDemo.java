package com.demo.common.service.thread.forkjoin;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.stream.LongStream;

public class ExecutorsDemo extends MyExcutor {
   private   List<Params> parameter = new ArrayList<>();

    /**
     * 求和
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        long[] data = LongStream.rangeClosed(1, 10_000_000).toArray();
        int size = 20;
        int pipe = data.length / size;

        ExcutorPoolDemo excutorPool = new ExcutorPoolDemo(this);
        for (int i = 0; i < size; i++) {
            Params params = Params.builder().size(1).data(data).from(i * pipe).to((i + 1) * pipe).build();
            excutorPool.execute(params);
            parameter.add(params);
        }

        int result = 0;
        List<FutureTask> futureList = excutorPool.getFutureList();
        for (FutureTask future : futureList) {
            result = (int) future.get();
        }
        System.out.println("求和 " + result);
    }

    @Override
    public Object doExcute() throws Exception {
        Params param = (Params) parameter.remove(0);
        long[] line = (long[]) param.getData();
        System.out.println(Thread.currentThread().getName() + " => " + param.getFrom() + " " + param.getTo());
        int sum = 0;
        for (int i = param.getFrom(); i < param.getTo(); i++) {
            sum += line[i];
        }
        return sum;
    }
}