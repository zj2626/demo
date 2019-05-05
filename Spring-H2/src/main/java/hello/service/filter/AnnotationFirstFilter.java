package hello.service.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.springframework.core.annotation.Order;

@Activate(group = {Constants.PROVIDER}, order = -20000)/*order从小到大排序执行*/
public class AnnotationFirstFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("开始调用 AnnotationFirstFilter");
        Result result = invoker.invoke(invocation);
        System.out.println("结束调用 AnnotationFirstFilter\n");
        return result;
    }
}
