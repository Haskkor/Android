package com.supinfo.supcrowdfunderandroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Category implements Serializable {

	private String catId;
	private String cname;	
	private String cdesc;

	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCdesc() {
		return cdesc;
	}
	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}
}