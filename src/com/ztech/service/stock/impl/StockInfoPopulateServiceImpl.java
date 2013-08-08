package com.ztech.service.stock.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztech.stock.database.model.Stock;


public class StockInfoPopulateServiceImpl extends AbstractStockPopulateService  {

	private KeyStatPopulateService keyStatPopulateService;
	private IncomePopulateService incomePopulateService;
	private BalanceSheetPopulateService balanceSheetPopulateService;
	
	@Override
	public void populateStockDatabse() {
		// Populate stock symbols first
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		((NyseStockPopulateService) ctx.getBean("nyseStockPopulateService")).populate();
//		((NasdaqStockPopulateService) ctx.getBean("nasdaqStockPopulateService")).populate();
//		((AmexStockPopulateService) ctx.getBean("amexStockPopulateService")).populate();
		
		// Populate other fields
		List<Stock> stockList = stockDao.getAllStocks();
String startSymbol = "CWBS";
boolean found = false;
		for (Stock stock: stockList) {
			
if (! stock.getSymbol().equals(startSymbol) && ! found) {
	continue;
} else {
	found = true;
}	
			keyStatPopulateService.populateKeyStat(stock);
			AbstractStockPopulateService.pause(HTML_PAGE_REQUEST_INTERVAL);
			
			incomePopulateService.populateIncome(stock);
			AbstractStockPopulateService.pause(HTML_PAGE_REQUEST_INTERVAL);

			balanceSheetPopulateService.populateAssetAndLiability(stock);
			AbstractStockPopulateService.pause(HTML_PAGE_REQUEST_INTERVAL);


//break;
		}
	}
	
	public void setKeyStatPopulateService(KeyStatPopulateService keyStatPopulateService) {
		this.keyStatPopulateService = keyStatPopulateService;
	}
	
	public void setIncomePopulateService(IncomePopulateService incomePopulateService) {
		this.incomePopulateService = incomePopulateService;
	}

	public void setBalanceSheetPopulateService(
			BalanceSheetPopulateService balanceSheetPopulateService) {
		this.balanceSheetPopulateService = balanceSheetPopulateService;
	}

	/*
	 * Test Main
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		((StockInfoPopulateServiceImpl) ctx.getBean("stockInfoPopulateService")).populateStockDatabse();
	}
}