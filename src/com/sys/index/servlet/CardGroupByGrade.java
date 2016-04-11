package com.sys.index.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys.card.dao.MajorNameDao;
import com.sys.index.dao.CardDao;

/**
 * Servlet implementation class CardGroupByGrade
 */
@WebServlet("/CardGroupByGrade")
public class CardGroupByGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardGroupByGrade() {
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
		String department=request.getParameter("department");    
		if(department!=null)
		   department=java.net.URLDecoder.decode(department, "UTF-8");
        String major=request.getParameter("major");
        String sex=request.getParameter("sex");
        String majorNum=null;
        if(major!=null)
		{
        	majorNum=major;
			major=MajorNameDao.getNameByNum(major);	
		}
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
		if(department!=null)
		{
			cur="department";
		}
		if(major!=null)
		{
			cur="major";
		}
		if(sex!=null)
		{
			cur="sex";
		}
		if(cookies!=null)
		for(Cookie c : cookies)
		{
			if(c.getName().equals("department"))
			{
				department=java.net.URLDecoder.decode(c.getValue(), "UTF-8");
			}
			if(c.getName().equals("major"))
			{
				major=java.net.URLDecoder.decode(c.getValue(), "UTF-8");
				majorNum=MajorNameDao.getNumByName(department,major);
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
		List cardList=CardDao.get(department, majorNum, null, sexNum, "grade");
		request.setAttribute("cardList",cardList);		
		request.setAttribute("department",department);			
		request.setAttribute("major",major);	
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
		request.getRequestDispatcher("pages/index/cardGroupByGrade.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
