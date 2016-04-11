package com.sys.card.dao;



import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.card.bean.StudentPoint;
import com.sys.card.servlet.CalculateStatistics;
import com.sys.manager.dao.SessionManager;

/**
 *  学生消费记录访问接口
 */
public class StudentPointDao {
	
	/**
     *  计算全校平均消费数据
     */
    public static List selectSchoolAvg()
    {
   	    Session session=SessionManager.getSession();
   	    String hql="select round(avg(s.average),2),round(avg(s.lunchAVG),2),round(avg(s.supperAVG),2) from StudentPoint s";
   	    Query query=session.createQuery(hql);
        List result=null;
        result=query.list();
        session.clear();
        session.close();
        return result;
    }
	/**
	 *  按学号查询
	 */
	 public static StudentPoint selectByPerson(String sno)
	 {
		 Session session=SessionManager.getSession();
    	 String hql="from StudentPoint where trim(sno)=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,sno);
         StudentPoint studentPoint=null;
         List<StudentPoint>list=query.list();
         if(list!=null)
         {
        	 if(list.size()!=0)
        	 {
        		 studentPoint=list.get(0);
        	 }
         }
         session.clear();
         session.close();
         return studentPoint;
	 }
	 
	 /**
	  *  按学号查询姓名
	  */
	 public static String selectNameBySno(String sno)
	 {
		 Session session=SessionManager.getSession();
    	 String hql="select t.sname from StudentInfo t where trim(sno)=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,sno);
         List list=query.list();
         String result=null;
         if(list!=null)
         {
        	 Iterator it=list.iterator();
        	 if(it.hasNext())
        	 {
        		 String s=(String) it.next();
        		 if(s!=null)
        		 result=s;
        	 }
         }
         session.clear();
         session.close();
         return result;
	 }
	 
	 /**
	  *  按学院、年级查询
	  */
     public static List select(String department,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,s.gender,s.hasScholarship,s.total,s.count,s.average,s.lunchTTL,s.lunchCNT,s.lunchAVG,s.supperTTL,s.supperCNT,s.supperAVG,s.point from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setInteger(1,grade);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     /**
	  *  按学院、专业、年级查询（消费记录）
	  */
     public static List selectByDMG(String department,String major,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,s.gender,s.hasScholarship,s.total,s.count,s.average,s.lunchTTL,s.lunchCNT,s.lunchAVG,s.supperTTL,s.supperCNT,s.supperAVG,s.point from StudentPoint s,StudentInfo t,DeptLocator d where trim(s.sno)=trim(t.sno) and trim(s.sno)=trim(d.sno) and t.department=? and substr(d.classno,3,4) = ? and t.grade=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setString(1,major);
         query.setInteger(2,grade);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     /**
	  *  按学院、专业、年级查询（消费预测）
	  */
     public static List predictByDMG(String department,String major,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,d.department,d.grade,s.total from StudentPoint s,StudentInfo t,DepartmentPoint d where trim(s.sno)=trim(t.sno) and t.department=d.department and t.grade=d.grade and d.department=? and substr(t.classNo,3,4) = ? and t.grade=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setString(1,major);
         query.setInteger(2,grade);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     /**
	  *  按学院、专业、年级查询（消费评级）
	  */
     public static List rateByDMG(String department,String major,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,s.point from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and substr(t.classNo,3,4) = ? and t.grade=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setString(1,major);
         query.setInteger(2,grade);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     /**
      *  按学院查询
      */
     public static List select(String department)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,s.gender,s.hasScholarship,s.total,s.count,s.average,s.lunchTTL,s.lunchCNT,s.lunchAVG,s.supperTTL,s.supperCNT,s.supperAVG,s.point from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     
     /**
      *  查询全部学生
      */
     public static List select()
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.sno,t.sname,s.gender,s.hasScholarship,s.total,s.count,s.average,s.lunchTTL,s.lunchCNT,s.lunchAVG,s.supperTTL,s.supperCNT,s.supperAVG,s.point from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno)";
    	 Query query=session.createQuery(hql);
         List result=null;
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     
     /**
      *  更新学生评分
      */
     public static void updatePoint(String sno,double point)
     {
    	 Session session=SessionManager.getSession();
		 Transaction transaction=session.beginTransaction();
    	 String hql="update StudentPoint s set s.point=? where trim(s.sno)=?";
    	 Query query=session.createQuery(hql);
    	 query.setDouble(0,point);
    	 query.setString(1,sno);
    	 query.executeUpdate();
		 transaction.commit();    
		 session.clear();
		 session.close();
     }  
     
	 /**
      *  统计绘图 （查询全校平均消费数据）
      */
     public static List selectDrawDataBySchool()
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2),round(avg(s.lunchCNT),2),round(avg(s.supperCNT),2),round(avg(s.count),2) from StudentPoint s";
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
      *  统计绘图 （根据性别查询全校平均消费数据）
      */
     public static List selectDrawDataBySchool(String gender)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s where s.gender=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,gender);
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
      *  统计绘图 （学院间消费对比数据）
      */
     public static List selectDrawByDepartmentComparison()
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.department,round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) group by t.department order by t.department";
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
      *  统计绘图 （学院间按 性别 消费对比数据）
      */
     public static List selectDrawByDepartmentComparison(String gender)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.department,round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and s.gender=? group by t.department order by t.department";
    	 Query query=session.createQuery(hql);
         query.setString(0,gender);
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
      *  统计绘图 （查询学院平均消费数据）
      */
     public static List selectDrawDataByDepartment(String department)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2),round(avg(s.lunchCNT),2),round(avg(s.supperCNT),2),round(avg(s.count),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? ";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
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
      *  统计绘图 （根据性别查询学院平均消费数据）
      */
     public static List selectDrawDataByDepartment(String department,String gender)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and s.gender=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setString(1,gender);
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
      *  统计绘图 （学院内年级间消费对比数据）
      */
     public static List selectDrawByGradeComparison(String department)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.grade,round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? group by t.grade order by t.grade";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
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
      *  统计绘图 （年级间按 性别 消费对比数据）
      */
     public static List selectDrawByGradeComparison(String department,String gender)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select t.grade,round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and s.gender=? group by t.grade order by t.grade";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setString(1,gender);
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
      *  统计绘图 （查询年级内平均消费数据）
      */
     public static List selectDrawDataByGrade(String department,String grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2),round(avg(s.lunchCNT),2),round(avg(s.supperCNT),2),round(avg(s.count),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=? ";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setInteger(1,Integer.parseInt(grade));
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
      *  统计绘图 （根据性别查询年级内平均消费数据）
      */
     public static List selectDrawDataByGrade(String department,String grade,String gender)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select round(avg(s.lunchTTL),2),round(avg(s.supperTTL),2),round(avg(s.total),2) from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and t.grade=? and s.gender=?";
    	 Query query=session.createQuery(hql);
         query.setString(0,department);
         query.setInteger(1,Integer.parseInt(grade));
         query.setString(2,gender);
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
      *  助学金推荐等级划分 （根据学院、专业、年级获取所有学生的评分）
      */
     public static List selectPointByDMG(String department,String major,int grade)
     {
    	 Session session=SessionManager.getSession();
    	 String hql="select s.point from StudentPoint s,StudentInfo t where trim(s.sno)=trim(t.sno) and t.department=? and substr(t.classNo,3,4)=? and t.grade=? order by s.point desc";
    	 Query query=session.createQuery(hql);
         List result=null;
         query.setString(0,department);
         query.setString(1,major);
         query.setInteger(2,grade);
         result=query.list();
         session.clear();
         session.close();
         return result;
     }
     /**
      *  格式化StudentPoint表
      */
     public static void formatDecimal()
     {
    	 Session session=SessionManager.getSession();
  		 Transaction transaction=session.beginTransaction();
    	 String hql="update StudentPoint s set s.average=round(s.average,2),s.lunchAVG=round(s.lunchAVG,2),s.supperAVG=round(s.supperAVG,2)";
    	 Query query=session.createQuery(hql);
  		 query.executeUpdate();
  		 hql="update StudentPoint s set s.point=round(s.point,2)";
  		 query=session.createQuery(hql);
 		 query.executeUpdate();
  		 transaction.commit(); 
  		 session.clear();
  		 session.close();
  		 CalculateStatistics.setFlag();
     }
     
     public static void main(String[] args)
     {
    	 formatDecimal();
    	 /*
    	 List list=selectDrawDataByGrade("计算机学院","2011");
    	 System.out.println(list);
    	 for(int i=0;i<list.size();i++)
    	 {
    		 Object[] obj=(Object[]) list.get(i);
    		 System.out.println(obj[0].toString());//+" "+obj[1].toString()+" "+obj[2].toString()+" "+obj[3].toString());//+" "+obj[3].toString()+" "+obj[4].toString()+" "+obj[5].toString());
    	 }
    	 */
     }
}