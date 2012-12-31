package com.demo.servlet.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleServiceDo4GetAndPost extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		super.service(arg0, arg1);
		response.setContentType("text/html; charset=utf-8");
        
        PrintWriter out = response.getWriter();
        out.println("处理所有的 HTTP 请求");  // 向客户端输出消息。。。
	}
}
