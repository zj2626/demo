package hello.aspect;

import com.alibaba.fastjson.JSON;
import hello.annotation.LogInfoMaker;
import hello.annotation.OperationParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    protected final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切点,用户变更操作日志
     */
    @Pointcut("(execution(public * hello.control.DoSomething.do*(..)))" +
            " || (execution(public * hello.control..*.impl.*.add*(..)))" +
            "")
    public void userInfo() {}

    @Order(1)
    @Around("userInfo()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        String userId = "zj";
        String userName = "zj";

        try {
            if (null != userId) {
                int argsLen = args.length;
                for (int i = 0; i < argsLen; i++) {
                    if (args[i] instanceof OperationParam) {
                        OperationParam param = (OperationParam) args[i];
                        param.setOperationUserId(userId);
                        param.setOperationUser(userName);
                        args[i] = param;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("设置权限用户失败 : " + e.getMessage());
        }
        return joinPoint.proceed(args);
    }

    /**
     * 切点, 打印出入参
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Order(2)
    @Around("@annotation(hello.annotation.LogInfoMaker)")
    public Object addLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        LogInfoMaker LogInfoMaker = getLogInfoMaker(joinPoint);
        if (isNotNull(LogInfoMaker)) {
            logger.info(LogInfoMaker.value().toString(), makeLogForm(LogInfoMaker, "入参") + ":{}", makeLogMsg(args));
        }
        Object result;
        try {
            result = joinPoint.proceed(args);
        } catch (Exception ex) {
            if (isNotNull(LogInfoMaker)) {
                logger.error(LogInfoMaker.value().toString(), makeLogForm(LogInfoMaker, "异常"), ex);
            }
            throw ex;
        }
        if (isNotNull(LogInfoMaker)) {
            logger.info(LogInfoMaker.value().toString(), makeLogForm(LogInfoMaker, "出参") + ":{}", makeLogMsg(result));
        }
        return result;
    }

    private LogInfoMaker getLogInfoMaker(JoinPoint joinPoint) {
        LogInfoMaker LogInfoMaker = null;
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            boolean flag = method.isAnnotationPresent(LogInfoMaker.class);
            if (flag) {
                LogInfoMaker = method.getAnnotation(LogInfoMaker.class);
            }
        } catch (Exception e) {
            logger.error("获取注解@LogInfoMaker异常 : " + e.getMessage());
        }
        return LogInfoMaker;
    }

    private boolean isNotNull(LogInfoMaker LogInfoMaker) {
        return null != LogInfoMaker;
    }

    private String makeLogMsg(Object msg) {
        return "【" + JSON.toJSONString(msg) + "】";
    }

    private String makeLogForm(LogInfoMaker LogInfoMaker, String entranceFlag) {
        return LogInfoMaker.value().getValue() + "-" + LogInfoMaker.value().getDesc() + entranceFlag;
    }
}
