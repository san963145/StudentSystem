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

import com.sys.network.dao.NtMainCategoryDao;
import com.sys.network.dao.NtSecondaryCategoryDao;
import com.sys.network.dao.NtSubCategoryDao;

/**
 * 基本类-大类-子类 下拉列表级联
 */
@WebServlet("/NetSelector")
public class NetSelector extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NetSelector() {
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
		String mainCategoryName=null;
		if(request.getParameter("department")!=null)
		{
			//mainCategoryName=new String(request.getParameter("department").getBytes("ISO-8859-1"),"utf-8");
			mainCategoryName=URLDecoder.decode(request.getParameter("department"), "utf-8");
		}
		if(mainCategoryName!=null)
	   {
		int mainCategoryNum=NtMainCategoryDao.getIdByName(mainCategoryName);
		List secondaryCategoryList=NtSecondaryCategoryDao.GetNtSecondaryCategoriesByMain(mainCategoryNum);
		PrintWriter out=response.getWriter();
		String majors="";
		Iterator it=secondaryCategoryList.iterator();
		while(it.hasNext())
		{
			Object[]obj=(Object[]) it.next();
			majors+=obj[0].toString();
			majors+="#";
		}
		if(majors.length()>0)
		   majors=majors.substring(0,majors.length()-1);
		out.print(majors);
		out.close();
	   }
		String secondaryCategoryName=null;
		if(request.getParameter("major")!=null)
		{
			//secondaryCategoryName=new String(request.getParameter("major").getBytes("ISO-8859-1"),"utf-8");
			secondaryCategoryName=URLDecoder.decode(request.getParameter("major"), "utf-8");
		}
		if(secondaryCategoryName!=null)
	   {
		int secondaryCategoryNum=NtSecondaryCategoryDao.getIdByName(secondaryCategoryName);
		List secondaryCategoryList=NtSubCategoryDao.GetNtSubCategoriesBySecond(secondaryCategoryNum);
		PrintWriter out=response.getWriter();
		String majors="";
		Iterator it=secondaryCategoryList.iterator();
		while(it.hasNext())
		{
			Object[]obj=(Object[]) it.next();
			majors+=obj[0].toString();
			majors+="#";
		}
		if(majors.length()>0)
		  majors=majors.substring(0,majors.length()-1);
		out.print(majors);
		out.close();
	   }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
