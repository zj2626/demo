package hello.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "bean.info")
public class BeanConfig {
    Map<String, Object> map;
    String[] array;
    List<String> list;
    List<String> list2;

    @Value("#{'${bean.info.list1}'.split(',')}")
    List<String> listN;
}
