/*
package com.demo.common.service.thread.pool;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

*/
/**
 * 示例: 定时推送用户预授信信息
 *
 * @author zhangj
 * @version $Id: PreCreditPushTaskExecutor.java, v 0.1 2019/4/20 15:42 zhangj Exp $
 *//*

public class PreCreditPushTaskExecutor implements TaskExecutor, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger("task.digest");

    */
/**
     * 核心线程池大小
     *//*

    private int concurrent = 5;
    */
/**
     * 最大线程池大小
     *//*

    private int maxConcurrent = 10;
    */
/**
     * 线程最大空闲时间
     *//*

    private int keepAliveTime = 0;
    */
/**
     * 线程等待队列大小
     *//*

    private int blockingQueueSize = 1024;

    private SendNotifyService sendNotifyService;

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(PreCreditPushTaskExecutor.class.getName() + "-%d").build();
        executorService = new ThreadPoolExecutor(
                concurrent,
                maxConcurrent,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                */
/*线程等待队列*//*

                new LinkedBlockingQueue<>(blockingQueueSize),
                threadFactory,
                */
/*拒绝策略*//*

                new TaskRunsPolicyImpl());
    }

    public void setSendNotifyService(SendNotifyService sendNotifyService) {
        this.sendNotifyService = sendNotifyService;
    }

    private UserinfoPreCreditService userinfoPreCreditService;

    public void setUserinfoPreCreditService(UserinfoPreCreditService userinfoPreCreditService) {
        this.userinfoPreCreditService = userinfoPreCreditService;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

    public void setMaxConcurrent(int maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setBlockingQueueSize(int blockingQueueSize) {
        this.blockingQueueSize = blockingQueueSize;
    }

    @Override
    public BaseResult execute(Task task) throws TaskException {
        BaseResult result = new BaseResult();

        List<UserinfoPreCreditDO> userinfoPreCreditDOList = userinfoPreCreditService.getAllNotPush();

        if (CollectionUtils.isNotEmpty(userinfoPreCreditDOList)) {
            for (UserinfoPreCreditDO userinfoPreCreditDO : userinfoPreCreditDOList) {
                executorService.execute(new Runnable() {

                    @Override
                    public void run() {
                        boolean ifSuccess = false;
                        try {
                            DigestContextHolder.init(UUID.randomUUID().toString());
                            ifSuccess = doPush(userinfoPreCreditDO);
                        } catch (Throwable t) {
                            LoggerUtil.error(logger, "settlement push preCredit failed.", t);
                        } finally {
                            logger.info("更新预授信状态推送:" + (ifSuccess ? "成功" : "失败") + " [" + JSON.toJSONString(userinfoPreCreditDO) + "]");
                            DigestContextHolder.clear();
                        }

                    }
                });
            }
        }

        return result;
    }

    private boolean doPush(UserinfoPreCreditDO userinfoPreCreditDO) {
        PreCreditPush preCreditPush = new PreCreditPush();
        BeanUtils.copyProperties(userinfoPreCreditDO, preCreditPush);
        preCreditPush.setIsGrant(0);
        preCreditPush.setIsActive(0);

        sendNotifyService.doPush(preCreditPush, preCreditPush.getParentuserid());

        // 修改状态
        userinfoPreCreditDO.setIspush(1);
        Integer isUpdateSucceed = userinfoPreCreditService.update(userinfoPreCreditDO);
        return null != isUpdateSucceed && 0 != isUpdateSucceed;

    }
}
*/
