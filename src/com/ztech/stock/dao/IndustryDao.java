package com.ztech.stock.dao;

import com.ztech.stock.database.model.Industry;


public interface IndustryDao {
	
	public Industry getIndustryByName(String name);
	public void saveIndustry(Industry industry);
}
