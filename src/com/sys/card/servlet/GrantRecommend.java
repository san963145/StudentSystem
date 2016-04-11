package com.sys.card.servlet;

import java.io.IOException;
import java.util.ArrayList;


import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import javax.servlet.http.HttpSession;

import com.sys.card.bean.RatingResult;
import com.sys.card.bean.StudentPoint;
import com.sys.card.dao.MajorNameDao;
import com.sys.card.dao.PointExceptionDao;
import com.sys.card.dao.StudentInfoDao;
import com.sys.card.dao.StudentPointDao;
import com.sys.grades.dao.DeptLocatorDao;
import com.sys.manager.bean.User;





/**
 * 助学金推荐
 */
@WebServlet("/GrantRecommend")
public class GrantRecommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GrantRecommend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * 请求获取年级内助学金推荐图表
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String department=request.getParameter("department");
		String major=request.getParameter("major");
		String grade=request.getParameter("grade");
		String sno=request.getParameter("sno");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String accessFlag="1";		
	   if(sno==null)
	  {
		String majorNum=MajorNameDao.getNumByName(department,major);
		List sList=StudentPointDao.rateByDMG(department, majorNum, Integer.parseInt(grade));
		List<RatingResult>resultList=new ArrayList<RatingResult>();		
		ArrayList<String[]> result=getResult(department,majorNum,grade);
		if(result.size()>0)
		{
		 List list=StudentPointDao.selectPointByDMG(department, majorNum, Integer.parseInt(grade));
	     if(sList.size()>0)
		 {
		  Iterator it=sList.iterator();
		  while(it.hasNext())
		  {
			  Object[]obj=(Object[]) it.next();
			  RatingResult r=new RatingResult();
			  String snum=obj[0].toString();
			  r.setSno(snum);
			  r.setSname(obj[1].toString());
			  r.setDepartment(department);
			  r.setGrade(grade);
			  r.setMajor(major);
			  double point=Double.parseDouble(obj[2].toString());
			  String p=getRank(point,list);
			  r.setPoint(p);
			  String info=PointExceptionDao.selectInfoBySno(snum);
			  r.setInfo(info);
			  resultList.add(r);			  
		  }
		 }
		}
	    request.setAttribute("result", result);
	    request.setAttribute("resultList", resultList);
		request.setAttribute("department",department);
		request.setAttribute("major",major);
		request.setAttribute("grade",grade);
	    request.getRequestDispatcher("pages/cardSystem/recommendByGroup.jsp").forward(request, response);
	  }
		else
		{
			if(user.getAuthority().equals("Dean"))
				 accessFlag=DeptLocatorDao.Check(sno, user.getDepartment());
			if(user.getAuthority().equals("Instructor"))
				 accessFlag=DeptLocatorDao.Check(sno, user.getDepartment(),user.getGrade());
			StudentPoint studentPoint=null;
		    studentPoint=StudentPointDao.selectByPerson(sno);
		    String d=StudentInfoDao.selectDepartmentBySno(sno);
		    String m=StudentInfoDao.selectMajorBySno(sno);
		    if(studentPoint!=null && d!=null && m!=null)
		    {	
			   String[] studentResult=new String[8];
			   studentResult[0]=sno;
			   studentResult[1]=StudentPointDao.selectNameBySno(sno);
			   studentResult[2]=studentPoint.getGender();
			   studentResult[3]=d;
			   studentResult[4]=new Double(studentPoint.getPoint()).toString();
			   studentResult[5]=getRank(studentPoint.getPoint(),d,m,sno.substring(0,4));
			   String info=PointExceptionDao.selectInfoBySno(sno);
			   studentResult[6]=info;			   
		       ArrayList<String[]> results=getResult(d,m,sno.substring(0,4));
		       m=MajorNameDao.getNameByNum(m);
			   studentResult[7]=m;
	           request.setAttribute("result",results);
	           request.setAttribute("studentResult", studentResult);
		    }
		    else
		    {
		    	request.setAttribute("error","1");
		    }
		    request.setAttribute("sno",sno);
		    request.setAttribute("accessFlag", accessFlag);
	        request.getRequestDispatcher("pages/cardSystem/recommendByPerson.jsp").forward(request, response);
		
		 }
	     
	}
	
	/**
 	 *  生成年级内助学金推荐划分标准表格
 	 */ 
     private  ArrayList<String[]> getResult(String department,String major,String grade)
     {
     	 ArrayList<String[]>result=new ArrayList<String[]>();
     	 List list=StudentPointDao.selectPointByDMG(department, major, Integer.parseInt(grade));
     	 
     	 if(list.size()>0)
     	{     	 
     	 int n=list.size();
     	 int x=(int) ((double)n/100.0*8.0);
     	 Double s1=Double.parseDouble(list.get(x).toString());
     	 x=(int) ((double)n/100.0*32.0);
     	 Double s2=Double.parseDouble(list.get(x).toString());
     	 x=(int) ((double)n/100.0*40.0);
     	 Double s3=Double.parseDouble(list.get(x).toString());
     	 x=(int) ((double)n/100.0*90.0);
     	 Double s4=Double.parseDouble(list.get(x).toString());
 	     String x1=s1.toString();
 	     String x2=s2.toString();
 	     String x3=s3.toString();
 	     String x4=s4.toString();
 	     String[] t1=new String[3];
 	     String[] t2=new String[3];
 	     String[] t3=new String[3];
 	     String[] t4=new String[3];
 	     String[] t5=new String[3];
 	     t1[0]="高于"+x1;
 	     t1[1]="5星";
 	     t1[2]="8%";
 	     t2[0]=x2+"-"+x1;
 	     t2[1]="4星";
 	     t2[2]="24%";
 	     t3[0]=x3+"-"+x2;
 	     t3[1]="3星";
 	     t3[2]="8%";
 	     t4[0]=x4+"-"+x3;
 	     t4[1]="2星";
 	     t4[2]="50%";
 	     t5[0]="低于"+x4;
 	     t5[1]="1星";
 	     t5[2]="10%";
 	     result.add(t1);
 	     result.add(t2);
 	     result.add(t3);
 	     result.add(t4);
 	     result.add(t5);
     	}
     	 return result;
     }
    
	/**
	 *  根据学生消费评分生成助学金推荐结果
	 */ 
    private String getRank(double point,List list)
    {
    	 if(list.size()>0)
    	{     	 
    	 int n=list.size();
    	 int x=(int) ((double)n/100.0*8.0);
    	 Double s1=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*32.0);
    	 Double s2=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*40.0);
    	 Double s3=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*90.0);
    	 Double s4=Double.parseDouble(list.get(x).toString());
	     if(point>s1)
	    	 return "5星";
	     else if(point<=s1 &&point>s2)
	     {
	    	 return "4星";
	     }
	     else if(point<=s2 &&point>s3)
	     {
	    	 return "3星";
	     }
	     else if(point<=s3 &&point>s4)
	     {
	    	 return "2星";
	     }
	     else
	     {
	    	 return "1星";
	     }
    	}
    	 return null;
    }
    /**
	 *  根据学生消费评分生成助学金推荐结果
	 */ 
    private String getRank(double point,String department,String major,String grade)
    {
    	 List list=StudentPointDao.selectPointByDMG(department, major, Integer.parseInt(grade));
    	 if(list.size()>0)
    	{     	 
    	 int n=list.size();
    	 int x=(int) ((double)n/100.0*8.0);
    	 Double s1=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*32.0);
    	 Double s2=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*40.0);
    	 Double s3=Double.parseDouble(list.get(x).toString());
    	 x=(int) ((double)n/100.0*90.0);
    	 Double s4=Double.parseDouble(list.get(x).toString());
	     if(point>s1)
	    	 return "5星";
	     else if(point<=s1 &&point>s2)
	     {
	    	 return "4星";
	     }
	     else if(point<=s2 &&point>s3)
	     {
	    	 return "3星";
	     }
	     else if(point<=s3 &&point>s4)
	     {
	    	 return "2星";
	     }
	     else
	     {
	    	 return "1星";
	     }
    	}
    	 return null;
    }
    public static void main(String[] args)
    {
    	/*
    	ArrayList<String[]>r=getResult("计算机学院","2011");
    	Iterator it=r.iterator();
    	while(it.hasNext())
    	{
    		String[] s=(String[]) it.next();
    		System.out.println(s[0]);
    		System.out.println(s[1]);
    		System.out.println(s[2]);
    	}
    	*/
    	new GrantRecommend().getResult("计算机学院","01","2012");
    }
}
