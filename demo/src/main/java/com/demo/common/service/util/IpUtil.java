package com.demo.common.service.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    /**
     * <p>
     * Web 服务器反向代理中用于存放客户端原始 IP 地址的 Http header 名字，
     * 若新增其他的需要增加或者修改其中的值。
     * </p>
     */
    private static final String[] PROXY_REMOTE_IP_ADDRESS = {"X-Forwarded-For", "X-Real-IP"};


    /**
     * <p>
     * 获取请求的客户端的 IP 地址。若应用服务器前端配有反向代理的 Web 服务器，
     * 需要在 Web 服务器中将客户端原始请求的 IP 地址加入到 HTTP header 中。
     * 详见 {@link #PROXY_REMOTE_IP_ADDRESS}
     * </p>
     */
    public static String getRemoteIp(HttpServletRequest request) {
        if (request != null) {
            for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; i++) {
                String ip = request.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
                if (ip != null && ip.trim().length() > 0) {
                    return getRemoteIpFromForward(ip.trim());
                }
            }
            return request.getRemoteHost();
        } else {
            return "当前Request为空，拿取不到IP地址";
        }
    }

    /**
     * <p>
     * 从 HTTP Header 中截取客户端连接 IP 地址。如果经过多次反向代理，
     * 在请求头中获得的是以“,&lt;SP&gt;”分隔 IP 地址链，第一段为客户端 IP 地址。
     * </p>
     *
     * @param xforwardIp 从 HTTP 请求头中获取转发过来的 IP 地址链
     * @return 客户端源 IP 地址
     */
    private static String getRemoteIpFromForward(String xforwardIp) {
        int commaOffset = xforwardIp.indexOf(',');
        if (commaOffset < 0) {
            return xforwardIp;
        }
        return xforwardIp.substring(0, commaOffset);
    }

    /**
     * 获取访问IP地址
     *
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "本地";
        }
        return ip;
    }
}
