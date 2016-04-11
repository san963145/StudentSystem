package com.sys.grades.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.sys.grades.bean.GradeSearchByGroup;
import com.sys.manager.dao.SessionManager;

public class GradeSearchByGroupDao {
	/**
   	 * 添加一行记录
   	 * @param args
   	 */
	public static void add(GradeSearchByGroup gradeSearchByGroup) {
		Session session = SessionManager.getSession();
		Transaction transaction = session.beginTransaction();
	    session.save(gradeSearchByGroup);
		transaction.commit();
		session.clear();
		session.close();
	}
	/**
   	 * 清空GradeSearchByGroup表
   	 * @param args
   	 */
	public static void clear()
    {
 	   Session session=SessionManager.getSession();
 	   Transaction transaction=session.beginTransaction();
 	   String hql="delete from GradeSearchByGroup";
 	   Query query=session.createQuery(hql);
 	   query.executeUpdate();
 	   transaction.commit(); 
 	  session.clear();
 	   session.close();
    }
	/**
   	 * 格式化GradeSearchByGroup表
   	 * @param args
   	 */
    public static void formatDecimal()
    {
 	   Session session=SessionManager.getSession();
 	   Transaction transaction=session.beginTransaction();
 	   String hql="update GradeSearchByGroup t set t.gpa=round(t.gpa,2),t.avg=round(t.avg,2),t.selected=round(t.selected,2),t.had=round(t.had,2),t.hadRate=round(t.hadRate,2)";
 	   Query query=session.createQuery(hql);
 	   query.executeUpdate();
 	   transaction.commit(); 
 	  session.clear();
 	   session.close(); 
    }
    /**
   	 * 根据学院、专业、年级 进行查询
   	 * @param args
   	 */
    public static List selectByGroup(String department,String major,int grade)
    {
 	   Session session=SessionManager.getSession();
   	   String hql="select d.gpa,d.avg,d.selected,d.had,d.hadRate from GradeSearchByGroup d where d.department = ? and d.majorNum = ? and d.grade = ? order by d.semester asc";
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
    public static void main(String[]args)
	{
		 List list=selectByGroup("计算机学院","01",2012);
		 for(int i=0;i<list.size();i++)
		 {
			 Object[] obj=(Object[]) list.get(i);
			 System.out.println(obj[0].toString()+" "+obj[1].toString()+" "+obj[2].toString()+" "+obj[3].toString()+" "+obj[4].toString()+" ");
		 }
	}
}
