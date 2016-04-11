package com.sys.grades.bean;

import java.io.Serializable;

/**
 * 学院-专业联系表
 * @author Administrator
 *
 */
public class McDept implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8025777120100942912L;
	//学院名
	private String collegename;
	//专业名称
	private String majorname;
	//专业号
	private String majornum;
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public String getMajornum() {
		return majornum;
	}
	public void setMajornum(String majornum) {
		this.majornum = majornum;
	}
	
	
}
