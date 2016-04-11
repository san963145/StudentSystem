package com.sys.network.bean;
// default package


/**
 * NtSubCategory entity. @author MyEclipse Persistence Tools
 */

public class NtSubCategory  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 6445154327351518226L;
	private int categoryId;
     private NtSecondaryCategory ntSecondaryCategory;
     private String categoryName;


    // Constructors

    /** default constructor */
    public NtSubCategory() {
    }

	/** minimal constructor */
    public NtSubCategory(int categoryId, NtSecondaryCategory ntSecondaryCategory) {
        this.categoryId = categoryId;
        this.ntSecondaryCategory = ntSecondaryCategory;
    }
    
    /** full constructor */
    public NtSubCategory(int categoryId, NtSecondaryCategory ntSecondaryCategory, String categoryName) {
        this.categoryId = categoryId;
        this.ntSecondaryCategory = ntSecondaryCategory;
        this.categoryName = categoryName;
    }

   
    // Property accessors

    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public NtSecondaryCategory getNtSecondaryCategory() {
        return this.ntSecondaryCategory;
    }
    
    public void setNtSecondaryCategory(NtSecondaryCategory ntSecondaryCategory) {
        this.ntSecondaryCategory = ntSecondaryCategory;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
   








}