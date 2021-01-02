package service.cloud.start;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.cloud.start.model.BatchEntity;
import service.cloud.start.model.TestcModel;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
/* restful学习记录见 spring-h-boot-start*/
public class RestApiController {
    private static Map<String, Object> map;

    static {
        map = new HashMap<>();
        map.put("success", true);
        map.put("code", 200);
        map.put("msg", "yes");
    }

    // 获取所有 Product
    @GetMapping("/products")
    public String products(@RequestHeader(value = "Content-Type", required = false) String contentType,
                           TestcModel model) {
        System.out.println("Get: " + model);
        System.out.println("contentType: " + contentType);
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
    public String create(
            @RequestHeader(value = "Content-Type", required = false) String contentType,
            @RequestHeader(value = "Host", required = false) String host,
            @RequestHeader(value = "User-Agent", required = false) String agent,
            HttpServletRequest request,
            @RequestBody TestcModel model) {
        System.out.println("Content-Type: == " + request.getHeader("Content-Type"));
        System.out.println("RemoteAddr  : == " + request.getRemoteAddr());
        System.out.println("RemoteUser  : == " + request.getRemoteUser());
        System.out.println("Method      : == " + request.getMethod());
        System.out.println("ContextPath : == " + request.getContextPath());
        System.out.println("PathInfo    : == " + request.getPathInfo());
        System.out.println("QueryString : == " + request.getQueryString());
        System.out.println("RequestURI  : == " + request.getRequestURI());
        System.out.println("ServletPath : == " + request.getServletPath());
        System.out.println("RequestedSessionId: == " + request.getRequestedSessionId());
        System.out.println("Create: " + " > " + model + " - " + contentType + " - " + host + " - " + agent);
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

    /**
     * 文件上传
     *
     * @param contentType
     * @param id
     * @param name
     * @param multipartFileList
     * @return
     */
    @PostMapping("/files")
    public String files(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam("files") List<MultipartFile> multipartFileList
    ) {
        System.out.println("contentType: " + contentType);
        System.out.println("id: " + id);
        System.out.println("name: " + name);
        for (MultipartFile multipartFile : multipartFileList) {
            System.out.println("files: " + multipartFile.getOriginalFilename());
        }
        return JSON.toJSONString(map);
    }

    /**
     * 以流的形式请求
     *
     * @param name
     * @param is
     * @return
     */
    @PostMapping("/stream")
    public String stream(
            @RequestParam(value = "name", required = false) String name,
            InputStream is
    ) {
        try {
            System.out.println("name: " + name);
            StringBuilder str = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line);
            }
            System.out.println("InputStream: " + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(map);
    }
}
