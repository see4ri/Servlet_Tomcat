package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends HttpServlet
{

    public HelloWorldServlet()
    {
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        response.setContentType("text/html; charset=utf-8");
        System.out.println("requst method : " + request.getMethod());
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
//        request.getReader();
        Writer outWriter = response.getWriter();
        PrintWriter out = response.getWriter();
        
        // write
/*        outWriter.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        outWriter.write("<html>");
        outWriter.write("<head>测试doGet:</head>");
        outWriter.write("<body>");
        outWriter.write("<p><center>Now time is : " + new Date() + "</center></p>");
        outWriter.write("<p>请求的数据是 Hello world!</p>");
        outWriter.write("</body>");
        outWriter.write("</html>");*/
        
        // append
/*        outWriter.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        outWriter.append("<html>");
        outWriter.append("<head>测试doGet:</head>");
        outWriter.append("<body>");
        outWriter.append("<p>Now time is : " + new Date() + "</p>");
        outWriter.append("<p>请求的数据是 Hello world!</p>");
        outWriter.append("</body>");
        outWriter.append("</html>");
*/
        // println
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        out.println("<html>");
        out.println("<head>测试doGet:</head>");
        out.println("<body>");
        out.println("<p><center>Now time is : " + new Date() + "</center></p>");
        out.println("<p>请求的method是" + request.getMethod() + "!</p>");
        out.println("<p>请求的数据是 Hello world!</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        response.setContentType("text/html; charset=utf-8");
        System.out.println("requst method : " + request.getMethod());
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
        PrintWriter out = response.getWriter();
        
        // println
        out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        out.println("<html>");
        out.println("<head>测试doPost:</head>");
        out.println("<body>");
        out.println("<p><center>Now time is : " + new Date() + "</center></p>");
        out.println("<p>请求的method是" + request.getMethod() + "!</p>");
        out.println("<p>请求的数据是 Hello world!</p>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
        
    }
    
    private void doService()
    {
    	// 优化 doGet 和 doPost
	}

}
