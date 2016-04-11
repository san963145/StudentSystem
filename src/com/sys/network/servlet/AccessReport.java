package com.sys.network.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.sys.card.dao.MajorNameDao;
import com.sys.network.bean.CategoryNameNum;
import com.sys.network.proc.CalculateAverageTime;

/**
 * 按学院、专业、年级、性别查询网络访问报告
 */
@WebServlet("/AccessReport")
public class AccessReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public  static String indexUrlPath=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccessReport() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String department=request.getParameter("department");
		String major=request.getParameter("major");
		String grade=request.getParameter("grade");
		String sex=request.getParameter("sex");
		String majorName=major;
		if(!"全部".equals(major))
		  major=MajorNameDao.getNumByName(department,major);
		request.setAttribute("department",department);		
		request.setAttribute("major",majorName);
		request.setAttribute("grade",grade);
		request.setAttribute("sex",sex);
		String majorNum=major;
		/*
		if(!major.equals("全部"))
		{
		   majorNum=McDeptDao.getNumByMajor(major);
		}
		else
		{
			majorNum=major;
		}
		*/
		if(sex.equals("男"))
		{
			sex="1";
		}
		else if(sex.equals("女"))
		{
			sex="2";
		}
		else
		{
			sex="全部";
		}
		if(!grade.equals("全部"))
		{
			grade=grade.substring(2,4);
		}
		Double onlineTime_School=CalculateAverageTime.CalculateAOT();
		Double gameTime_School=CalculateAverageTime.CalculateAGT();
		Double videoTime_School=CalculateAverageTime.CalculateAVT();
		Double onlineTime_grade=CalculateAverageTime.CalculateAverageOnlineTime(department, majorNum, grade, sex);
		Double gameTime_grade=CalculateAverageTime.CalculateAverageGameTime(department, majorNum, grade, sex);
		Double videoTime_grade=CalculateAverageTime.CalculateAverageVideoTime(department, majorNum, grade, sex);
		ArrayList<CategoryNameNum> categoryList=CalculateAverageTime.CalculateTop10sub(department, majorNum, grade, sex);
		request.setAttribute("onlineTime_School",onlineTime_School);
		request.setAttribute("gameTime_School",gameTime_School);
		request.setAttribute("videoTime_School",videoTime_School);
		request.setAttribute("onlineTime_grade",onlineTime_grade);
		request.setAttribute("gameTime_grade",gameTime_grade);
		request.setAttribute("videoTime_grade",videoTime_grade);
		request.setAttribute("categoryList",categoryList);		
		//System.out.println(categoryList.get(0).getName()+" "+categoryList.get(0).getNum());
		request.getRequestDispatcher("pages/network/accessReport.jsp").forward(request, response);
	}

}
