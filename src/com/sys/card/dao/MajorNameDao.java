package com.sys.card.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class MajorNameDao {
	
	/**
 	 * 根据专业名获取专业号
 	 * 
 	 */
   public static String getNumByName(String majorName)
   {
	   Session session=SessionManager.getSession();
  	   String hql="select t.majorNum from MajorName t where t.majorName=?";
  	   Query query=session.createQuery(hql);
  	   query.setString(0, majorName);
       List result=null;
       result=query.list();
       session.clear();
       session.close();
       
       String majorNum=null;
       if(result!=null)
       {
    	   if(result.size()>0)
             majorNum=result.get(0).toString();
       }
       return majorNum;
   }
   /**
	 * 根据学院、专业名获取专业号
	 * 
	 */
  public static String getNumByName(String department,String majorName)
  {
	   Session session=SessionManager.getSession();
 	   String hql="select t.majorNum from MajorName t,DeptLocator d where t.majorName=? and substr(d.classno,3,4)=t.majorNum";
 	   Query query=session.createQuery(hql);
 	   query.setString(0, majorName);
      List result=null;
      result=query.list();
      session.clear();
      session.close();
     
      String majorNum=null;
      if(result!=null)
      {
   	   if(result.size()>0)
            majorNum=result.get(0).toString();
      }
      return majorNum;
  }
   /**
	 * 根据专业号获取专业名
	 * 
	 */
  public static String getNameByNum(String majorNum)
  {
	   Session session=SessionManager.getSession();
 	   String hql="select t.majorName from MajorName t where t.majorNum=?";
 	   Query query=session.createQuery(hql);
 	   query.setString(0, majorNum);
       List result=null;
       result=query.list();
       session.clear();
       session.close();
       
       String majorName=null;
       if(result!=null)
       {
   	    if(result.size()>0)
   		  majorName=result.get(0).toString();
       }
       return majorName;
  }
  public static void main(String[] args)
  {
	  String s=getNumByName("航空航天学院","工程力学");
	  System.out.println(s);
  }
}
