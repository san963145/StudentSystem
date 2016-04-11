<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.network.bean.*" import="com.wiscom.is.*, java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="java.util.Iterator" import="java.util.ArrayList" import="java.util.Map" import="java.util.Set"%>
<%
String flag=(String)application.getAttribute("flag");
String flagCard=(String)application.getAttribute("flagCard");
String flagGrade=(String)application.getAttribute("flagGrade");
String flagNet=(String)application.getAttribute("flagNet");
if(flag!=null||flagCard!=null||flagGrade!=null||flagNet!=null){
%>
<jsp:forward page="../../error.jsp"/>	
<%} %>

<%
	String is_config = this.getServletContext().getRealPath("/client.properties");
    Cookie all_cookies[] = request.getCookies();
    Cookie myCookie;
    String decodedCookieValue = null;
    if (all_cookies != null) {
        for(int i=0; i< all_cookies.length; i++) {
            myCookie = all_cookies[i];
            if( myCookie.getName().equals("iPlanetDirectoryPro") ) {
                decodedCookieValue = URLDecoder.decode(myCookie.getValue(),"GB2312");
            }
        }
    }

	IdentityFactory factory = IdentityFactory.createFactory( is_config );
	IdentityManager im = factory.getIdentityManager();
	
	String curUser = "";
	String authority="";
	if (decodedCookieValue != null ) {
    	curUser = im.getCurrentUser(decodedCookieValue);
    }
	if(curUser.length()==0){
		String gotoURL = request.getRequestURL().toString();
		String loginURL = im.getLoginURL() +"?goto=" +java.net.URLEncoder.encode(gotoURL,"UTF-8");
%>
<script>
  location="<%=loginURL%>"
</script>
<% 
	}   // if(curUser.length()==0)
		
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	User user=null;
  if(curUser.length()!=0)
  {
	user=(User)session.getAttribute("user");
	if(user==null)
	{
		user=UserDao.select(curUser);
		if(user==null)
		{
%>
			<script>
			  location="http://i.cqu.edu.cn/deny.html";
			</script>
<% 
		}
		else
		{
		   session.setAttribute("user",user);
		   authority=user.getAuthority();
		}
	}
	else
	{
		authority=user.getAuthority();
	}
  }
%>
 
<% if(curUser.length()!=0){%>  
<%
   Map<String,List> gradesMap=(Map<String,List>)request.getAttribute("gradesMap");
   String department=(String)request.getAttribute("department");
   String major=(String)request.getAttribute("major");   
   String sex=(String)request.getAttribute("sex");
   String str=(String)request.getAttribute("str");
   String cur=(String)request.getAttribute("cur");
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>学生行为分析平台</title>
	 <!-- 禁缓存-->
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
    <!-- 响应屏幕宽度	-->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- 添加的css-->
    <link rel="stylesheet" href="dist/css/my.css">
    <!-- Bootstrap 3.3.4 框架-->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <!-- FontAwesome 4.3.0 图标-->
    <link rel="stylesheet" href="plugins/font-awesome/css/font-awesome.min.css">
    <!-- Theme style 网站构造-->
    <link rel="stylesheet" href="dist/css/AdminLTE.min.css"> 
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
    
    <script src="pages/js/index.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <body class="skin-purple-light sidebar-mini">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>学生行为分析平台&nbsp;&nbsp;<img src="img/arrow.gif" height="10" width="10" /></b>
		</a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle visible-xs" data-toggle="offcanvas" role="button"></a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
			  
               <!-- User info -->
              <li>
			    <a href="pages/manager/info.jsp"><%=curUser %></a>
			  </li>
			  <li>
			    <a href="logout.jsp"><i class="fa fa-mail-forward"></i></a>
			  </li>
        
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <div id="systems" class="collapse in">
			<div class="box-div bg-aqua">
			  <a href="pages/cardSystem/cardSystem.jsp">
				<!-- small box -->
				  <div class="small-box bg-aqua">
					<div class="box-div">
					  <h3>消费</h3>
					  <p>一卡通消费分析系统</p>
					</div>
					<div class="icon">
					  <i class="glyphicon glyphicon-shopping-cart"></i>
					</div>
					<a href="pages/cardSystem/cardSystem.jsp" class="small-box-footer">进入系统<i class="fa fa-arrow-circle-right"></i></a>
				  </div>
			  </a>
			</div>
			<div class="box-div bg-green">
			  <a href="pages/network/network.jsp">
				<!-- small box -->
				  <div class="small-box bg-green">
					<div class="box-div">
					  <h3>网络</h3>
					  <p>网络日志分析系统</p>
					</div>
					<div class="icon">
					  <i class="glyphicon glyphicon-globe"></i>
					</div>
					<a href="pages/network/network.jsp" class="small-box-footer">进入系统<i class="fa fa-arrow-circle-right"></i></a>
				  </div>
			  </a>
			</div>
			<div class="box-div bg-yellow">
			  <a href="pages/gradesSystem/gradesSystem.jsp">
				 <!-- small box -->
				  <div class="small-box bg-yellow">
					<div class="box-div">
					  <h3>成绩</h3>
					  <p>成绩预测系统</p>
					</div>
					<div class="icon">
					  <i class="glyphicon glyphicon-pencil"></i>
					</div>
					<a href="pages/gradesSystem/gradesSystem.jsp" class="small-box-footer">进入系统 <i class="fa fa-arrow-circle-right"></i></a>
				  </div>
			  </a>
			</div>
			<div class="box-div bg-red">
			  <a href="pages/manager/info.jsp">
				 <!-- small box -->
				  <div class="small-box bg-red">
					<div class="box-div">
					  <h3>用户</h3>
					  <p>用户及系统管理</p>
					</div>
					<div class="icon">
					  <i class="glyphicon glyphicon-user"></i>
					</div>
					<a href="#" class="small-box-footer">进入系统<i class="fa fa-arrow-circle-right"></i></a>
				  </div>
			  </a>
			</div>
		</div>
      </aside>
      <script>
         function getByDepartment(grade)
         { 
        	 var sex="";
        	 if(document.getElementById("sex"))
        		 sex=document.getElementById("sex").innerHTML;
     	
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<list.length;i++)
        	 {
        		 if(list[i].id)
        		  str+=(list[i].id+'#');
        	 }         	 
        	 document.cookie='sex='+encodeURI(sex); 
        	 document.cookie='str='+encodeURI(str);     	 
        	 location="GradesGroupByCollege?grade="+grade;
         }
         function getByMajor(grade)
         {
        	 var sex="";
        	 if(document.getElementById("sex"))
        		 sex=document.getElementById("sex").innerHTML; 
        	 var department="";
        	 if(document.getElementById("department"))
        		 department=document.getElementById("department").innerHTML; 
        	 
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<list.length;i++)
        	 {
        		 if(list[i].id)
        		  str+=(list[i].id+'#');
        	 }  
        	 document.cookie='department='+encodeURI(department); 
        	 document.cookie='sex='+encodeURI(sex); 
        	 document.cookie='str='+encodeURI(str); 
        	 location="GradesGroupByMajor?grade="+grade;
         }
         function getBySex(grade)
         {
        	 var department="";
        	 if(document.getElementById("department"))
        		 department=document.getElementById("department").innerHTML;
        	 var major="";
        	 if(document.getElementById("major"))
        		 major=document.getElementById("major").innerHTML;
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<list.length;i++)
        	 {
        		 if(list[i].id)
        		  str+=(list[i].id+'#');
        	 }         	
        	 document.cookie='department='+encodeURI(department); 
        	 document.cookie='major='+encodeURI(major); 
        	 document.cookie='str='+encodeURI(str);  
        	 location="GradesGroupBySex?grade="+grade;
         }
        </script>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
        	<h1>成绩按年级细分</h1>
			<ol class="breadcrumb">
				<li class="active"><a href="index.jsp"><i class="fa fa-dashboard"></i> 主页</a></li>
				<li>成绩</li>
				<%if(!"".equals(str)){ %>
				<%
				   String[] s=str.split("#");
				   for(int i=0;i<s.length;i++)
				   {
					   String item=(String)request.getAttribute(s[i]);
			    %>
			           <li><a class="str" id="<%=s[i]%>" href="javascript:gradesGet(<%=i%>,'<%=s[i]%>')"><%=item%></a></li>
			    <% 
				   }
				%>
				<%} %>
				<%if(cur!=null){ 
					String[] s=str.split("#");
				%>
				<li><a class="str" id="<%=cur%>" href="javascript:gradesGet(<%=s.length%>,'<%=cur%>')"><%=(String)request.getAttribute(cur)%></a></li>
				<%} %>
			    <li class="active">按年级划分</li>
			</ol>
		</section>
        <div class="row">
            <%
               if(gradesMap!=null){
                Set<String>set=gradesMap.keySet();
                Iterator it=set.iterator();
                int i=-1;
                while(it.hasNext()){
        	      String key=(String)it.next();
        	      i++;
        	      String gradeName="20"+key;
            %>
        	<div class="col-md-12">	
        		<section class="content-header">
		          <h4><%=gradeName %>级</h4>
		        </section>

		        <section class="content">
					<div class="col-md-6 col-md-offset-3">	
						<div class="box box-warning">
				            <div class="box-header">
				              <h3 class="box-title"><small>平均GPA</small></h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				            	<canvas id="lineChart<%=i%>"></canvas>
				            </div>
				            <div class="box-footer">
				            	<div class="pull-right">
				                <%if(department==null){ %>
				            	<a class="btn btn-warning btn-sm" onclick="getByDepartment('<%=gradeName%>')"><small>按学院细分</small></a>
				            	<%} %>
				            	<%if(department!=null&&major==null){ %>
				                <a class="btn btn-warning btn-sm" onclick="getByMajor('<%=gradeName%>')"><small>按专业细分</small></a>
				                <%} %>
				                <%if(sex==null){ %>
				                <a class="btn btn-warning btn-sm" onclick="getBySex('<%=gradeName%>')"><small>按性别细分</small></a>
				                <%} %>
				            	</div>
				             </div>
			            </div>
			            <!-- /.box -->
					</div>
		        </section><!-- /.content -->
		    </div>
            <%} %><!-- while -->
         <%} %><!-- if -->
        </div>
      </div><!-- /.content-wrapper -->



      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 1.0
        </div>
		<p class="text-center">
        <strong>重庆大学学工部</strong>
		</p>
      </footer>
    </div><!-- ./wrapper -->

    <!-- jQuery 2.1.4 -->
    <script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.4 -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <!-- FastClick 
    从点击屏幕上的元素到触发元素的 click 事件，移动浏览器会有大约 300 毫秒的等待时间。
    它想看看你是不是要进行双击（double tap）操作。-->
    <script src="plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>


    <!-- 2015年12月1日 添加的地方-->
	<!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
    <!-- Page script -->
    <script>
    	//成绩数据概况
    	var lineChartOptions = {
		   scaleShowGridLines : true,
	       scaleShowHorizontalLines: true,
	       scaleShowVerticalLines: true,
	        bezierCurve : false,
	        pointDot : true,
	        pointDotStrokeWidth : 1,
	       datasetStroke : true,
	       datasetFill : false,
		   responsive: true,
		}
    	<%
        if(gradesMap!=null){
         Set<String>set=gradesMap.keySet();
         Iterator it=set.iterator();
         int i=-1;
         while(it.hasNext()){
 	      String key=(String)it.next();
 	      i++;
 	      List gradesList=gradesMap.get(key);
 	      int n=gradesList.size();
 		  String semesters="";
 		  int j;
 		  for(j=1;j<n+1;j++)
 		  {
 			  semesters+="\"学期"+j+"\",";
 		  }
 		  String gpa="";
 		  for(j=0;j<n;j++)
 		  {
 			  gpa+=(gradesList.get(j).toString()+",");
 		  }
        %>
    	//学院1
		 var linedata<%=i%> = {
			labels : [<%=semesters%>],
			datasets : [
				{
					fillColor : "#f39c12",
					strokeColor : "#f39c12",
					pointColor : "#f39c12",
					pointStrokeColor : "#fff",
					data : [<%=gpa%>]
				}
			]
		};
		var lineChartCanvas<%=i%> = document.getElementById("lineChart<%=i%>").getContext("2d");
        var lineChart<%=i%> = new Chart(lineChartCanvas<%=i%>).Line(linedata<%=i%>,lineChartOptions);
        
        <%} %><!-- while -->
       <%} %><!-- if -->

    </script>
	<!-- /2015年12月1日 添加的地方-->
  </body>
</html>
<%}%>