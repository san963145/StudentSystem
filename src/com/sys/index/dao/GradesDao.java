package com.sys.index.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class GradesDao {
	/**
     *  查询全体学生平均GPA
     */
    public static List get()
    {
   	    Session session=SessionManager.getSession();
   	    String hql="select round(avg(m.gpasemester),2) from McHistoryGpa m group by m.semester order by m.semester";
   	    Query query=session.createQuery(hql);
        List result=query.list();
        session.clear();
        session.close();
        if(result!=null)
       	 if(result.size()>0)
       	 {
       		 Double obj=(Double) result.get(0);
       		 if(obj==null)
       			 result.clear();
       	 }
        return result;
    }
    /**
     *  查询条件范围内学生平均GPA
     */
	public static Map<String,List> get(String department,String major,String grade,String sex,String column)
	{
		Session session=SessionManager.getSession();
		String d="";
		String m="";
		String g="";
		String s="";
		if(department!=null)
		{
			d=" and t.department='"+department+"'";
		}
		if(major!=null)
		{
			m=" and substr(t.classno,3,4)='"+major+"'";
		}
		if(grade!=null)
		{
			g=" and substr(t.classno,0,2)='"+grade.substring(2,4)+"'";
		}
		if(sex!=null)
		{
			s=" and t.sex='"+sex+"'";
		}
		Map<String,List>map=new LinkedHashMap<String,List>();
   	    List groupList=NetworkDao.getGroups(department, major, grade, sex, column);
   	    for(int i=0;i<groupList.size();i++)
   	    {   	    	
   	    	String obj=(String) groupList.get(i);
			String sc="";
			if(column.equals("department"))
			{
				sc=" and t.department='"+obj+"'";
			}
			else if(column.equals("major"))
			{
				sc=" and substr(t.classno,3,4)='"+obj+"'";
			}
			else if(column.equals("grade"))
			{
				sc=" and substr(t.classno,0,2)='"+obj+"'";
			}
			else
			{
			   sc=" and t.sex='"+obj+"'";
			}
			String hql="select round(avg(m.gpasemester),2) from DeptLocator t,McHistoryGpa m where trim(t.sno)=trim(m.sno)"+d+m+g+s+sc+"group by m.semester order by m.semester";
			Query query=session.createQuery(hql);
	        List list=query.list();
	        if(list.size()>0)
	          map.put(obj, list);
   	    }
   	 session.clear();
   	    session.close();
        return map;
	}
	
	public static void main(String[] args)
    {
	 Map<String,List> map=get(null,null,null,null,"sex");
	 Set<String>s=map.keySet();
  	 Iterator it=s.iterator();
  	 while(it.hasNext())
  	 {
  		 String key=(String) it.next();
  		 List list=map.get(key);
  		 System.out.println(key);
  		 for(int i=0;i<list.size();i++)
  		 {
  			Double c=(Double) list.get(i);
  			System.out.println(c);
  		 }
  		 
  	 }
    }
}
