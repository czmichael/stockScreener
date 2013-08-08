package com.ztech.stock.database.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;


@Entity
@Table(name="stock")
public class Stock {

	private int id;
	private String company;
	private String symbol;
	private double price;
	private long marketCap;
	private double pricePerBook;
	private double pricePerEarning;
	private double yield;
	private double payOutRatio;
	private List<Income> incomeList = new ArrayList<Income>();
	private List<Asset> assetList = new ArrayList<Asset>();
	private List<Liability> liabilityList = new ArrayList<Liability>();
	private double currentAssetLiabilityRatio = 0.0;
	
	
	@javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "company")
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "symbol")
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "price")
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "market_cap")
	public long getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(long marketCap) {
		this.marketCap = marketCap;
	}

	@Column(name = "price_per_book")
	public double getPricePerBook() {
		return pricePerBook;
	}

	public void setPricePerBook(double pricePerBook) {
		this.pricePerBook = pricePerBook;
	}

	@Column(name = "price_per_earning")
	public double getPricePerEarning() {
		return pricePerEarning;
	}

	public void setPricePerEarning(double pricePerEarning) {
		this.pricePerEarning = pricePerEarning;
	}

	@Column(name = "yield")
	public double getYield() {
		return yield;
	}

	public void setYield(double yield) {
		this.yield = yield;
	}

	@Column(name = "pay_out_ratio")
	public double getPayOutRatio() {
		return payOutRatio;
	}

	public void setPayOutRatio(double payOutRatio) {
		this.payOutRatio = payOutRatio;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	public List<Income> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<Income> incomeList) {
		this.incomeList = incomeList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	public List<Asset> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	public List<Liability> getLiabilityList() {
		return liabilityList;
	}

	public void setLiabilityList(List<Liability> liabilityList) {
		this.liabilityList = liabilityList;
	}

	@Transient
	public double getCurrentAssetLiabilityRatio() {
		return currentAssetLiabilityRatio;
	}

	public void setCurrentAssetLiabilityRatio(double currentAssetLiabilityRatio) {
		this.currentAssetLiabilityRatio = currentAssetLiabilityRatio;
	}

	@Override
	public String toString() {
		return String.format("%-40s %-9s %,20d %8s %8s %8s %8s %8.2f", 
				this.getCompany(), this.getSymbol(), 
				this.getMarketCap(), this.getPricePerBook(),
				this.getPricePerEarning(), this.getYield(),
				this.getPayOutRatio(), this.getCurrentAssetLiabilityRatio());
	}
}
