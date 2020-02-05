package hello.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class QuartzConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(QuartzConfiguration.class);

    @Scheduled(cron = "0 0/3 * * * ?")
    public void firstJob() {
        logger.info("firstJob 任务执行成功！" + new Date());
    }
}
