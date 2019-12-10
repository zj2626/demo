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
    private long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

    /**
     * 求和
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(Params.builder().size(1).data(numbers).from(0).to(5).build());
        excutorPool.execute(Params.builder().size(1).data(numbers).from(5).to(10).build());
        excutorPool.execute(Params.builder().size(1).data(numbers).from(10).to(15).build());
        excutorPool.execute(Params.builder().size(1).data(numbers).from(15).to(20).build());
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
        int sum = 0;
        for (int i = param.getFrom(); i < param.getTo(); i++) {
            sum += line[i];
        }
        return sum;
    }
}