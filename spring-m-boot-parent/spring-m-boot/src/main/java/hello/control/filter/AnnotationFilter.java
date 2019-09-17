package hello.control.filter;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.filter.ConsumerContextFilter;

@Activate(group = {Constants.CONSUMER})
public class AnnotationFilter extends ConsumerContextFilter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.print("DUBBO CONSUMER _____\t");

        return super.invoke(invoker, invocation);
    }
}
