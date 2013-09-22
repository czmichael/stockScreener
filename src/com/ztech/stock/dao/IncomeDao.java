package com.ztech.stock.dao;


import java.sql.Date;

import com.ztech.stock.database.model.Income;
import com.ztech.stock.database.model.Stock;


public interface IncomeDao {

	public Income getIncomeByStockAndYear(Stock stock, Date year);
	public void saveIncome(Income income);
}
