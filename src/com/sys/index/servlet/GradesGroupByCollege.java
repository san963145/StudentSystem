package com.sys.index.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.index.dao.GradesDao;

/**
 * Servlet implementation class GradesGroupByCollege
 */
@WebServlet("/GradesGroupByCollege")
public class GradesGroupByCollege extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradesGroupByCollege() {
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
		String grade=request.getParameter("grade");
        String sex=request.getParameter("sex");
        String sexNum=sex;
        String str="";
        String cur=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null)
		for(Cookie c : cookies)
		{
			if(c.getName().equals("str"))
			{
				str=java.net.URLDecoder.decode(c.getValue(), "UTF-8");			
			}
		}
		if(grade!=null)
		{
			cur="grade";
		}
		if(sex!=null)
		{
			cur="sex";
		}
		if(cookies!=null)
		for(Cookie c : cookies)
		{
			if(c.getName().equals("grade"))
			{
				grade=java.net.URLDecoder.decode(c.getValue(), "UTF-8");
			}
			if(c.getName().equals("sex"))
			{
				sex=java.net.URLDecoder.decode(c.getValue(), "UTF-8");
				if(sex.equals("ÄÐ"))
				{
					sexNum="1";
				}
				if(sex.equals("Å®"))
				{
					sexNum="2";
				}
			}
		}
		if(cookies!=null)
		for(Cookie c : cookies)
		{
			c.setMaxAge(0);
			response.addCookie(c);
		}
		Map<String,List> gradesMap=GradesDao.get(null, null, grade, sexNum, "department");
		request.setAttribute("gradesMap",gradesMap);		
		request.setAttribute("grade",grade);	
		if(sex!=null)
		{
			if(sex.equals("1"))
			{
				sex="ÄÐ";
			}
			if(sex.equals("2"))
			{
				sex="Å®";
			}
		}
		request.setAttribute("sex",sex);	
		request.setAttribute("str",str);
		request.setAttribute("cur",cur);
		request.getRequestDispatcher("pages/index/gradesGroupByCollege.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
