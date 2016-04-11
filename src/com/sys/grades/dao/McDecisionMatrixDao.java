package com.sys.grades.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.grades.bean.McDecisionMatrix;
import com.sys.manager.dao.SessionManager;

public class McDecisionMatrixDao {
	   
	/**
   	 * 添加一行记录
   	 * @param args
   	 */
       public static void add(McDecisionMatrix m)
       {
    	   Session session=SessionManager.getSession();
  		   Transaction transaction=session.beginTransaction();
  		   session.save(m);
  		   transaction.commit();
  		 session.clear();
  		   session.close();
       }
       /**
      	 * 清空McDecisionMatrix表
      	 * 
      	 */
       public static void clear()
       {
    	   Session session=SessionManager.getSession();
    	   Transaction transaction=session.beginTransaction();
    	   String hql="delete from McDecisionMatrix";
    	   Query query=session.createQuery(hql);
    	   query.executeUpdate();
    	   transaction.commit(); 
    	   session.clear();
    	   session.close();
       }
       /**
     	 * 格式化McDecisionMatrix表
     	 * 
     	 */
       public static void formatDecimal()
       {
    	   Session session=SessionManager.getSession();
    	   Transaction transaction=session.beginTransaction();
    	   String hql="update McDecisionMatrix t set t.T1=round(t.T1,2),t.T2=round(t.T2,2),"
    	   		+ "t.T3=round(t.T3,2),t.T4=round(t.T4,2)";
    	   Query query=session.createQuery(hql);
    	   query.executeUpdate();
    	   transaction.commit(); 
    	   session.clear();
    	   session.close(); 
       }
       /**
     	 * 根据学院、专业、年级获取 预测矩阵信息
     	 * 
     	 */
       public static List getT(String department,String major,int grade)
       {
    	   Session session=SessionManager.getSession();
    	   String hql="select t.T1,t.T2,t.T3,t.T4 from McDecisionMatrix t where collegename=? and majoynum=? and grade=?";
    	   Query query=session.createQuery(hql);
    	   query.setString(0, department);
    	   query.setString(1, major);
    	   query.setInteger(2, grade);
           List result=null;
           result=query.list();
           session.clear();
    	   session.close();
           return result;
       }
       
       public static void main(String[]args)
       {
    	   formatDecimal();
       }
}
