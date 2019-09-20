package com.demo.common.service.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Arrays;

public class PushCallback implements MqttCallback {
    
    /**
     * 接收到消息
     *
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.printf("接收到消息 主题:[%s] Qos:[%s] 内容:[%s] \n", topic, message.getQos(), Arrays.toString(message.getPayload()));
    }
    
    /**
     * 发送成功
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.printf("发送成功 内容:[%s] \n", token);
    }
    
    /**
     * 连接丢失 可进行重连
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.printf("连接丢失 原因:[%s] \n", cause);
        cause.printStackTrace();
    }
    
}
