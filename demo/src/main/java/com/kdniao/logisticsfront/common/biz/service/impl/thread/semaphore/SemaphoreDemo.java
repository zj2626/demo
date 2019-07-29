package com.kdniao.logisticsfront.common.biz.service.impl.thread.semaphore;

import com.kdniao.logisticsfront.common.biz.service.impl.thread.semaphore.entity.Playground;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    static class Student implements Runnable {

        private int num;
        private Playground playground;

        public Student(int num, Playground playground) {
            this.num = num;
            this.playground = playground;
        }

        @Override
        public void run() {

            try {
                //获取跑道
                Playground.Track track = playground.getTrack();
                if (track != null) {
                    System.out.println("学生" + num + "在" + track.toString() + "上跑步");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(" - - 学生" + num + "释放" + track.toString());
                    //释放跑道
                    playground.releaseTrack(track);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {

        Executor executor = Executors.newCachedThreadPool();
        Playground playground = new Playground();
        for (int i = 0; i < 20; i++) {
            executor.execute(new Student(i+1, playground));
        }

    }

}
