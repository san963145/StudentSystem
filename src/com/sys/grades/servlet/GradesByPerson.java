package com.sys.grades.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sys.grades.dao.DeptLocatorDao;
import com.sys.grades.proc.CalculateHistoryGpa;
import com.sys.manager.bean.User;

/**
 * ∞¥—ß∫≈≤È—Ø
 */
@WebServlet("/GradesByPerson")
public class GradesByPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradesByPerson() {
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
		String sno=request.getParameter("sno");
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		String accessFlag="1";
		if(user.getAuthority().equals("Dean"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment());
		if(user.getAuthority().equals("Instructor"))
		   accessFlag=DeptLocatorDao.Check(sno, user.getDepartment(),user.getGrade());
		List hadRateList=CalculateHistoryGpa.GetSemHGGR(sno);
		List gpaList=CalculateHistoryGpa.GetSemHSGBySno(sno);
		List avgList=CalculateHistoryGpa.GetSemHAGBySno(sno);
		List retakeList=CalculateHistoryGpa.GetRestudyBySno(sno);
		List failedList=CalculateHistoryGpa.GetFailBySno(sno);
		List info=DeptLocatorDao.GetStuInfoBySno(sno);
		request.setAttribute("gpaList",gpaList);
		request.setAttribute("avgList",avgList);
		request.setAttribute("retakeList",retakeList);
		request.setAttribute("failedList",failedList);
		request.setAttribute("hadRateList",hadRateList);
		request.setAttribute("info",info);
		request.setAttribute("result","1");
		request.setAttribute("accessFlag", accessFlag);
		request.getRequestDispatcher("pages/gradesSystem/searchByPerson.jsp").forward(request, response);
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
		List hadRateList=CalculateHistoryGpa.GetSemHGGR(sno);
		List gpaList=CalculateHistoryGpa.GetSemHSGBySno(sno);
		List avgList=CalculateHistoryGpa.GetSemHAGBySno(sno);
		List retakeList=CalculateHistoryGpa.GetRestudyBySno(sno);
		List failedList=CalculateHistoryGpa.GetFailBySno(sno);
		List info=DeptLocatorDao.GetStuInfoBySno(sno);
		request.setAttribute("gpaList",gpaList);
		request.setAttribute("avgList",avgList);
		request.setAttribute("retakeList",retakeList);
		request.setAttribute("failedList",failedList);
		request.setAttribute("hadRateList",hadRateList);
		request.setAttribute("info",info);
		request.setAttribute("result","1");
		request.setAttribute("accessFlag", accessFlag);
		request.getRequestDispatcher("pages/gradesSystem/searchByPerson.jsp").forward(request, response);
	}

}
