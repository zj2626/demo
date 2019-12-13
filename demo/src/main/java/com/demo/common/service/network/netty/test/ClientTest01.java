package com.demo.common.service.network.netty.test;

import com.demo.common.service.network.netty.abs.MyNettyAddr;
import com.demo.common.service.thread.abs.ExcutorPoolDemo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClientTest01 extends MyNettyAddr {
    ClientHandler clientHandler = new ClientHandler();
    private ChannelFuture future;
    private boolean isClosed = false;

    @Test
    public void client() throws Exception {
        excutorPool = new ExcutorPoolDemo(this);
        excutorPool.execute(1);
        excutorPool.futureGet(5L);

        send(RandomStringUtils.random(32, true, true));
    }

    /**
     * https://www.iteye.com/blog/shift-alt-ctrl-2219057
     *
     * @param message
     * @return
     * @throws Exception
     */
    public String send(String message) throws Exception {
        return clientHandler.call(message, future.channel());
    }

    @Override
    public Object doExcute(Map<String, Object> parameter) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(10240, 0, 2, 0, 2))
                                    .addLast(new StringDecoder(UTF_8))
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast(new StringEncoder(UTF_8))
                                    .addLast(clientHandler)
                            ;
                        }
                    });
            future = bootstrap.connect(serverHost, serverPort).sync();
            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 异步发送");
//            future.channel().closeFuture().sync();
//            System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!group.isShutdown() && isClosed) {
                group.shutdownGracefully();
                System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName() + " 关闭");
            }
        }

        return null;
    }

    static class ClientHandler extends SimpleChannelInboundHandler<String> {

        //key is sequence ID，value is response message.
        private Map<Integer, String> response = new ConcurrentHashMap<Integer, String>();

        //key is sequence ID，value is request thread.
        private final Map<Integer, Thread> waiters = new ConcurrentHashMap<Integer, Thread>();

        private final AtomicInteger sequence = new AtomicInteger();


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //当channel就绪后。
            System.out.println("client channel is ready!");
            //ctx.writeAndFlush("started");//阻塞知道发送完毕
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
            JSONObject json = JSONObject.fromObject(message);
            Integer id = json.getInt("id");
            response.put(id, json.getString("md5Hex"));

            Thread thread = waiters.remove(id);//读取到response后，从waiters中移除并唤醒线程。
            synchronized (thread) {
                thread.notifyAll();
            }
        }


        public String call(String message, Channel channel) throws Exception {
            int id = sequence.incrementAndGet();//产生一个ID，并与当前request绑定
            Thread current = Thread.currentThread();
            waiters.put(id, current);
            JSONObject json = new JSONObject();
            json.put("id", id);
            json.put("source", message);
            channel.writeAndFlush(json.toString());
            while (!response.containsKey(id)) {
                synchronized (current) {
                    current.wait();//阻塞请求调用者线程，直到收到响应响应
                }
            }
            waiters.remove(id);
            return response.remove(id);

        }

    }
}
