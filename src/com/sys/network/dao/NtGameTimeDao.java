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
import java.util.List;
import java.util.Properties;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;
import com.sys.network.servlet.AccessReport;

public class NtGameTimeDao {
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
	
	/**
	 * 计算全校学生 平均游戏时间
	 * 
	 *  
	 */
	public static Double GetAGT() throws Exception{
		init();
		Class.forName(driverClass);
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		Double s=null;
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";			
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select d.sno,sum(d.online_time) online_time from nt_game_time d group by d.sno) r");
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
	 * 根据学院、专业、年级、性别获取所有平均游戏时间
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static Double GetAGTByCMGS(String collegename, String major,String grade, String sex) throws ClassNotFoundException, SQLException 
	{	
		init();
		Class.forName(driverClass);
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		Double s=null;
		if ("全部".equals(sex)&&"全部".equals(grade)&&"全部".equals(major)) {
			//hql = "select avg(r.online_time) from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DeptLocator s where s.department = ?) t,NtRecordCount d where t.sno = d.sno group by t.sno) r ";			
			try
			{
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,0,2) = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,3,4) = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and s.sex = ? and substr(s.classno,0,2) = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
				PreparedStatement ps=conn.prepareStatement("select round(avg(r.online_time)/3600.0,2) avg from (select t.sno,sum(d.online_time) online_time from (select distinct s.sno from DEPTLOCATOR s where s.department = ? and substr(s.classno,3,4) = ? and substr(s.classno,0,2) = ? and s.sex = ?) t,nt_game_time d where t.sno = d.sno group by t.sno) r");
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
	 * 根据学号查询出学生每个月的玩游戏时间
	 */
	public static List getNtGTBySno(String sno){
		Session session=SessionManager.getSession();
    	String hql = "select t.yearMonth,round(sum(t.onlineTime)/3600.0,2) from NtGameTime t where trim(t.sno) = ? group by t.yearMonth order by t.yearMonth";
    	Query query=session.createQuery(hql);
    	query.setString(0, sno.trim());
    	List list = query.list();
    	session.clear();
    	session.close();
    	return list;
	}

	public static void main(String[] args){
		 
	}
}
