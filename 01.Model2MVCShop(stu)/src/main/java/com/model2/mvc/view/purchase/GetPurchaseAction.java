package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.PurchaseService;

public class GetPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService purservice = new PurchaseServiceImpl();
		PurchaseVO purVO = purservice.getPurchase(tranNo);
		
		request.setAttribute("pur", purVO);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
