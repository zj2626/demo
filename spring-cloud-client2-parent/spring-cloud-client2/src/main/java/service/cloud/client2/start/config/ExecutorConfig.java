package service.cloud.client2.start.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 *
 * @author zhangj
 * @version $Id: ExecutorConfig.java, v 0.1 2019/10/30 16:36 zhangj Exp $
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    /**
     * 初始线程数.
     */
    private int corePoolSize = 10;
    /**
     * 最大线程数.
     */
    private int maxPoolSize = 200;
    /**
     * 阻塞队列大小.
     */
    private int queueCapacity = 500;

    @Bean
    public Executor asyncThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("zj-async-");
        executor.initialize();
        return executor;
    }

}
