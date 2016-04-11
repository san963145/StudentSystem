package com.sys.card.proc;


import java.util.Iterator;
import java.util.List;








import com.sys.card.bean.DepartmentPoint;
import com.sys.card.dao.DepartmentPointDao;
import com.sys.card.dao.PointExceptionDao;
import com.sys.card.dao.StudentInfoDao;
import com.sys.card.dao.StudentPointDao;
import com.sys.card.servlet.CalculateStatistics;

/**
 *  各类计算
 */
public class Calculate {
    /**
     *  处理原始数据
     */	
	  public static synchronized void initCalculate()
	  {
		  
	  }
      /**
       *  计算学院平均消费水平的各项数据，可外部调用
       */
       public static synchronized void calculateDepartmentPoint()
       {
    	   DepartmentPointDao.clear();    	   
    	   List<String>departments=DepartmentPointDao.getDepartments();
    	   Iterator iteratorDepartments=departments.iterator();
    	   while(iteratorDepartments.hasNext())
    	   {
    		   String department=(String) iteratorDepartments.next();
    		   List<Integer>grades=DepartmentPointDao.getGrades(department);
    		   Iterator iteratorGrades=grades.iterator();
    		   while(iteratorGrades.hasNext())
    		   {
    			   Integer grade=(Integer)iteratorGrades.next();
    			   calculateDepartmentPointByGrades(department,grade);
    		   }
    		   
    	   }
    	   CalculateStatistics.setFlag();
       }
       
       /**
        *  分年级计算各学院的平均消费水平的各项数据,私有方法,被calculateDepartmentPoint()调用
        */
       private static void calculateDepartmentPointByGrades(String department,Integer grade)
       {
    	   DepartmentPoint departmentPoint=new DepartmentPoint();
    	   departmentPoint.setDepartment(department);
    	   departmentPoint.setGrade(grade);
    	   double []q=new double[3];
    	   q=DepartmentPointDao.getQ1Q2Q3("total", department, grade);
    	   departmentPoint.setTotal_Q1(q[0]);
    	   departmentPoint.setTotal_Q2(q[1]);
    	   departmentPoint.setTotal_Q3(q[2]);
    	   departmentPoint.setTotal_MAX(DepartmentPointDao.getMax("total", department, grade));
    	   departmentPoint.setTotal_MEAN(DepartmentPointDao.getMean("total", department, grade));
    	   departmentPoint.setTotal_SD(DepartmentPointDao.getSD("total", department, grade,departmentPoint.getTotal_MEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("count", department, grade);
    	   departmentPoint.setCount_Q1(q[0]);
    	   departmentPoint.setCount_Q2(q[1]);
    	   departmentPoint.setCount_Q3(q[2]);
    	   departmentPoint.setCount_MAX(DepartmentPointDao.getMax("count", department, grade));
    	   departmentPoint.setCount_MEAN(DepartmentPointDao.getMean("count", department, grade));
    	   departmentPoint.setCount_SD(DepartmentPointDao.getSD("count", department, grade,departmentPoint.getCount_MEAN()));
    	   q=DepartmentPointDao.getQ1Q2Q3("average", department, grade);
    	   departmentPoint.setAve_Q1(q[0]);
    	   departmentPoint.setAve_Q2(q[1]);
    	   departmentPoint.setAve_Q3(q[2]);
    	   departmentPoint.setAve_MAX(DepartmentPointDao.getMax("average", department, grade));
    	   departmentPoint.setAve_MEAN(DepartmentPointDao.getMean("average", department, grade));
    	   departmentPoint.setAve_SD(DepartmentPointDao.getSD("average", department, grade,departmentPoint.getAve_MEAN()));
    	   q=DepartmentPointDao.getQ1Q2Q3("lunchTTL", department, grade);
           departmentPoint.setLunch_Q1(q[0]);
           departmentPoint.setLunch_Q2(q[1]);
           departmentPoint.setLunch_Q3(q[2]);
           departmentPoint.setLunch_MAX(DepartmentPointDao.getMax("lunchTTL", department, grade));
           departmentPoint.setLunch_MEAN(DepartmentPointDao.getMean("lunchTTL", department, grade));
           departmentPoint.setLunch_SD(DepartmentPointDao.getSD("lunchTTL", department, grade,departmentPoint.getLunch_MEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("lunchCNT", department, grade);
           departmentPoint.setLunch_CNTQ1(q[0]);
           departmentPoint.setLunch_CNTQ2(q[1]);
           departmentPoint.setLunch_CNTQ3(q[2]);
           departmentPoint.setLunch_CNTMAX(DepartmentPointDao.getMax("lunchCNT", department, grade));
           departmentPoint.setLunch_CNTMEAN(DepartmentPointDao.getMean("lunchCNT", department, grade));
           departmentPoint.setLunch_CNTSD(DepartmentPointDao.getSD("lunchCNT", department, grade,departmentPoint.getLunch_CNTMEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("lunchAVG", department, grade);
           departmentPoint.setLunch_AVGQ1(q[0]);
           departmentPoint.setLunch_AVGQ2(q[1]);
           departmentPoint.setLunch_AVGQ3(q[2]);
           departmentPoint.setLunch_AVGMAX(DepartmentPointDao.getMax("lunchAVG", department, grade));
           departmentPoint.setLunch_AVGMEAN(DepartmentPointDao.getMean("lunchAVG", department, grade));
           departmentPoint.setLunch_AVGSD(DepartmentPointDao.getSD("lunchAVG", department, grade,departmentPoint.getLunch_AVGMEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("supperTTL", department, grade);
           departmentPoint.setSupper_Q1(q[0]);
           departmentPoint.setSupper_Q2(q[1]);
           departmentPoint.setSupper_Q3(q[2]);
           departmentPoint.setSupper_MAX(DepartmentPointDao.getMax("supperTTL", department, grade));
           departmentPoint.setSupper_MEAN(DepartmentPointDao.getMean("supperTTL", department, grade));
           departmentPoint.setSupper_SD(DepartmentPointDao.getSD("supperTTL", department, grade,departmentPoint.getSupper_MEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("supperCNT", department, grade);
           departmentPoint.setSupper_CNTQ1(q[0]);
           departmentPoint.setSupper_CNTQ2(q[1]);
           departmentPoint.setSupper_CNTQ3(q[2]);
           departmentPoint.setSupper_CNTMAX(DepartmentPointDao.getMax("supperCNT", department, grade));
           departmentPoint.setSupper_CNTMEAN(DepartmentPointDao.getMean("supperCNT", department, grade));
           departmentPoint.setSupper_CNTSD(DepartmentPointDao.getSD("supperCNT", department, grade,departmentPoint.getSupper_CNTMEAN()));
           q=DepartmentPointDao.getQ1Q2Q3("supperAVG", department, grade);
           departmentPoint.setSupper_AVGQ1(q[0]);
           departmentPoint.setSupper_AVGQ2(q[1]);
           departmentPoint.setSupper_AVGQ3(q[2]);
           departmentPoint.setSupper_AVGMAX(DepartmentPointDao.getMax("supperAVG", department, grade));
           departmentPoint.setSupper_AVGMEAN(DepartmentPointDao.getMean("supperAVG", department, grade));
           departmentPoint.setSupper_AVGSD(DepartmentPointDao.getSD("supperAVG", department, grade,departmentPoint.getSupper_AVGMEAN()));           
           DepartmentPointDao.add(departmentPoint);
       }
       
       /**
        *  计算所有学生的评分,可外部调用
        */
       public static synchronized void calculateStudentPoint()
       {
		   PointExceptionDao.clear();
    	   List list=DepartmentPointDao.getAllData();
    	   if(list!=null)
    	   {
    		   Iterator it=list.iterator();
    		   while(it.hasNext())
    		   {
    			   DepartmentPoint departmentPoint=(DepartmentPoint)it.next();
    			   calculateStudentPointByGrades(departmentPoint);
    		   }
    	   }
       }
       
       /**
        *  分学院、年级计算学生评分,departmentPoint对应一条平均水平数据,私有方法,被calculateStudentPoint()调用
        */
       private static void calculateStudentPointByGrades(DepartmentPoint departmentPoint)  
       {
    	  // String[] labels={"Total","Count","Average","LunchTTL","LunchCNT","LunchAVG","SupperTTL","SupperCNT","SupperAVG"};
    	   double[] data=new double[9];
    	   double[] aveData=new double[54];
    	   double[] weight={0.2,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1};
    	   Object[] obj=new Object[56];
    	   obj=toArray(departmentPoint);
    	   List list=StudentPointDao.select((String)obj[0],(Integer)obj[1]); //obj[0]学院 ,obj[1]年级
    	   if(list!=null)
    	   {
    		   Iterator it=list.iterator();
    		   while(it.hasNext())                            //对选出的所有学生进行迭代
    		   {
    			   int i;
    			   Object[] studentData=(Object[])it.next();
    			   String sno=(String)studentData[0];
    			   double finalPoint=0;
    			   for(i=0;i<9;i++)
    			   {
    				   data[i]=Double.parseDouble(studentData[i+4].toString());      //学生的9项数据
    			   }
    			   for(i=0;i<54;i++)
    			   {
    				   aveData[i]=Double.parseDouble(obj[i+2].toString());           //学院的54项数据
    			   }
    			   for(i=0;i<9;i++)                                                  //计算该学生评分
    			   {
    				   
    				   double tempPoint=(data[i]-aveData[i*6+4])/aveData[i*6+5];		
   					   double realPoint=cal(tempPoint);
   					   finalPoint+=((1.0-realPoint)*weight[i]);
    			   }
				   finalPoint=60.0*finalPoint+40.0;
    			   StudentPointDao.updatePoint(sno,finalPoint);
    		   }
    	   }
       }
       /**
        *  计算生成学生的消费异常信息
        */
       public static void calculateException()
       {
    	   PointExceptionDao.clear();
    	   List studentList=PointExceptionDao.selectAllStudents();
    	   Iterator sIt=studentList.iterator();
    	   while(sIt.hasNext())
    	   {
    		   Object[]obj=(Object[]) sIt.next();
    		   String sno=obj[0].toString();
    		   int total=(int) Double.parseDouble(obj[1].toString());
    		   int count=Integer.parseInt(obj[2].toString());
    		   double avg=Double.parseDouble(obj[3].toString());
    		   List list=StudentInfoDao.selectGMBySno(sno.trim());    		   
    		   String grade="";
    		   String classNo="";
    		   if(list.size()>0)
    		   {
    			   Object[] o=(Object[]) list.get(0);
    			   grade=o[0].toString();
    			   classNo=o[1].toString();
    		   }
    		   //System.out.println(sno+" "+total+" "+count+" "+" "+avg+" "+grade+" "+classNo);
    		   if(list.size()>0)
    		   {
    		     if(grade.substring(grade.length()-2,grade.length()).compareTo(classNo.substring(0, 2))<0)
    		     {
    			   PointExceptionDao.add(sno, "留级");
    			   StudentPointDao.updatePoint(sno,40);
    		     }
    		   }
    		   else
    		   {} 
    		   List dList=PointExceptionDao.selectMeanQ(sno);
    		   if(dList.size()>0)
    		   {
    				   Object[] ob=(Object[]) dList.get(0);
    				   //double avgMean=Double.parseDouble(ob[0].toString());
    				   double q1=Double.parseDouble(ob[1].toString());
    				   double q3=Double.parseDouble(ob[2].toString());  
    				   double c_q1=Double.parseDouble(ob[3].toString());
    				   double c_q2=Double.parseDouble(ob[4].toString());
    				   double c_q3=Double.parseDouble(ob[5].toString());
    				   if(avg>=q3+1.5*(q3-q1))
    				   {
    					   PointExceptionDao.add(sno, "消费均值偏高"); 
    					   StudentPointDao.updatePoint(sno,40);
    				   }
    				   if(total==0||count==0||count<=c_q2-1.5*(c_q3-c_q1))
    				   {
    					   PointExceptionDao.add(sno, "消费次数偏低");
    					   StudentPointDao.updatePoint(sno,40);
    				   }
    		   }   		  
    	   }
    	   CalculateStatistics.setFlag();
       }
       /**
        *  将departmentPoint对应的一条数据转化为数组
        */
       public static Object[] toArray(DepartmentPoint departmentPoint)
       {
    	   Object[] obj=new Object[56];
    	   obj[0]=departmentPoint.getDepartment();
    	   obj[1]=departmentPoint.getGrade();
    	   obj[2]=departmentPoint.getTotal_Q1();
    	   obj[3]=departmentPoint.getTotal_Q2();
    	   obj[4]=departmentPoint.getTotal_Q3();
    	   obj[5]=departmentPoint.getTotal_MAX();
    	   obj[6]=departmentPoint.getTotal_MEAN();
    	   obj[7]=departmentPoint.getTotal_SD();
    	   obj[8]=departmentPoint.getCount_Q1();
    	   obj[9]=departmentPoint.getCount_Q2();
    	   obj[10]=departmentPoint.getCount_Q3();
    	   obj[11]=departmentPoint.getCount_MAX();
    	   obj[12]=departmentPoint.getCount_MEAN();
    	   obj[13]=departmentPoint.getCount_SD();
    	   obj[14]=departmentPoint.getAve_Q1();
    	   obj[15]=departmentPoint.getAve_Q2();
    	   obj[16]=departmentPoint.getAve_Q3();
    	   obj[17]=departmentPoint.getAve_MAX();
    	   obj[18]=departmentPoint.getAve_MEAN();
    	   obj[19]=departmentPoint.getAve_SD();
    	   obj[20]=departmentPoint.getLunch_Q1();
    	   obj[21]=departmentPoint.getLunch_Q2();
    	   obj[22]=departmentPoint.getLunch_Q3();
    	   obj[23]=departmentPoint.getLunch_MAX();
    	   obj[24]=departmentPoint.getLunch_MEAN();
    	   obj[25]=departmentPoint.getLunch_SD();
    	   obj[26]=departmentPoint.getLunch_CNTQ1();
    	   obj[27]=departmentPoint.getLunch_CNTQ2();
    	   obj[28]=departmentPoint.getLunch_CNTQ3();
    	   obj[29]=departmentPoint.getLunch_CNTMAX();
    	   obj[30]=departmentPoint.getLunch_CNTMEAN();
    	   obj[31]=departmentPoint.getLunch_CNTSD();
    	   obj[32]=departmentPoint.getLunch_AVGQ1();
    	   obj[33]=departmentPoint.getLunch_AVGQ2();
    	   obj[34]=departmentPoint.getLunch_AVGQ3();
    	   obj[35]=departmentPoint.getLunch_AVGMAX();
    	   obj[36]=departmentPoint.getLunch_AVGMEAN();
    	   obj[37]=departmentPoint.getLunch_AVGSD();
    	   obj[38]=departmentPoint.getSupper_Q1();
    	   obj[39]=departmentPoint.getSupper_Q2();
    	   obj[40]=departmentPoint.getSupper_Q3();
    	   obj[41]=departmentPoint.getSupper_MAX();
    	   obj[42]=departmentPoint.getSupper_MEAN();
    	   obj[43]=departmentPoint.getSupper_SD();
    	   obj[44]=departmentPoint.getSupper_CNTQ1();
    	   obj[45]=departmentPoint.getSupper_CNTQ2();
    	   obj[46]=departmentPoint.getSupper_CNTQ3();
    	   obj[47]=departmentPoint.getSupper_CNTMAX();
    	   obj[48]=departmentPoint.getSupper_CNTMEAN();
    	   obj[49]=departmentPoint.getSupper_CNTSD();
    	   obj[50]=departmentPoint.getSupper_AVGQ1();
    	   obj[51]=departmentPoint.getSupper_AVGQ2();
    	   obj[52]=departmentPoint.getSupper_AVGQ3();
    	   obj[53]=departmentPoint.getSupper_AVGMAX();
    	   obj[54]=departmentPoint.getSupper_AVGMEAN();
    	   obj[55]=departmentPoint.getSupper_AVGSD();
    	   return obj;
       }
       
       /**
        *  学生评分计算中间步骤,私有方法,被calculateStudentPointByGrades(DepartmentPoint departmentPoint)调用
        */
       private static double cal(double u)
       {
    		double y=Math.abs(u);
    		double y2 = y*y;
    		double z=Math.exp(-0.5 * y2) * 0.398942280401432678;
    		double p=0;
    		int k=28;
    		double s = -1;
    		double fj=k;
    		if(y>3)
    		{
    		  for(int i=1;i<=k;i++)
    		  {
    		   p = fj / (y+p);
    		   fj=fj - 1.0;
    		  }
    		  p = z / (y+p);
    		}
    		else
    		{
    		  for(int i=1;i<=k;i++)
    		  {
    		    p = fj * y2 / (2.0*fj +1.0 + s * p);
    		    s = -s;
    		    fj = fj - 1.0;
    		  }
    		  p = 0.5 - z * y / ( 1 - p );
    		}
    		if(u>0) p = 1.0 - p;
    		return p;
       }
       public static void main(String[] args)
       {
    	   calculateException();
    	   //PointExceptionDao.clear();
    	   //DepartmentPointDao.clear();
       }
}
