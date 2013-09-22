package com.ztech.service.stock.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztech.service.stock.StockFilterService;
import com.ztech.stock.dao.StockDao;
import com.ztech.stock.database.model.Stock;
import com.ztech.stock.screen.StockScreen;
import com.ztech.stock.util.QueryParam;


public class StockFilterServiceImpl implements StockFilterService {

	private StockDao stockDao;
	private StockScreen stockScreen;
	
	@Override
	public List<Stock> filterStock(List<QueryParam> paramList, String orderByField) {
		// First screen stock based on key statistics
		List<Stock> stockFirstScreenList = stockDao.filterStock(paramList, orderByField);
		// Second screen stock based on other factors such as income statement and balance sheet
stockScreen.setAssetLiabilityRatio(1.2);
stockScreen.setAssetGrowthRate(0.05);
stockScreen.loadAllScreenCriteria();
		List<Stock> stockSecondScreenList = new ArrayList<Stock>();
		for (Stock stock: stockFirstScreenList) {
			if (stockScreen.isStockPass(stock)) {
				stockSecondScreenList.add(stock);
			}
		}
		return stockSecondScreenList;
	}

	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}
	
	
	
	
	
	
	/*
	 * Test main
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		StockFilterServiceImpl stockFilterService = 
						(StockFilterServiceImpl) ctx.getBean("stockFilterService");

		List<QueryParam> paramList = new ArrayList<QueryParam>();
		paramList.add(new QueryParam("marketCap", 200000000L, 500000000000L));
		paramList.add(new QueryParam("pricePerBook", 0.2, 5.0));
		paramList.add(new QueryParam("pricePerEarning", 3.0, 18.0));
		paramList.add(new QueryParam("yield", 0.20, 50.0));
		paramList.add(new QueryParam("payOutRatio", 1.0, 50.0));
		List<Stock> stockList = stockFilterService.filterStock(paramList, "marketCap");
		
		System.out.println(String.format("%-6s %-40s %-40s %-14s %-19s %-8s %-8s %-8s %-4s %-10s %-4s", 
				"", "COMPANY", "Industry", "SYMBOL", "MARKET CAP", "P/B", "P/E", "YIELD", "Payout", "Asset/Liab", ""));
		for (int i = 0; i < stockList.size(); i++) {
			Stock stock = stockList.get(i);
			System.out.println(String.format("%-6s %-150s", (i + 1) + ". ", stock.toString()));
		}
	}

	public void setStockScreen(StockScreen stockScreen) {
		this.stockScreen = stockScreen;
	}
}
