package com.kdniao.logisticsfront.common.biz.service.impl.thread.wait.sleep.join;

public class MainSuspend {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        Object o2 = new Object();

        RunClass2 runClass1 = new RunClass2(o);
        RunClass2 runClass2 = new RunClass2(o);
        Thread t1 = new Thread(runClass1);
        Thread t2 = new Thread(runClass2);

        t1.start();
        t2.start();

        Thread.sleep(2000);
        System.out.println("\nresume t1");
        t1.resume();
        Thread.sleep(500);
        System.out.println("\nresume t2");
        t2.resume(); // 如果不使用sleep暂停则会先执行resume导致第二个线程获得锁之后挂起而无法继续执行

        t1.join();
        t2.join();
    }

    static class RunClass2 implements Runnable {
        private Object u;

        public RunClass2(Object u) {
            this.u = u;
        }

        @Override
        public void run() {
            System.out.println("Before " + Thread.currentThread().getName());
            synchronized (u) { // suspend之后依然不会释放锁资源->对象u,所以会卡这里
                System.out.println("Before in " + Thread.currentThread().getName() + "====" + u.hashCode());
                Thread.currentThread().suspend();
                System.out.println("After in " + Thread.currentThread().getName());
            }
            System.out.println("After " + Thread.currentThread().getName());
        }

        // 不推荐使用 suspend() 去挂起线程的原因，是因为 suspend() 在导致线程暂停的同时，并不会去释放任何锁资源。
        // 其他线程都无法访问被它占用的锁。直到对应的线程执行 resume() 方法后，被挂起的线程才能继续，从而其它被阻塞在这个锁的线程才可以继续执行。
        // 如果 resume() 操作出现在 suspend() 之前执行，那么线程将一直处于挂起状态，同时一直占用锁，这就产生了死锁;
        // 而且，对于被挂起的线程，它的线程状态还是 Runnable。
    }
}


