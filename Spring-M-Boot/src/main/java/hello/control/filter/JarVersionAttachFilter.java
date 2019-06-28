package hello.control.filter;

import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;

public class JarVersionAttachFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        System.out.println("开始调用 JarVersionAttachFilter");
        Result result = invoker.invoke(invocation);
//        System.out.println("结束调用 JarVersionAttachFilter");
        return result;
    }
}
