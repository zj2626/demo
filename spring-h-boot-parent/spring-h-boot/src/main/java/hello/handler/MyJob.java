
package hello.handler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

@Slf4j
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("secondJob 任务执行成功！" + new Date()
                + " | " + jobExecutionContext.getTrigger().getJobKey().getName()
                + " | " + jobExecutionContext.getTrigger().getJobKey().getGroup()
                + " | " + jobExecutionContext.getTrigger().getDescription()
        );
    }
}

