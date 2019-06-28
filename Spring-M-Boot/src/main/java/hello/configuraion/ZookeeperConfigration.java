package hello.configuraion;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfigration {
//    @Value("${dubbo.registry.address}")
    private String connectString = "192.168.1.232:2181";

    @Bean
    public CuratorFramework getCuratorFramework() {
        ExponentialBackoffRetry curatorRetryPolicy =
                new ExponentialBackoffRetry(1000, 3);

        return CuratorFrameworkFactory
                .newClient(connectString, 60000, 30000, curatorRetryPolicy);
    }
}
