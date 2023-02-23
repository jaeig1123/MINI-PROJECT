package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.ProductService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	
	
	// ������ ��ǰ DB���
	public void insertPurchase(PurchaseVO purchase) throws Exception {
		
		System.out.println();
		System.out.println("==================Purchase insertPurchase ����===================");
		
		Connection con = DBUtil.getConnection();

		String sql = "insert into TRANSACTION values (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement stmt = con.prepareStatement(sql);
	
		//stmt.setString(0, purohaseVO.getPaymentOption()); �ֹ� ����Ʈ ��ȣ tranno
		ProductVO product = new ProductVO();
		UserVO user =new UserVO();
		stmt.setInt(1, product.getProdNo()); // ��ǰ ��ȣ productvo
		stmt.setString(2, user.getUserId()); // ����� id uservo
		stmt.setString(3, purchase.getPaymentOption()); // �ֹ� ���� ���
		stmt.setString(4, purchase.getReceiverName()); // �ֹ��� �̸�
		stmt.setString(5, purchase.getReceiverPhone()); // �ֹ��� ��ȭ��ȣ
		stmt.setString(6, purchase.getDivyAddr()); // ��� �ּ�
		stmt.setString(7, purchase.getDivyRequest()); // ��� �䱸����
		stmt.setString(8, purchase.getTranCode()); // ���Ż����ڵ� trancode
		stmt.setString(9, purchase.getDivyDate().replace("-", "")); // ��� ��� ��¥
		
		
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

		String sql = "SELECT"
				+ "prod_no, buyer_id, payment_option, receiver_name, receiver_phone, dlvy_addr, dlvy_request, dlvy_date, order_date"
				+ "FROM"
				+ "transaction"
				+ "WHERE"
				+ "tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase);

		ResultSet rs = stmt.executeQuery();
		

		PurchaseVO pur = null;
		ProductService proservice = null;
		UserService userservice = null;
		
		
		while (rs.next()) {
			pur = new PurchaseVO();
			proservice = new ProductServiceImpl();
			userservice = new UserServiceImpl();
						
			pur.setPurchaseProd(proservice.getProduct(rs.getInt("prod_no")));
			pur.setBuyer(userservice.getUser(rs.getString("buyer_id")));
			pur.setPaymentOption(rs.getString("payment_option"));
			pur.setReceiverName(rs.getString("receiver_name"));
			pur.setReceiverPhone(rs.getString("receiver_phone"));
			pur.setDivyAddr(rs.getString("dlvy_addr"));
			pur.setDivyDate(rs.getString("dlvy_date"));
			pur.setOrderDate(rs.getDate("order_date"));
			
		}
		
		
		System.out.println("==================Purchase findPurchase ����===================");
		System.out.println();
		
		con.close();
		
		return pur;
	}

	
	// ���Ÿ�� ����Ʈ
	public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String purchase) throws Exception {
		
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

		PurchaseVO pur = null;
		ProductService proservice = null;
		UserService userservice = null;
		
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				pur = new PurchaseVO();
				proservice = new ProductServiceImpl();
				userservice = new UserServiceImpl();

				pur.setTranNo(rs.getInt("TRAN_NO"));
				pur.setPurchaseProd(proservice.getProduct(rs.getInt("PROD_NO")));
				pur.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
				pur.setPaymentOption(rs.getString("PAYMENT_OPTION"));
				pur.setReceiverName(rs.getString("RECEIVER_NAME"));
				pur.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				pur.setDivyAddr(rs.getString("DEMAILADDR"));
				pur.setDivyRequest(rs.getString("DLVY_REQUEST"));
				pur.setTranCode(rs.getString("TRAN_STATUS_CODE"));
				pur.setOrderDate(rs.getDate("ORDER_DATA"));
				pur.setDivyDate(rs.getString("DLVY_DATE").replace("-", ""));

				list.add(pur);
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

	public void updatePurchase(PurchaseVO purchase) throws Exception {
		
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, dlvy_addr=?, dlvy_request=?, dlvy_date=? where tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		ProductService proservice = new ProductServiceImpl();
		UserService userservice = new UserServiceImpl();
		
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.executeUpdate();
		
		System.out.println("==================��ǰ ���� ����===================");
		
		con.close();
	}

	// ���Ÿ�� ����Ʈ
		public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {
			
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

			PurchaseVO pur = null;
			ProductService proservice = null;
			UserService userservice = null;
			
			ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
			if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					pur = new PurchaseVO();
					proservice = new ProductServiceImpl();
					userservice = new UserServiceImpl();

					pur.setTranNo(rs.getInt("TRAN_NO"));
					pur.setPurchaseProd(proservice.getProduct(rs.getInt("PROD_NO")));
					pur.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
					pur.setPaymentOption(rs.getString("PAYMENT_OPTION"));
					pur.setReceiverName(rs.getString("RECEIVER_NAME"));
					pur.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
					pur.setDivyAddr(rs.getString("DEMAILADDR"));
					pur.setDivyRequest(rs.getString("DLVY_REQUEST"));
					pur.setTranCode(rs.getString("TRAN_STATUS_CODE"));
					pur.setOrderDate(rs.getDate("ORDER_DATA"));
					pur.setDivyDate(rs.getString("DLVY_DATE").replace("-", ""));

					list.add(pur);
					if (!rs.next())
						break;
					
					
				}
			}
			
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
			
			return map;
			
		}
		
}
	
