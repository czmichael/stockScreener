package com.ztech.service.stock.impl;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.ztech.stock.database.model.Industry;
import com.ztech.stock.database.model.Sector;
import com.ztech.stock.database.model.Stock;

public class SectorIndustryPopulateService extends StockInfoPopulateServiceImpl {

	private Logger logger = Logger.getLogger(SectorIndustryPopulateService.class);
	private static final String YAHOO_SECTOR_INDUSTRY_URL_1ST = "http://finance.yahoo.com/q/in?s=";
	private static final String YAHOO_SECTOR_INDUSTRY_URL_2ND = "+Industry";
	
	
	public void populateSectorIndustry(Stock stock) {
		StringBuilder yahooSectorIndustryUrl = new StringBuilder(YAHOO_SECTOR_INDUSTRY_URL_1ST + 
				stock.getSymbol() + YAHOO_SECTOR_INDUSTRY_URL_2ND);
		logger.info(yahooSectorIndustryUrl);
		String htmlDocString = getHtmlWebService().downloadHtml(yahooSectorIndustryUrl.toString());
		if (htmlDocString == null) {
			return;
		}
		Document doc = Jsoup.parse(htmlDocString);
		try {
			
			Element sectorHead = doc.select("th:containsOwn(Sector:)").first();
			Element sectorElement = sectorHead.nextElementSibling();
			String sectorValue = null;
			if (sectorElement != null) {
				sectorValue = sectorElement.text();
			}
			Element industryHead = doc.select("th:containsOwn(Industry:)").first();
			Element industryElement = industryHead.nextElementSibling();
			String industryValue = null;
			if (industryElement != null) {
				industryValue = industryElement.text();
			}
			
			if (sectorValue.trim().length() > 0) {
				Sector sector = sectorDao.getSectorByName(sectorValue);
				stock.setSector(sector);
			}
			if (industryValue.trim().length() > 0) {
				Industry industry = industryDao.getIndustryByName(industryValue);
				stock.setIndustry(industry);
			}
			stockDao.updateStock(stock);
		} catch (Exception e) {
			logger.warn("stock: " + stock.getSymbol() + " Sector/Industry information error.");
		}
		AbstractStockPopulateService.pause();
	}
}
