package com.kdniao.logisticsfront.common.biz.service.impl.thread.new1;

public class MainSynchronized {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        Object o2 = new Object();

        RunClass4 runClass1 = new RunClass4("runClass1", "ss", o);
        RunClass4 runClass2 = new RunClass4("runClass2", "dd", o2);
        Thread t1 = new Thread(runClass1);
        Thread t2 = new Thread(runClass2);
        t1.start();
        Thread.sleep(500);
        t2.start();
        t1.join();
        t2.join();
    }

}

class RunClass4 implements Runnable {
    private static String name;
    private String sex;
    private Object u;

    public RunClass4(String name, String sex, Object u) {
        this.name = name;
        this.sex = sex;
        this.u = u;
    }

    @Override
    public void run() {
        fun();
    }

    public void fun() {
//    public synchronized void fun() { // stop 500
        synchronized (this) // stop 500
//        synchronized (name)  // stop 5000
//        synchronized (sex)  // stop 500
//        synchronized (u)  // stop 500
//        synchronized (Locks.class)  // stop 5000
        {
            System.out.println(name + " - " + Thread.currentThread().getName());
            System.out.println(u.hashCode());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Locks {

}