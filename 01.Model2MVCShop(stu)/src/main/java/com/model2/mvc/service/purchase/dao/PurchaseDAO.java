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
	
	
	// ������ ��ǰ DB���
	public void insertPurchase(PurchaseVO purohaseVO) throws Exception {
		
		System.out.println();
		System.out.println("==================Purchase insertPurchase ����===================");
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
	
		//stmt.setString(0, purohaseVO.getPaymentOption()); �ֹ� ����Ʈ ��ȣ tranno
		stmt.setInt(1, purohaseVO.getPurchaseProd().getProdNo()); // ��ǰ ��ȣ productvo
		stmt.setString(2, purohaseVO.getBuyer().getUserId()); // ����� id uservo
		stmt.setString(3, purohaseVO.getPaymentOption()); // �ֹ� ���� ���
		stmt.setString(4, purohaseVO.getReceiverName()); // �ֹ��� �̸�
		stmt.setString(5, purohaseVO.getReceiverPhone()); // �ֹ��� ��ȭ��ȣ
		stmt.setString(6, purohaseVO.getDivyAddr()); // ��� �ּ�
		stmt.setString(7, purohaseVO.getDivyRequest()); // ��� �䱸����
		stmt.setString(8, "1"); // ���Ż����ڵ� trancode
		//stmt.setString(0, purohaseVO.getDivyRequest()); ���ų�¥ sysdate
		stmt.setString(9, purohaseVO.getDivyDate()); // ��� ��� ��¥
		
		
		stmt.executeUpdate();
		
		System.out.println("==================Purchase insertPurchase ������===================");
		System.out.println();
		
		con.close();
	}

	// ���� �� ����
	public PurchaseVO findPurchase(int purchase) throws Exception { 
		
		System.out.println();
		System.out.println("==================Purchase findPurchase ����===================");
		
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
		
		
		System.out.println("==================Purchase findPurchase ����===================");
		System.out.println();
		
		con.close();
		
		return purchaseVO;
	}

	
	// ���Ÿ�� ����Ʈ
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String purchaseVO) throws Exception {
		
		System.out.println();
		System.out.println("==================Purchase getPurchaseList ����===================");
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
		System.out.println("�ο��� ��:" + total);

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
		System.out.println("==================Purchase getPurchaseList ����===================");
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
		
		System.out.println("==================��ǰ ���� ����===================");
		
		con.close();
	}

}
