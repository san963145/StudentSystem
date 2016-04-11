package com.sys.card.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.card.dao.StudentPointDao;
import com.sys.manager.bean.User;

/**
 * Servlet implementation class StatisticsDraw
 */
@WebServlet("/DrawStatistics")
public class DrawStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrawStatistics() {
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
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user==null)   //直接地址栏方式访问
		{
			request.getRequestDispatcher("pages/cardSystem/cardSystem.jsp").forward(request, response);
		}
		else
		{
		   if(user.getAuthority().equals("Admin"))
		  {
			List list1=StudentPointDao.selectDrawDataBySchool();
			List list2_1=StudentPointDao.selectDrawDataBySchool("男");
			List list2_2=StudentPointDao.selectDrawDataBySchool("女");
			request.setAttribute("flag","1");
			request.setAttribute("list1",list1);
			request.setAttribute("list2_1",list2_1);
			request.setAttribute("list2_2",list2_2);
			request.getRequestDispatcher("pages/cardSystem/statisticalResultsAdmin.jsp").forward(request, response);
		  }
		   else if(user.getAuthority().equals("Dean"))
		  {
			String department=user.getDepartment();
			List list1=StudentPointDao.selectDrawDataByDepartment(department);
			List list2_1=StudentPointDao.selectDrawDataByDepartment(department,"男");
			List list2_2=StudentPointDao.selectDrawDataByDepartment(department,"女");
			request.setAttribute("flag","1");
			request.setAttribute("list1",list1);
			request.setAttribute("list2_1",list2_1);
			request.setAttribute("list2_2",list2_2);
			request.getRequestDispatcher("pages/cardSystem/statisticalResultsDean.jsp").forward(request, response);
		  }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user.getAuthority().equals("Instructor"))
		{
			String department=user.getDepartment();
			String grade=request.getParameter("grade");
			List list1=StudentPointDao.selectDrawDataByGrade(department,grade);
			List list2_1=StudentPointDao.selectDrawDataByGrade(department,grade,"男");
			List list2_2=StudentPointDao.selectDrawDataByGrade(department,grade,"女");
			request.setAttribute("list1",list1);
			request.setAttribute("list2_1",list2_1);
			request.setAttribute("list2_2",list2_2);
			request.setAttribute("grade",grade);
			request.getRequestDispatcher("pages/cardSystem/statisticalResultsInstructor.jsp").forward(request, response);
		}
		
	}

}
