package com.sys.network.dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;







import java.util.Properties;


import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;
import com.sys.network.bean.CategoryNameNum;
import com.sys.network.servlet.AccessReport;

public class NtRecordCountDao {
	private static String driverClass="";
	private static String jdbcURL="";
	private static String name="";
	private static String pwd="";
	
	
	private static void init()
	{
		Properties prop=new Properties();
	    String path = AccessReport.indexUrlPath;
	    InputStream in=null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    driverClass = prop.getProperty("driverClass");
	    jdbcURL = prop.getProperty("jdbcURL");
	    name = prop.getProperty("name");
	    pwd = prop.getProperty("pwd");
	}
	
	
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
	/**
	 * 通过子类id获取访问量
	 */
	public static int GetAccessCountBySubId(int sid){
		Session session=SessionManager.getSession();
    	String hql = "select sum(t.accessCount) from NtRecordCount t where t.subCategoryId = ?";
    	Query query=session.createQuery(hql);
    	query.setInteger(0, sid);
    	List list = query.list();
    	int accesscount = 0;
    	if(list.size()>0){
    		 if(list.get(0)!=null){
    			 accesscount = Integer.parseInt(list.get(0).toString()); 
    		 }
    	}
    	session.clear();
    	session.close();
    	return accesscount;
	}
	/**
	 * 获取范围内最常访问的10类网站
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static ArrayList<CategoryNameNum> getTop10Category(String collegename, String major,String grade, String sex) throws SQLException, ClassNotFoundException{
		init();
		Class.forName(driverClass);
		
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		ArrayList<CategoryNameNum> list=new ArrayList<CategoryNameNum>();
		if ("全部".equals(sex)&&"全部".equals(grade)&&"全部".equals(major)) {
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";			
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.execute();
				ResultSet rs=ps.getResultSet();	
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
			
		}else if("全部".equals(sex)&&"全部".equals(grade)&&!"全部".equals(major)){
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ? and substr(s.classno,5,2) = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if("全部".equals(sex)&&!"全部".equals(grade)&&!"全部".equals(major)){
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ? and substr(s.classno,5,2) = ? and substr(s.classno,0,2) = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.setString(3, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if("全部".equals(sex)&&!"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,0,2) = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&"全部".equals(grade)&&!"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,3,4) = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.setString(3, major);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&!"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,0,2) = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.setString(3, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else  
		{
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ? and s.sex = ?) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.setString(3, grade);
				ps.setString(4, sex);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				while(rs.next())
				{
					CategoryNameNum n=new CategoryNameNum();
					n.setName(rs.getString("name"));
					n.setNum(rs.getInt("num"));
					list.add(n);
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}
		
		ArrayList<CategoryNameNum>result=new ArrayList<CategoryNameNum>();
		if(list.size()>0)
		{
			for(int i=0;i<10;i++)
			{
				result.add(list.get(i));
			}
		}
		return result;
	}
	/**
	 * 根据学院、专业、年级、性别获取所有平均游戏时间
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static Double GetAOTByCMGS(String collegename, String major,String grade, String sex) throws ClassNotFoundException, SQLException 
	{	
		init();
		Class.forName(driverClass);
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		Double s=null;
		if ("全部".equals(sex)&&"全部".equals(grade)&&"全部".equals(major)) {
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";			
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if("全部".equals(sex)&&"全部".equals(grade)&&!"全部".equals(major)){
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ? and substr(s.classno,5,2) = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if("全部".equals(sex)&&!"全部".equals(grade)&&!"全部".equals(major)){
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ? and substr(s.classno,5,2) = ? and substr(s.classno,0,2) = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.setString(3, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if("全部".equals(sex)&&!"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,0,2) = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&"全部".equals(grade)&&!"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,3,4) = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.setString(3, major);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else if(!"全部".equals(sex)&&!"全部".equals(grade)&&"全部".equals(major)){
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,0,2) = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, sex);
				ps.setString(3, grade);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}else  
		{
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ? and s.sex = ?) t,nt_record_count d where t.sno = d.sno group by t.sno) r");
				ps.setString(1, collegename);
				ps.setString(2, major);
				ps.setString(3, grade);
				ps.setString(4, sex);
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
		}
		return s;
	}
	
	/**
	 * 获取全校学生 平均上网时间
	 */
	public static Double GetAOT() throws Exception{
		init();
		Class.forName(driverClass);
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		Double s=null;
		 
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";			
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select d.sno,sum(d.online_time) online_time from nt_record_count d group by d.sno) r");
				ps.execute();
				ResultSet rs=ps.getResultSet();			
				if(rs.next())
				{
					s=rs.getDouble("avg");
				}			
			}  catch(Exception e)
			  {
				e.printStackTrace();
			  }		
			   finally{
				conn.close();
			  }
			return s;
	}
	/**
	 * 根据学号查询出学生每个月的上网时间
	 */
	public static List getNtOTBySno(String sno){
		Session session=SessionManager.getSession();
    	String hql = "select t.yearMonth,round(sum(t.onlineTime)/3600.0,2) from NtRecordCount t where trim(t.sno) = ? group by t.yearMonth order by t.yearMonth";
    	Query query=session.createQuery(hql);
    	query.setString(0, sno.trim());
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}


	/**
	 * 根据学号查询学生浏览前十sub
	 */
	public static ArrayList<CategoryNameNum> getTop10BySno(String sno){
		Session session=SessionManager.getSession();
    	String hql = " select u.categoryName,sum(t.accessCount) from NtRecordCount t,NtSubCategory u where t.subCategoryId = u.categoryId and trim(t.sno) = ? group by u.categoryName order by sum(t.accessCount) desc";
    	Query query=session.createQuery(hql);
    	query.setString(0, sno.trim());
    	List list = query.list();
    	session.clear();
    	session.close();
    	ArrayList<CategoryNameNum>result=new ArrayList<CategoryNameNum>();
		if(list.size()>0)
		{
			for(int i=0;i<10;i++)
			{
				CategoryNameNum c=new CategoryNameNum();
				Object[] obj=(Object[]) list.get(i);
				c.setName(obj[0].toString());
				c.setNum(Integer.parseInt(obj[1].toString()));
				result.add(c);
			}
		}
		return result;
	}

	

	public static void main(String[] args){
		List list=getNtOTBySno("20111883");
		for(int i=0;i<list.size();i++)
		{
			Object[] obj=(Object[]) list.get(i);
			System.out.println(obj[1]);
		}
	}
}
