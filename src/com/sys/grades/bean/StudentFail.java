package com.sys.grades.bean;

    /**
	 * 学生挂科门数列表
	 * 
	 */
public class StudentFail {
	private String sno;
	private int failnum;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public int getFainum() {
		return failnum;
	}
	public void setFainum(int fainum) {
		this.failnum = fainum;
	}
}
