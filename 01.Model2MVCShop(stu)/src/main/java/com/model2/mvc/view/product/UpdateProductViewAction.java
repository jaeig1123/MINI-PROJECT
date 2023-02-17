package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.ProductService;

public class UpdateProductViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("여기는 UpdateProductView");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService productservice = new ProductServiceImpl();
		ProductVO productVO = productservice.getProduct(prodNo);
		
		request.setAttribute("pro", productVO);
		
		System.out.println("req 완료 네비 시작--> view.jsp");
		
		return "forward:/product/updateProductView.jsp"; 
	}

}
