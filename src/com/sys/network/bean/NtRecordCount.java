package com.sys.network.bean;
// default package



/**
 * NtRecordCountId entity. @author MyEclipse Persistence Tools
 */

public class NtRecordCount  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -4704581499694733713L;
	private String sno;
     private int subCategoryId;
     private String yearMonth;
     private int onlineTime;
     private int accessCount;
    // Property accessors

    public String getSno() {
        return this.sno;
    }
    
    public void setSno(String sno) {
        this.sno = sno;
    }

    public int getSubCategoryId() {
        return this.subCategoryId;
    }
    
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getYearMonth() {
        return this.yearMonth;
    }
    
    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }


	public int getOnlineTime() {
		return onlineTime;
	}


	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}


	public int getAccessCount() {
		return accessCount;
	}


	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}
    
}