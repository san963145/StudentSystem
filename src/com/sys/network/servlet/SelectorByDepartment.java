package com.sys.network.servlet;

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
 * 学院、年级 下拉列表级联
 */
@WebServlet("/SelectorByDepartment")
public class SelectorByDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectorByDepartment() {
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
		//String department=new String(request.getParameter("department").getBytes("ISO-8859-1"),"utf-8");
		String department=URLDecoder.decode(request.getParameter("department"), "utf-8");
		List gradeList=DeptLocatorDao.getGradesByDepartment(department);
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		if(user.getAuthority().equals("Instructor"))
		{
			gradeList.clear();
			String s=user.getGrade();
			String[] gs=s.split("#");
			for(int i=0;i<gs.length;i++)
			{
				gradeList.add(gs[i]);
			}
		}
		PrintWriter out=response.getWriter();
		String grades="";
		Iterator it=gradeList.iterator();
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
