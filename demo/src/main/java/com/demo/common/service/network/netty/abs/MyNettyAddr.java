package com.demo.common.service.network.netty.abs;

import com.demo.common.service.thread.abs.MyExcutor;

public abstract class MyNettyAddr extends MyExcutor {
    public static String serverHost = "127.0.0.1";
    public static int serverPort = 18088;
    public static int bossThread = 2;
    public static int workerThread = 3;
}
