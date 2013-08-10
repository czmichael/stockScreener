package com.ztech.service.stock.impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztech.stock.database.model.Stock;


public class NyseStockPopulateService extends AbstractStockPopulateService {

	private static final String NYSE_PAGE_URL_PREFIX = 
					"http://www.advfn.com/nyse/newyorkstockexchange.asp?companies=";
	
	public void populate() {
		Character curAlphabet = 'A', lastAlphabet = 'Z';
//curAlphabet = 'Q';
		
		StringBuilder nysePageUrl;
		// Loop through all companies listing page starting from letter 'A' 
		while (curAlphabet <= lastAlphabet) {
			try {
				nysePageUrl = new StringBuilder(NYSE_PAGE_URL_PREFIX + curAlphabet);
				Document doc = Jsoup.parse(getHtmlWebService().downloadHtml(nysePageUrl.toString()));
				// Select table with class 'market'
				Elements companyTable = doc.select("table.market");
				Elements rows = companyTable.select("tr");
				
				// Parse out the company and symbol text from HTML
				String company, symbol;
				for (Element row : rows) {
					Elements links = row.select("a");
					if (links.size() > 1) {
						company = links.get(0).text().trim();
						symbol = links.get(1).text().trim();
						Stock stock = new Stock();
						stock.setCompany(company);
						stock.setSymbol(symbol);
						stockDao.saveStock(stock);
					}
				}
				curAlphabet++;
				// wait 1 second before submitting next HTML request to avoid being blocked.
				Thread.sleep(HTML_PAGE_REQUEST_INTERVAL);
			} catch (InterruptedException e) {
				// that's OK
			}
		}
	}

	/*
	 * Test Main
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		((NyseStockPopulateService) ctx.getBean("nyseStockPopulateService")).populate();
	}
}
