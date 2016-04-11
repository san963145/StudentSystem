package com.sys.grades.proc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.sys.grades.bean.McNewestGpa;
import com.sys.grades.dao.McNewestGpaDao;
import com.sys.grades.dao.McStuGradeDao;
import com.sys.grades.servlet.CalculateGrades;

public class CalculateNewGpa {
	
	
	  //obj[0]学号 ，obj[1]课程号，obj[2]成绩，obj[3]学分,obj[4]重修，obj[5]教师，obj[6]补考
	/**
   	 * 计算最新GPA
   	 * 
   	 */
      public static void calculateNewGpa()
      {
    	  McNewestGpaDao.clear();
    	  List snoList=McStuGradeDao.selectAllSno();
    	  Iterator snoIt=snoList.iterator();
    	  List grades=null;
    	  HashSet<String>courseNoSet=new HashSet<String>();  //存储挂科课程
    	  while(snoIt.hasNext())
    	  {
    		  String sno=(String) snoIt.next();
    		  grades=McStuGradeDao.selectAllBySno(sno.trim());
    		  Iterator it=grades.iterator();
    		  double a=0;   //GPA分子
    		  double b=0;   //GPA分母
    		  int failedNum=0;         //挂科门数
    		  int restudyNum=0;         //重修门数
    		  double g;     //单科绩点
    		  courseNoSet.clear();
    		  while(it.hasNext())
    		  {
    			  Object[] obj=(Object[]) it.next();
    			  String courseNo=obj[1].toString(); 
    			  String grade=obj[2].toString();   			  
    			  double courseCredit=Double.parseDouble(obj[3].toString());
    			  String elective=obj[4].toString(); 
    			  String teacherName=obj[5].toString();
    			  String testType=obj[6].toString();
    			  
    			  if(teacherName.equals("国家四级")||teacherName.equals("国家六级")||elective.equals("二专"))
    				  continue;
    			  if(grade.matches("\\d+"))
    			  {
    				  if(Integer.parseInt(grade)<60)
    					  g=0;
    				  else
    				  {
    					  double _grade=Double.parseDouble(grade);
    					  if(_grade>90)
    						  _grade=90;
    				      g=(_grade-50.0)/10.0;
    				  }
    			  }
    			  else
    			  {
    				  
    				  if(grade.equals("优"))
    				  {
    					 g=4.0;
    				  }
    				  else if(grade.equals("良")||grade.equals("合格"))
    				  {
    					  g=3.5;
    				  }
    				  else if(grade.equals("中"))
    				  {
    					  g=2.5;
    				  }
    				  else if(grade.equals("及"))
    				  {
    					  g=1.5;
    				  }
    				  else
    				  {
    					  g=0;
    				  }
    			  }
    			  
    			  if(g==0&&elective.equals("正常")&&testType.equals("正考"))
    			  {
    				  failedNum++;
    			  }
    			  if(elective.equals("重修"))
    			  {
    				  restudyNum++;
    			  }
    			  
    			  if(elective.equals("正常")&&testType.equals("正考"))
    			  {
    				  if(g>0)
    				  {
    					  b+=courseCredit;
    	    			  a+=(g*courseCredit); 
    				  }
    				  else
    				  {
    					  courseNoSet.add(courseNo);
    					  b+=courseCredit;
    				  }
    			  }
    			  if(elective.equals("重修")||testType.equals("补考"))
    			  {
    				  if(g>0)
    				  {
    					  if(courseNoSet.contains(courseNo))
    					  {
    	    			   a+=(1.0*courseCredit); 
    	    			   courseNoSet.remove(courseNo);
    					  }
    				  }
    			  }    			  
    		  }
    		  double gpa=a/b;
    		  McNewestGpa mcNewestGpa=new McNewestGpa();    		  
    		  mcNewestGpa.setSno(sno);
    		  mcNewestGpa.setNewestgpa(gpa);
    		  mcNewestGpa.setFailednum(failedNum);
    		  mcNewestGpa.setRestudynum(restudyNum);
    		  //System.out.println(sno+" "+gpa+" "+failedNum+" "+restudyNum);
    		  McNewestGpaDao.add(mcNewestGpa);
    	  }
    	  McNewestGpaDao.formatDecimal();
    	  CalculateGrades.setFlag();
      }
      
      public static void main(String[]args)
      {
    	  calculateNewGpa();
      }
}
