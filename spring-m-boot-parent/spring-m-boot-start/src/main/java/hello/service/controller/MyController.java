package hello.service.controller;

import hello.control.BaseResult;
import hello.control.DoSomething;
import hello.control.template.InvokeCallback;
import hello.control.template.InvokeTemplate;
import hello.lock.LockServiceA;
import hello.lock.LockServiceB;
import hello.spring.scope.DemoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Scope("prototype")
@RestController
public class MyController {
    @Autowired
    private DoSomething doSomething;
    @Autowired
    private DemoService demoService;
    @Autowired
    private LockServiceA lockServiceA;
    @Autowired
    private LockServiceB lockServiceB;

    private InvokeTemplate template = new InvokeTemplate();

//    public MyController(DoSomething doSomething) {
//        this.doSomething = doSomething;
//    }
    
    @ApiOperation(value = "/")
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        System.out.println("hello");
        response.sendRedirect("swagger-ui.html");
    }

    @GetMapping("/dubbo")
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

    @GetMapping("/dubbo2")
    public BaseResult dubbo2() {
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
                result.setSuccess(doSomething.dodubbo2());
            }
        });

        return result;
    }

    @GetMapping("/redis")
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

    @GetMapping("/kafka")
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

    /* 执行:rabbitmq-plugins enable rabbitmq_management 访问:http://127.0.0.1:15672/ */
    @GetMapping("/rabbitmq")
    public BaseResult rabbitmq() {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {

            }

            @Override
            public void doInvoke() {
                result.setSuccess(doSomething.dorabbitmq());
            }
        });

        return result;
    }
    
    @GetMapping("/activemq")
    public BaseResult activemq() {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {
            
            }
            
            @Override
            public void doInvoke() {
                result.setSuccess(doSomething.doactivemq());
            }
        });
        
        return result;
    }

    @GetMapping("/fuck")
    public String fuck(String name) {
        System.out.println("fuck " + name);
        System.out.println("fuck action");
        System.out.println("fuck action action");
        return "fff createCommand";
    }

    /***********************************/

    @GetMapping("/setN")
    public String seta(int n) {
        System.out.println(demoService.hashCode());
        demoService.setNum(n);
        return "";
    }

    @GetMapping("/getN")
    public String geta() {
        System.out.println(demoService.hashCode());
        return "" + demoService.getNum();
    }

    /***********************************/

    @GetMapping("/testLockA")
    public String testLockA() {
        lockServiceA.invokeF();
//        lockServiceA.invokeS();
        return "";
    }

    @GetMapping("/testLockB")
    public String testLockB() {
        lockServiceB.invokeF();
//        lockServiceB.invokeS();
        return "";
    }

    @GetMapping("/request")
    public String doRequest(String name) {
        return doSomething.doHttpRequest(name);
    }

    @GetMapping("/test/hystrix")
    public String hystrix(String name) {
        return doSomething.doHystrixHttpRequest(name);
    }

    @GetMapping("/testError")
    public String testError() {
        doSomething.testError();
        return "";
    }
}
