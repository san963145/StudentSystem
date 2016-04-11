package com.sys.grades.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import com.sys.manager.dao.SessionManager;

/**
 * 学生所属学院信息
 * @author Administrator
 *
 */
public class DeptLocatorDao {

	/**
	 * 通过学号获取班级号
	 */
    public static String GetClassnoBySno(String sno){
    	Session session=SessionManager.getSession();
    	String hql = "select t.classno from DeptLocator t where trim(t.sno) = ?";
    	Query query=session.createQuery(hql);
    	query.setString(0, sno);
    	List list = query.list();
    	String classno=null;
    	if(list.size()>0)
    	   classno = (String) list.get(0);
    	session.clear();
    	session.close();
    	return classno;
    }
 
    /**
     * 获取所有的学院
     */
    public static List GetAllColleges(){
    	Session session=SessionManager.getSession();
    	String hql = "select distinct(department) from DeptLocator t ";
    	Query query=session.createQuery(hql);
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
    }

    
    /**
     * 根据学院号来获取所有年级
     */
    public static List getGradesByDepartment(String department){
    	Session session=SessionManager.getSession();
   	    String hql="select distinct t.grade from DeptLocator t where t.department=? order by t.grade";
   	    Query query=session.createQuery(hql);
   	    query.setString(0, department);
        List result=null;
        result=query.list();
        session.clear();
        session.close();
        return result;
    }
    /**
     * 根据学院、专业、年级获取所有学生的学号
     */
    public static List GetSnoByCMG(String collegename,String major,int grade)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select distinct t.sno from DeptLocator t where t.department = ? and substr(t.classno,0,2) = ? and substr(t.classno,3,4) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, collegename);
       	query.setString(1, Integer.toString(grade).substring(2,4));
       	query.setString(2, major);
       	List list = query.list();
       	session.clear();
       	session.close();
       	return list;
   	}
    /**
     * 根据学院、专业、年级获取所有学生的学号，姓名
     */
    public static List GetSnosByCMG(String collegename,String major,int grade)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select distinct t.sno,t.sname from DeptLocator t where t.department = ? and substr(t.classno,0,2) = ? and substr(t.classno,3,4) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, collegename);
       	query.setString(1, Integer.toString(grade).substring(2,4));
       	query.setString(2, major);
       	List list = query.list();
       	session.clear();
       	session.close();
       	return list;
   	}
    /**
     * 根据学院、专业获取所有学生的学号
     */
    public static List GetSnosByCMG(String collegename,String major)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select distinct t.sno from DeptLocator t where t.department = ? and substr(t.classno,3,4) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, collegename);
       	query.setString(1, major);
       	List list = query.list();
       	session.clear();
       	session.close();
       	return list;
   	}
    /**
   	 * 根据学号查询出所有信息
   	 * @param args
   	 */
   	public static List GetStuInfoBySno(String sno){
   		Session session=SessionManager.getSession();
    	String hql = "select t.sno,t.sname,t.fromprovince,t.department,t.grade,t.classno,t.sex,t.people from DeptLocator t where trim(t.sno) = ?";
    	Query query=session.createQuery(hql);
    	query.setString(0, sno.trim());
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
   	}
   	/**
   	 * 根据学院、专业 获取年级列表
   	 * @param args
   	 */
   	public static List GetGradesByMajor(String department,String major)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select  distinct t.grade from DeptLocator t where t.department=? and substr(t.classno,3,4) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, department);
       	query.setString(1, major);
       	List list = query.list();
       	session.clear();
       	session.close();
       	return list;
   	}
   	/**
   	 * 根据学院获取专业列表
   	 * @param args
   	 */
   	public static List getMajorNumByDepartment(String department)
    {
 	   Session session=SessionManager.getSession();
   	   String hql="select distinct substr(t.classno,3,4) from DeptLocator t where t.department=?";
   	   Query query=session.createQuery(hql);
   	   query.setString(0, department);
        List result=null;
        result=query.list();
        if(result!=null)
        {
        	Object[] obj=result.toArray();
    		Arrays.sort(obj);
    		result=Arrays.asList(obj);
        }
        session.clear();
        session.close();
        return result;
    }
    /**
     * 根据学生姓名查询信息
     * @param department
     * @param major
     * @return
     */
 	public static List GetStuInfoBySname(String sname){
 		Session session = SessionManager.getSession();
 		List list=new ArrayList<>();
 		String hql = "select t.sno,t.sname,t.department,s.majorName,substr(t.classno,0,2) from DeptLocator t,MajorName s where t.sname = ? and substr(t.classno,3,4)=s.majorNum";
 		Query query = session.createQuery(hql);
 		query.setString(0, sname);
 		list = query.list();
 		session.clear();
 		session.close();
 		return list;
 	}
 	/**
	 * 根据学院年级查询学生信息
	 * @param department
	 * @param major
	 * @return
	 */
	public static List GetStuInfoByCG(String collegename,String grade){
		Session session = SessionManager.getSession();
		if("全部".equals(grade)){
			String hql = "select t.sno,t.sname,t.department,s.majorName,substr(t.classno,0,2) from DeptLocator t,MajorName s where t.department = ?  and substr(t.classno,3,4)=s.majorNum";
			Query query = session.createQuery(hql);
			query.setString(0, collegename);
			List list = query.list();
			session.clear();
			session.close();
			return list;
		}else{
			String hql = "select t.sno,t.sname,t.department,s.majorName,substr(t.classno,0,2) from DeptLocator t,MajorName s where t.department = ? and substr(t.classno,0,2) = ?  and substr(t.classno,3,4)=s.majorNum";
			Query query = session.createQuery(hql);
			query.setString(0, collegename);
			query.setString(1, grade.substring(2, 4));
			List list = query.list();
			session.clear();
			session.close();
			return list;
		}
	}
	/**
	 * 根据学院年级、姓名查询学生信息
	 * @param department
	 * @param major
	 * @return
	 */
	public static List GetStuInfoByCG(String sname,String collegename,String grade){
		Session session = SessionManager.getSession();
		if("全部".equals(grade)){
			String hql = "select t.sno,t.sname,t.department,s.majorName,substr(t.classno,0,2) from DeptLocator t,MajorName s where t.department = ? and t.sname=? and substr(t.classno,3,4)=s.majorNum";
			Query query = session.createQuery(hql);
			query.setString(0, collegename);
			query.setString(1, sname);
			List list = query.list();
			session.clear();
			session.close();
			return list;
		}else{
			String hql = "select t.sno,t.sname,t.department,s.majorName,substr(t.classno,0,2) from DeptLocator t,MajorName s where t.department = ? and substr(t.classno,0,2) = ? and t.sname=? and substr(t.classno,3,4)=s.majorNum";
			Query query = session.createQuery(hql);
			query.setString(0, collegename);
			query.setString(1, grade.substring(2, 4));
			query.setString(2, sname);
			List list = query.list();
			session.clear();
			session.close();
			return list;
		}
	}
	/**
     * 判断学号是否在学院范围内
     */
    public static String Check(String sno,String department)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select t.sno from DeptLocator t where t.department = ? and trim(t.sno) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, department);
       	query.setString(1, sno);
       	List list = query.list();
       	session.clear();
       	session.close();
       	if(list.size()>0)
       	    return "1";
       	else
       		return "2";
   	}
    /**
     * 判断学号是否在学院、年级范围内
     */
    public static String Check(String sno,String department,String grade)
   	{
    	String flag="2";
    	String[] grades=grade.split("#");
   		Session session=SessionManager.getSession();
       	String hql = "select t.sno from DeptLocator t where t.department = ? and trim(t.sno) = ? and substr(t.classno,0,2) = ?";
       	Query query=null;
       	for(int i=0;i<grades.length;i++)
       	{
       	  query=session.createQuery(hql);
       	  query.setString(0, department);
       	  query.setString(1, sno);
       	  query.setString(2, grades[i].substring(2,4));
       	  List list = query.list();
       	  if(list.size()>0)
       	  {
       		  flag="1";
       		  break;
       	  }
       	}
       	session.clear();
       	session.close();
       	return flag;
   	}
    /**
     * 判断姓名是否在学院范围内
     */
    public static String CheckSname(String sname,String department)
   	{
   		Session session=SessionManager.getSession();
       	String hql = "select t.sname from DeptLocator t where t.department = ? and trim(t.sname) = ?";
       	Query query=session.createQuery(hql);
       	query.setString(0, department);
       	query.setString(1, sname);
       	List list = query.list();
       	session.clear();
       	session.close();
       	if(list.size()>0)
       	    return "1";
       	else
       		return "2";
   	}
    /**
     * 判断姓名是否在学院、年级范围内
     */
    public static String CheckSname(String sname,String department,String grade)
   	{
    	String flag="2";
    	String[] grades=grade.split("#");
   		Session session=SessionManager.getSession();
       	String hql = "select t.sname from DeptLocator t where t.department = ? and trim(t.sname) = ? and substr(t.classno,0,2) = ?";
       	Query query=null;
       	for(int i=0;i<grades.length;i++)
       	{
       	  query=session.createQuery(hql);
       	  query.setString(0, department);
       	  query.setString(1, sname);
       	  query.setString(2, grades[i].substring(2,4));
       	  List list = query.list();
       	  if(list.size()>0)
       	  {
       		  flag="1";
       		  break;
       	  }
       	}
       	session.clear();
       	session.close();
       	return flag;
   	}
	public static void main(String[] args)
	{
		String flag=Check("20126652","计算机学院","2012");
		System.out.println(flag);
	}
}
