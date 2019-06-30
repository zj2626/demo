package hello.configuraion;

import hello.request.ExterfaceInvokeIOHttpSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpSenderConfig {
    @Value("${http.host-name}")
    private String hostname;

    @Bean
    public ExterfaceInvokeIOHttpSender xterfaceInvokeIOHttpSender() throws Exception {
        ExterfaceInvokeIOHttpSender sender = new ExterfaceInvokeIOHttpSender();
        sender.setAppName(hostname);
        sender.setHostname("http://127.0.0.1:8073");
        sender.setContentType("application/json; charset=UTF-8");
        sender.setMaxTotalConnection(200);
        sender.setReadTimeout(5000);
        sender.setConnectionTimeout(5000);
        sender.afterPropertiesSet();

        return sender;
    }
}
