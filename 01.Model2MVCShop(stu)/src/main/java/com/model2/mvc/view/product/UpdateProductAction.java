package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.ProductService;

public class UpdateProductAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		ProductVO product = new ProductVO();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("filename"));
		
		ProductService productservice = new ProductServiceImpl();
		productservice.updateProduct(product);
		
		request.setAttribute("pro", product);
		
		
		return "forward:/product/getProduct.jsp"; 
	}

}
