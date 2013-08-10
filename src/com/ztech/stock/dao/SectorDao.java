package com.ztech.stock.dao;

import com.ztech.stock.database.model.Sector;

public interface SectorDao {
	
	public Sector getSectorByName(String name);
	public void saveSectory(Sector sector);
}
