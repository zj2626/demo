package hello.service.filter;

import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger("sm.test");
    
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // logger.info("FirstFilter");
        return invoker.invoke(invocation);
    }
}
