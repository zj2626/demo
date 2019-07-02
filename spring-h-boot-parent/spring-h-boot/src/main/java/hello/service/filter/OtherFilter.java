package hello.service.filter;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtherFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger("sm.test");

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("OtherFilter");
        return invoker.invoke(invocation);
    }
}
