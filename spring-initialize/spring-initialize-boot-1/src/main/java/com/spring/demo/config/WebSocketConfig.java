package com.spring.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// 启用webSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 表示在[参数]的域上可以向客户端发消息。
        registry.enableSimpleBroker("/subscribeData", "/user");
        // 表示给指定用户发送一对一的主题前缀是[参数]
        // registry.setUserDestinationPrefix("/user");
        // 表示客户单向服务器端发送时的主题上面需要加[参数]作为前缀
        registry.setApplicationDestinationPrefixes("/communication");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs/websocket").withSockJS();
    }
}