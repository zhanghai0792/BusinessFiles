package com.hy.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hy.model.Teacher;

public class AcademicSecretaryFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
			
		    HttpServletRequest servletRequest = (HttpServletRequest) request;
	        HttpServletResponse servletResponse = (HttpServletResponse) response;
		    HttpSession session = servletRequest.getSession();
		    Teacher user = (Teacher) session.getAttribute("loginteacher");
		    session.setMaxInactiveInterval(1*60*60*4);//4个小时
		    String path = servletRequest.getRequestURI();
		   
	      if(path.indexOf("/BusinessFiles/login.jsp") > -1) {// 登陆页面无需过滤
	          chain.doFilter(servletRequest, servletResponse);
	          return;
	      } 
	    // System.out.println(user);
         if (user== null || "".equals(user.getId())) {// 判断如果没有取到员工信息,就跳转到登陆页面
        	 
        	 //System.out.println("退出过滤器");
             PrintWriter out = servletResponse.getWriter();
             out.print("<script type='text/javaScript'>top.window.location.href='/BusinessFiles/login.jsp';</script>");
           
          } else {
             chain.doFilter(request, response);// 已经登陆,继续此次请求
         }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
