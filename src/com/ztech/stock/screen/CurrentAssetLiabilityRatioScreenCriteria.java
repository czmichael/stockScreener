package com.ztech.stock.screen;

import java.util.List;

import com.ztech.stock.database.model.Asset;
import com.ztech.stock.database.model.Liability;
import com.ztech.stock.database.model.Stock;

public class CurrentAssetLiabilityRatioScreenCriteria implements StockScreenCriteria {

	private double ratio = 1.0;
	
	@Override
	public boolean isStockPass(Stock stock) {
		boolean isPass = false;
		List<Asset> stockAssetList = stock.getAssetList();
		List<Liability> stockLiabilityList = stock.getLiabilityList();
		if (stockAssetList.size() > 0 && stockLiabilityList.size() > 0) {
			Asset asset = stockAssetList.get(0);
			Liability liability = stockLiabilityList.get(0);
			if (asset.getYear().compareTo(liability.getYear()) == 0) {
				long currentAsset = asset.getTotalCurrentAsset();
				long currentliability = liability.getTotalCurrentLiability();
				if (currentAsset > 0 && currentliability > 0) {
					double ratio = (double) currentAsset / (double) currentliability;
					stock.setCurrentAssetLiabilityRatio(ratio);
					isPass = (ratio >= this.ratio);
				}
			}
		}
		return isPass;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
}