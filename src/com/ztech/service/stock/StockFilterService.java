package com.ztech.service.stock;

import java.util.List;

import com.ztech.stock.database.model.Stock;
import com.ztech.stock.util.QueryParam;


public interface StockFilterService {

	public List<Stock> filterStock(List<QueryParam> paramList, String orderByField);
}
