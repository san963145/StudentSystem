package com.sys.network.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sys.network.proc.NtDataStatisticsPro;

/**
 * 按姓名、学院、年级查询 网络访问报告
 */
@WebServlet("/ReportByGroup")
public class ReportByGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportByGroup() {
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
		String sname=request.getParameter("sname");
		String department=request.getParameter("department");
		String grade=request.getParameter("grade");
		List list=new ArrayList<>();
		if(!sname.equals(""))
		{
			if(department.equals("选择学院"))
			   list=NtDataStatisticsPro.GetSInfoBySname(sname);
			else
				list=NtDataStatisticsPro.GetSInfoByCG(sname, department, grade);
		}
		else
		{
			
			list=NtDataStatisticsPro.GetSInfoByCG(department, grade);
		}
		request.setAttribute("list",list);
		request.setAttribute("department",department);
		request.setAttribute("grade",grade);
		request.getRequestDispatcher("pages/network/searchByGroup.jsp").forward(request, response);
		
	}

}
