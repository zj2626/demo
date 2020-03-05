package com.demo.common.service.mysql;

import com.demo.common.service.mysql.dal.mapper.OrdersMapper;
import com.demo.common.service.mysql.dal.model.Orders;
import com.demo.common.service.mysql.dal.model.OrdersExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(OperationMain.class);

    public static ApplicationContext applicationContext;
    private static Integer sum = 0;

    static {
        /*
         * 手动的方式调用, 开启生成mybatis的sql日志(必须在spring初始化之前执行); 也可以通过配置文件开启
         * */
        //        org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
        //        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
        //        org.apache.ibatis.logging.LogFactory.useJdkLogging();
        //        org.apache.ibatis.logging.LogFactory.useCommonsLogging();
        //        org.apache.ibatis.logging.LogFactory.useStdOutLogging();

        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-mybatis.xml");
    }

    public static void main(String[] args) {
        OperationMain operationMain = new OperationMain();

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Future<?>> futureTasks = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            futureTasks.add(service.submit(operationMain));
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
        //        LineitemMapper lineitemMapper = applicationContext.getBean(LineitemMapper.class);
        OrdersMapper ordersMapper = applicationContext.getBean(OrdersMapper.class);

        logger.info(Thread.currentThread().getName());

        long start = System.currentTimeMillis();
        OrdersExample example = new OrdersExample();
        OrdersExample.Criteria criteria = example.createCriteria();
        criteria.andOTotalpriceEqualTo(100D);

        List<Orders> list = ordersMapper.selectByExample(example);

        long end = System.currentTimeMillis();
        logger.debug(Thread.currentThread().getName() + " -> 结果: " + list.size() + " | -> 时间:" + (end - start));
        logger.info(Thread.currentThread().getName() + " -> 结果: " + list.size() + " | -> 时间:" + (end - start));
    }
}
