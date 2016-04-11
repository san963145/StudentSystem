package com.sys.grades.bean;

import java.io.Serializable;

/**
 * 学生历史的成绩
 * @author Administrator
 *
 */
public class McHistoryGpa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1737308691465248212L;
	//学号
	private String sno;
	//学期
	private int semester;
	//分学期的GPA
	private double  gpasemester;
	//分学期的平均分
	private double averscoresmester;
	//每学期的选课分
	private double selectcreditsemester;
	//每学期获得的学分
	private double hadcreditsemester;
	//每学期的学分获得率
	private double hadcreditratesemester;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public double getGpasemester() {
		return gpasemester;
	}
	public void setGpasemester(double gpasemester) {
		this.gpasemester = gpasemester;
	}
	public double getAverscoresmester() {
		return averscoresmester;
	}
	public void setAverscoresmester(double averscoresmester) {
		this.averscoresmester = averscoresmester;
	}
	public double getSelectcreditsemester() {
		return selectcreditsemester;
	}
	public void setSelectcreditsemester(double selectcreditsemester) {
		this.selectcreditsemester = selectcreditsemester;
	}
	public double getHadcreditsemester() {
		return hadcreditsemester;
	}
	public void setHadcreditsemester(double hadcreditsemester) {
		this.hadcreditsemester = hadcreditsemester;
	}
	public double getHadcreditratesemester() {
		return hadcreditratesemester;
	}
	public void setHadcreditratesemester(double hadcreditratesemester) {
		this.hadcreditratesemester = hadcreditratesemester;
	}
	
	
}
