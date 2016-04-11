package com.sys.grades.bean;

import java.io.Serializable;

    /**
	 * 存储   按学院查询的计算结果
	 * 
	 */
public class GradeSearchByGroup implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String department;
	String majorNum;
	int grade;
	int semester;
	double gpa;
	double avg;
	double selected;
	double had;
	double hadRate;
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMajorNum() {
		return majorNum;
	}
	public void setMajorNum(String majorNum) {
		this.majorNum = majorNum;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public double getSelected() {
		return selected;
	}
	public void setSelected(double selected) {
		this.selected = selected;
	}
	public double getHad() {
		return had;
	}
	public void setHad(double had) {
		this.had = had;
	}
	public double getHadRate() {
		return hadRate;
	}
	public void setHadRate(double hadRate) {
		this.hadRate = hadRate;
	}
	

}
