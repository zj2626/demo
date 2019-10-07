package service.cloud.turbine.start;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Eureka Turbine: 每个服务Hystrix Dashboard数据进行了整合
 *
 * @EnableTurbine包含@EnableDiscoveryClient
 *
 * Dashboard 访问地址:http://localhost:18094/hystrix
 * Dashboard-Stream 地址:http://localhost:18094/turbine.stream
 */
@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableTurbine
@SpringBootApplication
public class SpringCloudTurbineApplication {
    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudTurbineApplication.class, args);
    }

    /**
     * @return
     * @LoadBalanced 开启负载均衡
     */
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
        registrationBean.addUrlMappings("/hystrix.stream","/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

    @HystrixCommand(fallbackMethod = "onError")
    @GetMapping("/test/hystrix")
    public String doServiceRequestHalfFailed3(Boolean success) {
        return restTemplate.getForObject("http://spring-cloud-client02/half?success=" + success, String.class);
    }

    @HystrixCommand(fallbackMethod = "onError")
    @GetMapping("/test/hystrix2")
    public String doServiceRequestHalfFailed4(Boolean success) {
        return restTemplate.getForObject("http://spring-cloud-client02/half?success=" + success, String.class);
    }

    private String onError(Boolean success){
        return "ERROR...";
    }
}
