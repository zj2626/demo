package service.cloud.start;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.*;
import service.cloud.start.model.TestcModel;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class RestApiController {
    private static Map<String, Object> map;

    static {
        map = new HashMap<>();
        map.put("success", true);
        map.put("code", 200);
        map.put("msg", "成功");
    }

    // 获取所有 Product
    @GetMapping("/products")
    public String products() {
        return JSON.toJSONString(map);
    }

    // 获取指定 Product
    @GetMapping("/products/{code}")
    public String product(@PathVariable Integer code) {
        System.out.println("product: " + code);
        return JSON.toJSONString(map);
    }

    // 获取分页 Product 模拟
    @GetMapping("/products/{code}/reviews")
    public String page(@PathVariable Integer code, Integer page, Integer size) {
        System.out.println("page: " + code);
        System.out.println("page: " + page);
        System.out.println("page: " + size);
        return JSON.toJSONString(map);
    }

    // 新建一个 Product, 内容放在请求体里 [默认Content-Type为application/json]
    @PostMapping("/products")
    public String create(@RequestBody TestcModel model) {
        System.out.println("create: " + model);
        return JSON.toJSONString(map);
    }

    // 更新一个 Product, 内容放在请求体里 [默认Content-Type为application/json]
    @PutMapping("/products/{code}")
    public String update(@PathVariable Integer code, @RequestBody TestcModel model) {
        System.out.println("update: " + code);
        System.out.println("update: " + model);
        return JSON.toJSONString(map);
    }

    // 删除一个 Product
    @DeleteMapping("/products/{code}")
    public String delete(@PathVariable Integer code) {
        System.out.println("delete: " + code);
        return JSON.toJSONString(map);
    }

    /*
    * rest命名规范
    * 1.GET：读取（Read）
        POST：新建（Create）
        PUT：更新（Update）
        PATCH：更新（Update），通常是部分更新
        DELETE：删除（Delete）
    * 2.只支持get和post的时候，可用post模拟其他请求，但是要加属性[X-HTTP-Method-Override: PUT]
    * 3.宾语(地址)必须是名称不能有动词
    * 4.统一使用复数，即使是查一条数据
    * 5.级别不要太多，后面的级别可以用查询字符串表达(?name=xxx)
    * 6.状态码必须精确，每次请求都要返回状态码
    * 7.返回信息要是Json而不要返回纯本文 ???
    * 8.返回信息中提供其他相关的api的链接以方便使用
    * */
}
