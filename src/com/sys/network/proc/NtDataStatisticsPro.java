package com.sys.network.proc;

import java.util.ArrayList;
import java.util.List;

import com.sys.grades.dao.DeptLocatorDao;
import com.sys.network.bean.CategoryNameNum;
import com.sys.network.dao.NtGameTimeDao;
import com.sys.network.dao.NtRecordCountDao;
import com.sys.network.dao.NtVdeoTimeDao;

public class NtDataStatisticsPro {
	/**
	 * 根据学号查询学生每个月上网情况
	 */
	public static List GetNtOTBySno(String sno){
		List list = NtRecordCountDao.getNtOTBySno(sno);
		return list;
	}
	/**
	 * 根据学号查询学生浏览前十sub
	 */
	public static ArrayList<CategoryNameNum> GetTop10BySno(String sno){
		ArrayList<CategoryNameNum>result = NtRecordCountDao.getTop10BySno(sno);
		return result;
	}
	/**
	 * 根据学号查询学生每个月看视频时间
	 * @param sno
	 * @return
	 */
	public static List GetNtVTBySno(String sno){
		List list = NtVdeoTimeDao.getNtVTBySno(sno);
		return list;
	}
	/**
	 * 根据学号查询学生每个月玩游戏时间
	 * @param sno
	 * @return
	 */
	public static List GetNtGTBySno(String sno){
		List list = NtGameTimeDao.getNtGTBySno(sno);
		return list;
	}
	/**
	 * 根据用户名显示学生信息
	 */
	public static List GetSInfoBySname(String sname){
		List list = DeptLocatorDao.GetStuInfoBySname(sname);
		return list;
	}

	/**
	 * 根据学院年级查询学生信息
	 */
	public static List GetSInfoByCG(String collegename,String grade){
		List list = DeptLocatorDao.GetStuInfoByCG(collegename, grade);
		return list;
	}
	/**
	 * 根据学院年级、姓名查询学生信息
	 */
	public static List GetSInfoByCG(String sname,String collegename,String grade){
		List list = DeptLocatorDao.GetStuInfoByCG(sname,collegename, grade);
		return list;
	}
	public static void main(String[]args)
	{
		List list=GetSInfoByCG("计算机学院","2012");
		for(int i=0;i<list.size();i++)
		{
			Object[]obj=(Object[]) list.get(i);
			System.out.println(obj[0].toString()+" "+obj[1].toString()+" "+obj[2].toString()+" "+obj[3].toString()+" "+obj[4].toString());
		}
	}
}
