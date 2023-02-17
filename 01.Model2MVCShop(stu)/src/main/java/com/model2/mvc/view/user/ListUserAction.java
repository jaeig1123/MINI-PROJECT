package com.model2.mvc.view.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


public class ListUserAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		SearchVO searchVO=new SearchVO();
		
		System.out.println("====================��ġ�κн���========================");
		
		int page=1;
		String menu = request.getParameter("menu");
		System.out.println("page Ȯ�� : " + request.getParameter("page"));
		if(request.getParameter("page") != null)
			page=Integer.parseInt(request.getParameter("page"));
		
		searchVO.setPage(page);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		System.out.println("��ġ Ȯ�� 1 : " + request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("��ġ Ȯ�� 2 : " + request.getParameter("searchKeyword"));
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		searchVO.setPageUnit(Integer.parseInt(pageUnit));
		
		UserService service=new UserServiceImpl();
		HashMap<String,Object> map=service.getUserList(searchVO);

		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		System.out.println(menu);
		request.setAttribute("menu", menu);
		
		System.out.println("====================��ġ�κг�========================");
		
		return "forward:/user/listUser.jsp";
	}
}