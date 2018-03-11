package com.julyerr.interviews.servlet.multiThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MultiThreadCount extends HttpServlet {
//    实例变量
    int count = 0;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String countStr = req.getParameter("count");
        int tmpCount = 0;
        if(countStr!=null){
            tmpCount+= Integer.parseInt(countStr);
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html><head><title>ConcurrentServlet</title></head><body><h1>");
        //        同步代码块
//        synchronized (this){
            count+=tmpCount;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printWriter.write("奖池总数："+count);
//        }
        printWriter.write("</h1></body></html>");
        printWriter.flush();
        printWriter.close();
    }
}
