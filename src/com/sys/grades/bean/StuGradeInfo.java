package com.sys.grades.bean;

    /**
	 * 学生原始成绩表
	 * 
	 */
public class StuGradeInfo {

	//标志主键
	private int id;
	// 学号
	private String sno;
	// 学期
	private int semester;
	// 学分
	private double coursecredit;
	// 分数成绩
	private String grade;
	// 选修
	private String elective;
	//老师
	private String teachername;
	//考试类别
	private String testtype;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public double getCoursecredit() {
		return coursecredit;
	}

	public void setCoursecredit(double coursecredit) {
		this.coursecredit = coursecredit;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getElective() {
		return elective;
	}

	public void setElective(String elective) {
		this.elective = elective;
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
	
}
