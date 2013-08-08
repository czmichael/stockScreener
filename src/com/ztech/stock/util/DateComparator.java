package com.ztech.stock.util;

import java.util.Comparator;

import com.ztech.stock.database.model.Asset;

public class DateComparator implements Comparator<Asset> {

	@Override
	public int compare(Asset a1, Asset a2) {
		return a1.getYear().compareTo(a2.getYear());
	}

}
