package hello.service.controller;

import hello.control.DoRedisSomething;
import hello.service.start.SpringMBootStartApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:application-comp.properties", "classpath:logback-spring.xml"})
@SpringBootTest(classes = SpringMBootStartApplication.class)
public class MyControllerTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MyController controller;
    @Autowired
    private DoRedisSomething doRedisSomething;

    @Test
    public void redis() {
//        controller.redis(null);
    }
}