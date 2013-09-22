package com.ztech.service.stock.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztech.stock.database.model.Stock;
import com.ztech.stock.util.OperationalMode;


public class StockInfoPopulateServiceImpl extends AbstractStockPopulateService  {

	private Logger logger = Logger.getLogger(StockInfoPopulateServiceImpl.class);
	protected String operationalMode = null;
	private String startSymbol = null;
	private KeyStatPopulateService keyStatPopulateService;
	private SectorIndustryPopulateService sectorIndustryPopulateService;
	private IncomePopulateService incomePopulateService;
	private BalanceSheetPopulateService balanceSheetPopulateService;
	
	
	@Override
	public void populateStockDatabse() {
		boolean found = false;
		
		// Populate other fields
		List<Stock> stockList = stockDao.getAllStocks();
		for (Stock stock: stockList) {
			if (startSymbol != null && ! stock.getSymbol().equals(startSymbol) && ! found) {
				logger.info("Skipping stock: " + stock.getSymbol());
				continue;
			} else {
				found = true;
			}	
			
			keyStatPopulateService.populateKeyStat(stock);
			incomePopulateService.populateIncome(stock);
			balanceSheetPopulateService.populateAssetAndLiability(stock);
			
			if (operationalMode.equals(OperationalMode.CREATE)) {
				sectorIndustryPopulateService.populateSectorIndustry(stock);
			}

			if (operationalMode.equals(OperationalMode.TEST)) {
				logger.info("App in TEST mode, done.");
				break;
			}
		}
	}
	
	public void setKeyStatPopulateService(KeyStatPopulateService keyStatPopulateService) {
		this.keyStatPopulateService = keyStatPopulateService;
	}

	public void setSectorIndustryPopulateService(
			SectorIndustryPopulateService sectorIndustryPopulateService) {
		this.sectorIndustryPopulateService = sectorIndustryPopulateService;
	}
	
	public void setIncomePopulateService(IncomePopulateService incomePopulateService) {
		this.incomePopulateService = incomePopulateService;
	}

	public void setBalanceSheetPopulateService(
			BalanceSheetPopulateService balanceSheetPopulateService) {
		this.balanceSheetPopulateService = balanceSheetPopulateService;
	}

	public String getStartSymbol() {
		return startSymbol;
	}

	public void setStartSymbol(String startSymbol) {
		this.startSymbol = startSymbol;
	}

	public String getOperationalMode() {
		return operationalMode;
	}

	public void setOperationalMode(String operationalMode) {
		this.operationalMode = operationalMode;
	}
}