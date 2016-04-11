package com.sys.index.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;

public class CardDao {
	
	/**
     *  查询全体学生平均消费数据
     */
    public static List get()
    {
   	    Session session=SessionManager.getSession();
   	    String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s";
   	    Query query=session.createQuery(hql);
        List result=query.list();
        session.clear();
        session.close();
        if(result!=null)
       	 if(result.size()>0)
       	 {
       		 Object[] obj=(Object[]) result.get(0);
       		 if(obj[0]==null)
       			 result.clear();
       	 }
        return result;
    }
    /**
     *  按条件查询学生平均消费数据
     */
	public static List get(String department,String major,String grade,String sex,String column)
	{
		Session session=SessionManager.getSession();
		String d="";
		String m="";
		String g="";
		String s="";
		String groupby=" group by t."+column+" order by t."+column;
		if(department!=null)
		{
			  d=" and t.department='"+department+"'";
		}
		if(major!=null)
		{
			  m=" and substr(t.classNo,3,4)='"+major+"'";
		}
		if(grade!=null)
		{
			  g=" and substr(t.classNo,0,2)='"+grade.substring(2,4)+"'";
		}
		if(sex!=null)
		{
			  s=" and t.sex='"+sex+"'";
		}
   	    String hql="select t."+column+",round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) "+d+m+g+s+groupby;
   	    if(column.equals("major"))
   	    {
   	    	hql="select m.majorName,round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t,MajorName m where trim(s.sno)=trim(t.sno) and m.majorNum=substr(t.classNo,3,4) "+d+m+g+s+" group by m.majorName order by m.majorName desc";
   	    }
   	    if(column.equals("grade"))
   	    {
   	    	hql="select substr(t.classNo,0,2),round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) "+d+m+g+s+" group by substr(t.classNo,0,2) order by substr(t.classNo,0,2)";
   	    }
   	    Query query=session.createQuery(hql);
        List result=query.list();
        session.clear();
        session.close();
        if(result!=null)
       	 if(result.size()>0)
       	 {
       		 Object[] obj=(Object[]) result.get(0);
       		 if(obj[0]==null)
       			 result.clear();
       	 }
        List<Object[]>list=new ArrayList<Object[]>();
        for(int i=0;i<result.size();i++)
        {
        	Object[] obj=(Object[]) result.get(i);
        	if(!obj[1].toString().equals("0.0"))
        	{
        		list.add(obj);
        	}
        }
        return list;
	}
    
	public static void main(String[] args)
    {
   	 List list=get("人文社会科学高等研究院","0702",null,"1","grade");
   	 for(int i=0;i<list.size();i++)
   	 {
   		 Object[] obj=(Object[]) list.get(i);
   		 System.out.println(obj[0]+" "+obj[1]+" "+obj[2]+" "+obj[3]);
   	 }
    }
}
