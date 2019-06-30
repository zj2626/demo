package hello.control;

import com.alibaba.fastjson.JSON;
import hello.data.model.Testc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    private RestService restService;

    // 获取所有 Product
    @GetMapping("/products")
    public String products() {
        return JSON.toJSONString(restService.findAll());
    }

    // 获取指定 Product
    @GetMapping("/products/{code}")
    public String product(@PathVariable Integer code) {
        return JSON.toJSONString(restService.findOne(code));
    }

    // 获取分页 Product 模拟
    @GetMapping("/products/{code}/reviews")
    public String page(@PathVariable Integer code, Integer page, Integer size) {
        System.out.println(code);
        System.out.println(page);
        System.out.println(size);
        return JSON.toJSONString(restService.findAll());
    }

    // 新建一个 Product, 内容放在请求体里 [默认Content-Type为application/json]
    @PostMapping("/products")
    public String create(@RequestBody Testc model) {
        restService.create(model);
        return "{\"success\":true}";
    }

    // 更新一个 Product, 内容放在请求体里 [默认Content-Type为application/json]
    @PutMapping("/products/{code}")
    public String update(@PathVariable Integer code, @RequestBody Testc model) {
        model.setId(code);
        restService.update(model);
        return "{\"success\":true}";
    }

    // 删除一个 Product
    @DeleteMapping("/products/{code}")
    public String delete(@PathVariable Integer code) {
        restService.delOne(code);
        return "{\"success\":true}";
    }
}
