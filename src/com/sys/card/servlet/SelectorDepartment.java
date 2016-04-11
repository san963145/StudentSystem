package com.sys.card.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.card.dao.DepartmentPointDao;
import com.sys.manager.bean.User;

/**
 * Servlet implementation class SelectorDepartment
 */
@WebServlet("/SelectorDepartment")
public class SelectorDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectorDepartment() {
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
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		List list=DepartmentPointDao.getDepartmentsByUser(user);
		PrintWriter out=response.getWriter();
		String departments="";
		Iterator it=list.iterator();
		while(it.hasNext())
		{
			String s=it.next().toString();
			departments+=s;
			departments+="#";
		}
		if(departments.length()>0)
			departments=departments.substring(0,departments.length()-1);		
		out.print(departments);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
