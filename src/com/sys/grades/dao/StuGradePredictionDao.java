package com.sys.grades.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.grades.bean.StuGradePrediction;
import com.sys.manager.dao.SessionManager;

public class StuGradePredictionDao {
	/**
  	 * 添加一行StuGradePrediction记录
  	 * 
  	 */
	public static void add(StuGradePrediction stuGradePrediction) {
		Session session = SessionManager.getSession();
		Transaction transaction = session.beginTransaction();
	    session.save(stuGradePrediction);
		transaction.commit();
		session.clear();
		session.close();
	}
	/**
  	 * 清空StuGradePrediction表
  	 * 
  	 */
	public static void clear()
    {
 	   Session session=SessionManager.getSession();
 	   Transaction transaction=session.beginTransaction();
 	   String hql="delete from StuGradePrediction";
 	   Query query=session.createQuery(hql);
 	   query.executeUpdate();
 	   transaction.commit(); 
 	  session.clear();
 	   session.close();
    }
	/**
     * 根据学院、专业、年级获取学生的预测数据
     */
    public static List GetByCMG(String collegename,String major,int grade)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select trim(s.sno),s.sname,s.gpa,s.risk from DeptLocator t,StuGradePrediction s where trim(t.sno)=trim(s.sno) and t.department = ? and substr(t.classno,0,2) = ? and substr(t.classno,3,4) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, collegename);
       	query.setString(1, Integer.toString(grade).substring(2,4));
       	query.setString(2, major);
       	List list = query.list(); 
       	session.clear();
       	session.close();
       	return list;
   	}
    public static void main(String[] args)
    {
    	List list=GetByCMG("计算机学院","01",2012);
    	for(int i=0;i<list.size();i++)
    	{
    		Object[] obj=(Object[]) list.get(i);
    		System.out.println(obj[0]+" "+obj[1]+" "+obj[2]+" "+obj[3]);
    	}
    	System.out.println(list.size());
    }
    
}
