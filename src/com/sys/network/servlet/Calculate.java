package com.sys.network.servlet;

import java.io.IOException;
import java.io.PrintWriter;





import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.network.dao.WebsitesStatisticsDao;
import com.sys.network.proc.CalculateCategory;
import com.sys.network.proc.WebsitesStatisticsPro;


/**
 * Servlet implementation class Calculate
 */
@WebServlet("/Calculate")
public class Calculate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static boolean flag=false;  
	private static boolean initflag=false;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calculate() {
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
		application.setAttribute("flagNet","1");
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
			  out.println("网站分类数据计算完成...");		
		      flag=false;
			}
			else
			{
				out.print("0");
			}
			out.close();
			if(!initflag)
			{
			 initflag=true;
			 final String path=this.getServletContext().getRealPath("/");
			 new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub					
					CalculateCategory.calculate(path);
			        WebsitesStatisticsPro.addWebsitesStatistics();
				}
				 
			 }).start();
			 
			}
		    
		}
		else if(step.equals("2"))
		{
	      out.println("计算过程结束!");
		  out.close();
		}
		application.removeAttribute("flagNet");
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
    	WebsitesStatisticsDao.clear();
    }
}
