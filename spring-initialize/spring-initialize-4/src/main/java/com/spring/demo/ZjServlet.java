package com.spring.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ZjServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("ZJ--- doGet");

        String path = ZjServlet.class.getResource("/").getPath();
        path += req.getRequestURI();
        System.out.println("path : " + path);

        resp.setContentType("text/html");
//        resp.getWriter().write(path);

        InputStream inputStream  = new FileInputStream(new File(path));
        byte[] bypes = new byte[2048];
        inputStream.read(bypes);
        inputStream.close();
        resp.getWriter().write(new String(bypes));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        System.out.println("ZJ--- doPost");
    }
}
