package com.sys.network.bean;
// default package


/**
 * NtWebsites entity. @author MyEclipse Persistence Tools
 */

public class NtWebsites  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1398142396624896781L;
	private int siteId;
     private int subCategoryId;
     private String url;


    // Constructors

    /** default constructor */
    public NtWebsites() {
    }

	/** minimal constructor */
    public NtWebsites(int siteId) {
        this.siteId = siteId;
    }
    
    /** full constructor */
    public NtWebsites(int siteId, int subCategoryId, String url) {
        this.siteId = siteId;
        this.subCategoryId = subCategoryId;
        this.url = url;
    }

   
    // Property accessors

    public int getSiteId() {
        return this.siteId;
    }
    
    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getSubCategoryId() {
        return this.subCategoryId;
    }
    
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
   








}