package service.cloud.client.start;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-cloud-client
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-cloud-client
 * ********************
 * <p>
 * Ribbon Client [RestTemplate]
 *
 * Edgware版本之后的@EnableDiscoveryClient是可选的(且可以取代@EnableEurekaServer),只需要引入spring-cloud-starter-netflix为前缀的包就可以自动注入
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}

/**
 * Config Client
 * <p>
 * spring.cloud.config.label 指明远程仓库的分支
 * spring.cloud.config.profile
 * * dev开发环境配置文件
 * * test测试环境
 * * pro正式环境
 * spring.cloud.config.uri= http://localhost:18092/ 指明配置服务中心的网址。
 */

/**
 * Feign/Ribbon
 * <p>
 * Feign是自带断路器 只需要在配置中开启
 * <p>
 * Dashboard 访问地址:http://127.0.0.1:8090/hystrix
 * Dashboard-Stream 地址:http://127.0.0.1:8090/hystrix.stream
 */