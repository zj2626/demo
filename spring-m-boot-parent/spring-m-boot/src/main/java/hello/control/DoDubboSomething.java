package hello.control;

import hello.service.DoHSomething;
import hello.service.DoWithAnnotation;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Future;

@Service
public class DoDubboSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoDubboSomething.class);

    @Reference(group = "${dubbo.consumer.group}")
    private DoHSomething doHSomething;

    @Reference(group = "${dubbo.consumer.group}", cache = "true")
    private DoWithAnnotation doWithAnnotation;

    public boolean dodubbo() {
        System.out.println("do  doHSomething: " + (doHSomething != null));
        System.out.println("do  doWithAnnotation: " + (doWithAnnotation != null));

        try {
            RpcContext rpcContext = RpcContext.getContext();

            System.out.println(rpcContext == null);

            //################################## 同步
            System.out.println("\n同步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");

            rpcContext.set("aa", "a1");
            rpcContext.setAttachment("bb", "b1");
            rpcContext.set(Constants.NAME, "zj2626");
            rpcContext.setUrl(URL.valueOf("www.baidu.com"));

            for (int i = 0; i < 3; i++) {
                String res = doHSomething.remoteToDubboSync("user " + i);
                System.out.println("同步请求: " + res);
            }
            System.out.println("同步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");

            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide2 = rpcContext.isConsumerSide();
            String serverIP2 = rpcContext.getRemoteHost();
            URL rpcContextUrl2 = rpcContext.getUrl();
            System.out.println("-------------- a");

            //################################## 异步
            System.out.println("\n异步 DUBBO请求 >>>>>>>>>>>>>>>>>> ");

            rpcContext.set("cc", "c1");
            rpcContext.setAttachment("dd", "d1");
            rpcContext.set(Constants.NAME, "zj2626");
            rpcContext.setUrl(URL.valueOf("www.baidu.com"));

            doHSomething.remoteToDubboAsync("user " + 0);
            doHSomething.remoteToDubboAsync("user " + 1);
            System.out.println("异步请求 doing...");

            // 本端是否为消费端，这里会返回true
            boolean isConsumerSide3 = rpcContext.isConsumerSide();
            String serverIP3 = rpcContext.getRemoteHost();
            URL rpcContextUrl3 = rpcContext.getUrl();
            System.out.println("-------------- b");

            System.out.println("wait for getting Future, 卡在这里等待获得异步返回结果");
            Future<String> future = rpcContext.getFuture();
            if (future != null) {
                String thing = future.get();
                System.out.println("\n异步请求 " + thing);
            }
            System.out.println("异步 DUBBO请求 <<<<<<<<<<<<<<<<<<< \n");

            boolean isConsumerSide4 = rpcContext.isConsumerSide();
            String serverIP4 = rpcContext.getRemoteHost();
            URL rpcContextUrl4 = rpcContext.getUrl();
            System.out.println("-------------- c");

            System.out.println("RPC a -> " + serverIP2 + " > " + isConsumerSide2 + " > " + rpcContextUrl2);
            System.out.println("RPC b -> " + serverIP3 + " > " + isConsumerSide3 + " > " + rpcContextUrl3);
            System.out.println("RPC c -> " + serverIP4 + " > " + isConsumerSide4 + " > " + rpcContextUrl4);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dodubbo2() {
        //        System.out.println("\n回声测试开始 >>>>>>>>>>>>>");
        //        EchoService echoService = (EchoService) doWithAnnotation;
        //        String status = (String) echoService.$echo("OK\n");
        //        System.out.println(status);
        //        System.out.println("\n回声测试完毕 <<<<<<<<<<<<<");

        System.out.println("测试缓存 缓存的最大量是1000");
        // 改为1001则下面的测试则会调用接口而不是使用缓存 (缓存的最大量是1000)
        for (int n = 0; n < 1000; n++) {
            int i;
            // 每个相同参数调用2次,第二次会直接走缓存不会调用到外部服务
            for (i = 0; i < 2; i++) {
                String result = doWithAnnotation.sayFuck(String.valueOf(n));
                System.out.printf("第%d个 第%d次 获得结果: %s\n", n + 1, i + 1, result);
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
        System.out.println("测试LRU有移除最开始的一个缓存项 执行时间: " + LocalDateTime.now());
        doWithAnnotation.sayFuck(String.valueOf(0));
        doWithAnnotation.sayFuck(String.valueOf(1));

        return true;
    }
}
