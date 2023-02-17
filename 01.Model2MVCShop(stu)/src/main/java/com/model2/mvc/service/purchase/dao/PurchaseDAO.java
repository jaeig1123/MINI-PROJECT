package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	
	
	// 구매한 상품 DB등록
	public void insertPurchase(PurchaseVO purohaseVO) throws Exception {
		
		System.out.println();
		System.out.println("==================Purchase insertPurchase 들어옴===================");
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
	
		//stmt.setString(0, purohaseVO.getPaymentOption()); 주문 리스트 번호 tranno
		stmt.setInt(1, purohaseVO.getPurchaseProd().getProdNo()); // 상품 번호 productvo
		stmt.setString(2, purohaseVO.getBuyer().getUserId()); // 사용자 id uservo
		stmt.setString(3, purohaseVO.getPaymentOption()); // 주문 결제 방식
		stmt.setString(4, purohaseVO.getReceiverName()); // 주문자 이름
		stmt.setString(5, purohaseVO.getReceiverPhone()); // 주문자 전화번호
		stmt.setString(6, purohaseVO.getDivyAddr()); // 배송 주소
		stmt.setString(7, purohaseVO.getDivyRequest()); // 배송 요구사항
		stmt.setString(8, "1"); // 구매상태코드 trancode
		//stmt.setString(0, purohaseVO.getDivyRequest()); 구매날짜 sysdate
		stmt.setString(9, purohaseVO.getDivyDate()); // 배송 희망 날짜
		
		
		stmt.executeUpdate();
		
		System.out.println("==================Purchase insertPurchase 나갔음===================");
		System.out.println();
		
		con.close();
	}

	// 구매 상세 정보
	public PurchaseVO findPurchase(int purchase) throws Exception { 
		
		System.out.println();
		System.out.println("==================Purchase findPurchase 들어옴===================");
		
		Connection con = DBUtil.getConnection();

		String sql = "select * from TRANSACTION where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase);

		ResultSet rs = stmt.executeQuery();
		

		PurchaseVO purchaseVO = null;
		ProductService proservice = null;
		UserService userservice = null;
		
		
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			proservice = new ProductServiceImpl();
			userservice = new UserServiceImpl();
						
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(proservice.getProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("ORDER_DATA"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));
		}
		
		
		System.out.println("==================Purchase findPurchase 나감===================");
		System.out.println();
		
		con.close();
		
		return purchaseVO;
	}

	
	// 구매목록 리스트
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String purchaseVO) throws Exception {
		
		System.out.println();
		System.out.println("==================Purchase getPurchaseList 들어옴===================");
		System.out.println();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from TRANSACTION ";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " where TRAN_NO='" + searchVO.getSearchKeyword()
						+ "'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " where BUYER_ID='" + searchVO.getSearchKeyword()
						+ "'";
			}
		}
		sql += " order by TRAN_NO";

		
		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		PurchaseVO purchasevo = null;
		ProductService proservice = null;
		UserService userservice = null;
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				purchasevo = new PurchaseVO();
				proservice = new ProductServiceImpl();
				userservice = new UserServiceImpl();

				purchasevo.setTranNo(rs.getInt("TRAN_NO"));
				purchasevo.setPurchaseProd(proservice.getProduct(rs.getInt("PROD_NO")));
				purchasevo.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
				purchasevo.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				purchasevo.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchasevo.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchasevo.setDivyAddr(rs.getString("DEMAILADDR"));
				purchasevo.setDivyRequest(rs.getString("DLVY_REQUEST"));
				purchasevo.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				purchasevo.setOrderDate(rs.getDate("ORDER_DATA"));
				purchasevo.setDivyDate(rs.getString("DLVY_DATE"));

				list.add(purchasevo);
				if (!rs.next())
					break;
			}
		}
		
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		System.out.println();
		System.out.println("==================Purchase getPurchaseList 나감===================");
		System.out.println();
		
		return map;
	}

	public void updateProduct(ProductVO productVO) throws Exception {
		
		
		Connection con = DBUtil.getConnection();

		String sql = "update PRODUCT set PROD_NAME=?, PROD_DETAIL=?,MANUFACTURE_DAY=?,PRICE=?,IMAGE_FILE=? where PROD_NO=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		System.out.println("==================상품 수정 성공===================");
		
		con.close();
	}

}
