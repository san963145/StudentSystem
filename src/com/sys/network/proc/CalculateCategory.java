package com.sys.network.proc;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;





import com.sys.network.dao.NtMainCategoryDao;
import com.sys.network.dao.NtSecondaryCategoryDao;
import com.sys.network.dao.NtSubCategoryDao;
import com.sys.network.dao.NtWebsitesDao;

public class CalculateCategory {
	
	/**
	 * 计算"分层浏览树形结构" 所有数据，存储至path目录下
	 */
      public static void calculate(String path)
      {
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
  		String st=new String(s);
  		File file=new File(path,"category.txt");
  	    BufferedWriter bufferedWriter=null;
		try {
				bufferedWriter=new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		try {
			bufferedWriter.write(st);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	   
      }
      
      public static void main(String[] args)
      {
    	 
    	  
      }
}
