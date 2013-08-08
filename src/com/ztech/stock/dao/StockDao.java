package com.ztech.stock.dao;

import java.util.List;

import com.ztech.stock.database.model.Stock;
import com.ztech.stock.util.QueryParam;


public interface StockDao {

	public Stock getStockById(int id);
	
	public List<Stock> getAllStocks();
	
	public void saveStock(Stock stock);
	
	public void updateStock(Stock stock);
	
	public Boolean stockSymbolExist(String symbol);
	
	public Long getStockRowCount();
	
	public List<Stock> filterStock(List<QueryParam> paramList, String orderByField);
}
