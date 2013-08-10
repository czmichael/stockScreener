package com.ztech.service.stock.impl;

import javax.xml.ws.Service;

import com.ztech.service.stock.StockPopulateService;
import com.ztech.stock.dao.AssetDao;
import com.ztech.stock.dao.IncomeDao;
import com.ztech.stock.dao.IndustryDao;
import com.ztech.stock.dao.LiabilityDao;
import com.ztech.stock.dao.SectorDao;
import com.ztech.stock.dao.StockDao;
import com.ztech.web.service.HtmlWebServiceFactory;
import com.ztech.web.service.WebServiceFactory;
import com.ztech.web.service.html.HtmlWebService;


public abstract class AbstractStockPopulateService implements StockPopulateService{

	static final int HTML_PAGE_REQUEST_INTERVAL = 3000;
	StockDao stockDao;
	IncomeDao incomeDao;
	AssetDao assetDao;
	LiabilityDao liabilityDao;
	SectorDao sectorDao;
	IndustryDao industryDao; 
	
	@Override
	public void populateStockDatabse() {}
	
	public void setStockDao(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	public void setIncomeDao(IncomeDao incomeDao) {
		this.incomeDao = incomeDao;
	}

	public void setAssetDao(AssetDao assetDao) {
		this.assetDao = assetDao;
	}
	
	public void setLiabilityDao(LiabilityDao liabilityDao) {
		this.liabilityDao = liabilityDao;
	}

	public void setSectorDao(SectorDao sectorDao) {
		this.sectorDao = sectorDao;
	}
	
	public void setIndustryDao(IndustryDao industryDao) {
		this.industryDao = industryDao;
	}
	
	public HtmlWebService getHtmlWebService() {
		WebServiceFactory fac = new HtmlWebServiceFactory();
		Service service = fac.obtainService();
		return service.getPort(HtmlWebService.class);
	}

	public static void pause(int pauseTimeInMicroSec) {
		try {
			Thread.sleep(pauseTimeInMicroSec);
		} catch (InterruptedException e) {} 
	}
	
	/*
	 * Yahoo HTML finance figure looks like this:
	 * <strong> 1,274,400&nbsp;&nbsp; </strong>
	 */
	protected long parseFigure(String figure) {
		figure = figure.trim().replace(",", "").replace("\u00a0", "");
		if (! figure.contains("-")) {
			if (figure.startsWith("(")) {
				figure = figure.replace("(","").replace(")", "");
				return 0 - (Long.parseLong(figure.trim()) * 1000 );
			} else {
				return Long.parseLong(figure.trim()) * 1000;
			}
		} else {
			return 0;
		}
	}
}