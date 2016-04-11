package com.sys.network.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class NtSecondaryCategoryDao {
	
	/**
	 * 获取所有的NtMain
	 */
	public static List GetNtSecondaryCategoriesByMain(int superiorId){
		Session session=SessionManager.getSession();
    	String hql = "select  t.categoryName,t.categoryId from NtSecondaryCategory t where t.ntMainCategory = ?";
    	Query query=session.createQuery(hql);
    	query.setInteger(0,superiorId);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}
	
	/**
	 * 根据 网站分类名 获取ID
	 */
	public static int getIdByName(String name){
		Session session=SessionManager.getSession();
    	String hql = "select t.categoryId from NtSecondaryCategory t where t.categoryName = ?";
    	Query query=session.createQuery(hql);
    	query.setString(0,name);
    	List list = query.list();
    	session.clear();
    	session.close();
    	Integer result=-1;
    	if(list.size()>0)
    		result=Integer.parseInt(list.get(0).toString());
    	return result;
	}
	public static void main(String[] args){
		List list = GetNtSecondaryCategoriesByMain(1);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[]) it.next();
			System.out.println(obj[0]+" "+obj[1]);
		}
		System.out.println(GetNtSecondaryCategoriesByMain(1));
	}
}
