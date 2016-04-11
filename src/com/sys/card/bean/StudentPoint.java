package com.sys.card.bean;

/**
 * 学生消费记录及评分Bean
 */
public class StudentPoint {                 //学生消费记录表
	private String sno;
	private String gender;
	private String hasScholarship;
	private double total;
	private int count;
	private double average;
	private double lunchTTL;
	private int lunchCNT;
	private double lunchAVG;
	private double supperTTL;
	private int supperCNT;
	private double supperAVG;
	private double point;
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHasScholarship() {
		return hasScholarship;
	}
	public void setHasScholarship(String hasScholarship) {
		this.hasScholarship = hasScholarship;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getLunchTTL() {
		return lunchTTL;
	}
	public void setLunchTTL(double lunchTTL) {
		this.lunchTTL = lunchTTL;
	}
	public int getLunchCNT() {
		return lunchCNT;
	}
	public void setLunchCNT(int lunchCNT) {
		this.lunchCNT = lunchCNT;
	}
	public double getLunchAVG() {
		return lunchAVG;
	}
	public void setLunchAVG(double lunchAVG) {
		this.lunchAVG = lunchAVG;
	}
	public double getSupperTTL() {
		return supperTTL;
	}
	public void setSupperTTL(double supperTTL) {
		this.supperTTL = supperTTL;
	}
	public int getSupperCNT() {
		return supperCNT;
	}
	public void setSupperCNT(int supperCNT) {
		this.supperCNT = supperCNT;
	}
	public double getSupperAVG() {
		return supperAVG;
	}
	public void setSupperAVG(double supperAVG) {
		this.supperAVG = supperAVG;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
	
}
