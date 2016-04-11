package com.sys.grades.proc;

import java.util.List;

import com.sys.grades.bean.GradeSearchByGroup;
import com.sys.grades.dao.DeptLocatorDao;
import com.sys.grades.dao.GradeSearchByGroupDao;
import com.sys.grades.dao.McHistoryGpaDao;
import com.sys.grades.dao.StuGradeInfoDao;
import com.sys.grades.servlet.CalculateGrades;

public class CalculateByGroup {
	  
	/**
   	 * 按学院年级专业 查询的计算结果 存储至数据库
   	 * 
   	 */
  	  public static void calculate()
  	  {
  		    GradeSearchByGroupDao.clear();
  		    List departmentList=DeptLocatorDao.GetAllColleges();
  		    int i;
  		    List semesterList=StuGradeInfoDao.seletSemester_1();  //获取当前所有学期
  		    //List semesterList=StuGradeInfoDao.seletSemesters();
  		    for(i=0;i<departmentList.size();i++)
  		    {
  		    	String department=(String) departmentList.get(i);
  		    	//System.out.println(department);
  		    	List majorList=DeptLocatorDao.getMajorNumByDepartment(department);
  				List gradeList=DeptLocatorDao.getGradesByDepartment(department);
  				for(int j=0;j<majorList.size();j++)
  				{
  					String majorNum=majorList.get(j).toString();
  					for(int k=0;k<gradeList.size();k++)
  					{
  						//System.out.println((String)majorList.get(j));
  						int grade=Integer.parseInt(gradeList.get(k).toString());
  						if(grade>0)
  						{
  						  //System.out.println(grade);
  							//System.out.println(department+" "+majorNum+" "+grade);
  						  List snoList=DeptLocatorDao.GetSnoByCMG(department, majorNum,grade); 
  						  for(int s=0;s<semesterList.size();s++)
  						  {
  							  int semester=Integer.parseInt(semesterList.get(s).toString());
  							  //System.out.println(semester);
  							  double gpa=0;
  							  double avg=0;
  							  double selected=0;
  							  double had=0;
  							  double hadRate=0;
  							  boolean findFlag=true;
  							  int sum=0;
  							  for(int m=0;m<snoList.size();m++)
  							  {
  								  findFlag=true;
  								  String sno=snoList.get(m).toString().trim();
  								  //System.out.println(semester+" "+sno);
  								  Double _gpa=McHistoryGpaDao.GetHSGpaBySS(semester,sno);
  								  //System.out.println(department+" "+majorNum+" "+grade+" "+_gpa);
  								  if(_gpa!=null)
  								    gpa=gpa+_gpa;
  								  else
  								  {
  									  findFlag=false;
  									  //m=snoList.size()+1;
  								  }
  								  Double _avg=McHistoryGpaDao.GetHAGBySS(semester, sno);
  								  if(_avg!=null)
  								    avg=avg+_avg;
  								  else
  								  {
  									  findFlag=false;
  									  //m=snoList.size()+1;
  								  }
  								  Double _selected=McHistoryGpaDao.GetHSGBySS(semester, sno);
  								  if(_selected!=null)
  								     selected=selected+_selected;
  								  else
								  {
									  findFlag=false;
									  //m=snoList.size()+1;
								  }
  								  Double _had=McHistoryGpaDao.GetHHGBySS(semester, sno);
  								  if(_had!=null)
 								     had=had+_had;
 								  else
								  {
									  findFlag=false;
									  //m=snoList.size()+1;
								  }
  								  Double _hadRate=McHistoryGpaDao.GetHGGRBySS(semester, sno);
  								  if(_hadRate!=null)
  								    hadRate=hadRate+_hadRate;
  								  else
								  {
									  findFlag=false;
									 // m=snoList.size()+1;
								  }
  								  if(findFlag)
  									  sum++;
  							  }
  							  if(sum>0 && gpa>0 && selected>0)
  							  {
  							    gpa=gpa/(double)sum;
  							    avg=avg/(double)sum;
  							    selected=selected/(double)sum;
  							    had=had/(double)sum;
  							    hadRate=hadRate/(double)sum;
  							    GradeSearchByGroup g=new GradeSearchByGroup();
  							    g.setDepartment(department);
  							    g.setMajorNum(majorNum);
  							    g.setGrade(grade);
  							    g.setSemester(semester);
  							    g.setGpa(gpa);
  							    g.setAvg(avg);
  							    g.setSelected(selected);
  							    g.setHad(had);
  							    g.setHadRate(hadRate);
  							    GradeSearchByGroupDao.add(g);  	
  							  }
  						  } 
  						}
  					}				
  				}				
  		    }
  		    GradeSearchByGroupDao.formatDecimal();
  		    CalculateGrades.setFlag();
  	  }
  	  public static void main(String[]args)
  	  {
  		 // calculate();
  		GradeSearchByGroupDao.formatDecimal();
  	  }
}
