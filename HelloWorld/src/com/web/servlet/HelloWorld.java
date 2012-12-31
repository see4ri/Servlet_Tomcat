package com.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorld extends HttpServlet
{

//    /**
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//        
//    }

    /**
     * override {{@link #doGet()}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }
    
    /**
     * override {{@link #doPost()}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }
}
