package hello.handler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@Configuration
public class MyQuartzManager {
    /*
    Scheduler - 调度器, 与调度程序交互的主要API。
    Job - 业务job,  由希望由调度程序执行的组件实现的接口。
    JobDetail - 用于定义作业的实例。
    Trigger - 触发器, 定义执行给定作业的计划的组件, 用来定义一个指定的Job何时被执行。
    JobBuilder - Job构建器, 用于定义/构建JobDetail实例，用于定义作业的实例。
    TriggerBuilder - 触发器构建器, 用于定义/构建触发器实例。
     */

    public static final String JOB1 = "job01";
    public static final String GROUP1 = "group01";
    public static final String DEFAULT_CRON = "0 0/3 * * * ?";

    /**
     * 任务调度
     */
    private Scheduler scheduler;

    /**
     * 开始执行定时任务
     */
    @Bean
    public void startJob() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        scheduler = schedulerFactoryBean.getScheduler();
        startJobTask(scheduler);

        scheduler.start();
        log.info("任务已经启动......");
    }

    /**
     * 启动定时任务
     *
     * @param scheduler
     */
    private void startJobTask(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder
                .newJob(MyJob.class)
                .withIdentity(JOB1, GROUP1)
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(DEFAULT_CRON);

        CronTrigger cronTrigger = TriggerBuilder
                .newTrigger()
                .withIdentity(JOB1, GROUP1)
                .withSchedule(cronScheduleBuilder)
                .build();

        scheduler.scheduleJob(jobDetail, cronTrigger);

    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改任务的执行时间
     *
     * @param name
     * @param group
     * @param cron  cron表达式
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder)
                    .build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null)
            return;
        scheduler.deleteJob(jobKey);
    }

}
