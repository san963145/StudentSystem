package com.sys.card.bean;

import java.io.Serializable;

/**
 * 学院年级平均消费水平Bean
 */
public class DepartmentPoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3138032392412048399L;
	
	private String department;
	private int grade;
	private double total_Q1;
	private double total_Q2;
	private double total_Q3;
	private double total_MAX;
	private double total_MEAN;
	private double total_SD;
	private double count_Q1;
	private double count_Q2;
	private double count_Q3;
	private double count_MAX;
	private double count_MEAN;
	private double count_SD;
	private double ave_Q1;
	private double ave_Q2;
	private double ave_Q3;
	private double ave_MAX;
	private double ave_MEAN;
	private double ave_SD;
	private double lunch_Q1;
	private double lunch_Q2;
	private double lunch_Q3;
	private double lunch_MAX;
	private double lunch_MEAN;
	private double lunch_SD;
	private double lunch_CNTQ1;
	private double lunch_CNTQ2;
	private double lunch_CNTQ3;
	private double lunch_CNTMAX;
	private double lunch_CNTMEAN;
	private double lunch_CNTSD;
	private double lunch_AVGQ2;
	private double lunch_AVGQ1;
	private double lunch_AVGQ3;
	private double lunch_AVGMAX;
	private double lunch_AVGMEAN;
	private double lunch_AVGSD;
	private double supper_Q1;
	private double supper_Q2;
	private double supper_Q3;
	private double supper_MAX;
	private double supper_MEAN;
	private double supper_SD;
	private double supper_CNTQ1;
	private double supper_CNTQ2;
	private double supper_CNTQ3;
	private double supper_CNTMAX;
	private double supper_CNTMEAN;
	private double supper_CNTSD;
	private double supper_AVGQ1;
	private double supper_AVGQ2;
	private double supper_AVGQ3;
	private double supper_AVGMAX;
	private double supper_AVGMEAN;
	private double supper_AVGSD;
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
	public double getTotal_Q1() {
		return total_Q1;
	}
	public void setTotal_Q1(double total_Q1) {
		this.total_Q1 = total_Q1;
	}
	public double getTotal_Q2() {
		return total_Q2;
	}
	public void setTotal_Q2(double total_Q2) {
		this.total_Q2 = total_Q2;
	}
	public double getTotal_Q3() {
		return total_Q3;
	}
	public void setTotal_Q3(double total_Q3) {
		this.total_Q3 = total_Q3;
	}
	public double getTotal_MAX() {
		return total_MAX;
	}
	public void setTotal_MAX(double total_MAX) {
		this.total_MAX = total_MAX;
	}
	public double getTotal_MEAN() {
		return total_MEAN;
	}
	public void setTotal_MEAN(double total_MEAN) {
		this.total_MEAN = total_MEAN;
	}
	public double getTotal_SD() {
		return total_SD;
	}
	public void setTotal_SD(double total_SD) {
		this.total_SD = total_SD;
	}
	public double getCount_Q1() {
		return count_Q1;
	}
	public void setCount_Q1(double count_Q1) {
		this.count_Q1 = count_Q1;
	}
	public double getCount_Q2() {
		return count_Q2;
	}
	public void setCount_Q2(double count_Q2) {
		this.count_Q2 = count_Q2;
	}
	public double getCount_Q3() {
		return count_Q3;
	}
	public void setCount_Q3(double count_Q3) {
		this.count_Q3 = count_Q3;
	}
	public double getCount_MAX() {
		return count_MAX;
	}
	public void setCount_MAX(double count_MAX) {
		this.count_MAX = count_MAX;
	}
	public double getCount_MEAN() {
		return count_MEAN;
	}
	public void setCount_MEAN(double count_MEAN) {
		this.count_MEAN = count_MEAN;
	}
	public double getCount_SD() {
		return count_SD;
	}
	public void setCount_SD(double count_SD) {
		this.count_SD = count_SD;
	}
	public double getAve_Q1() {
		return ave_Q1;
	}
	public void setAve_Q1(double ave_Q1) {
		this.ave_Q1 = ave_Q1;
	}
	public double getAve_Q2() {
		return ave_Q2;
	}
	public void setAve_Q2(double ave_Q2) {
		this.ave_Q2 = ave_Q2;
	}
	public double getAve_Q3() {
		return ave_Q3;
	}
	public void setAve_Q3(double ave_Q3) {
		this.ave_Q3 = ave_Q3;
	}
	public double getAve_MAX() {
		return ave_MAX;
	}
	public void setAve_MAX(double ave_MAX) {
		this.ave_MAX = ave_MAX;
	}
	public double getAve_MEAN() {
		return ave_MEAN;
	}
	public void setAve_MEAN(double ave_MEAN) {
		this.ave_MEAN = ave_MEAN;
	}
	public double getAve_SD() {
		return ave_SD;
	}
	public void setAve_SD(double ave_SD) {
		this.ave_SD = ave_SD;
	}
	public double getLunch_Q1() {
		return lunch_Q1;
	}
	public void setLunch_Q1(double lunch_Q1) {
		this.lunch_Q1 = lunch_Q1;
	}
	public double getLunch_Q2() {
		return lunch_Q2;
	}
	public void setLunch_Q2(double lunch_Q2) {
		this.lunch_Q2 = lunch_Q2;
	}
	public double getLunch_Q3() {
		return lunch_Q3;
	}
	public void setLunch_Q3(double lunch_Q3) {
		this.lunch_Q3 = lunch_Q3;
	}
	public double getLunch_MAX() {
		return lunch_MAX;
	}
	public void setLunch_MAX(double lunch_MAX) {
		this.lunch_MAX = lunch_MAX;
	}
	public double getLunch_MEAN() {
		return lunch_MEAN;
	}
	public void setLunch_MEAN(double lunch_MEAN) {
		this.lunch_MEAN = lunch_MEAN;
	}
	public double getLunch_SD() {
		return lunch_SD;
	}
	public void setLunch_SD(double lunch_SD) {
		this.lunch_SD = lunch_SD;
	}
	public double getLunch_CNTQ1() {
		return lunch_CNTQ1;
	}
	public void setLunch_CNTQ1(double lunch_CNTQ1) {
		this.lunch_CNTQ1 = lunch_CNTQ1;
	}
	public double getLunch_CNTQ2() {
		return lunch_CNTQ2;
	}
	public void setLunch_CNTQ2(double lunch_CNTQ2) {
		this.lunch_CNTQ2 = lunch_CNTQ2;
	}
	public double getLunch_CNTQ3() {
		return lunch_CNTQ3;
	}
	public void setLunch_CNTQ3(double lunch_CNTQ3) {
		this.lunch_CNTQ3 = lunch_CNTQ3;
	}
	public double getLunch_CNTMAX() {
		return lunch_CNTMAX;
	}
	public void setLunch_CNTMAX(double lunch_CNTMAX) {
		this.lunch_CNTMAX = lunch_CNTMAX;
	}
	public double getLunch_CNTMEAN() {
		return lunch_CNTMEAN;
	}
	public void setLunch_CNTMEAN(double lunch_CNTMEAN) {
		this.lunch_CNTMEAN = lunch_CNTMEAN;
	}
	public double getLunch_CNTSD() {
		return lunch_CNTSD;
	}
	public void setLunch_CNTSD(double lunch_CNTSD) {
		this.lunch_CNTSD = lunch_CNTSD;
	}
	public double getLunch_AVGQ2() {
		return lunch_AVGQ2;
	}
	public void setLunch_AVGQ2(double lunch_AVGQ2) {
		this.lunch_AVGQ2 = lunch_AVGQ2;
	}
	public double getLunch_AVGQ1() {
		return lunch_AVGQ1;
	}
	public void setLunch_AVGQ1(double lunch_AVGQ1) {
		this.lunch_AVGQ1 = lunch_AVGQ1;
	}
	public double getLunch_AVGQ3() {
		return lunch_AVGQ3;
	}
	public void setLunch_AVGQ3(double lunch_AVGQ3) {
		this.lunch_AVGQ3 = lunch_AVGQ3;
	}
	public double getLunch_AVGMAX() {
		return lunch_AVGMAX;
	}
	public void setLunch_AVGMAX(double lunch_AVGMAX) {
		this.lunch_AVGMAX = lunch_AVGMAX;
	}
	public double getLunch_AVGMEAN() {
		return lunch_AVGMEAN;
	}
	public void setLunch_AVGMEAN(double lunch_AVGMEAN) {
		this.lunch_AVGMEAN = lunch_AVGMEAN;
	}
	public double getLunch_AVGSD() {
		return lunch_AVGSD;
	}
	public void setLunch_AVGSD(double lunch_AVGSD) {
		this.lunch_AVGSD = lunch_AVGSD;
	}
	public double getSupper_Q1() {
		return supper_Q1;
	}
	public void setSupper_Q1(double supper_Q1) {
		this.supper_Q1 = supper_Q1;
	}
	public double getSupper_Q2() {
		return supper_Q2;
	}
	public void setSupper_Q2(double supper_Q2) {
		this.supper_Q2 = supper_Q2;
	}
	public double getSupper_Q3() {
		return supper_Q3;
	}
	public void setSupper_Q3(double supper_Q3) {
		this.supper_Q3 = supper_Q3;
	}
	public double getSupper_MAX() {
		return supper_MAX;
	}
	public void setSupper_MAX(double supper_MAX) {
		this.supper_MAX = supper_MAX;
	}
	public double getSupper_MEAN() {
		return supper_MEAN;
	}
	public void setSupper_MEAN(double supper_MEAN) {
		this.supper_MEAN = supper_MEAN;
	}
	public double getSupper_SD() {
		return supper_SD;
	}
	public void setSupper_SD(double supper_SD) {
		this.supper_SD = supper_SD;
	}
	public double getSupper_CNTQ1() {
		return supper_CNTQ1;
	}
	public void setSupper_CNTQ1(double supper_CNTQ1) {
		this.supper_CNTQ1 = supper_CNTQ1;
	}
	public double getSupper_CNTQ2() {
		return supper_CNTQ2;
	}
	public void setSupper_CNTQ2(double supper_CNTQ2) {
		this.supper_CNTQ2 = supper_CNTQ2;
	}
	public double getSupper_CNTQ3() {
		return supper_CNTQ3;
	}
	public void setSupper_CNTQ3(double supper_CNTQ3) {
		this.supper_CNTQ3 = supper_CNTQ3;
	}
	public double getSupper_CNTMAX() {
		return supper_CNTMAX;
	}
	public void setSupper_CNTMAX(double supper_CNTMAX) {
		this.supper_CNTMAX = supper_CNTMAX;
	}
	public double getSupper_CNTMEAN() {
		return supper_CNTMEAN;
	}
	public void setSupper_CNTMEAN(double supper_CNTMEAN) {
		this.supper_CNTMEAN = supper_CNTMEAN;
	}
	public double getSupper_CNTSD() {
		return supper_CNTSD;
	}
	public void setSupper_CNTSD(double supper_CNTSD) {
		this.supper_CNTSD = supper_CNTSD;
	}
	public double getSupper_AVGQ1() {
		return supper_AVGQ1;
	}
	public void setSupper_AVGQ1(double supper_AVGQ1) {
		this.supper_AVGQ1 = supper_AVGQ1;
	}
	public double getSupper_AVGQ2() {
		return supper_AVGQ2;
	}
	public void setSupper_AVGQ2(double supper_AVGQ2) {
		this.supper_AVGQ2 = supper_AVGQ2;
	}
	public double getSupper_AVGQ3() {
		return supper_AVGQ3;
	}
	public void setSupper_AVGQ3(double supper_AVGQ3) {
		this.supper_AVGQ3 = supper_AVGQ3;
	}
	public double getSupper_AVGMAX() {
		return supper_AVGMAX;
	}
	public void setSupper_AVGMAX(double supper_AVGMAX) {
		this.supper_AVGMAX = supper_AVGMAX;
	}
	public double getSupper_AVGMEAN() {
		return supper_AVGMEAN;
	}
	public void setSupper_AVGMEAN(double supper_AVGMEAN) {
		this.supper_AVGMEAN = supper_AVGMEAN;
	}
	public double getSupper_AVGSD() {
		return supper_AVGSD;
	}
	public void setSupper_AVGSD(double supper_AVGSD) {
		this.supper_AVGSD = supper_AVGSD;
	}
	
}
