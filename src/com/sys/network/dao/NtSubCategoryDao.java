package com.sys.network.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.sys.manager.dao.SessionManager;

public class NtSubCategoryDao {
	
	/**
	 * 获取所有的NtMain
	 */
	public static List GetNtSubCategoriesBySecond(int superiorId){
		Session session=SessionManager.getSession();
    	String hql = "select t.categoryName,t.categoryId from NtSubCategory t where t.ntSecondaryCategory = ?";
    	Query query=session.createQuery(hql);
    	query.setInteger(0, superiorId);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}
	/**
	 * 查询出所有subcategories
	 * @return
	 */
	public static List GetAllSubCategories(){
		Session session=SessionManager.getSession();
    	String hql = "select  t.categoryId,t.categoryName from NtSubCategory t";
    	Query query=session.createQuery(hql);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}
	/**
	 * 通过subid查询出完整name
	 * @param args
	 */
	public static List GetNameBySid(int sid){
		Session session=SessionManager.getSession();
    	String hql = "select v.categoryName,u.categoryName,t.categoryName from  NtSubCategory t,NtSecondaryCategory u,NtMainCategory v where t.ntSecondaryCategory = u.categoryId and u.ntMainCategory = v.categoryId and t.categoryId = ?";
    	Query query=session.createQuery(hql);
    	query.setInteger(0, sid);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}
	public static void main(String[] args){
		List list = GetNtSubCategoriesBySecond(7);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[]) it.next();
			System.out.println(obj[0]+" "+obj[1]);
		}
	}
}
