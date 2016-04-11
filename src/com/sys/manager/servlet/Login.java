package com.sys.manager.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.manager.bean.Admin;
import com.sys.manager.dao.AdminDao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		List list=AdminDao.select(userName, password);
		HttpSession session=request.getSession();
		if(list.size()>0)
		{
			Admin admin=new Admin();
			admin.setUserName(userName);
			admin.setPassword(password);
			session.setAttribute("admin", admin);
			request.setAttribute("result","1");
			request.getRequestDispatcher("pages/admin/calculate.jsp").forward(request, response);
		}
		else
		{
			request.setAttribute("result","2");
			request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
		}
	}

}
