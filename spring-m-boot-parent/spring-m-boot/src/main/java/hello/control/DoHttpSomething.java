package hello.control;

import hello.annotation.MovieRecommender;
import hello.annotation.SimpleMovieLister;
import hello.hystrix.HystrixUtil;
import hello.request.ExterfaceInvokeIOHttpSender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DoHttpSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoHttpSomething.class);

    @Autowired
    private ExterfaceInvokeIOHttpSender exterfaceInvokeIOHttpSender;

    public DoHttpSomething() {
        logger.info("构造造 DoSomething");
    }

    /*httpRequest*/
    public String doHttpRequest(String message) {

        message = StringUtils.isNotEmpty(message) ? message : "defultFuck";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("message", message);
        String response = null;
        try {
            response = exterfaceInvokeIOHttpSender.sendGet(params, "/message");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response);

        return response;
    }

    /*hystrix httpRequest*/
    public String doHystrixHttpRequest(String message) {
        message = StringUtils.isNotEmpty(message) ? message : "defultFuck";

        String response = HystrixUtil.send(message);
        System.out.println("HystrixThread > " + response);
        System.out.println("MainThread    > " + Thread.currentThread().getName());

        return response;
    }
}
