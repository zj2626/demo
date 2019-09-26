package hello.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SubscribeCallback implements MqttCallback {
    
    private String name;
    
    public SubscribeCallback(String name) {
        this.name = name;
    }
    
    /**
     * 接收到消息
     *
     * @param topic
     * @param message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        System.out.printf("SubscribeCallback 接收到消息 订阅clientId:[%s] 主题:[%s] Qos:[%s] 内容:[%s] \n", name, topic, message.getQos(),
                new String(message.getPayload()));
    }
    
    /**
     * 发送成功
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.printf("SubscribeCallback 发送成功 内容:[%s] \n", token);
    }
    
    /**
     * 连接丢失 可进行重连
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.printf("SubscribeCallback 连接丢失 原因:[%s] \n", cause);
        cause.printStackTrace();
    }
    
}
