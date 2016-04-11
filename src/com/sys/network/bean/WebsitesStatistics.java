package com.sys.network.bean;
// default package


/**
 * WebsitesStatistics entity. @author MyEclipse Persistence Tools
 */

public class WebsitesStatistics  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	 private static final long serialVersionUID = 146461322322719926L;
	 private String weburl;
     private String type;
     private int subaccesscount;
     private int subid;

    // Constructors

    /** default constructor */
    public WebsitesStatistics() {
    }

	/** minimal constructor */
    public WebsitesStatistics(String weburl) {
        this.weburl = weburl;
    }
    
    /** full constructor */
    public WebsitesStatistics(String weburl, String type, int subaccesscount,int subid) {
        this.weburl = weburl;
        this.type = type;
        this.subaccesscount = subaccesscount;
        this.subid = subid;
    }

   
    // Property accessors

    public String getWeburl() {
        return this.weburl;
    }
    
    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public int getSubaccesscount() {
        return this.subaccesscount;
    }
    
    public void setSubaccesscount(int subaccesscount) {
        this.subaccesscount = subaccesscount;
    }

	public int getSubid() {
		return subid;
	}

	public void setSubid(int subid) {
		this.subid = subid;
	}
    
    
}