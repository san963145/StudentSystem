<%@page import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page import="com.sys.manager.bean.*" import="com.sys.card.dao.*" import="com.sys.grades.bean.StudentFail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" import="java.util.Iterator" import="java.util.ArrayList"%>
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
<%  
  String result=(String)request.getAttribute("result");
  List resultList=(List)request.getAttribute("resultList");
  List failedList=(List)request.getAttribute("failedList");
  List<String>departmentList=(List<String>)DepartmentPointDao.getDepartmentsByUser(user);
  String department=(String)request.getAttribute("department");
  String major=(String)request.getAttribute("major");
  String grade=(String)request.getAttribute("grade");
%> 

<% if(curUser.length()!=0){
	if(AccessReport.indexUrlPath==null)
	  {
		AccessReport.indexUrlPath= this.getServletContext().getRealPath("/indexUrl.properties");
	  }
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>成绩预测系统</title>
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
    
    <script src="pages/js/selector.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
 
  <body class="skin-yellow-light sidebar-mini" onload="init()">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>成绩预测系统&nbsp;<img src="img/arrow.gif" height="10" width="10"/></b>
		</a>
		
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle visible-xs" data-toggle="offcanvas" role="button">
            <span class="sr-only"><%=curUser %></span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- User info -->
              <li>
			    <a href="pages/manager/info.jsp" data-toggle="tooltip" data-placement="bottom" title="用户信息"><%=curUser %></a>
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
	  
		<!-- 各个系统选择菜单 -->
        <div id="systems" class="collapse">
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
					<a href="pages/manager/info.jsp" class="small-box-footer">进入系统<i class="fa fa-arrow-circle-right"></i></a>
				  </div>
			  </a>
			</div>
		</div>
		
		<!-- sidebar: style can be found in sidebar.less -->
        <section id="sidebar" class="sidebar collapse in">
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
           
			
            <li class="treeview">
              <a href="#">
                <i class="fa fa-edit"></i> 
				<span>学生成绩报告</span> 
				<i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="pages/gradesSystem/searchByGroup.jsp"><i class="fa fa-circle-o"></i> 按年级统计</a></li>
                <li><a href="pages/gradesSystem/searchByPerson.jsp"><i class="fa fa-circle-o"></i> 按学生统计</a></li>
              </ul>
            </li>
			<li>
              <a href="pages/gradesSystem/consumption.jsp">
                 <i class="fa fa-credit-card"></i> <span>消费特征提取</span>
              </a>
            </li>
            <li>
              <a href="pages/gradesSystem/internetUse.jsp">
                 <i class="fa fa-wifi"></i> <span>网络访问特征提取</span>
              </a>
            </li>
			<li>
              <a href="pages/gradesSystem/prediction.jsp">
                 <i class="fa fa-mortar-board"></i> <span>学生成绩预测</span>
              </a>
            </li>    
          </ul>
        </section>
        <!-- /.sidebar -->
	  </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
				
				<small>成绩报告</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/gradesSystem/gradesSystem.jsp"> 成绩预测</a></li>
			<li><a href="#"> 成绩报告</a></li>
			<li class="active"> 按年级统计</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
		  
			<div class="col-md-12">
			<!-- SELECT2 EXAMPLE -->
			  <div class="box box-warning">
				<div class="box-header with-border">
				  <h3 class="box-title">按年级查询</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form action="GradesByGroup" method="post" onsubmit="return check()">
						<div class="row">
							<div class="col-md-3">
							  <div class="form-group">
								<label>学院</label>
								<select class="form-control " id="departments" style="width: 100%;" name="department" onchange="sendDepartment(this.value)">
								  <%if(department==null){ %>
								   <option selected="selected">选择学院</option>
								   <%}else{ %>
								   <option selected="selected"><%=department %></option>
								   <%} %>
								   
								</select>
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-3">
							  <div class="form-group">
								<label>专业</label>
								<select class="form-control " id="majors" name="major" style="width: 100%;">
								  <%if(major==null){ %>
								   <option selected="selected">选择专业</option>
								   <%}else{ %>
								   <option selected="selected"><%=major %></option>
								   <%} %>
								  
								</select> 
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-3">
							  <div class="form-group">
								<label>年级</label>
								<select class="form-control " id="grades" name="grade" style="width: 100%;">
								  <%if(grade==null){ %>
								   <option selected="selected">选择年级</option>
								   <%}else{ %>
								   <option selected="selected"><%=grade %></option>
								   <%} %>
								  
								</select>
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-3">
							  <div class="col-md-8 col-md-offset-2">
								  <label>&nbsp;</label>
								  <button type="submit" class="btn btn-warning btn-block btn-flat">查询</button>
							  </div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</form>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			
			<%if(result!=null){ if(resultList.size()>0){%>
			<div class="col-md-6 connectedSortable">
				 
				  <div class="box box-warning">
					<div class="box-header with-border">
					  <h3 class="box-title">挂科门数学生列表(单位:门)</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body" >
						<table id="result" class="table table-bordered table-hover">
						<thead>
						  <tr>
							<th>学号</th>
							<th>挂科门数</th>							
						  </tr>
						</thead>
						<tbody>
						    <%if(failedList!=null){
							   Iterator t=failedList.iterator();
							   while(t.hasNext()){
								   Object[] obj=(Object[])t.next();%>  
							   <tr>
							     <td><%=obj[0].toString()%></td>
							     <td><%=obj[2].toString()%></td>
							   </tr>
							   <%} %>
						   <%} %> 
						</tbody>
                        </table>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
				
				
				  <div class="box box-warning">
				   <div class="box-header with-border">
					  <h3 class="box-title">重修门数学生列表(单位:门)</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<table id="result2" class="table table-bordered table-hover">
						<thead>
						  <tr>
							<th>学号</th>
							<th>重修门数</th>							
						  </tr>
						</thead>
						<tbody>
						  <%if(failedList!=null){
							   Iterator t=failedList.iterator();
							   while(t.hasNext()){
								   Object[] obj=(Object[])t.next();%>  
							   <tr>
							     <td><%=obj[0].toString()%></td>
							     <td><%=obj[1].toString()%></td>
							   </tr>
							   <%} %>
						   <%} %> 
						</tbody>
                        </table>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
				  				  			  				  				  				  								  
			</div>
          
			<div class="col-md-6 connectedSortable">
				
				<div class="box box-warning" id="d1">
					<div class="box-header with-border">
					  <h3 class="box-title">平均GPA</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<canvas id="lineChart1" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
				
				
				
				  <div class="box box-warning" id="d2">
					<div class="box-header with-border">
					  <h3 class="box-title">平均分数(单位:分)</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<canvas id="lineChart2" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
				
				
				
				  <div class="box box-warning" id="d3">
					<div class="box-header with-border">
					  <h3 class="box-title">平均选课学分(单位:学分)</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<canvas id="lineChart3" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
			
				
				
				  <div class="box box-warning" id="d4">
					<div class="box-header with-border">
					  <h3 class="box-title">获得学分的均值(单位:学分)</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<canvas id="lineChart4" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box --> 
				  
				
				<div class="box box-warning" id="d5">
					<div class="box-header with-border">
					  <h3 class="box-title">学分获得率的均值</h3>
					  <div class="box-tools pull-right">
						<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
					  </div>
					</div>
					<div class="box-body">
						<canvas id="lineChart5" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
								
			</div>
			 <%}else{%><h4><b>&nbsp;&nbsp;&nbsp;不存在该项数据！</b></h4><% }}%>
          </div><!-- /.row (main row) -->
        </section><!-- /.content -->
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
    <!-- jQuery UI 1.11.4 -->
    <script src="plugins/jQueryUI/jquery-ui.min.js"></script>
    <!-- Bootstrap 3.3.4 -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
	 <!-- Select2 -->
    <script src="plugins/select2/select2.full.min.js"></script>
    <!-- FastClick 
    从点击屏幕上的元素到触发元素的 click 事件，移动浏览器会有大约 300 毫秒的等待时间。
    它想看看你是不是要进行双击（double tap）操作。-->
    <script src="plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
	<!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
	<!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
	<!-- my js -->
    <script src="dist/js/my.js"></script>
	<!-- Page script -->
	<%if(result!=null){if(resultList.size()>0){ %>
	<%
	  List<Double>gpaList=new ArrayList<Double>();
	  List<Double>avgList=new ArrayList<Double>();
	  List<Double>selectedList=new ArrayList<Double>();
	  List<Double>hadList=new ArrayList<Double>();
	  List<Double>hadRateList=new ArrayList<Double>();
	  int n=resultList.size();
	  String semesters="";
	  int j;
	  for(j=1;j<n+1;j++)
	  {
		  semesters+="\"学期"+j+"\",";
	  }
	  //semesters=semesters.substring(0,semesters.length()-1);
	  for(int i=0;i<n;i++)
	  {
		  Object[] obj=(Object[])resultList.get(i);
		  gpaList.add(Double.parseDouble(obj[0].toString()));
		  avgList.add(Double.parseDouble(obj[1].toString()));
		  selectedList.add(Double.parseDouble(obj[2].toString()));
		  hadList.add(Double.parseDouble(obj[3].toString()));
		  hadRateList.add(Double.parseDouble(obj[4].toString()));		  
	  }
	  String gpa="";
	  String avg="";
	  String selected="";
	  String had="";
	  String hadRate="";
	  for(j=0;j<n;j++)
	  {
		  gpa+=(gpaList.get(j)+",");
	  }
	  //gpa=gpa.substring(0,gpa.length()-1);
	  for(j=0;j<n;j++)
	  {
		  avg+=(avgList.get(j)+",");
	  }
	  //avg=avg.substring(0,avg.length()-1);
	  for(j=0;j<n;j++)
	  {
		  selected+=(selectedList.get(j)+",");
	  }
	  //selected=selected.substring(0,selected.length()-1);
	  for(j=0;j<n;j++)
	  {
		  had+=(hadList.get(j)+",");
	  }
	  //had=had.substring(0,had.length()-1);
	  for(j=0;j<n;j++)
	  {
		  hadRate+=(hadRateList.get(j)+",");
	  }
	  //hadRate=hadRate.substring(0,hadRate.length()-1);
	%>
    <script>
     $(function () {
        //Initialize Select2 Elements
        $(".select2").select2();
        
        var lineChartCanvas = $("#lineChart1").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas);
        
        var data = {
        		labels : [<%=semesters%>],

        		datasets : [
        			{
        				fillColor : "rgba(255,255,255,0)",	
        				strokeColor : "rgba(243,156,18,1)",
        				pointColor : "rgba(243,156,18,1)",
        				pointStrokeColor : "#fff",       				
        				data : [<%=gpa%>]       			       				
        			}
        		]
        	}
       
        var lineOptions = {
        		///Boolean - Whether grid lines are shown across the chart
        	    scaleShowGridLines : true,

        	    //String - Colour of the grid lines
        	    scaleGridLineColor : "rgba(0,0,0,.05)",

        	    //Number - Width of the grid lines
        	    scaleGridLineWidth : 1,

        	    //Boolean - Whether to show horizontal lines (except X axis)
        	    scaleShowHorizontalLines: true,

        	    //Boolean - Whether to show vertical lines (except Y axis)
        	    scaleShowVerticalLines: true,

        	    //Boolean - Whether the line is curved between points
        	    bezierCurve : false,

        	    //Number - Tension of the bezier curve between points
        	    bezierCurveTension : 0.4,

        	    //Boolean - Whether to show a dot for each point
        	    pointDot : true,

        	    //Number - Radius of each point dot in pixels
        	    pointDotRadius : 4,

        	    //Number - Pixel width of point dot stroke
        	    pointDotStrokeWidth : 1,

        	    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
        	    pointHitDetectionRadius : 20,

        	    //Boolean - Whether to show a stroke for datasets
        	    datasetStroke : true,

        	    //Number - Pixel width of dataset stroke
        	    datasetStrokeWidth : 2,

        	    //Boolean - Whether to fill the dataset with a colour
        	    datasetFill : true,
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        lineChart.Line(data, lineOptions);
        
        var lineChartCanvas2 = $("#lineChart2").get(0).getContext("2d");
        var lineChart2 = new Chart(lineChartCanvas2);        
        var data2 = {
        		labels : [<%=semesters%>],
        		datasets : [
        			{
        				fillColor : "rgba(255,255,255,0)",	
        				strokeColor : "rgba(243,156,18,1)",
        				pointColor : "rgba(243,156,18,1)",
        				pointStrokeColor : "#fff",       				
        				data : [<%=avg%>]       			       				
        			}
        		]
        	}
        lineChart2.Line(data2, lineOptions);
        
        var lineChartCanvas3 = $("#lineChart3").get(0).getContext("2d");
        var lineChart3 = new Chart(lineChartCanvas3);        
        var data3 = {
        		labels : [<%=semesters%>],
        		datasets : [
        			{
        				fillColor : "rgba(255,255,255,0)",	
        				strokeColor : "rgba(243,156,18,1)",
        				pointColor : "rgba(243,156,18,1)",
        				pointStrokeColor : "#fff",       				
        				data : [<%=selected%>]       			       				
        			}
        		]
        	}
        lineChart3.Line(data3, lineOptions);
        
        var lineChartCanvas4 = $("#lineChart4").get(0).getContext("2d");
        var lineChart4 = new Chart(lineChartCanvas4);        
        var data4 = {
        		labels : [<%=semesters%>],
        		datasets : [
        			{
        				fillColor : "rgba(255,255,255,0)",	
        				strokeColor : "rgba(243,156,18,1)",
        				pointColor : "rgba(243,156,18,1)",
        				pointStrokeColor : "#fff",       				
        				data : [<%=had%>]       			       				
        			}
        		]
        	}
        lineChart4.Line(data4, lineOptions);
        
        var lineChartCanvas5 = $("#lineChart5").get(0).getContext("2d");
        var lineChart5 = new Chart(lineChartCanvas5);        
        var data5 = {
        		labels : [<%=semesters%>],
        		datasets : [
        			{
        				fillColor : "rgba(255,255,255,0)",	
        				strokeColor : "rgba(243,156,18,1)",
        				pointColor : "rgba(243,156,18,1)",
        				pointStrokeColor : "#fff",       				
        				data : [<%=hadRate%>]       			       				
        			}
        		]
        	}
        lineChart5.Line(data5, lineOptions);
        
        $("#result").DataTable({
			"aLengthMenu": [[10,15,20,30], [10,15,20,30]],
			"bStateSave": false,
			"bLengthChange":true,
			"oLanguage": {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "没有匹配结果",
				"sInfo": "显示第_START_至_END_项结果，共_TOTAL_项",
				"sInfoEmpty": "没有数据",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				"sSearch": "搜索：",
				"oPaginate": {
				"sFirst": "<<",
				"sPrevious": "<",
				"sNext": ">",
				"sLast": ">>"
				}
				}
			
		} );
		$("#result2").DataTable({
			"aLengthMenu": [[10,15,20,30], [10,15,20,30]],
			"bStateSave": false,
			"bLengthChange":true,
		    "oLanguage": {
			"sLengthMenu": "每页显示 _MENU_ 条记录",
			"sZeroRecords": "没有匹配结果",
			"sInfo": "显示第_START_至_END_项结果，共_TOTAL_项",
			"sInfoEmpty": "没有数据",
			"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
			"sSearch": "搜索：",
			"oPaginate": {
			"sFirst": "<<",
			"sPrevious": "<",
			"sNext": ">",
			"sLast": ">>"
			}
			}
		} );
      });
    </script>
<%}} %>
  </body>
</html>

<%}%>