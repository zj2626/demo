package com.kdniao.logisticsfront.common.biz.service.impl.thread.concurrent;

public class TestCompareAndSwap {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSwap(expectedValue, (int) (Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }
    }

    static class CompareAndSwap {
        private int value;//内存值

        //获取内存值
        public synchronized int get() {
            return value;
        }

        //比较
        public synchronized boolean compareAndSwap(int expectedValue, int newValue) {
            int oldValue = value;//线程读取内存值，与预估值比较
            if (oldValue == expectedValue) {
                this.value = newValue;
                return true;
            }
            return false;
        }
    }
}

