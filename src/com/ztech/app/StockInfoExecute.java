package com.ztech.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztech.service.stock.impl.StockInfoPopulateServiceImpl;
import com.ztech.stock.util.OperationalMode;


/**
 * This class is the entry point to either gather stock information from scratch 
 * or just perform an update operation on information that changes over time.
 */
public class StockInfoExecute {
	
	public static void main(String[] args) {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		StockInfoPopulateServiceImpl stockPopulateService = 
				((StockInfoPopulateServiceImpl) ctx.getBean("stockInfoPopulateService"));
		
stockPopulateService.setStartSymbol("AAPL");
stockPopulateService.setOperationalMode(OperationalMode.TEST);
//stockPopulateService.setOperationalMode(OperationalMode.UPDATE);
		stockPopulateService.populateStockDatabse();
	}

}
