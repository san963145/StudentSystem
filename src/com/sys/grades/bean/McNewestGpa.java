package com.sys.grades.bean;

import java.io.Serializable;

/**
 * 学生最新的成绩
 * @author Administrator
 *
 */
public class McNewestGpa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621706462389311470L;
	//学号
	private String sno;
	//最新GPA
	private double newestgpa;
	//挂科门数
	private int failednum;
	//重修门数
	private int restudynum;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public double getNewestgpa() {
		return newestgpa;
	}
	public void setNewestgpa(double newestgpa) {
		this.newestgpa = newestgpa;
	}
	public int getFailednum() {
		return failednum;
	}
	public void setFailednum(int failednum) {
		this.failednum = failednum;
	}
	public int getRestudynum() {
		return restudynum;
	}
	public void setRestudynum(int restudynum) {
		this.restudynum = restudynum;
	}
	
	
}
