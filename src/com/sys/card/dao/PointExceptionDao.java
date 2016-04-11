package com.sys.card.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.card.bean.PointException;
import com.sys.manager.dao.SessionManager;

/**
 *  学生异常消费记录访问接口
 */
public class PointExceptionDao {
	  
	/**
	 *  添加异常记录
	 */
      public static void add(String sno,String info)
      {
    	 Session session=SessionManager.getSession();
 		 Transaction transaction=session.beginTransaction();
 		 PointException pointException=new PointException();
 		 pointException.setSno(sno);
 		 pointException.setInfo(info);
 		 session.save(pointException);
 		 transaction.commit();
 		session.clear();
        session.close();
        
      }
      
      /**
       *  情况所有异常记录
       */
      public static void clear()
      {
    	 Session session=SessionManager.getSession();
  		 Transaction transaction=session.beginTransaction();
  		 String hql="delete from PointException";
  		 Query query=session.createQuery(hql);
  		 query.executeUpdate();
  		 transaction.commit(); 
  		session.clear();
        session.close();
        
      }
      public static List selectAllStudents()
      {
    	  Session session=SessionManager.getSession();
     	 String hql="select s.sno,s.total,s.count,s.average from StudentPoint s";
     	 Query query=session.createQuery(hql);
          List result=null;
          result=query.list();
          session.clear();
          session.close();
          
          return result;
      }
      public static List selectMeanQ(String sno)
      {
    	  Session session=SessionManager.getSession();
     	 String hql="select d.ave_MEAN,d.ave_Q1,d.ave_Q3,d.count_Q1,d.count_Q2,d.count_Q3 from StudentInfo s,DepartmentPoint d where s.department=d.department and s.grade=d.grade and trim(s.sno)=?";
     	 Query query=session.createQuery(hql);
     	  query.setString(0,sno);
          List result=null;
          result=query.list();
          session.clear();
          session.close();
     
          return result;
      }
      public static String selectInfoBySno(String sno)
      {
    	  Session session=SessionManager.getSession();
     	  String hql="select s.info from PointException s where trim(s.sno)=?";
     	  Query query=session.createQuery(hql);
     	  query.setString(0,sno);
          List result=null;
          result=query.list();
          session.clear();
          session.close();
      
          String r="无";
          if(result!=null)
        	  if(result.size()>0)
               r=(String) result.get(0);
          return r;
      }
}
