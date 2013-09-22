package com.ztech.service.stock.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ztech.stock.database.model.Income;
import com.ztech.stock.database.model.Stock;


public class IncomePopulateService extends StockInfoPopulateServiceImpl {
	
	private Logger logger = Logger.getLogger(IncomePopulateService.class);
	private static final String YAHOO_INCOME_URL_1ST = "http://finance.yahoo.com/q/is?s=";
	private static final String YAHOO_INCOME_STAT_URL_2ND= "+Income+Statement&annual";
	private static final String NET_INCOME_TEXT = "Net Income Applicable To Common Shares";
	
	
	public void populateIncome(Stock stock) {

		// TODO: call to get 
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");  
		String thisYear = formatter.format(new java.util.Date());
		int lastYearInt = Integer.parseInt(thisYear) - 1;
		String lastYear = Integer.toString(lastYearInt);
		try {
			
			Income existingIncome = 
					incomeDao.getIncomeByStockAndYear(stock, getDateFromYearString(lastYear));
			
			
			if (existingIncome == null) {
				StringBuilder yahooIncomeUrl = new StringBuilder(YAHOO_INCOME_URL_1ST + 
						stock.getSymbol() + YAHOO_INCOME_STAT_URL_2ND);
				logger.info(yahooIncomeUrl);
				String htmlDocString = getHtmlWebService().downloadHtml(yahooIncomeUrl.toString());
				if (htmlDocString == null) {
					return;
				}
				Document doc = Jsoup.parse(htmlDocString);
				
				String year1, year2, year3;
				long income1, income2, income3;
				Elements yearElements = doc.select("th");
				if (yearElements.size() == 3) {
					year1 = yearElements.get(0).text();
					year2 = yearElements.get(1).text();
					year3 = yearElements.get(2).text();
					
					if (year1.contains("20") && year1.contains(",") ) {
						year1 = year1.split(",")[1].trim();
						year2 = year2.split(",")[1].trim();
						year3 = year3.split(",")[1].trim();

						Elements netIncomeTextEle = doc.getElementsContainingOwnText(NET_INCOME_TEXT);
						if (netIncomeTextEle.size() == 1) {
							Element netIncomeParentEle = netIncomeTextEle.get(0).parent();
							Element netIncome1Ele = netIncomeParentEle.nextElementSibling();
							income1 = parseFigure(netIncome1Ele.text().trim());
							Element netIncome2Ele = netIncome1Ele.nextElementSibling();
							income2 = parseFigure(netIncome2Ele.text().trim());
							Element netIncome3Ele = netIncome2Ele.nextElementSibling();
							income3 = parseFigure(netIncome3Ele.text().trim());
							 
							saveIncome(year1, income1, stock);
							saveIncome(year2, income2, stock);
							saveIncome(year3, income3, stock);
						}
					}
				}
				AbstractStockPopulateService.pause();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void saveIncome(String year, long amount, Stock stock) {
		Income income = new Income();
		income.setStock(stock);
		income.setNetIncome(amount);
		try {
			income.setYear(getDateFromYearString(year));
			incomeDao.saveIncome(income);
		} catch (ParseException e) {
			logger.error("Income object saving failed, year format parsing error for year: " + year);
		}
	}
	
	private Date getDateFromYearString(String year) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		return new Date(formatter.parse(year).getTime());
	}
}
