package com.sys.grades.bean;

    /**
	 * 学生基本信息表
	 * 
	 */
public class DeptLocator {

	//学号
	private String sno;
	//学生姓名
	private String sname;
	//地方
	private String fromprovince;
	//所属学院
	private String department;
	//年级
	private int grade;
	//班级号
	private String classno;
	//性别
	private String sex;
	//民族
	private String people;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getFromprovince() {
		return fromprovince;
	}
	public void setFromprovince(String fromprovince) {
		this.fromprovince = fromprovince;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getClassno() {
		return classno;
	}
	public void setClassno(String classno) {
		this.classno = classno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	
	
	
}
