package service.cloud.client.start.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class HelloClientController {
    @ApiOperation(value = "/")
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }
}
