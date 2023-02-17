package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.ProductService;
import com.model2.mvc.service.user.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println();
		System.out.println("=============Purrchase Action Action Action 진입================");
		
		
		PurchaseVO purchase = new PurchaseVO();
		
		ProductService proservice = new ProductServiceImpl();
		UserService userservice = new UserServiceImpl();
		
		//상품번호, 상품명, 상품상세정보, 제조일자, 가격, 등록일자	ProductVO
		//구매자아이디	UserVO
		purchase.setPurchaseProd(proservice.getProduct(Integer.parseInt(request.getParameter("prodNo"))));	
		purchase.setBuyer(userservice.getUser(request.getParameter("buyerId")));		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		
		
		PurchaseService purchaseservice = new PurchaseServiceImpl();
		purchaseservice.addPurchase(purchase); 
		
		request.setAttribute("pur", purchase);
		
		
		System.out.println("=============Purrchase Action Action Action 나감================");
		System.out.println();
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
