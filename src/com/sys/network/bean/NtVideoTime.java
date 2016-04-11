package com.sys.network.bean;
// default package

/**
 * NtVideoTime entity. @author MyEclipse Persistence Tools
 */

public class NtVideoTime implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -880632177032992563L;
	private String sno;
	private String yearMonth;
	private int onlineTime;

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public int getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

}