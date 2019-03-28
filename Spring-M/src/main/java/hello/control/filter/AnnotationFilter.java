package hello.control.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

@Activate(group = {Constants.CONSUMER})
public class AnnotationFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("_________________CONSUMER Filter");
        Result result = invoker.invoke(invocation);
        return result;
    }
}
