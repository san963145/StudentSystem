package com.sys.card.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.card.bean.DepartmentPoint;
import com.sys.manager.bean.User;
import com.sys.manager.dao.SessionManager;

/**
 *  学院平均消费访问接口
 */
public class DepartmentPointDao {
	
	/**
	 *  添加一条数据
	 */
	 public static void add(DepartmentPoint departmentPoint)
	 {
		 Session session=SessionManager.getSession();
		 Transaction transaction=session.beginTransaction();
		 session.save(departmentPoint);
		 transaction.commit();
		 session.clear();
         session.close();
         
	 }
	 
	 /**
	  *  清空所有数据
	  */
	 public static void clear()
	 {
		 Session session=SessionManager.getSession();
  		 Transaction transaction=session.beginTransaction();
  		 String hql="delete from DepartmentPoint";
  		 Query query=session.createQuery(hql);
  		 query.executeUpdate();
  		 transaction.commit(); 
  		session.clear();
        session.close();
        
	 }
	 
	 /**
	  *  获得所有数据
	  */
	 public static List getAllData()
	 {
		 Session session=SessionManager.getSession();
	   	 String hql="from DepartmentPoint";
	   	 Query query=session.createQuery(hql);
	     List list=query.list();
	     session.clear();
         session.close();
         
	     return list; 
	 }
	 
	 /**
	  *  根据权限获得所有学院名列表
	  */
     public static List<String> getDepartmentsByUser(User user)
	 {
		Session session=SessionManager.getSession();
   	    String hql="select distinct t.department from StudentInfo t";
   	    Query query=session.createQuery(hql);
        List list=query.list();
        List<String> result=new ArrayList<String>();
        if(list.size()>0)
        {
        	Iterator it=list.iterator();
        	while(it.hasNext())
        	{
        		String s=(String) it.next();
        		if(s!=null)
        		result.add(s);
        	}
        }
        session.clear();
        session.close();
        
        if(user!=null)
        	if(!user.getAuthority().equals("Admin"))
        	{
        		result.clear();
        		result.add(user.getDepartment());
        	}
        return result;
	 }
     /**
	  *  获得所有学院名列表
	  */
     public static List<String> getDepartments()
	 {
		Session session=SessionManager.getSession();
   	    String hql="select distinct t.department from StudentInfo t";
   	    Query query=session.createQuery(hql);
        List list=query.list();
        List<String> result=new ArrayList<String>();
        if(list.size()>0)
        {
        	Iterator it=list.iterator();
        	while(it.hasNext())
        	{
        		String s=(String) it.next();
        		if(s!=null)
        		result.add(s);
        	}
        }
        session.clear();
        session.close();
        
        return result;
	 }
     /**
      *  获得某学院所有年级列表
      */
	 public static List<Integer> getGrades(String department)
	 {
		Session session=SessionManager.getSession();
   	    String hql="select distinct t.grade from StudentInfo t where department=? order by t.grade";
   	    Query query=session.createQuery(hql);
   	    query.setString(0,department);
        List list=query.list();
        List<Integer> result=new ArrayList<Integer>();
        if(list.size()>0)
        {
        	Iterator it=list.iterator();
        	while(it.hasNext())
        	{
        		Object a=it.next();
        		if(a!=null)
        		result.add(Integer.parseInt(a.toString()));
        	}
        }
        session.clear();
        session.close();
        
        return result;
	 }
	 
	 /**
	  *  获取学院department、年级grade、字段column的最大值
	  */
     public static double getMax(String column,String department,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select max(s."+column+") from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=?";
    	 Query query=session.createQuery(hql);
    	 query.setString(0,department);
    	 query.setInteger(1,grade);
         List list=query.list();
         double result=0;
         if(list.size()>0)
         {
        	Iterator it=list.iterator();
          	if(it.hasNext())
          	{
          		Object o=it.next();
          		if(o!=null)
          		result=Double.parseDouble(o.toString());
          	}
         }
         session.clear();
         session.close();
        
         return result;
     }
     
     /**
      *  获取学院department、年级grade、字段column的平均值
      */
     public static double getMean(String column,String department,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select avg(s."+column+") from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=?";
    	 Query query=session.createQuery(hql);
    	 query.setString(0,department);
    	 query.setInteger(1,grade);
         List list=query.list();
         double result=0;
         if(list.size()>0)
         {
         	Iterator it=list.iterator();
         	if(it.hasNext())
         	{
         		Object o=it.next();
         		if(o!=null)
         		result=Double.parseDouble(o.toString());
         	}
         }
         session.clear();
         session.close();
        
         return result;
     }
     
     /**
      *  获取学院department、年级grade、字段column的标准差
      */
     public static double getSD(String column,String department,int grade,double mean)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s."+column+" from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=?";
    	 Query query=session.createQuery(hql);
    	 query.setString(0,department);
    	 query.setInteger(1,grade);
         List list=query.list();
         double result=0;
         int count=0;
         if(list.size()>0)
         {
         	Iterator it=list.iterator();
         	while(it.hasNext())
         	{
         		Object o=it.next();
         		if(o!=null)
         		{
         		 result+=Math.pow(Double.parseDouble(o.toString())-mean,2);
         		 count++;
         		}
         	}
         }
         if(count!=0)
         {
          result=result/count;
          result=Math.sqrt(result);
         }
         session.clear();
         session.close();
      
         return result;
         
     }
     
     /**
      *  获取学院department、年级grade、字段column的四分位值
      */
     public static double[] getQ1Q2Q3(String column,String department,int grade)
     {
    	 double[] Q1Q2Q3=new double[3];	
    	 long count=0;
    	 Session session=SessionManager.getSession();
    	 String hql="select count(s.sno) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=?";
    	 Query query=session.createQuery(hql);
    	 query.setString(0,department);
    	 query.setInteger(1,grade);
         List list=query.list();
         if(list.size()>0)
         {
         	Iterator it=list.iterator();
         	if(it.hasNext())
         	{
         		Object o=it.next();
         		if(o!=null)
         		count=Long.parseLong(o.toString());
         	}
         }
		 long Q1count=Math.round((count*25/100)>(count-1)?(count-1):(count*25/100));
		 long Q2count=Math.round((count*50/100)>(count-1)?(count-1):(count*50/100));
		 long Q3count=Math.round((count*75/100)>(count-1)?(count-1):(count*75/100));
		 if(Q3count<0)
			 Q3count=0;
		 if(Q1count<0)
			 Q1count=0;
		 if(Q2count<0)
			 Q2count=0;
		 hql="select s."+column+" from STUDENT_POINT s, STUDENT_INFO t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=? order by s."+column;
		 //hql="select rownum num,temp.* value from ("+hql+") temp";
		 //hql="select value from ("+hql+") where num="+Q1count;
		 query=session.createSQLQuery(hql);
		 query.setString(0,department);
    	 query.setInteger(1,grade);
    	 List list1=query.list();
         if(list1.size()>0)
         {
         	Q1Q2Q3[0]=Double.parseDouble(list1.get((int)Q1count).toString());
         }
         hql="select s."+column+" from STUDENT_POINT s, STUDENT_INFO t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=? order by s."+column;
		 //hql="select * from ("+hql+") where rownum="+(Q2count+1);
		 query=session.createSQLQuery(hql);
         query.setString(0,department);
    	 query.setInteger(1,grade);
    	 List list2=query.list();
         if(list2.size()>0)
         {
         	Q1Q2Q3[1]=Double.parseDouble(list1.get((int)Q2count).toString());
         }
         hql="select s."+column+" from STUDENT_POINT s, STUDENT_INFO t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=? order by s."+column;
		 //hql="select * from ("+hql+") where rownum="+(Q3count+1);
         query=session.createSQLQuery(hql);
         query.setString(0,department);
    	 query.setInteger(1,grade);
    	 List list3=query.list();
         if(list3.size()>0)
         {
         	Iterator it=list3.iterator();
         	if(it.hasNext())
         	{
         		Q1Q2Q3[2]=Double.parseDouble(list1.get((int)Q3count).toString());
         	}
         }
         session.clear();
         session.close();
         
         return Q1Q2Q3;
     }
     public static List getGenderComparison()
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.department,s.gender,avg(s.total) as avg from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) group by t.department,s.gender";
    	 Query query=session.createQuery(hql);
         List list=query.list();
         session.clear();
         session.close();
        
         return list;
     }
     public static void formatDecimal()
     {
    	 Session session=SessionManager.getSession();
  		 Transaction transaction=session.beginTransaction();
    	 String hql="update DepartmentPoint s set s.total_MEAN=round(s.total_MEAN,2),s.total_SD=round(s.total_SD,2),s.count_MEAN=round(s.count_MEAN,2),s.count_SD=round(s.count_SD,2),"
    	 		+ "s.ave_MEAN=round(s.ave_MEAN,2),s.ave_SD=round(s.ave_SD,2),s.lunch_MEAN=round(s.lunch_MEAN,2),s.lunch_SD=round(s.lunch_SD,2),"
    	 		+ "s.lunch_CNTMEAN=round(s.lunch_CNTMEAN,2),s.lunch_CNTSD=round(s.lunch_CNTSD,2),s.lunch_AVGMEAN=round(s.lunch_AVGMEAN,2),s.lunch_AVGSD=round(s.lunch_AVGSD,2),"
    	 		+ "s.supper_CNTMEAN=round(s.supper_CNTMEAN,2),s.supper_AVGMEAN=round(s.supper_AVGMEAN,2),s.supper_CNTSD=round(s.supper_CNTSD,2),s.supper_AVGSD=round(s.supper_AVGSD,2),"
    	 		+ "s.supper_MEAN=round(s.supper_MEAN,2),s.supper_SD=round(s.supper_SD,2)";
    	 Query query=session.createQuery(hql);
  		 query.executeUpdate();
  		 hql="update StudentPoint s set s.point=round(s.point,2)";
  		 query=session.createQuery(hql);
 		 query.executeUpdate();
  		 transaction.commit(); 
  		session.clear();
        session.close();
        
     }
     public static void main(String[] args)
     {
    	System.out.println(getMax("total","123",12));
     }
}
