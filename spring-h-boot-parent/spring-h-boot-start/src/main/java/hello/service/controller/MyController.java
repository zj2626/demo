package hello.service.controller;

import hello.service.BaseResult;
import hello.service.DoSomething;
import hello.transaction.DoSomethingForTransaction;
import hello.transaction.DoSomethingProxy;
import hello.transaction.DoTransaction;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MyController {
    @Autowired
    private DoSomething doSomething;
    @Autowired
    private DoSomethingForTransaction doSomethingForTransaction;
    @Autowired
    private DoSomethingProxy doSomethingProxy;
    @Autowired
    private DoTransaction doTransaction;

    public MyController() {
        System.out.println("<构造函数> MyController ");
    }
    
    @ApiOperation(value = "/")
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        System.out.println("hello");
        response.sendRedirect("swagger-ui.html");
    }
    
    @GetMapping("/transaction")
    public BaseResult transaction(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            result.setSuccess(doTransaction.dotransaction(code));
        }
        return result;
    }

    @GetMapping("/transactionTXWrite")
    public BaseResult transactionTX(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            try {
                result.setSuccess(doTransaction.dotransactionTXWrite(code));
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }
        return result;
    }

    @GetMapping("/transactionTXRead")
    public BaseResult transactionTX2(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            try {
                for (int i = 0; i < 10; i++) {
                    result.setSuccess(doTransaction.dotransactionTXRead(code));
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }
        return result;
    }

    @GetMapping("/transactionAnnotation")
    public BaseResult transactionAnnotation(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            try {
                result.setSuccess(doTransaction.dotransactionAnnotation(code));
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }
        return result;
    }

    /*先调用transactionB再调用transactionA,观察控制台输出结果中transactionB接口的打印数据的变化*/
    @GetMapping("/transactionA")
    public BaseResult transactionA(String name) {
        BaseResult result = new BaseResult();
        try {
            result.setSuccess(false);
            result.setSuccess(doSomethingForTransaction.dotransactionTXA(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/transactionB")
    public BaseResult transactionB() {
        BaseResult result = new BaseResult();
        try {
            for (int i = 0; i < 30; i++) {
                doSomethingForTransaction.dotransactionTXB();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/transactionC")
    public BaseResult transactionC() {
        BaseResult result = new BaseResult();
        try {
            result.setSuccess(true);
            doSomethingForTransaction.dotransactionTXC();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/transactionD")
    public BaseResult transactionD(String name) {
        BaseResult result = new BaseResult();
        try {
            result.setSuccess(true);
            doSomethingProxy.tranA(name);
//            doSomethingProxy.tranB(name);
//            doSomethingProxy.tranC(name);
//            doSomethingProxy.tranD(name);
//            doSomethingProxy.tranE(name);
//            doSomethingProxy.tranF(name);
//            doSomethingProxy.tranG(name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/transactionE")
    public BaseResult transactionE(String name) {
        BaseResult result = new BaseResult();
        try {
            result.setSuccess(true);
            doSomethingProxy.transactionFunctionA(name);
//            doSomethingProxy.transactionFunctionB(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/log")
    public String testMybatisLog(@RequestParam String name) {
        doSomething.testMybatisLog(name);

        return "god";
    }
}
