package hello.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 展示各种配置文件读取映射
 */
@Component
public class BeanShow {

    @Value("#{'${bean.info.array}'.split(',')[0]}")
    private String array0;

    @Value("#{'${bean.info.list}'.split(',')[1]}")
    private String list1;

    @Value("#{'${bean.info.list}'.split(',')}")
    private List<String> infoList;

    @Value("${bean.info.none:#{null}}")
    private String nullBean;

    @Autowired
    private BeanConfig beanConfig;

    @PostConstruct
    public void demo() {
        System.out.println("配置文件 -- beanConfig: " + JSON.toJSONString(beanConfig));

        System.out.println("配置文件 -- array0: " + array0);

        System.out.println("配置文件 -- list1: " + list1);

        System.out.println("配置文件 -- infoList: " + JSON.toJSONString(infoList));

        System.out.println("配置文件 -- nullBean: " + nullBean);
    }
}
