package com.sys.card.servlet;

import java.io.IOException;




import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.card.bean.StudentPoint;
import com.sys.card.dao.StudentPointDao;
import com.sys.grades.dao.DeptLocatorDao;
import com.sys.manager.bean.User;

/**
 * 处理按学号查询请求
 */
@WebServlet("/SearchByPerson")
public class SearchByPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchByPerson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 点击消费记录中"详细"链接
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sno=request.getParameter("sno");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String accessFlag="1";
		if(user.getAuthority().equals("Dean"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment());
		if(user.getAuthority().equals("Instructor"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment(),user.getGrade());
		StudentPoint studentPoint=null;
	    studentPoint=StudentPointDao.selectByPerson(sno);
	    String sname=StudentPointDao.selectNameBySno(sno);
	    List list=StudentPointDao.selectSchoolAvg();
	    request.setAttribute("studentPoint",studentPoint);
	    request.setAttribute("list",list);
	    request.setAttribute("sname",sname);
	    request.setAttribute("result", "1");
	    request.setAttribute("accessFlag", accessFlag);
    	request.getRequestDispatcher("pages/cardSystem/searchByPerson.jsp").forward(request, response);
	}

	/**
	 * 按学号查询
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sno=request.getParameter("sno");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String accessFlag="1";
		if(user.getAuthority().equals("Dean"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment());
		if(user.getAuthority().equals("Instructor"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment(),user.getGrade());
		StudentPoint studentPoint=null;
	    studentPoint=StudentPointDao.selectByPerson(sno);
	    String sname=StudentPointDao.selectNameBySno(sno);
	    List list=StudentPointDao.selectSchoolAvg();
	    request.setAttribute("studentPoint",studentPoint);
	    request.setAttribute("list",list);
	    request.setAttribute("sname",sname);
	    request.setAttribute("result", "1");
	    request.setAttribute("accessFlag", accessFlag);
    	request.getRequestDispatcher("pages/cardSystem/searchByPerson.jsp").forward(request, response);
	}

}
