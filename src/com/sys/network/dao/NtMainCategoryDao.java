package com.sys.network.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class NtMainCategoryDao {
	
	/**
	 * 获取所有的NtMain
	 */
	public static List GetNtMainCategories(){
		Session session=SessionManager.getSession();
    	String hql = "select t.categoryName,t.categoryId from NtMainCategory t";
    	Query query=session.createQuery(hql);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}
	
	/**
	 * 根据网站分类名 获取ID
	 */
    public static int getIdByName(String categoryName)
    {
    	Session session=SessionManager.getSession();
    	String hql = "select distinct t.categoryId from NtMainCategory t where t.categoryName=?";
    	Query query=session.createQuery(hql);
    	query.setString(0, categoryName);
    	List list = query.list();
    	session.clear();
    	session.close();
    	Integer result=-1;
    	if(list.size()>0)
    		result=Integer.parseInt(list.get(0).toString());
    	return result;
    }
	public static void main(String[] args){
		List list=GetNtMainCategories();
		for(int i=0;i<list.size();i++)
		{
			Object[]obj=(Object[]) list.get(i);
			System.out.println(obj[0].toString()+" "+obj[1].toString());
		}
	}
}
