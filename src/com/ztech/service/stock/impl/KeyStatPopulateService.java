package com.ztech.service.stock.impl;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ztech.stock.database.model.Stock;


public class KeyStatPopulateService extends StockInfoPopulateServiceImpl {
	
	private Logger logger = Logger.getLogger(KeyStatPopulateService.class);
	private static final String YAHOO_KEY_STAT_URL_1ST = "http://finance.yahoo.com/q/ks?s=";
	private static final String YAHOO_KEY_STAT_URL_2ND= "+Key+Statistics";
	
	public void populateKeyStat(Stock stock) {
		long marketCap = 0;
		double price = 0.0, pricePerBook = 0.0, pricePerEarning = 0.00, yield = 0.0,
			   payOutRatio = 0.0;
		
		StringBuilder yahooKeyStatUrl = new StringBuilder(YAHOO_KEY_STAT_URL_1ST + 
				stock.getSymbol() + YAHOO_KEY_STAT_URL_2ND);
		logger.info(yahooKeyStatUrl);		
		String htmlDocString = getHtmlWebService().downloadHtml(yahooKeyStatUrl.toString());
		if (htmlDocString == null) {
			return;
		}
		Document doc = Jsoup.parse(htmlDocString);

		// Get price
		Elements priceElement = doc.select("span#yfs_l84_" + stock.getSymbol().toLowerCase());
		
		// This is a valid symbol and information returns OK.
		if (priceElement.size() > 0 && ! doc.toString().contains("no Key Statistics")) {
			String priceString = priceElement.text().trim();
			price = Double.parseDouble(priceString.replace(",", ""));
			
			// Get market cap
			Elements marketCapElement = doc.select("span#yfs_j10_" + stock.getSymbol().toLowerCase());
			String marketCapString = marketCapElement.text();
			if (! marketCapString.equals("N/A") && ! marketCapString.isEmpty()) {
				marketCap = convertMarketCap(marketCapString.replace(",", "").trim());
			}
			// Get P/B value
			Element pricePerBookElement = doc.select("td.yfnc_tabledata1").get(6);
			String pricePerBookString = pricePerBookElement.text();
			if (! pricePerBookString.equals("N/A")) {
				pricePerBook = Double.parseDouble(pricePerBookString.replace(",", ""));
			}
			// Get P/E value
			Element pricePerEarningElement = doc.select("td.yfnc_tabledata1").get(2);
			String pricePerEarningString = pricePerEarningElement.text();
			if (! pricePerEarningString.equals("N/A")) {
				pricePerEarning = Double.parseDouble(pricePerEarningString.replace(",", ""));
			}
			// Get Yield
			Element yieldElement = doc.select("td.yfnc_tabledata1").get(49);
			String yieldString = yieldElement.text();
			if (! yieldString.equals("N/A")) {
				yield = Double.parseDouble(yieldString.replace("%", ""));
			}
			// Get pay out ratio
			Element payOutRatioElement = doc.select("td.yfnc_tabledata1").get(53);
			String payOutRatioString = payOutRatioElement.text(); 
			if (! payOutRatioString.equals("N/A")) {
				payOutRatio = Double.parseDouble(payOutRatioString.replace("%", "").
						replace(",", ""));
			}
			
			stock.setPrice(price);
			stock.setMarketCap(marketCap);
			stock.setPricePerBook(pricePerBook);
			stock.setPricePerEarning(pricePerEarning);
			stock.setYield(yield);
			stock.setPayOutRatio(payOutRatio);
			stockDao.updateStock(stock);
		}
		AbstractStockPopulateService.pause();
	}

	private long convertMarketCap(String marketCapString) {
		long marketCap = 0;
		String marketCapValueString = marketCapString.substring(0, marketCapString.length() - 1);
		if (marketCapString.endsWith("K")) {
			marketCap = (long) (Double.parseDouble(marketCapValueString) * 1000);
		} else if (marketCapString.endsWith("M")) {
			marketCap = (long) (Double.parseDouble(marketCapValueString) * 1000000);
		} else if (marketCapString.endsWith("B")) {
			marketCap = (long) (Double.parseDouble(marketCapValueString) * 1000000000);
		} else {
			marketCap = 0;
		} 
		return marketCap;
	}
}
