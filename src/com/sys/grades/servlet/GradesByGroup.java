package com.sys.grades.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.card.dao.MajorNameDao;
import com.sys.grades.dao.GradeSearchByGroupDao;
import com.sys.grades.dao.McNewestGpaDao;






/**
 * 按学院查询
 */
@WebServlet("/GradesByGroup")
public class GradesByGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradesByGroup() {
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
		String department=request.getParameter("department");
		String major=request.getParameter("major");
		String grade=request.getParameter("grade");
		major=MajorNameDao.getNumByName(department,major);
		//ProcCsv.getDataForCsv(department, major, grade);
        //Draw.draw();
		//String majorNum=McDeptDao.getNumByMajor(major);
		List resultList=GradeSearchByGroupDao.selectByGroup(department, major, Integer.parseInt(grade));
		List failedList=McNewestGpaDao.SelectByCMG(department, major, Integer.parseInt(grade));

		request.setAttribute("resultList",resultList);
		request.setAttribute("failedList",failedList);
		request.setAttribute("result","1");
		request.setAttribute("department",department);
	    major=MajorNameDao.getNameByNum(major);
	    request.setAttribute("major",major);
	    request.setAttribute("grade",grade);
		request.getRequestDispatcher("pages/gradesSystem/searchByGroup.jsp").forward(request, response);
	}

}
