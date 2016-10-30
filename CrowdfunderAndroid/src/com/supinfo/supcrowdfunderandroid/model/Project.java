package com.supinfo.supcrowdfunderandroid.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Project implements Serializable {
	
	private String projectId;
	private String creator;
	private String name;
	private String creationDate;
	private String completionDate;
	private float amountNeeded;
	private float donationAmount;
	private String description;
	
	public String getProjectId() {
		return projectId;
	}
	
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getCreator() {
		return creator;
	}
	
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getCompletionDate() {
		return completionDate;
	}
	
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	
	public float getAmountNeeded() {
		return amountNeeded;
	}
	
	public void setAmountNeeded(float amountNeeded) {
		this.amountNeeded = amountNeeded;
	}
	
	public float getDonationAmount() {
		return donationAmount;
	}
	
	public void setDonationAmount(float donationAmount) {
		this.donationAmount = donationAmount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}