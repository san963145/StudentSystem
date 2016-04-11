package com.sys.network.bean;
// default package

import java.util.HashSet;
import java.util.Set;


/**
 * NtSecondaryCategory entity. @author MyEclipse Persistence Tools
 */

public class NtSecondaryCategory  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1020237353936147999L;
	private int categoryId;
     private NtMainCategory ntMainCategory;
     private String categoryName;
     private Set ntSubCategories = new HashSet(0);


    // Constructors

    /** default constructor */
    public NtSecondaryCategory() {
    }

	/** minimal constructor */
    public NtSecondaryCategory(int categoryId, NtMainCategory ntMainCategory) {
        this.categoryId = categoryId;
        this.ntMainCategory = ntMainCategory;
    }
    
    /** full constructor */
    public NtSecondaryCategory(int categoryId, NtMainCategory ntMainCategory, String categoryName, Set ntSubCategories) {
        this.categoryId = categoryId;
        this.ntMainCategory = ntMainCategory;
        this.categoryName = categoryName;
        this.ntSubCategories = ntSubCategories;
    }

   
    // Property accessors

    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public NtMainCategory getNtMainCategory() {
        return this.ntMainCategory;
    }
    
    public void setNtMainCategory(NtMainCategory ntMainCategory) {
        this.ntMainCategory = ntMainCategory;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set getNtSubCategories() {
        return this.ntSubCategories;
    }
    
    public void setNtSubCategories(Set ntSubCategories) {
        this.ntSubCategories = ntSubCategories;
    }
   








}