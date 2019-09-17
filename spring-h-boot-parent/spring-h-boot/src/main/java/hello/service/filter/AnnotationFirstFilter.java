package hello.service.filter;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = {Constants.PROVIDER}, order = -20000)/*order从小到大排序执行*/
public class AnnotationFirstFilter implements Filter {
    
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("开 始 调 用 AnnotationFirstFilter");
        Result result = invoker.invoke(invocation);
        return result;
    }
}
