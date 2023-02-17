package com.model2.mvc.service.user;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public interface PurchaseService {
	
	public PurchaseVO addPurchase(PurchaseVO purchaseVO) throws Exception;
	
	public PurchaseVO getPurchase(int PurchaseVO) throws Exception;
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String PurchaseVO) throws Exception;
	
	public HashMap<String, Object> getSaleList(SearchVO serachVO) throws Exception;
	
	public PurchaseVO updatePurchase(PurchaseVO purchaseVO) throws Exception;
	
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception;
	
}
