package com.sys.index.dao;

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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sys.manager.dao.SessionManager;
import com.sys.network.bean.CategoryNameNum;
import com.sys.network.servlet.AccessReport;

public class NetworkDao {
	
	private static String driverClass="oracle.jdbc.driver.OracleDriver";
	private static String jdbcURL="jdbc:oracle:thin:@172.31.2.33:1529:ORCLSCHOOL";
	private static String name="StuSysUser";
	private static String pwd="StuSys12345678";
	
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
     *  查询全体学生访问量Top10网站类型
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
     */
    public static ArrayList<CategoryNameNum> get() throws ClassNotFoundException, SQLException
    {
    	init();
		Class.forName(driverClass);		
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		ArrayList<CategoryNameNum> list=new ArrayList<CategoryNameNum>();
		try
		{
			PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s) b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
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
    public static List getGroups(String department,String major,String grade,String sex,String column)
    {
    	Session session=SessionManager.getSession();
    	String d="";
		String m="";
		String g="";
		String s="";
		if(department!=null)
		{
			d=" and s.department='"+department+"'";
		}
		if(major!=null)
		{
			m=" and substr(s.classno,3,4)='"+major+"'";
		}
		if(grade!=null)
		{
			g=" and substr(s.classno,0,2)='"+grade.substring(2,4)+"'";
		}
		if(sex!=null)
		{
			s=" and s.sex='"+sex+"'";
		}
    	String hql="";
    	if(column.equals("department"))
    	{
   	        hql="select distinct s.department from DeptLocator s where s.sno is not null"+d+m+g+s+" order by s.department";
    	}
    	else if(column.equals("major"))
    	{
    		hql="select distinct substr(s.classno,3,4) from DeptLocator s where s.sno is not null"+d+m+g+s+" order by substr(s.classno,3,4)";	
    	}
    	else if(column.equals("grade"))
    	{
    		hql="select distinct substr(s.classno,0,2) from DeptLocator s where s.sno is not null"+d+m+g+s+" order by substr(s.classno,0,2)";
    	}
    	else if(column.equals("sex"))
    	{
    		hql="select distinct s.sex from DeptLocator s where s.sno is not null"+d+m+g+s+" order by s.sex";
    	}
   	    Query query=session.createQuery(hql);
        List list=query.list();
        session.clear();
        session.close();
        return list;
    }
    /**
     *  查询条件范围内学生 访问量Top10网站类型
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
	public static Map<String,ArrayList<CategoryNameNum>> get(String department,String major,String grade,String sex,String column) throws ClassNotFoundException, SQLException
	{
		String d="";
		String m="";
		String g="";
		String s="";
		if(department!=null)
		{
			d=" and s.department='"+department+"'";
		}
		if(major!=null)
		{
			m=" and substr(s.classNo,3,4)='"+major+"'";
		}
		if(grade!=null)
		{
			g=" and substr(s.classNo,0,2)='"+grade.substring(2,4)+"'";
		}
		if(sex!=null)
		{
			s=" and s.sex='"+sex+"'";
		}
   	    init();
		Class.forName(driverClass);		
		Connection conn=DriverManager.getConnection(jdbcURL, name, pwd);
		Map<String,ArrayList<CategoryNameNum>>map=new LinkedHashMap<String,ArrayList<CategoryNameNum>>();
		List groupList=getGroups(department,major,grade,sex,column);
		for(int i=0;i<groupList.size();i++)
		{			
			ArrayList<CategoryNameNum> list=new ArrayList<CategoryNameNum>();
			String obj=(String) groupList.get(i);
			String sc="";
			if(column.equals("department"))
			{
				sc=" and s.department='"+obj+"'";
			}
			else if(column.equals("major"))
			{
				sc=" and substr(s.classNo,3,4)='"+obj+"'";
			}
			else if(column.equals("grade"))
			{
				sc=" and substr(s.classNo,0,2)='"+obj+"'";
			}
			else
			{
			   sc=" and s.sex='"+obj+"'";
			}
			try
			{
				PreparedStatement ps=conn.prepareStatement("select s.category_name name,e.sum num from (select c.sub_category_id,sum(c.access_count) sum from (select distinct s.sno from DEPTLOCATOR s where s.sno is not null"+d+m+g+s+sc+") b,nt_record_count c where b.sno=c.sno group by c.sub_category_id) e,nt_sub_category s where s.category_id=e.sub_category_id order by e.sum desc");
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
				
			  }		
			ArrayList<CategoryNameNum>result=new ArrayList<CategoryNameNum>();
			if(list.size()>0)
			{
				for(int j=0;j<10;j++)
				{
					result.add(list.get(j));
				}
			}
			if(result.size()>0)
			  map.put(obj, result);
		}	
		conn.close();
	    return map;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
	 Map<String,ArrayList<CategoryNameNum>> map=get(null,null,null,null,"sex");
   	 Set<String>s=map.keySet();
   	 Iterator it=s.iterator();
   	 while(it.hasNext())
   	 {
   		 String key=(String) it.next();
   		 ArrayList<CategoryNameNum> list=map.get(key);
   		 System.out.println(key);
   		 for(int i=0;i<list.size();i++)
   		 {
   			CategoryNameNum c=list.get(i);
   			System.out.println(c.getName()+" "+c.getNum());
   		 }
   		 
   	 }
    }
}
