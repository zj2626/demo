package com.kdniao.logisticsfront.common.biz.service.impl.thread.wait.sleep.join;

public class ReadAndWrite {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("");
        Write write = new Write(buffer);
        ReadA readA = new ReadA(buffer);
        ReadB readB = new ReadB(buffer);

        new Thread(readA).start();
        new Thread(write).start();
        new Thread(readB).start();
    }

    static class Write implements Runnable {
        StringBuffer buffer;

        public Write(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            synchronized (buffer) {
                for (int i = 0; i < 5; i++) {
                    if (!"".equals(this.buffer.toString())) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("buffer write >");
                    buffer.append("\tinfo-").append(i);
                    System.out.println("buffer write finish");
                    buffer.notifyAll();

                    try {
                        System.out.println("WRITE");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class ReadA implements Runnable {
        StringBuffer buffer;

        public ReadA(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            synchronized (buffer) {
                for (int i = 0; i < 10; i++) {
                    if ("".equals(this.buffer.toString())) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("buffer read a < " + buffer.toString().length());
                    StringBuffer result = buffer.delete(0, buffer.toString().length());
                    System.out.println("buffer read a finish " + result.toString());
                    buffer.notifyAll();

                    try {
                        System.out.println("READ A");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class ReadB implements Runnable {
        StringBuffer buffer;

        public ReadB(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            synchronized (buffer) {
                for (int i = 0; i < 10; i++) {
                    if ("".equals(this.buffer.toString())) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("buffer read b < " + buffer.toString().length());
                    StringBuffer result = buffer.delete(0, buffer.toString().length());
                    System.out.println("buffer read b finish " + result.toString());
                    buffer.notifyAll();

                    try {
                        System.out.println("READ B");
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
