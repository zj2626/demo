package com.kdniao.logisticsfront.demo.web.gateway;

import com.kdniao.demo.common.util.Print;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @author zhangj
 * @version $Id: PushOrderFeeAjaxController.java, v 0.1 2018/12/20 18:03 zhangj Exp $
 */

@Controller
@RequestMapping("/gateway/request.json")
public class PushOrderFeeAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(PushOrderFeeAjaxController.class);

    @RequestMapping(method = {RequestMethod.POST})
    public void doPushOrderState(HttpServletRequest request, HttpServletResponse response) {
        String requestbody = null;
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            requestbody = IOUtils.toString(inputStream);
            logger.info("请求体【" + requestbody + "】");
            Print.out("", URLDecoder.decode(requestbody, "utf-8"));
        } catch (Exception e) {
            logger.error("获取请求体失败", e);
            return;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }


    }


}
