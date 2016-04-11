package com.sys.manager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.manager.bean.User;
import com.sys.manager.dao.UserDao;

/**
 * Servlet implementation class UserManager
 */
@WebServlet("/AddUser")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
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
		String userId=request.getParameter("userId");		
		String department=request.getParameter("department");
		String grade=request.getParameter("grade");
		String authority=request.getParameter("authority");
		String comments=request.getParameter("comments");
		User user=new User();
		user.setUserId(userId);
		user.setDepartment(department);
		if(authority.equals("Ôº³¤"))
		{
			authority="Dean";
		}
		else
		{
			authority="Instructor";
			user.setGrade(grade);
		}
		user.setAuthority(authority);
		user.setComments(comments);
		UserDao.addUser(user);
		request.setAttribute("result","1");
		request.getRequestDispatcher("pages/manager/addUser.jsp").forward(request, response);
	}

}
