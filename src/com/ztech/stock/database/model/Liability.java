package com.ztech.stock.database.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="liability")
public class Liability {

	private int id;
	private Stock stock;
	private long totalCurrentLiability;
	private Date year;
	
	@javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_id", nullable = false)
	public Stock getStock() {
		return stock;
	}
	
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	
	@Column(name = "total_current_liability")
	public long getTotalCurrentLiability() {
		return totalCurrentLiability;
	}
	
	public void setTotalCurrentLiability(long totalCurrentLiability) {
		this.totalCurrentLiability = totalCurrentLiability;
	}
	
	@Column(name = "year")
	public Date getYear() {
		return year;
	}
	
	public void setYear(Date year) {
		this.year = year;
	}
	
}
