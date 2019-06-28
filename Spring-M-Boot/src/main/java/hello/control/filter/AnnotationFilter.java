package hello.control.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.filter.ConsumerContextFilter;

@Activate(group = {Constants.CONSUMER})
public class AnnotationFilter extends ConsumerContextFilter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("_________________dubbo CONSUMER Filter");

        return super.invoke(invoker, invocation);
    }
}
