package de.rwth.i5.hiwi.model;

public class RAM {

	public int getmB() {
		return mB;
	}

	public void setmB(int mB) {
		this.mB = mB;
	}

	private int serialNo;
	private String manufacturer;
	private int mB;

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
