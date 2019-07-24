package service.cloud.start;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;
import service.cloud.start.model.BatchEntity;
import service.cloud.start.model.TestcModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
/* restful学习记录见 spring-m-boot-start*/
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
        System.out.println("code: " + code);
        System.out.println("page: " + page);
        System.out.println("size: " + size);
        return JSON.toJSONString(map);
    }

    // 新建一个 Product, 内容放在请求体里 [默认Content-Type为application/json]
    @PostMapping(value = "/products", produces = "application/json;charset=utf-8")
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

    /*批量操作*/
    @PostMapping("/products/batch") // ???????待完成
    public String batch(@RequestBody BatchEntity model) {
        System.out.println(model);

        switch (model.getMethod()) {
            case create:
                List<TestcModel> modelList = JSONArray.parseArray(model.getData(), TestcModel.class);
                System.out.println(modelList);
                break;
            case update:
                List<TestcModel> modelList2 = JSON.parseArray(model.getData(), TestcModel.class);
                System.out.println(modelList2);
                break;
            case delete:
                String[] ids = model.getData().split(",");
                System.out.println(ids);
                break;
            default:
        }

        return "{\"success\":true, \"code\":200}";
    }
}
