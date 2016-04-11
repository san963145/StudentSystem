package com.sys.grades.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.grades.dao.DeptLocatorDao;
import com.sys.manager.bean.User;
import com.sys.network.bean.CategoryNameNum;
import com.sys.network.proc.NtDataStatisticsPro;

/**
 * Servlet implementation class NetByPerson
 */
@WebServlet("/NetByPerson")
public class NetByPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NetByPerson() {
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
		String sno=request.getParameter("sno");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String accessFlag="1";
		if(user.getAuthority().equals("Dean"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment());
		if(user.getAuthority().equals("Instructor"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment(),user.getGrade());
		request.setAttribute("sno",sno);
		List onlineList=NtDataStatisticsPro.GetNtOTBySno(sno);
		List gameList=NtDataStatisticsPro.GetNtGTBySno(sno);
		List videoList=NtDataStatisticsPro.GetNtVTBySno(sno);
		ArrayList<CategoryNameNum> categoryList=NtDataStatisticsPro.GetTop10BySno(sno);
		request.setAttribute("onlineList",onlineList);
		request.setAttribute("gameList",gameList);
		request.setAttribute("videoList",videoList);
		request.setAttribute("categoryList",categoryList);
		request.setAttribute("accessFlag", accessFlag);
		request.getRequestDispatcher("pages/gradesSystem/internetUse.jsp").forward(request, response);
	}

}
