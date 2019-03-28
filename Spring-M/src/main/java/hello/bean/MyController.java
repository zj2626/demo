package hello.bean;

import hello.control.BaseResult;
import hello.control.DoSomething;
import hello.control.InvokeCallback;
import hello.control.InvokeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private DoSomething doSomething;

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

    @RequestMapping("/control")
    public BaseResult control(String same) {
        final Boolean sameBoolean = Boolean.valueOf(same);
        System.out.println("into control");

        final BaseResult result = new BaseResult();
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void checkParameters() {
                System.out.println("do checkParameters");
            }


            @Override
            public void doInvoke() {
                System.out.println("do invoke");
                result.setSuccess(doSomething.dosome(sameBoolean));
            }
        });

        return result;
    }

    @RequestMapping("/kafka")
    public BaseResult kafka(String same) {
        final BaseResult result = new BaseResult();
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
}
