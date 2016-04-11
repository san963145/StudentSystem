package com.sys.network.bean;
// default package

/**
 * NtGameTime entity. @author MyEclipse Persistence Tools
 */

public class NtGameTime  implements java.io.Serializable {


    // Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1780486190647294669L;
	private String sno;
    private String yearMonth;
    private int onlineTime;


    public int getOnlineTime() {
        return this.onlineTime;
    }
    
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

	public void setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
    }
   








}