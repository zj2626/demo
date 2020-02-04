package hello.service.controller;

import hello.control.*;
import hello.control.template.InvokeCallback;
import hello.control.template.InvokeTemplate;
import hello.database.entity.Person;
import hello.lock.LockServiceA;
import hello.lock.LockServiceB;
import hello.service.model.KafkaRequest;
import hello.service.model.RedisRequest;
import hello.spring.scope.DemoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Scope("prototype")
@RestController
public class MyController {
    @Autowired
    private DoSomething doSomething;
    @Autowired
    private DoDubboSomething doDubboSomething;
    @Autowired
    private DoRedisSomething doRedisSomething;
    @Autowired
    private DoMQSomething doMQSomething;
    @Autowired
    private DoSqlSomething doSqlSomething;
    @Autowired
    private DoHttpSomething doHttpSomething;
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
                result.setSuccess(doDubboSomething.dodubbo());
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
            public void doInvoke() {
                System.out.println("do invoke");
                result.setSuccess(doDubboSomething.dodubbo2());
            }
        });

        return result;
    }

    @GetMapping("/redis")
    public BaseResult redis(RedisRequest request) {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void doInvoke() {
                result.setSuccess(doRedisSomething.doredis(request));
            }
        });

        return result;
    }

    @GetMapping("/kafka")
    @ApiOperation(value = "kafka")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "times", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "name", defaultValue = "topic-4"),
            @ApiImplicitParam(paramType = "query", dataType = "Boolean", name = "needSort", defaultValue = "false")
    })
    public BaseResult kafka(Integer times, String name, Boolean needSort) {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void doInvoke() {
                KafkaRequest request = new KafkaRequest();
                request.setNeedSort(needSort);
                request.setTimes(times);
                request.setTopic(name);
                result.setSuccess(doMQSomething.dokafka(request));
            }
        });

        return result;
    }

    @GetMapping("/kafkaCustomProducer")
    @ApiOperation(value = "kafkaCustomProducer")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "name", defaultValue = "topic-1")
    public String kafkaCustomProducer(String name) {
        doMQSomething.kafkaCustomProducer(name);
        return "success";
    }

    /* 执行:rabbitmq-plugins enable rabbitmq_management 访问:http://127.0.0.1:15672/ */
    @GetMapping("/rabbitmq")
    public BaseResult rabbitmq() {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void doInvoke() {
                result.setSuccess(doMQSomething.dorabbitmq());
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
            public void doInvoke() {
                result.setSuccess(doMQSomething.doactivemq());
            }
        });

        return result;
    }

    @GetMapping("/mqtt")
    @ApiOperation(value = "MQTT")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "topic", defaultValue = "demo_topic_2626", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "message", defaultValue = "这是推送消息的内容", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "qos", defaultValue = "0", required = true)
    })
    public BaseResult mqtt(String topic, String message, int qos) {
        final BaseResult result = new BaseResult();
        result.setSuccess(false);
        this.template.invoke(result, new InvokeCallback() {
            @Override
            public void doInvoke() {
                result.setSuccess(doMQSomething.domqtt(topic, message, qos));
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

    @PostMapping("/doJdbcTemplate")
    public String doJdbcTemplate(@RequestBody Person msg) {
        return "" + doSqlSomething.doSql(msg);
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
        return doHttpSomething.doHttpRequest(name);
    }

    @GetMapping("/test/hystrix")
    public String hystrix(String name) {
        return doHttpSomething.doHystrixHttpRequest(name);
    }

    @GetMapping("/testError")
    public String testError() {
        doSomething.testError();
        return "";
    }
}
