package com.sys.grades.proc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.sys.grades.bean.McDecisionMatrix;
import com.sys.grades.bean.StuGradePrediction;
import com.sys.grades.dao.DeptLocatorDao;
import com.sys.grades.dao.McDecisionMatrixDao;
import com.sys.grades.dao.McHistoryGpaDao;
import com.sys.grades.dao.StuGradeInfoDao;
import com.sys.grades.dao.StuGradePredictionDao;
import com.sys.grades.servlet.CalculateGrades;

public class CalculatePrediction {
	/**
	 * 计算预测矩阵
	 * @param args
	 */
	  public static void calculateMatrix()
	  {
		  McDecisionMatrixDao.clear();
		  List departmentList=DeptLocatorDao.GetAllColleges();
		  Iterator departmentIt=departmentList.iterator();
		  while(departmentIt.hasNext())
		  {
			  String department=departmentIt.next().toString();
			  List majorList=DeptLocatorDao.getMajorNumByDepartment(department);
			  Iterator majorIt=majorList.iterator();
			  while(majorIt.hasNext())
			  {
				  String major=majorIt.next().toString();
				  List gradeList=DeptLocatorDao.GetGradesByMajor(department,major);
				  Iterator gradeIt=gradeList.iterator();
				  while(gradeIt.hasNext())
				  {
					  Integer grade=Integer.parseInt(gradeIt.next().toString());
					  //System.out.println(department+" "+major+"　"+grade);
					  if(grade!=0)
					  calculateMatrixByDMG(department,major,grade);
				  }
			  }
		  }
		  McDecisionMatrixDao.formatDecimal();
		  CalculateGrades.setFlag();
	  }
	  /**
		 * 计算所有预测数据
		 * @param args
		 */
	  public static void calculate()
	  {
		    StuGradePredictionDao.clear();
		    List departmentList=DeptLocatorDao.GetAllColleges();
		    int i;
		    for(i=0;i<departmentList.size();i++)
		    {
		    	String department=(String) departmentList.get(i);
		    	//System.out.println(department);
		    	List majorList=DeptLocatorDao.getMajorNumByDepartment(department);
				List gradeList=DeptLocatorDao.getGradesByDepartment(department);
				for(int j=0;j<majorList.size();j++)
				{
					for(int k=0;k<gradeList.size();k++)
					{
						//System.out.println((String)majorList.get(j));
						int grade=Integer.parseInt(gradeList.get(k).toString());
						if(grade>0)
						{
						  //System.out.println(grade);
						  PreByCMG(department,(String)majorList.get(j),grade);
						}
					}				
				}				
		    }
		    CalculateGrades.setFlag();
	  }
	  /**
		 * 依据学院、专业、年级来预测学生的下学期Gpa以及是否存在挂科高风险
		 * @param args
		 */
		public static List<StuGradePrediction> PreByCMG(String collegename,String major,int grade){
			List<StuGradePrediction> preStuList = new ArrayList<StuGradePrediction>(); 
			List snoList = DeptLocatorDao.GetSnosByCMG(collegename, major, grade);
			Iterator it = snoList.iterator();
			while(it.hasNext()){
		     Object[] obj =	(Object[])it.next();
		     StuGradePrediction sgp = new StuGradePrediction();
		     String sno = obj[0].toString();
		     String sname = obj[1].toString();
		     List semestersList = StuGradeInfoDao.seletSemestersBySno(sno.trim());
		     if(semestersList.size()>0){
		    	 //System.out.println(12132);
		    	 Object[] obj1 = semestersList.toArray();
					Arrays.sort(obj1);
					semestersList=Arrays.asList(obj1);
					int semester = (int) semestersList.get(semestersList.size()-1);
					double smesterGpa = McHistoryGpaDao.GetHSGpaBySS(semester,sno);
					sgp.setSno(sno);
					sgp.setSname(sname);
					List list = CalculateHistoryGpa.GetFailBySno(sno.trim());
					
					if(list!=null)
						if(list.size()>0)
						{
					     int newestfailnum = (int) list.get(list.size() - 1);
					     //System.out.println(newestfailnum);
					String risk = GetRiskBySnoFail(newestfailnum,collegename,major,grade);
					sgp.setGpa(smesterGpa);
					sgp.setRisk(risk);
					StuGradePredictionDao.add(sgp);
					//preStuList.add(sgp); 
						}
		     }
			}
			return preStuList;
		}
		/**
	   	 * 根据最新学期的挂科门数  预测是否高风险
	   	 * 
	   	 */
	  public static String GetRiskBySnoFail(int newestfailnum,String department,String major,int grade)
      {
   	      int failnum;
   	      String result=null;
   	      if(newestfailnum<2)
   		     failnum=0;
   	      else
   		     failnum=1;
   	      List list=McDecisionMatrixDao.getT(department, major, grade);
   	      if(list!=null)
   	    	  if(list.size()>0)
   	    	  {
   	    		  Iterator it=list.iterator();
   	    		  if(it.hasNext())
   	    		  {
   	    			  Object[] obj=(Object[]) it.next();
   	    			  if(failnum==0)
   	    			  {
   	    				  double t1=Double.parseDouble(obj[0].toString());
   	    				  double t2=Double.parseDouble(obj[1].toString());
   	    				  if(t1<t2)
   	    					  result="是";
   	    				  else
   	    					  result="否";
   	    			  }
   	    			  else
   	    			  {
   	    				  double t3=Double.parseDouble(obj[2].toString());
 	    				  double t4=Double.parseDouble(obj[3].toString());
 	    				  if(t3<t4)
 	    					  result="是";
 	    				  else
 	    					  result="否";
   	    			  }
   	    		  }
   	    	  }
          return result;
      }
	  
	  /**
	   	 * 根据学院专业年级  计算预测矩阵
	   	 * @param args
	   	 */
      public static void calculateMatrixByDMG(String department,String majorNum,int grade)
      {
    	  List snoList=DeptLocatorDao.GetSnoByCMG(department, majorNum, grade);
    	  int s1=0,s2=0,s3=0,s4=0;
    	  Iterator snoIt=snoList.iterator();
    	  /*
    	  File file=new File("D:\\","1.csv");
		  BufferedWriter bufferedWriter=null;
    	  try {
			bufferedWriter=new BufferedWriter(new OutputStreamWriter( new FileOutputStream(file),"GBK"));
			try {
				bufferedWriter.write(new String(("学号,").getBytes("GBK")));
				bufferedWriter.write(new String(("学期1,").getBytes("GBK")));
				bufferedWriter.write(new String(("学期2,").getBytes("GBK")));
				bufferedWriter.write(new String(("学期3,").getBytes("GBK")));
				bufferedWriter.write(new String(("学期4,").getBytes("GBK")));
				bufferedWriter.write(new String(("学期5\r\n").getBytes("GBK")));
				while(snoIt.hasNext())
		    	  {
		    		  String sno=snoIt.next().toString();
		    		  System.out.println(sno);
		    		  List failedList=StuGradeInfoDao.GetFailBySno(sno.trim());  
		    		 if(failedList.size()>0)
		    		 {
						bufferedWriter.write(new String((sno.trim()+",").getBytes("GBK")));
						Iterator rt=failedList.iterator();
						while(rt.hasNext())
						{
							int re=(int) rt.next();
							bufferedWriter.write(new String((Integer.toString(re)+",").getBytes("GBK")));
						}
						bufferedWriter.write(new String("\r\n".getBytes("GBK")));	
		    		 }
		    	  }
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		  } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } catch (FileNotFoundException e) {
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
    	  */
    	  while(snoIt.hasNext())
    	  {
    		  String sno=snoIt.next().toString();
    		  //System.out.println(sno);
    		  List failedList=StuGradeInfoDao.GetFailBySno(sno.trim());  
    		  Iterator it=failedList.iterator();
    		  int temp=0;
    		  if(it.hasNext())
    		  {
    			  temp=(int) it.next();
    			  if(temp<1)
    				  temp=0;
    			  else 
    				  temp=1;  
    			 
    		  }
    		  while(it.hasNext())
    		  {
    			  int t=(int) it.next();
    			  if(t<1)
    				  t=0;
    			  else 
    				  t=1;
    			  if(temp==0&&t==0)
    				  s1++;
    			  else if(temp==0&&t==1)
    				  s2++;
    			  else if(temp==1&&t==0)
    				  s3++;
    			  else 
    				  s4++;
    			  temp=t;
    		  }    	
    	  }
    	  //System.out.println(s1+" "+s2+" "+s3+" "+s4);
    	  double t1;
    	  if(s1+s2!=0)
    	      t1=(double)s1/((double)s1+(double)s2);
    	  else
    		  t1=0;
    	  double t2;
    	  if(s1+s2!=0)
    	      t2=(double)s2/((double)s1+(double)s2);
    	  else
    		  t2=0;
    	  double t3;
    	  if(s3+s4!=0)
    	      t3=(double)s3/((double)s3+(double)s4);
    	  else
    		  t3=0;
    	  double t4;
    	  if(s3+s4!=0)
    	      t4=(double)s4/((double)s3+(double)s4);
    	  else
    		  t4=0;
    	  McDecisionMatrix m=new McDecisionMatrix();
    	  m.setCollegename(department);
    	  m.setMajoynum(majorNum);
    	  m.setGrade(grade);
    	  m.setT1(t1);
    	  m.setT2(t2);
    	  m.setT3(t3);
    	  m.setT4(t4);
    	  //System.out.println(t1+" "+t2+" "+t3+" "+t4);
    	  //System.out.println(mx);
    	  if(s1!=0||s2!=0||s3!=0||s4!=0)
    	  McDecisionMatrixDao.add(m);    
    	  
      }
      public static void main(String[]args)
      {
    	  calculateMatrix();
      }
}
