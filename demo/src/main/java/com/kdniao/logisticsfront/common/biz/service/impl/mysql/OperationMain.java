package com.kdniao.logisticsfront.common.biz.service.impl.mysql;

import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.mapper.LineitemMapper;
import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.mapper.OrdersMapper;
import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.model.Orders;
import com.kdniao.logisticsfront.common.biz.service.impl.mysql.dal.model.OrdersExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 通过mybatis操作数据库
 */
public class OperationMain implements Runnable {
    public static ApplicationContext applicationContext;
    private static Integer sum = 0;
    private boolean ifWrite = false;

    public OperationMain(boolean ifWrite) {
        this.ifWrite = ifWrite;
    }

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void main(String[] args) throws InterruptedException {
        OperationMain readMain = new OperationMain(false);
        OperationMain writeMain = new OperationMain(true);

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future<?>> futureTasks = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                futureTasks.add(service.submit(readMain));
            } else {
                futureTasks.add(service.submit(writeMain));
            }
            // Thread.sleep(1);
        }
        try {
            for (Future<?> futureTask : futureTasks) {
                futureTask.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) + " --- " + sum);

        service.shutdown();
    }

    @Override
    public void run() {
        LineitemMapper lineitemMapper = applicationContext.getBean(LineitemMapper.class);
        OrdersMapper ordersMapper = applicationContext.getBean(OrdersMapper.class);

        System.out.println(Thread.currentThread().getName());

        long start = System.currentTimeMillis();
        if (!ifWrite) {
            OrdersExample example = new OrdersExample();
            OrdersExample.Criteria criteria = example.createCriteria();

            List<Orders> list = ordersMapper.selectByExample(example);
            System.out.println("list.size " + list.size());
        }

        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " --- " + (end - start) + "\n");
    }
}
