package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		
		System.out.println("init resources : " + getServletConfig().getInitParameter("resources"));
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
		System.out.println("mapper : " + mapper);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		System.out.println("service path = " + path);
		
		try{
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext());
			
	
			System.out.println("req,res 받아오기");
			String resultPage=action.execute(request, response);
			
			System.out.println("resultpage = " + resultPage);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			System.out.println("result = " + result);
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}