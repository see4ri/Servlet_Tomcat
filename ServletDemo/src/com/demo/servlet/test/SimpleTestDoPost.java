package com.demo.servlet.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleTestDoPost extends HttpServlet
{
	// 处理客户端的 POST 请求
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html; charset=utf-8");
        
        PrintWriter out = response.getWriter();
        out.println("处理 HTTP POST 请求");
    }
}
