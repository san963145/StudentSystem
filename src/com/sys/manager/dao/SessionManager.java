package com.sys.manager.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *  Hibernate会话管理
 */
public class SessionManager {
	private static SessionFactory factory;
	
	/**
	 *  生成一个Session
	 */
	@SuppressWarnings("deprecation")
	public static Session getSession()
	{
		if(factory==null)
		{
			Configuration config=new Configuration().configure();  
	        factory=config.buildSessionFactory();  
		}
		
        return factory.openSession();
	}
	public static void close()
	{
		factory.close();
		factory=null;
	}
}
