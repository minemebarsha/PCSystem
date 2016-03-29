package de.rwth.i5.hiwi.model;

import java.math.BigDecimal;

public class CPU {

	private int serialNo;
	private String manufacturer;
	private BigDecimal mHz;
	
	public BigDecimal getmHz() {
		return mHz;
	}
	public void setmHz(BigDecimal mHz) {
		this.mHz = mHz;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	
}
