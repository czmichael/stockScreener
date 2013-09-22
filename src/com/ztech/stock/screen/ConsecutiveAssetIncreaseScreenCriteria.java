package com.ztech.stock.screen;

import java.util.Collections;
import java.util.List;

import com.ztech.stock.database.model.Asset;
import com.ztech.stock.database.model.Stock;
import com.ztech.stock.util.DateComparator;

public class ConsecutiveAssetIncreaseScreenCriteria implements StockScreenCriteria {

	private double growthPercentage = 0.0;
	
	@Override
	public boolean isStockPass(Stock stock) {
		double yearToYearGrowthRate = 0;
		List<Asset> assetList = stock.getAssetList();
		Collections.sort(assetList, new DateComparator());


		if (assetList.size() > 2) {
			for (int i = 0; i < assetList.size() - 1; i++) {
				double prevAsset = assetList.get(i).getTotalCurrentAsset();
				double currAsset = assetList.get(i+1).getTotalCurrentAsset();
				if (prevAsset > 0 && currAsset > 0) {
					yearToYearGrowthRate = (currAsset - prevAsset) / prevAsset;
					if (yearToYearGrowthRate < growthPercentage) {
						return false;
					} else {
						continue;
					}
				} else {
					return false;
				}
			}
			// After passing all years' test
			return true;
			
		} else {
			return false;
		}
	}

	public double getGrowthPercentage() {
		return growthPercentage;
	}

	public void setGrowthPercentage(double growthPercentage) {
		this.growthPercentage = growthPercentage;
	}
}
