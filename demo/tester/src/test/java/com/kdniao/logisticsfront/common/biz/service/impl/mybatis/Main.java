package com.kdniao.logisticsfront.common.biz.service.impl.mybatis;

import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.mapper.UcAreaDOMapper;
import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.UcAreaDO;
import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.UcAreaDOExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main implements Runnable {
    public static ApplicationContext applicationContext;
    private static Integer sum = 0;
    private boolean ifWrite = false;

    public Main(boolean ifWrite) {
        this.ifWrite = ifWrite;
    }

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static void main(String[] args) throws InterruptedException {
        Main readMain = new Main(false);
        Main writeMain = new Main(true);

        ExecutorService service = Executors.newFixedThreadPool(50);
        List<Future<?>> futureTasks = new ArrayList<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                futureTasks.add(service.submit(readMain));
            } else {
                futureTasks.add(service.submit(writeMain));
            }
            Thread.sleep(1);
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
        UcAreaDOMapper ucAreaDOMapper = (UcAreaDOMapper) applicationContext.getBean("areaDOMapper");

        System.out.println(Thread.currentThread().getName());

        long start = System.currentTimeMillis();
        if (!ifWrite) {
            UcAreaDOExample example = new UcAreaDOExample();
            UcAreaDOExample.Criteria criteria = example.createCriteria();

            List<UcAreaDO> list = ucAreaDOMapper.selectByExample(example);
            System.out.println("list.size " + list.size());
        } else {
            UcAreaDO record = new UcAreaDO();
            record.setId("10007.0");
            record.setAreaCode("231282022");
            record.setAreaName(UUID.randomUUID().toString());

            int updateInt = ucAreaDOMapper.updateByPrimaryKey(record);
            System.out.println("updateInt " + updateInt);
        }

        long end = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " --- " + (end - start) + "\n");
    }
}
