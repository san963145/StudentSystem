package com.sys.grades.proc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.sys.grades.bean.McHistoryGpa;
import com.sys.grades.bean.StuGradeInfo;
import com.sys.grades.bean.StudentFail;
import com.sys.grades.dao.DeptLocatorDao;
import com.sys.grades.dao.McHistoryGpaDao;
import com.sys.grades.dao.McStuGradeDao;
import com.sys.grades.dao.StuGradeInfoDao;
import com.sys.grades.servlet.CalculateGrades;

public class CalculateHistoryGpa {
	
	/**
     * 计算出学期gpa后暂存于一个STUGRADEINFO表中
     */
	public static void calculateHistoryGpa() {
        StuGradeInfoDao.clear();
		List snoList = McStuGradeDao.selectAllSno();
		Iterator snoIt = snoList.iterator();
		// mcHistoryGpaList总的列表
		while (snoIt.hasNext()) {
			String sNo = (String) snoIt.next();
			List stuInfo = McStuGradeDao.selectBySno(sNo.trim());
			Iterator stuInfoIt = stuInfo.iterator();
			while (stuInfoIt.hasNext()) {
				Object[] obj = (Object[]) stuInfoIt.next();
				String classNo = DeptLocatorDao.GetClassnoBySno(sNo.trim());
				// 取前两位，再取出日期中2015中后两位
				int gradeNo = Integer.parseInt(classNo.substring(0, 2));
				Date date = (Date) obj[4];
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String day = sdf.format(date);
				int yearNo = Integer.parseInt(day.substring(2, 4));
				int dayNo = Integer.parseInt(day.substring(4, 8));
				// dayno在0905-下一年0219为上半学期，而0220-0904为下半学期
				StuGradeInfo stuGradeInfo = new StuGradeInfo();
				if (yearNo - gradeNo == 0
						|| (yearNo - gradeNo == 1 && dayNo <= 219)) {
					// 将该对象放入同一学期中,计算该学生该学期的成绩！
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(1);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if (yearNo - gradeNo == 1 && 219 < dayNo && dayNo <= 904) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(2);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if ((yearNo - gradeNo == 1 && 904 < dayNo)
						|| (yearNo - gradeNo == 2 && dayNo <= 219)) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(3);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if (yearNo - gradeNo == 2 && dayNo > 219 && dayNo <= 904) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(4);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if ((yearNo - gradeNo == 2 && dayNo > 904)
						|| (yearNo - gradeNo == 3 && dayNo <= 219)) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(5);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if ((yearNo - gradeNo == 3 && dayNo > 219 && dayNo <= 904)) {
					//System.out.println(date+" "+day);
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(6);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if ((yearNo - gradeNo == 3 && dayNo > 904)
						|| (yearNo - gradeNo == 4 && dayNo <= 219)) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(7);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if (yearNo - gradeNo == 4 && 219 < dayNo && dayNo <= 904) {
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(8);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				} else if((yearNo - gradeNo == 4 && dayNo > 904)
						|| (yearNo - gradeNo == 5 && dayNo <= 219)){
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(9);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				}else if(yearNo - gradeNo == 5 && 219 < dayNo && dayNo <= 904){
					stuGradeInfo.setSno((String) obj[0]);
					stuGradeInfo.setSemester(10);
					stuGradeInfo.setGrade((String) obj[1]);
					stuGradeInfo.setCoursecredit((double) obj[2]);
					stuGradeInfo.setElective((String) obj[3]);
					stuGradeInfo.setTeachername((String)obj[5]);
					stuGradeInfo.setTesttype((String)obj[6]);
				}
				// 将mcStuGrade存入数据库
				StuGradeInfoDao.addStuGradeInfo(stuGradeInfo);
				
			}
		}

	}

	/**
     * 将临时表中数据取出计算后存入最终的学生学期GPA表中
     */
	public static void AddStuGradeInfo() {
		McHistoryGpaDao.clear();		
		// 再来操作数据库
		List sNoInfoList = StuGradeInfoDao.selectAllSno();
		Iterator sNoInfoIt = sNoInfoList.iterator();
		while (sNoInfoIt.hasNext()) {
			String sno = ((String) sNoInfoIt.next()).trim();
			// 这里要trim去掉空格！！！
			List smesterList = StuGradeInfoDao.seletSemestersBySno(sno);
			Iterator smesterIt = smesterList.iterator();
			while (smesterIt.hasNext()) {
				int semester = (int) smesterIt.next();
				List stuGradeInfoList = StuGradeInfoDao.selectBySnoSemester(
						sno, semester);
				Iterator stuGradeInfoIt = stuGradeInfoList.iterator();
				double a = 0; // GPA分子
				double b = 0; // GPA分母
				double avergrade;// 学期平均分
				double stugrade = 0;// 学生分数
				double totalcoursecredit = 0;// 学期所选学分
				double totalcoursecreditgain = 0;// 学期所得学分
				double gradegainrate;// 学期学分获得率
				while (stuGradeInfoIt.hasNext()) {
					Object[] objArray = (Object[]) (stuGradeInfoIt.next());
					String grade = objArray[2].toString();
					String elective = objArray[4].toString();
					String teachername = objArray[5].toString();
					String testtype = objArray[6].toString();
					double g;
					double realgrade = 0;
					double courseCredit = Double.parseDouble(objArray[3]
							.toString());
					double realCourseGain = 0;
					if(elective.equals("重修")||elective.equals("二专")||teachername.equals("国家四级")||teachername.equals("国家六级")||testtype.equals("补考")){
						continue;
					}
					totalcoursecredit += courseCredit;
					if (grade.matches("\\d+")) {
						if (Integer.parseInt(grade) < 60) {
							//判断下学期补考是否通过，过了给60，没过给0 分
							//g = 0;
							//realCourseGain = 0;
							//这里要看补考时是否通过,判断是否存在下一学期！
							if(semester+1<=smesterList.size()){
								String failgrade = StuGradeInfoDao.SelectFailBySnoSemester(sno,semester+1,teachername);
								if(failgrade!=null){
									if(Integer.parseInt(failgrade)>=60){
										realCourseGain = courseCredit;
										g = 1;
										
										realgrade = 60*courseCredit;
									}else{
										realCourseGain = 0;
										g = 0;
										realgrade = 0;
									}
								}else{
									realCourseGain = 0;
									g = 0;
									realgrade = 0;
								}
							}else{
								realCourseGain = 0;
								g = 0;
								realgrade = 0;
							}
						} else{
							double _grade=Double.parseDouble(grade);
		  					  if(_grade>90)
		  						  _grade=90;
		  				      g=(_grade-50.0)/10.0;
		  				    realgrade = Double.parseDouble(grade)*courseCredit;
		  				  realCourseGain = courseCredit;
						}
					} else {
						if (grade.equals("优")) {
							g = 4.0;
							realgrade = 95.0*courseCredit;
							realCourseGain = courseCredit;
						} else if (grade.equals("良") || grade.equals("合格")) {
							g = 3.5;
							realgrade = 85.0*courseCredit;
							realCourseGain = courseCredit;
						} else if (grade.equals("中")) {
							g = 2.5;
							realgrade = 75.0*courseCredit;
							realCourseGain = courseCredit;
						} else if (grade.equals("及")) {
							g = 1.5;
							realgrade = 65.0*courseCredit;
							realCourseGain = courseCredit;
						} else if (grade.equals("不")) {
							realgrade = 50.0*courseCredit;
							g = 0;
							//realCourseGain = 0;
							//这里要看补考时是否通过,判断是否存在下一学期！
							if(semester+1<=smesterList.size()){
								String failgrade = StuGradeInfoDao.SelectFailBySnoSemester(sno,semester+1,teachername);
								if(failgrade!=null){
									if("不".equals(failgrade)){
										realCourseGain = 0;
										g = 0;
										realgrade = 0;
									}else{
										realCourseGain = courseCredit;
										g = 1;
										realgrade = 60*courseCredit;
									}
								}else{
									realCourseGain = 0;
									g = 0;
									realgrade = 0;
								}
							}else{
								realCourseGain = 0;
								 g = 0;
								 realgrade = 0;
							}
						} else if (grade.equals("合格")) {
							realgrade = 85.0*courseCredit;
							g = 3.5;
							realCourseGain = courseCredit;
						} else {
							g = 0;
							realgrade = 0.0*courseCredit;
							realCourseGain = 0;
						}
					}
					b += courseCredit;
					a += (g * courseCredit);
					// 计算平均分
					stugrade += realgrade;
					totalcoursecreditgain += realCourseGain;
				}
				double gpasemester = a / b;
				avergrade = stugrade / totalcoursecredit;
				gradegainrate = totalcoursecreditgain / totalcoursecredit;
				McHistoryGpa mcHistoryGpa = new McHistoryGpa();
				mcHistoryGpa.setSno(sno);
				mcHistoryGpa.setAverscoresmester(avergrade);
				mcHistoryGpa.setGpasemester((double)gpasemester);
				mcHistoryGpa.setSemester(semester);
				mcHistoryGpa.setSelectcreditsemester(totalcoursecredit);
				mcHistoryGpa.setHadcreditsemester(totalcoursecreditgain);
				mcHistoryGpa.setHadcreditratesemester(gradegainrate);
				McHistoryGpaDao.add(mcHistoryGpa);
			}

		}
		McHistoryGpaDao.formatDecimal();
		CalculateGrades.setFlag();
	}

	/**
	 * 计算挂科门数
	 * @param collegename
	 * @param major
	 * @param grade
	 * @return
	 */
	public static List CalFail(String collegename,String major,int grd){
		List snoList = DeptLocatorDao.GetSnoByCMG(collegename, major, grd);
		Iterator snoIt = snoList.iterator();
		List stufailList = new ArrayList<>();
		while(snoIt.hasNext()){
			String sno = (String) snoIt.next();
			int failnum = 0;
			List list = McStuGradeDao.selectFRBySno(sno);
			Iterator it = list.iterator();
			StudentFail sf = new StudentFail();
			while(it.hasNext()){
				Object[] obj = (Object[])it.next();
				String grade =obj[0].toString();  
				String elective = obj[1].toString();
				String teachername = obj[2].toString();
				String testtype = obj[3].toString();
				if("国家四级".equals(teachername) || "国家六级".equals(teachername) || "二专".equals(elective) || "重修".equals(elective)){
					continue;
				}else{
					if("补考".equals(testtype)){
						continue;
					}else{
						double g ;
						if(grade.matches("\\d+"))
		  			  {
		  				  if(Integer.parseInt(grade)<60)
		  					  g=0;
		  				  else{
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
						if(g==0){
							failnum++;
						}
					}
				}
				
			}
			sf.setSno(sno);
			sf.setFainum(failnum);
			if(failnum==0){
				
			}else{
				stufailList.add(sf);
			}
		}
		return stufailList;
	}
	/**
	 * 根据学号计算每学期Gpa
	 * @param args
	 */
	public static List GetHSGBySno(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		if(semestersList.size()!=0){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHSGpaBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
       return list;
	}
	/**
	 * 依据学号计算每学期平均分
	 * @param args
	 */
	public static List GetHAGBySno(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		if(semestersList!=null){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHAGBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
		return list;
	}
	/**
	 * 依据学号计算每学期学分获得率
	 * @param args
	 */
	public static List GetHGGR(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		if(semestersList!=null){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHGGRBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
		return list;
	}
	/**
	 * 根据学号计算每学期Gpa
	 * @param args
	 */
	public static List GetSemHSGBySno(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		Object[] obj=semestersList.toArray();
		Arrays.sort(obj);
		semestersList=Arrays.asList(obj);
		if(semestersList.size()!=0){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHSGpaBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
       return list;
	}
	/**
	 * 依据学号计算每学期平均分
	 * @param args
	 */
	public static List GetSemHAGBySno(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		Object[] obj=semestersList.toArray();
		Arrays.sort(obj);
		semestersList=Arrays.asList(obj);
		if(semestersList!=null){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHAGBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
		return list;
	}
	/**
	 * 依据学号计算每学期学分获得率
	 * @param args
	 */
	public static List GetSemHGGR(String sno){
		List list = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		Object[] obj=semestersList.toArray();
		Arrays.sort(obj);
		semestersList=Arrays.asList(obj);
		if(semestersList!=null){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int semester = (int) semIt.next();
				Object smesterGpa = McHistoryGpaDao.GetHGGRBySS(semester,sno);
				if(smesterGpa!=null){
					list.add(smesterGpa);
				}
			}
		}
		return list;
	}
	/**
	 * 根据学号计算学生分学期重修情况
	 * @param args
	 */
	public static List GetRestudyBySno(String sno){
		List stuRestudyList = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		Object[] obj1=semestersList.toArray();
		Arrays.sort(obj1);
		semestersList=Arrays.asList(obj1);
		if(semestersList.size()!=0){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int restudynum = 0;
				int semester = (int) semIt.next();
				//System.out.println(semester);
				List list = StuGradeInfoDao.selectBySnoSemester(sno, semester);
				Iterator it = list.iterator();
				while(it.hasNext()){
					Object[] obj = (Object[])it.next();
					String elective = obj[4].toString();
					if("重修".equals(elective)){
						restudynum++;
					}else{
						 
					}
				}
				stuRestudyList.add(restudynum);
			}
		}
		return stuRestudyList;
	}
	/**
	 * 根据学号计算学生每学期挂科门数
	 * @param args
	 */
	public static List GetFailBySno(String sno){
		List stufailList = new ArrayList<>();
		List semestersList = StuGradeInfoDao.seletSemestersBySno(sno);
		Object[] obj1=semestersList.toArray();
		Arrays.sort(obj1);
		semestersList=Arrays.asList(obj1);
		if(semestersList.size()!=0){
			Iterator semIt = semestersList.iterator();
			while(semIt.hasNext()){
				int failnum = 0;
				int semester = (int) semIt.next();
				List list = StuGradeInfoDao.selectBySnoSemester(sno, semester);
				Iterator it = list.iterator();
				while(it.hasNext()){
					Object[] obj = (Object[])it.next();
					String grade = obj[2].toString();
					String elective = obj[4].toString();
					String teachername = obj[5].toString();
					String testtype = obj[6].toString();
					if("国家四级".equals(teachername) || "国家六级".equals(teachername) || "二专".equals(elective) || "重修".equals(elective)){
						continue;
					}else{
						if("补考".equals(testtype)){
							continue;
						}else{
							double g ;
							if(grade.matches("\\d+"))
			  			  {
			  				  if(Integer.parseInt(grade)<60)
			  					  g=0;
			  				  else
			  				      g=(Double.parseDouble(grade)-50.0)/10.0;
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
							if(g==0){
								failnum++;
							}
						}
					}
				}
				stufailList.add(failnum);
			}
		}
		return stufailList;
	}
	public static void main(String[] args) {
		AddStuGradeInfo();
	}
}
