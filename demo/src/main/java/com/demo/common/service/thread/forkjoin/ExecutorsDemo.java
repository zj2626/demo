package com.demo.common.service.thread.forkjoin;

import com.demo.common.service.thread.abs.MyExcutor;
import com.demo.common.service.thread.abs.Params;
import com.demo.common.service.thread.abs.ThreadDemo;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ExecutorsDemo extends MyExcutor {
    private int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};

    /**
     * 求和
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        threadExcutor = new ThreadDemo(this);
        threadExcutor.execute(Params.builder().size(1).data(numbers).from(0).to(5).build());
        threadExcutor.execute(Params.builder().size(1).data(numbers).from(5).to(10).build());
        threadExcutor.execute(Params.builder().size(1).data(numbers).from(10).to(15).build());
        threadExcutor.execute(Params.builder().size(1).data(numbers).from(15).to(20).build());
        int result = 0;
        List<Future> futureList = threadExcutor.getFutureList();
        for (Future future : futureList) {
            result = (int) future.get();
        }
        System.out.println("求和 " + result);
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        Params param = (Params) parameter.get("requestParam");
        int[] line = (int[]) param.getData();
        int sum = 0;
        for (int i = param.getFrom(); i < param.getTo(); i++) {
            sum += line[i];
        }
        return sum;
    }
}