package com.sys.network.dao;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.sys.manager.dao.SessionManager;

public class NtWebsitesDao {
	
	/**
	 * 获取所有的NtMain
	 */
	public static List GetNtWebsitesSub(int superiorId){
		Session session=SessionManager.getSession();
    	String hql = "select distinct t.url from NtWebsites t where t.subCategoryId = ?";
    	Query query=session.createQuery(hql);
    	query.setInteger(0, superiorId);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}

	public static void main(String[] args){
		List list = GetNtWebsitesSub(163);
		Iterator it = list.iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
}
