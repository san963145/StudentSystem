package com.sys.card.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.card.dao.DepartmentPointDao;
import com.sys.card.dao.PointExceptionDao;
import com.sys.card.dao.StudentPointDao;
import com.sys.card.proc.Calculate;

/**
 * 处理评级计算请求
 */
@WebServlet("/CalculateStatistics")
public class CalculateStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static boolean flag=false;  
	private static boolean initflag1=false;
	private static boolean initflag2=false;
	private static boolean initflag3=false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculateStatistics() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		ServletContext application=(ServletContext) request.getServletContext();
		application.setAttribute("flagCard","1");
		String step=request.getParameter("step");
		PrintWriter out=response.getWriter();
		if(step.equals("0"))
		{
		  out.println("开始计算...");
		  out.close();
		}
		else if(step.equals("1"))
		{			
			if(flag)
			{
			  flag=false;
			  out.println("学校平均消费数据计算完成...");				      
			}
			else
			{
				out.print("0");
			}
		    out.close();
		    if(!initflag1)
			{
			 initflag1=true;
			 new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Calculate.initCalculate();
						 Calculate.calculateDepartmentPoint();
					}					 
			 }).start();			 
			}
		}
		else if(step.equals("2"))
		{			
			if(flag)
			{
			  flag=false;
			  out.println("学生评分计算完成...");				      
			}
			else
			{
				out.print("0");
			}
		    out.close();
		    if(!initflag2)
			{
			 initflag2=true;
			 new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Calculate.calculateStudentPoint();
						DepartmentPointDao.formatDecimal();	
						StudentPointDao.formatDecimal();
					}
					 
			 }).start();
			 
			}
		}
		else if(step.equals("3"))
		{			
			if(flag)
			{	
			  flag=false;
			  out.println("异常数据统计完成...");			      
			}
			else
			{
				out.print("0");
			}
		    out.close();
		    if(!initflag3)
			{
			 initflag3=true;
			 new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Calculate.calculateException();
					}
					 
			 }).start();
			 
			}
		}
		else if(step.equals("4"))
		{
	      out.println("计算过程结束!");	      
		  out.close();
		  initflag1=initflag2=initflag3=false;
		}
		application.removeAttribute("flagCard");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	public static void setFlag()
	{
		flag=true;
	}
    public static void main(String[] args)
    {
       PointExceptionDao.clear();
 	   DepartmentPointDao.clear();
    }
}
