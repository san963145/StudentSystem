package com.sys.network.proc;

import java.util.ArrayList;

import com.sys.network.bean.CategoryNameNum;
import com.sys.network.dao.NtGameTimeDao;
import com.sys.network.dao.NtRecordCountDao;
import com.sys.network.dao.NtVdeoTimeDao;

public class CalculateAverageTime {

	/**
	 * 根据学院、专业、年级、性别（限定下）来计算平均上网时间
	 */
	public static Double CalculateAverageOnlineTime(String collegename,
			String major, String grade, String sex) {
		Double avg = null;
		try {
			avg = NtRecordCountDao.GetAOTByCMGS(collegename, major, grade, sex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avg;
	}
	/**
	 * 计算全校平均上网时间
	 */
	public static Double CalculateAOT(){
		try {
			return NtRecordCountDao.GetAOT();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据学院、专业、年级、性别（限定下）来计算平均上网游戏时间
	 */
	public static Double CalculateAverageGameTime(String collegename,
			String major, String grade, String sex) {
		Double avg = null;
		try {
			avg = NtGameTimeDao.GetAGTByCMGS(collegename, major, grade, sex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avg;
	}
	/**
	 * 计算全校平均游戏时间
	 */
	public static Double CalculateAGT(){
		try {
			return NtGameTimeDao.GetAGT();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据学院、专业、年级、性别（限定下）来计算平均上网视频时间
	 */
	public static Double CalculateAverageVideoTime(String collegename,
			String major, String grade, String sex) {
		Double avg = null;
		try {
			avg = NtVdeoTimeDao.GetAGVByCMGS(collegename, major, grade, sex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avg;
	}
	/**
	 * 计算全校平均视频时间
	 */
	public static Double CalculateAVT(){
	   try {
		return NtVdeoTimeDao.GetAVT();
	} catch (Exception e) {
		e.printStackTrace();
	}
	   return null;
	}
	/**
     * 根据学院、专业、年级、性别（限定下）来计算前十浏览的子类
     */
	public static ArrayList<CategoryNameNum> CalculateTop10sub(String collegename,
			String major, String grade, String sex){
		try {
			return NtRecordCountDao.getTop10Category(collegename, major, grade, sex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<CategoryNameNum>();
	}
	
}
