package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.ProductService;

public class AddProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("=============Product ¡¯¿‘================");
		
		ProductVO product = new ProductVO();
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("filename"));
		
		System.out.println(product);
		
		ProductService productservice = new ProductServiceImpl();
		productservice.addProduct(product); 
		
		request.setAttribute("pro", product);
		
		return "forward:/product/productView.jsp"; 
	}
	

}
