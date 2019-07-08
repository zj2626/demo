package service.cloud.zipkin.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * Zipkin Server 访问路径: http://localhost:18093/zipkin/
 */
//@EnableEurekaClient
@EnableZipkinServer
@SpringBootApplication
public class SpringCloudZipkinApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZipkinApplication.class, args);
    }
}
