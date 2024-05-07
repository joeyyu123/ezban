package com.ezban.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	Set<String> ALLOW_LIST = new HashSet<>(Arrays.asList("/login", "/logout"));
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		
		if(ALLOW_LIST.contains(httpRequest.getServletPath()) || 
		           httpRequest.getServletPath().contains(".")) {
		            chain.doFilter(request, response);
		            return;
		        }
		
		boolean isLoggedIn = (session != null && session.getAttribute("memberMail") != null);
		
		if(isLoggedIn) {
			chain.doFilter(request, response);
		}else {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
		}
		
	}

}
