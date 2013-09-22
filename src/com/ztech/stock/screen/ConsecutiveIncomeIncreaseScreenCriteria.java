//package com.ztech.stock.screen;
//
//import java.util.Collections;
//import java.util.List;
//
//import com.ztech.stock.database.model.Asset;
//import com.ztech.stock.database.model.Income;
//import com.ztech.stock.database.model.Stock;
//import com.ztech.stock.util.DateComparator;
//
//public class ConsecutiveIncomeIncreaseScreenCriteria implements StockScreenCriteria {
//
//	private double growthPercentage = 0.0;
//	
//	@Override
//	public boolean isStockPass(Stock stock) {
//		double yearToYearGrowthRate = 0;
//		List<Income> incomeList = stock.getIncomeList();
//		Collections.sort(incomeList, new DateComparator());
//
//
//		if (incomeList.size() > 2) {
//			boolean isPass = false;
//			for (int i = 0; i < incomeList.size() - 1; i++) {
//				double prevIncome = incomeList.get(i).getNetIncome();
//				double currIncome = incomeList.get(i+1).getNetIncome();
//				if (prevIncome > 0 && currIncome > 0) {
//					yearToYearGrowthRate = (currIncome - prevIncome) / prevIncome;
//					if (yearToYearGrowthRate < growthPercentage) {
//						return false;
//					} else {
//						continue;
//					}
//				} else {
//					return false;
//				}
//			}
//			// After passing all years' test
//			return true;
//			
//		} else {
//			return false;
//		}
//	}
//
//	public double getGrowthPercentage() {
//		return growthPercentage;
//	}
//
//	public void setGrowthPercentage(double growthPercentage) {
//		this.growthPercentage = growthPercentage;
//	}
//}
