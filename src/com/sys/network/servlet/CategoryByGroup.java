package com.sys.network.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sys.network.dao.WebsitesStatisticsDao;

/**
 * 根据基本类-大类-子类 查询网址分类
 */
@WebServlet("/CategoryByGroup")
public class CategoryByGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryByGroup() {
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
		String type=department+"-"+major+"-"+grade;
		List list=WebsitesStatisticsDao.getByType(type);
		request.setAttribute("list", list);
		request.setAttribute("department",department);
	    request.setAttribute("major",major);
	    request.setAttribute("grade",grade);
		request.getRequestDispatcher("pages/network/queryCategory.jsp").forward(request, response);
	}

}
