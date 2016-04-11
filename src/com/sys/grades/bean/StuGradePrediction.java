package com.sys.grades.bean;

    /**
	 * 学生成绩预测结果 表
	 * 
	 */
public class StuGradePrediction {

	//标志主键
	private int id;
	//学号
	private String sno;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	//姓名
	private String sname;
	//最新GPA
	private double gpa;
	//是否存在挂科风险
	private String risk;
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
	public double getGpa() {
		return gpa;
	}
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	public String getRisk() {
		return risk;
	}
	public void setRisk(String risk) {
		this.risk = risk;
	}
	
}
