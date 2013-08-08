package com.ztech.stock.screen;

import java.util.ArrayList;
import java.util.List;

import com.ztech.stock.database.model.Stock;


public class StockScreen {

	private double assetLiabilityRatio;
	private double assetGrowthRate;
	private CurrentAssetLiabilityRatioScreenCriteria currentAssetLiabilityRatioScreenCriteria; 
	private ConsecutiveAssetIncreaseScreenCriteria consecutiveAssetIncreaseScreenCriteria;
	
	private List<StockScreenCriteria> screenCriteriaList = new ArrayList<StockScreenCriteria>();
	
	public void loadAllScreenCriteria() {
		currentAssetLiabilityRatioScreenCriteria.setRatio(assetLiabilityRatio);
		consecutiveAssetIncreaseScreenCriteria.setGrowthPercentage(assetGrowthRate);
		screenCriteriaList.add(currentAssetLiabilityRatioScreenCriteria);
		screenCriteriaList.add(consecutiveAssetIncreaseScreenCriteria);
		
	}
	
	public boolean isStockPass(Stock stock) {
		boolean isPass = true;
System.out.println("\n\nstock: " + stock.getSymbol());		
		for (StockScreenCriteria criteria: screenCriteriaList) {

			// If any one criteria fails, fail the stock.
			if (! criteria.isStockPass(stock)) {
System.out.println("stock :" + stock.getSymbol() + " failed on " + criteria.getClass().toString());				
				isPass = false;
				break;
			}
		}
		return isPass;
	}

	public double getAssetLiabilityRatio() {
		return assetLiabilityRatio;
	}

	public void setAssetLiabilityRatio(double assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}

	public double getAssetGrowthRate() {
		return assetGrowthRate;
	}

	public void setAssetGrowthRate(double assetGrowthRate) {
		this.assetGrowthRate = assetGrowthRate;
	}

	public void setCurrentAssetLiabilityRatioScreenCriteria(
			CurrentAssetLiabilityRatioScreenCriteria currentAssetLiabilityRatioScreenCriteria) {
		this.currentAssetLiabilityRatioScreenCriteria = currentAssetLiabilityRatioScreenCriteria;
	}

	public void setConsecutiveAssetIncreaseScreenCriteria(
			ConsecutiveAssetIncreaseScreenCriteria consecutiveAssetIncreaseScreenCriteria) {
		this.consecutiveAssetIncreaseScreenCriteria = consecutiveAssetIncreaseScreenCriteria;
	}
}
