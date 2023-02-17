package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.PurchaseService;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	private ProductDAO productDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
		productDAO = new ProductDAO();
	}

	public PurchaseVO addPurchase(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);
		return purchaseVO;
	}

	public PurchaseVO getPurchase(int purchaseVO) throws Exception{
		return purchaseDAO.findPurchase(purchaseVO);
	}

	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) throws Exception {
		return null;
	}

	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {

	}

	public HashMap<String, Object>  getPurchaseList(SearchVO searchVO, String purchaseVO) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO, purchaseVO);
	}

	public HashMap<String, Object>  getSaleList(SearchVO serachVO) throws Exception {
		return null;
	}

}
