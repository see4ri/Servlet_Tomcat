package com.web.app;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class HelloWorld extends HttpServlet{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static void main() {
	System.out.println("Main entry!");
}

public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
response.setContentType("text/html");
 PrintWriter out = response.getWriter();
out.println("<html><head><title>");
out.println("This is my first Servlet");
out.println("</title></head><body>");
out.println("<h1>Hello,World!</h1>");
out.println("</body></html>");
}
}
