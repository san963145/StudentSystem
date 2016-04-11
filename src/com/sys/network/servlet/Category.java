package com.sys.network.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 树形结构分层浏览
 */
@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
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
		/*
		StringBuilder s=new StringBuilder();
		List mainCategoryList=NtMainCategoryDao.GetNtMainCategories();
		s.append("<ul id=\"parent\" class=\"list-group\">");
		for(int i=0;i<mainCategoryList.size();i++)
		{
	    	Object[]obj=(Object[])mainCategoryList.get(i);
	    	s.append("<li class=\"list-group-item bg-red\" id=\"group"+i+"\" data-toggle=\"collapse\" data-target=\"#list"+obj[1].toString()+"group\" data-parent=\"#\">");
	    	s.append("<i class=\"fa fa-plus-square\"></i>");
	    	s.append(obj[0].toString());
	    	s.append("</li>");
	    	s.append("<ul class=\"collapse\" id=\"list"+obj[1].toString()+"group\">");
	    	int superiodId=Integer.parseInt(obj[1].toString());
		    List secondaryCategoryList=NtSecondaryCategoryDao.GetNtSecondaryCategoriesByMain(superiodId);
		    for(int j=0;j<secondaryCategoryList.size();j++)
		    {
		      Object[]obj_1=(Object[])secondaryCategoryList.get(j);
		      s.append("<li class=\"list-group-item bg-gray\" data-toggle=\"collapse\" data-target=\"#list"+obj_1[1].toString()+"Group010100\" data-parent=\"#\">");
		      s.append("<i class=\"fa fa-plus-square\"></i>");
		      s.append(obj_1[0].toString());
		      s.append("</li>");
		      s.append("<ul class=\"collapse\" id=\"list"+obj_1[1].toString()+"Group010100\">");
		      int secondId=Integer.parseInt(obj_1[1].toString()); 
		      List subCategoryList=NtSubCategoryDao.GetNtSubCategoriesBySecond(secondId);
		      for(int k=0;k<subCategoryList.size();k++)
		      {
		         Object[] obj_2=(Object[])subCategoryList.get(k);
		         s.append("<li class=\"list-group-item\" data-toggle=\"collapse\" data-target=\"#list"+obj_2[1].toString()+"Group010101\" data-parent=\"#\">");
		         s.append("<i class=\"fa fa-plus-square\"></i>");
		         s.append(obj_2[0].toString());
		         s.append("</li>");
		         s.append("<ul class=\"collapse\" id=\"list"+obj_2[1].toString()+"Group010101\">");
		         int subId=Integer.parseInt(obj_2[1].toString()); 
			     List urlList=NtWebsitesDao.GetNtWebsitesSub(subId);
			     for(int l=0;l<urlList.size();l++)
			     {
			        String url=urlList.get(l).toString();
			        s.append("<li class=\"list-group-item\">"+url+"</li>");			        
			     }
			     s.append("</ul>");
		      }
		      s.append("</ul>");
		    }
		    s.append("</ul>");
		}
		s.append("</ul>");
		*/
		//System.out.println(s);
		String path=this.getServletContext().getRealPath("/");
		File file=new File(path,"category.txt");
		@SuppressWarnings("resource")
		BufferedReader f=new BufferedReader(new FileReader(file));
		String t=f.readLine();
		String s="";
		while(t!=null)
		{
			s+=t;
			t=f.readLine();
		}
		request.setAttribute("s", s);
		request.getRequestDispatcher("pages/network/category.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
