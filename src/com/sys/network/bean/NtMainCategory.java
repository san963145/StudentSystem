package com.sys.network.bean;
// default package

import java.util.HashSet;
import java.util.Set;

/**
 * NtMainCategory entity. @author MyEclipse Persistence Tools
 */

public class NtMainCategory  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = -386411250328063717L;
	private int categoryId;
     private String categoryName;
     private Set ntSecondaryCategories = new HashSet(0);


    // Constructors

    /** default constructor */
    public NtMainCategory() {
    }

	/** minimal constructor */
    public NtMainCategory(int categoryId) {
        this.categoryId = categoryId;
    }
    
    /** full constructor */
    public NtMainCategory(int categoryId, String categoryName, Set ntSecondaryCategories) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.ntSecondaryCategories = ntSecondaryCategories;
    }

   
    // Property accessors

    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set getNtSecondaryCategories() {
        return this.ntSecondaryCategories;
    }
    
    public void setNtSecondaryCategories(Set ntSecondaryCategories) {
        this.ntSecondaryCategories = ntSecondaryCategories;
    }
   








}