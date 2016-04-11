package com.sys.card.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class StudentInfoDao {
	/**
     *  根据学号获取学院
     */
	 public static String selectDepartmentBySno(String sno)
	 {
		 Session session=SessionManager.getSession();
   	     String hql="select s.department from StudentInfo s where trim(s.sno)=?";
   	     Query query=session.createQuery(hql);
        query.setString(0,sno);
        List list=query.list();
        String department=null;
        if(list!=null)
        {
       	 if(list.size()!=0)
       	 {
       		 department=(String) list.get(0);
       	 }
        }
        session.clear();
        session.close();
        
        return department;
	 }  
	 /**
      *  根据学号获取专业
      */
	 public static String selectMajorBySno(String sno)
	 {
		 Session session=SessionManager.getSession();
    	 String hql="select substr(s.classNo,3,4) from StudentInfo s where trim(s.sno)=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,sno);
         List list=query.list();
         String major=null;
         if(list!=null)
         {
        	 if(list.size()!=0)
        	 {
        		 major=(String) list.get(0);
        	 }
         }
         session.clear();
         session.close();
         return major;
	 }
	 /**
      *  根据学号获取专业
      */
	 public static List selectGMBySno(String sno)
	 {
		 Session session=SessionManager.getSession();
    	 String hql="select s.grade,s.classNo from StudentInfo s where trim(s.sno)=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,sno);
         List list=query.list();
         session.clear();
         session.close();
         return list;
	 }
	 
	 public static void main(String[]args)
	 {
		 String s=selectMajorBySno("20124932");
		 System.out.println(s);
	 }
}
