package com.sys.grades.dao;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.grades.bean.McHistoryGpa;
import com.sys.manager.dao.SessionManager;

public class McHistoryGpaDao {

	/**
	 * 添加学生学期gpa到数据库中
	 * @param mchistorygpa
	 */
	public static void add(McHistoryGpa mchistorygpa) {
		Session session = SessionManager.getSession();
		Transaction transaction = session.beginTransaction();
	    session.save(mchistorygpa);
		transaction.commit();
		session.clear();
		session.close();
	}
	/**
  	 * 清空McHistoryGap表
  	 * 
  	 */
	public static void clear()
    {
 	   Session session=SessionManager.getSession();
 	   Transaction transaction=session.beginTransaction();
 	   String hql="delete from McHistoryGpa";
 	   Query query=session.createQuery(hql);
 	   query.executeUpdate();
 	   transaction.commit(); 
 	  session.clear();
 	   session.close();
    }
	/**
  	 * 格式化McHistoryGap表
  	 * 
  	 */
	public static void formatDecimal()
    {
 	   Session session=SessionManager.getSession();
 	   Transaction transaction=session.beginTransaction();
 	   String hql="update McHistoryGpa t set t.gpasemester=round(t.gpasemester,2),t.averscoresmester=round(t.averscoresmester,2),"
 	   		+ "t.hadcreditratesemester=round(t.hadcreditratesemester,2)";
 	   Query query=session.createQuery(hql);
 	   query.executeUpdate();
 	   transaction.commit(); 
 	  session.clear();
 	   session.close(); 
    }
	/**
	 * 依据学生学号、学期来列出historysmestergpa
	 */
	public static Double GetHSGpaBySS(int semester,String sno) {
		Session session = SessionManager.getSession();
		String hql = "select t.gpasemester from McHistoryGpa t where trim(t.sno) = ? and t.semester = ?";
		Query query = session.createQuery(hql);
		query.setString(0, sno.trim());
		query.setInteger(1, semester);
		List list = query.list();
		Double result =null;
		if(list!=null)
		  if(list.size()>0)
		   result=(Double) list.get(0);
		session.clear();
		session.close();
		return result;
	}
	/**
	 * 依据学生学号、学期列出学期平均得分GPA
	 * @param args
	 */
	public static Double GetHAGBySS(int semester,String sno){
		Session session = SessionManager.getSession();
		String hql = "select t.averscoresmester from McHistoryGpa t where trim(t.sno) = ? and t.semester = ?";
		Query query = session.createQuery(hql);
		query.setString(0, sno.trim());
		query.setInteger(1, semester);
		List list = query.list();
		Double result =null;
		if(list!=null)
		  if(list.size()>0)
		   result=(Double) list.get(0);
		session.clear();
		session.close();
		return result;
	}
	/**
	 * 依据学生学号、学期列出学期选课学分
	 * @param args
	 */
	public static Double GetHSGBySS(int semester,String sno){
		Session session = SessionManager.getSession();
		String hql = "select t.selectcreditsemester from McHistoryGpa t where trim(t.sno) = ? and t.semester = ?";
		Query query = session.createQuery(hql);
		query.setString(0, sno.trim());
		query.setInteger(1, semester);
		List list = query.list();
		Double result =null;
		if(list!=null)
		  if(list.size()>0)
		   result=(Double) list.get(0);
		session.clear();
		session.close();
		return result;
	}
	/**
	 * 依据学生学号、学期列出学期获得学分
	 * @param args
	 */
	public static Double GetHHGBySS(int semester,String sno){
		Session session = SessionManager.getSession();
		String hql = "select t.hadcreditsemester from McHistoryGpa t where trim(t.sno) = ? and t.semester = ?";
		Query query = session.createQuery(hql);
		query.setString(0, sno.trim());
		query.setInteger(1, semester);
		List list = query.list();
		Double result =null;
		if(list!=null)
		  if(list.size()>0)
		   result=(Double) list.get(0);
		session.clear();
		session.close();
		return result;
	}
	/**
	 * 依据学生学号、学期列出学期学分获得率
	 * @param args
	 */
	public static Double GetHGGRBySS(int semester,String sno){
		Session session = SessionManager.getSession();
		String hql = "select t.hadcreditratesemester from McHistoryGpa t where trim(t.sno) = ? and t.semester = ?";
		Query query = session.createQuery(hql);
		query.setString(0, sno.trim());
		query.setInteger(1, semester);
		List list = query.list();
		Double result =null;
		if(list!=null)
		  if(list.size()>0)
		   result=(Double) list.get(0);
		session.clear();
		session.close();
		return result;
	}
	
	/**
  	 * 根据学号，学期查询选出补考的grade
  	 * @param sno
  	 * @param semester
  	 * @return
  	 */
   public static String SelectFailBySnoSemester(String sno,int semester,String teachername){
  		Session session = SessionManager.getSession();
  		String hql = "select t.grade from McStuGrade t where t.testtype = '补考' and trim(sno) = ? and semester = ? and teachername = ?";
  		Query query=session.createQuery(hql);
          query.setString(0,sno.trim());
          query.setInteger(1, semester);
          query.setString(2, teachername);
          String result=null;
          result=(String) query.list().get(0);
          session.clear();
          session.close();
          return result;    
  	}
  public static void main(String[] args)
  {
	  Double d=GetHSGpaBySS(1,"20124917");
	  System.out.println(d);
  }

}
