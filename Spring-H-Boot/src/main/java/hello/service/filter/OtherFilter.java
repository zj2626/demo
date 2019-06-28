package hello.service.filter;

import com.alibaba.dubbo.rpc.*;

public class OtherFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("OtherFilter");
        Result result = invoker.invoke(invocation);
        return result;
    }
}
