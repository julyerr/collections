package com.julyerr.interviews.servlet.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EncodingFilterTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        System.out.println(user);
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("测试正常");
        printWriter.flush();
    }
}
