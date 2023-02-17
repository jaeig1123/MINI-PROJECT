package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.ProductService;

public class AddPurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println();
		System.out.println("=============PurrchaseView ADDPurchase 진입================");
		
		
		int prod_no = Integer.parseInt(request.getParameter("prod_no"));
		
		
		ProductService productservice = new ProductServiceImpl();
		ProductVO proVO = productservice.getProduct(prod_no);
				
		request.setAttribute("pro", proVO);
		
		System.out.println("=============PurrchaseView ADDPurchase 나감================");
		System.out.println();
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
