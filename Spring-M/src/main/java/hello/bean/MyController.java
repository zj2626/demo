package hello.bean;

import com.alibaba.dubbo.common.utils.StringUtils;
import hello.control.*;
import hello.lock.LockServiceA;
import hello.lock.LockServiceB;
import hello.spring.scope.DemoService;
import hello.transaction.DoSomethingForTransaction;
import hello.transaction.DoSomethingProxy;
import hello.transaction.DoTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Scope("prototype")
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
    @Autowired
    private DemoService demoService;
    @Autowired
    private LockServiceA lockServiceA;
    @Autowired
    private LockServiceB lockServiceB;

    private InvokeTemplate template = new InvokeTemplate();

    public MyController() {
        System.out.println("<构造函数> MyController ");
    }
//    public MyController(DoSomething doSomething) {
//        System.out.println("<构造函数> MyController 2");
//        this.doSomething = doSomething;
//    }

    @RequestMapping("/")
    public String index() {
        System.out.println("hello");
        return "hello";
    }

    @RequestMapping("/shit")
    public String shit() {
        System.out.println("shit ");
        System.out.println("shit action");
        System.out.println("shit action action");
        return "shit createCommand";
    }

    @RequestMapping("/dubbo")
    public BaseResult dubbo() {
        final BaseResult result = new BaseResult();
        System.out.println("into dubbo");

        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {
                System.out.println("do checkParameters");
            }


            @Override
            public void doInvoke() {
                System.out.println("do invoke");
                result.setSuccess(doSomething.dodubbo());
            }
        });

        return result;
    }

    @RequestMapping("/dubbo2")
    public BaseResult dubbo2(String same) {
        final Boolean sameBoolean = Boolean.valueOf(same);
        System.out.println("into dubbo2");

        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {
                System.out.println("do checkParameters");
            }


            @Override
            public void doInvoke() {
                System.out.println("do invoke");
                result.setSuccess(doSomething.dodubbo2(sameBoolean));
            }
        });

        return result;
    }

    @RequestMapping("/redis")
    public BaseResult redis() {
        System.out.println("into redis");

        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {
                System.out.println("do checkParameters");
            }


            @Override
            public void doInvoke() {
                System.out.println("do invoke");
                result.setSuccess(doSomething.doredis());
            }
        });

        return result;
    }

    @RequestMapping("/kafka")
    public BaseResult kafka(String same) {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {

            }


            @Override
            public void doInvoke() {
                result.setSuccess(doSomething.dokafka());
            }
        });

        return result;
    }

    @RequestMapping("/fuck")
    public String fuck(String name) {
        System.out.println("fuck " + name);
        System.out.println("fuck action");
        System.out.println("fuck action action");
        return "fff createCommand";
    }

    @RequestMapping("/transaction")
    public BaseResult transaction(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            result.setSuccess(doTransaction.dotransaction(code));
        }
        return result;
    }

    @RequestMapping("/transactionTX")
    public BaseResult transactionTX(String code) {
        BaseResult result = new BaseResult();
        result.setSuccess(false);
        if (StringUtils.isNotEmpty(code)) {
            try {
                result.setSuccess(doTransaction.dotransactionTX(code));
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
            }
        }
        return result;
    }

    @RequestMapping("/transactionAnnotation")
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
    @RequestMapping("/transactionA")
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

    @RequestMapping("/transactionB")
    public BaseResult transactionB() {
        BaseResult result = new BaseResult();
        try {
            for (int i = 0; i < 28; i++) {
                doSomethingForTransaction.dotransactionTXB();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/transactionC")
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

    @RequestMapping("/transactionD")
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

    @RequestMapping("/transactionE")
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

    /***********************************/

    @RequestMapping("/setN")
    public String seta(int n) {
        System.out.println(demoService.hashCode());
        demoService.setNum(n);
        return "";
    }

    @RequestMapping("/getN")
    public String geta() {
        System.out.println(demoService.hashCode());
        return "" + demoService.getNum();
    }

    /***********************************/

    @RequestMapping("/testLockA")
    public String testLockA() {
        lockServiceA.invokeF();
//        lockServiceA.invokeS();
        return "";
    }

    @RequestMapping("/testLockB")
    public String testLockB() {
//        lockServiceB.invokeF();
        lockServiceB.invokeS();
//        lockServiceA.invokeF();
//        lockServiceA.invokeS2();
        return "";
    }
}
