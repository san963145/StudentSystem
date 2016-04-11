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
 * Servlet implementation class DrawComparison
 */
@WebServlet("/DrawComparison")
public class DrawComparison extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DrawComparison() {
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
			List list1=StudentPointDao.selectDrawByDepartmentComparison();
			List list2_1=StudentPointDao.selectDrawByDepartmentComparison("男");
			List list2_2=StudentPointDao.selectDrawByDepartmentComparison("女");
			request.setAttribute("flag","1");
			request.setAttribute("list1",list1);
			request.setAttribute("list2_1",list2_1);
			request.setAttribute("list2_2",list2_2);
			request.getRequestDispatcher("pages/cardSystem/resultsComparisonAdmin.jsp").forward(request, response);
		  }
		   else if(user.getAuthority().equals("Dean"))
		  {
			String department=user.getDepartment();
			List list1=StudentPointDao.selectDrawByGradeComparison(department);
			List list2_1=StudentPointDao.selectDrawByGradeComparison(department,"男");
			List list2_2=StudentPointDao.selectDrawByGradeComparison(department,"女");
			request.setAttribute("flag","1");
			request.setAttribute("list1",list1);
			request.setAttribute("list2_1",list2_1);
			request.setAttribute("list2_2",list2_2);
			request.getRequestDispatcher("pages/cardSystem/resultsComparisonDean.jsp").forward(request, response);
		  }
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
