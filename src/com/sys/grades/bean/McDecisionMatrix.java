package com.sys.grades.bean;

import java.io.Serializable;

/**
 * 学期判断矩阵
 * @author Administrator
 *
 */
public class McDecisionMatrix implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1721340148761393339L;
	//学院名
	private String collegename;
	//专业号
	private String majoynum;
	//年级
	private int grade;
	//[T1]
	private double T1;
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	//[T2]
	private double T2;
	//[T3]
	private double T3;
	//[T4]
	private double T4;
	public String getCollegename() {
		return collegename;
	}
	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}
	public String getMajoynum() {
		return majoynum;
	}
	public void setMajoynum(String majoynum) {
		this.majoynum = majoynum;
	}
	public double getT1() {
		return T1;
	}
	public void setT1(double t1) {
		T1 = t1;
	}
	public double getT2() {
		return T2;
	}
	public void setT2(double t2) {
		T2 = t2;
	}
	public double getT3() {
		return T3;
	}
	public void setT3(double t3) {
		T3 = t3;
	}
	public double getT4() {
		return T4;
	}
	public void setT4(double t4) {
		T4 = t4;
	}
	
}
