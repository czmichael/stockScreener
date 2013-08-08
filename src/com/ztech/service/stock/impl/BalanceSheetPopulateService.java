package com.ztech.service.stock.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ztech.stock.dao.AssetDao;
import com.ztech.stock.database.model.Asset;
import com.ztech.stock.database.model.Income;
import com.ztech.stock.database.model.Liability;
import com.ztech.stock.database.model.Stock;

public class BalanceSheetPopulateService extends StockInfoPopulateServiceImpl {
	
	private Logger logger = Logger.getLogger(BalanceSheetPopulateService.class);
	private static final String YAHOO_BALANCE_SHEET_URL_1ST = "http://finance.yahoo.com/q/bs?s=";
	private static final String YAHOO_BALANCE_SHEET_URL_2ND = "+Balance+Sheet&annual";
	private static final String TOTAL_CURRENT_ASSETS_TEXT = "Total Current Assets";
	private static final String TOTAL_CURRENT_LIABILITIES_TEXT = "Total Current Liabilities";
	
	public void populateAssetAndLiability(Stock stock) {
		StringBuilder yahooBalanceSheetUrl = new StringBuilder(YAHOO_BALANCE_SHEET_URL_1ST + 
				stock.getSymbol() + YAHOO_BALANCE_SHEET_URL_2ND);
		logger.info(yahooBalanceSheetUrl);
		String htmlDocString = getHtmlWebService().downloadHtml(yahooBalanceSheetUrl.toString());
		if (htmlDocString == null) {
			return;
		}
		Document doc = Jsoup.parse(htmlDocString);

		String year1, year2, year3;
		long asset1 = 0, asset2 = 0, asset3 = 0, 
			 liability1 = 0, liability2 = 0, liability3 = 0;
		
		Elements periodEle = doc.getElementsContainingOwnText("Period Ending");
		if (periodEle.size() == 1) {
			Element year1Ele = periodEle.get(0).parent().parent().nextElementSibling();
			if (year1Ele != null && year1Ele.toString().contains("20")) {
				Element year2Ele = year1Ele.nextElementSibling();
				if (year2Ele != null && year2Ele.toString().contains("20")) {
					Element year3Ele = year2Ele.nextElementSibling();
					if (year3Ele != null && year3Ele.toString().contains("20")) {
						year1 = year1Ele.text().split(",")[1].trim();
						year2 = year2Ele.text().split(",")[1].trim();
						year3 = year3Ele.text().split(",")[1].trim();
						
						Elements currentAssetEle = doc.getElementsContainingOwnText(TOTAL_CURRENT_ASSETS_TEXT);
						if (currentAssetEle.size() == 1) {
							Element asset1Ele = currentAssetEle.get(0).parent().nextElementSibling();
							if (asset1Ele != null) {
								Element asset2Ele = asset1Ele.nextElementSibling();
								Element asset3Ele = asset2Ele.nextElementSibling();
								asset1 = parseFigure(asset1Ele.text().trim());
								asset2 = parseFigure(asset2Ele.text().trim());
								asset3 = parseFigure(asset3Ele.text().trim());
							}
						}
						
						Elements currentLiabilityEle = doc.getElementsContainingOwnText(TOTAL_CURRENT_LIABILITIES_TEXT);
						if (currentLiabilityEle.size() == 1) {
							Element liability1Ele = currentLiabilityEle.get(0).parent().nextElementSibling();
							if (liability1Ele != null) {
								Element liability2Ele = liability1Ele.nextElementSibling();
								Element liability3Ele = liability2Ele.nextElementSibling();
								liability1 = parseFigure(liability1Ele.text().trim());
								liability2 = parseFigure(liability2Ele.text().trim());
								liability3 = parseFigure(liability3Ele.text().trim());
							}
						}
						
						saveAsset(year1, asset1, stock);
						saveAsset(year2, asset2, stock);
						saveAsset(year3, asset3, stock);
						
						saveLiability(year1, liability1, stock);
						saveLiability(year2, liability2, stock);
						saveLiability(year3, liability3, stock);
					}
				}
			}
		}
	}
	
	private void saveAsset(String year, long amount, Stock stock) {
		Asset asset = new Asset();
		asset.setStock(stock);
		asset.setTotalCurrentAsset(amount);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date utilYear;
		try {
			utilYear = new Date(formatter.parse(year).getTime());
			asset.setYear(utilYear);
			assetDao.saveAsset(asset);
		} catch (ParseException e) {
			logger.error("Asset object saving failed, year format parsing error for year: " + year);
		}
	}
	
	private void saveLiability(String year, long amount, Stock stock) {
		Liability liability = new Liability();
		liability.setStock(stock);
		liability.setTotalCurrentLiability(amount);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Date utilYear;
		try {
			utilYear = new Date(formatter.parse(year).getTime());
			liability.setYear(utilYear);
			liabilityDao.saveLiability(liability);
		} catch (ParseException e) {
			logger.error("Liability object saving failed, year format parsing error for year: " + year);
		}
	}
	
}
