//package service.cloud.client.start.service.fallback;
//
//import org.springframework.stereotype.Component;
//import service.cloud.client.start.service.InterfaceFeignService;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 熔断以后执行这里的方法
// */
//@Component
//public class SchedualServiceHiHystric implements InterfaceFeignService {
//    @Override
//    public String fun(String name) {
//        return "fallback to here method[fun]";
//    }
//
//    @Override
//    public String fun2(Boolean success) {
//        return "fallback to here method[fun2]";
//    }
//
//    @Override
//    public String hi(String name) {
//        return "fallback to here method[doServiceRequestHi]";
//    }
//
//    @Override
//    public String doHalfFailed(Boolean success) {
//        return "fallback to here method[doServiceRequestHalfFailed]";
//    }
//
//    @Override
//    public String zipkinMethod2(String name) {
//        return "ERROR";
//    }
//
//    @Override
//    public String zipkinMethod4(String name) {
//        return "ERROR";
//    }
//}
