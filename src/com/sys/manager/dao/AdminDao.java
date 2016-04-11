package com.sys.manager.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


public class AdminDao {
	/**
 	 *  验证用户名、密码是否正确
 	 */
     public static List select(String userName,String password)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.userName from Admin t where t.userName=? and t.password=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,userName);
         query.setString(1,password);
         List result=query.list();
         session.clear();
         session.close();
         
         return result;
     }
}
