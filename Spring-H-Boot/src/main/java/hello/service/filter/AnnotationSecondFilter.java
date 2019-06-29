package hello.service.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

@Activate(group = {Constants.PROVIDER}, order = -10000)
public class AnnotationSecondFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        System.out.println("结束调用 AnnotationSecondFilter\n");
        return result;
    }
}
