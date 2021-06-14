package com.demo.common.service.jvm.block;

/**
 * 线程死锁等待演示
 */
public class BlockDemo implements Runnable {
    int a, b;

    public BlockDemo(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (Integer.valueOf(a)) {
            synchronized (Integer.valueOf(b)) {
                System.out.println(a + b);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new BlockDemo(1, 2)).start();
            new Thread(new BlockDemo(2, 1)).start();
        }
    }
}

