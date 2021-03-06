package hello.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorFrameworkConfig {
    @Value("${dubbo.registry.address}")
    private String connectString;

    @Bean
    public CuratorFramework curatorFramework() {
        connectString = connectString.replace("zookeeper://", "");

        ExponentialBackoffRetry curatorRetryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework =  CuratorFrameworkFactory.newClient(connectString, 60000, 30000, curatorRetryPolicy);
        curatorFramework.start();
        return curatorFramework;
    }
}
