package com.ztech.stock.util;

public class QueryParam {

	private String paramName;
	private Object min;
	private Object max;
	
	public QueryParam() {}
	
	public QueryParam(String paramName, Object min, Object max) {
		this.paramName = paramName;
		this.min = min;
		this.max = max;
	}

	public Object getMin() {
		return min;
	}

	public void setMin(Object min) {
		this.min = min;
	}

	public Object getMax() {
		return max;
	}

	public void setMax(Object max) {
		this.max = max;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
