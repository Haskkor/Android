package com.supinfo.supcrowdfunderandroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Donate implements Serializable{
	
	private long donateId;
	private float amount;
	
	public long getDonateId() {
		return donateId;
	}
	public void setDonateId(long donateId) {
		this.donateId = donateId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
}