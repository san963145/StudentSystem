package com.sys.grades.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 原始成绩表
 * @author Administrator
 *
 */
public class McStuGrade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5614722082659401898L;
	//学号
	private String sno;
	//课程编号
	private String coursenum;
	//课程名
	private String coursename;
	//成绩
	private double grade;
	//学分
	private double coursecredit;
	//选修
	private String elective;
	//类别
	private String coursetype;
	//教师名
	private String teachername;
	//考试类别
	private String testtype;
	//备注
	private String note;
	//考试时间
	private Date date;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getCoursenum() {
		return coursenum;
	}
	public void setCoursenum(String coursenum) {
		this.coursenum = coursenum;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getElective() {
		return elective;
	}
	public void setElective(String elective) {
		this.elective = elective;
	}
	public String getCoursetype() {
		return coursetype;
	}
	public void setCoursetype(String coursetype) {
		this.coursetype = coursetype;
	}
	public String getTeachername() {
		return teachername;
	}
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}
	public String getTesttype() {
		return testtype;
	}
	public void setTesttype(String testtype) {
		this.testtype = testtype;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getCoursecredit() {
		return coursecredit;
	}
	public void setCoursecredit(double coursecredit) {
		this.coursecredit = coursecredit;
	}
	
    
}
