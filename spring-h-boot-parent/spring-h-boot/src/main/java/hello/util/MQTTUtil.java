package hello.util;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Title:Server 这是发送消息的服务端
 * Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 *
 * @author
 */
public class MQTTUtil {
    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    private static final String HOST = "tcp://127.0.0.1:1883";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String CLIENT_ID = "client-01";
    
    private static MqttClient client;
    
    private String userName = "paho";  //非必须
    private String passWord = "";  //非必须
    
    public MQTTUtil() {
        try {
            client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 连接服务器
     */
    public void connect(MqttCallback callBack) {
        MqttConnectOptions options = new MqttConnectOptions();
        // 清空session 表示每次连接到服务器都以新的身份连接
        options.setCleanSession(true);
        // 超时时间
        options.setConnectionTimeout(10);
        // 会话心跳时间
        options.setKeepAliveInterval(20);
        // options.setUserName(userName);
        // options.setPassword(passWord.toCharArray());
        
        try {
            client.setCallback(callBack);
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param topics
     * @param qos    Quality of Service,服务质量 [发布者的Qos, 订阅者的Qos]
     *               level 0：最多一次的传输
     *               level 1：至少一次的传输，
     *               level 2： 只有一次的传输
     * @throws MqttException
     */
    public void subscribe(String[] topics, int[] qos) throws MqttException {
        client.subscribe(topics, qos);
    }
}
