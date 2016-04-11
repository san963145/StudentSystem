package com.sys.card.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.grades.dao.DeptLocatorDao;
import com.sys.manager.bean.User;

/**
 * Servlet implementation class SelectorGrade
 */
@WebServlet("/SelectorGrade")
public class SelectorGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectorGrade() {
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
		String department=URLDecoder.decode(request.getParameter("department"), "utf-8");
		List list=DeptLocatorDao.getGradesByDepartment(department);	
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user.getAuthority().equals("Instructor"))
		{
			list.clear();
			String s=user.getGrade();
			String[] gs=s.split("#");
			for(int i=0;i<gs.length;i++)
			{
				list.add(gs[i]);
			}
		}
		PrintWriter out=response.getWriter();
		String grades="";
		Iterator it=list.iterator();
		while(it.hasNext())
		{
			String s=it.next().toString();
			if(!s.equals("0"))
			{
			 grades+=s;
			 grades+="#";
			}
		}
		if(grades.length()>0)
			grades=grades.substring(0,grades.length()-1);		
		out.print(grades);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
