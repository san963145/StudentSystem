<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.sys.card.bean.*" import="com.sys.card.dao.*" import="com.wiscom.is.*, java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List" import="java.util.Iterator" %>
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
  String department=(String)request.getAttribute("department");
  String major=(String)request.getAttribute("major");
  String grade=(String)request.getAttribute("grade");
  List<RatingResult> resultList=(List<RatingResult>)request.getAttribute("resultList");
  int[] countList=(int[])request.getAttribute("countList");
  List<String>departmentList=(List<String>)DepartmentPointDao.getDepartmentsByUser(user);
  
  String[] grades=null;
  if(user!=null){
  if(user.getAuthority().equals("Instructor"))
  {
    grades=user.getGrade().split("#");
  }  
  }
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
    <title>一卡通消费分析系统</title>
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
	<!-- Select2 选择框输入-->
    <link rel="stylesheet" href="plugins/select2/select2.min.css">
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
  
  <body class="skin-blue-light sidebar-mini" onload="init()">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>一卡通消费分析系统<img src="img/arrow.gif" height="10" width="10"/></b>
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
	  
		<!-- 各个系统选择菜单 -->
        <div id="systems" class="collapse">
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
                <i class="fa fa fa-edit"></i> 
				<span>消费记录</span> 
				<i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <%if(authority.equals("Admin")){ %>
                <li class="admin"><a href="pages/cardSystem/searchByGroup.jsp"><i class="fa fa-circle-o"></i> 按学院查询</a></li>
                <%} %>
                <%if(authority.equals("Dean")){ %>
                <li class="dean"><a href="pages/cardSystem/searchByGroup.jsp"><i class="fa fa-circle-o"></i> 按年级查询</a></li>
                <%} %>
                <%if(authority.equals("Instructor")){%> 
				<li class="instruct"><a href="pages/cardSystem/searchByGroup.jsp"><i class="fa fa-circle-o"></i> 年级整体查询</a></li>
				<%} %>
                <li><a href="pages/cardSystem/searchByPerson.jsp"><i class="fa fa-circle-o"></i> 按学生查询</a></li>
              </ul>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-pie-chart"></i>
                <span>消费报告</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <%if(authority.equals("Admin")){ %>
                <li class="admin"><a href="DrawStatistics"><i class="fa fa-circle-o"></i> 全校统计</a></li>
                <%} %>
                <%if(authority.equals("Dean")){ %>
				<li class="dean"><a href="DrawStatistics"><i class="fa fa-circle-o"></i> 学院统计</a></li>
				<%} %>
				<%if(authority.equals("Instructor")){%> 
				<li class="instruct"><a href="pages/cardSystem/statisticalResultsInstructor.jsp"><i class="fa fa-circle-o"></i> 年级统计</a></li>
				<%} %>
				<%if(authority.equals("Admin")){ %>
				<li class="admin"><a href="DrawComparison"><i class="fa fa-circle-o"></i> 学院对比</a></li>
				<%} %>
				<%if(authority.equals("Dean")){ %>
				<li class="dean"><a href="DrawComparison"><i class="fa fa-circle-o"></i> 年级对比</a></li>
                <%} %>

              </ul>
            </li>
            <li class="treeview active">
              <a href="#">
                <i class="fa fa-sort-amount-desc"></i>
                <span>消费指数</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="pages/cardSystem/ratingsResults.jsp"><i class="fa fa-circle-o"></i> 查看结果</a></li>
              </ul>
            </li>
			<li>
              <a href="pages/cardSystem/consumptionForecast.jsp">
                 <i class="fa fa-line-chart"></i> <span>消费预测</span>
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-signal"></i> <span>助学金推荐</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="pages/cardSystem/recommendByGroup.jsp"><i class="fa fa-circle-o"></i> 按年级查询</a></li>
                <li><a href="pages/cardSystem/recommendByPerson.jsp"><i class="fa fa-circle-o"></i> 按学生查询</a></li>
              </ul>
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
				<small>消费指数</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/cardSystem/cardSystem.jsp" > 一卡通</a></li>
			<li><a href="#" > 消费指数</a></li>
			<li class="active">评级结果</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
			
			<div class="col-md-12">
			<!-- SELECT2 EXAMPLE -->
			  <div class="box box-info">
				<div class="box-header with-border">
				  <h3 class="box-title">查看对象</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form action="GetRatingResult" method="post" onsubmit="return check()">
						<div class="row">
							<div class="col-md-3">
							  <div class="form-group">
								<label>学院</label>
								<select class="form-control" id="departments" name="department" style="width: 100%;" onchange="sendDepartment(this.value)">
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
								<select class="form-control" id="majors" name="major" style="width: 100%;">								  
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
								<select class="form-control" id="grades" name="grade" style="width: 100%;">

								   <%if(grade==null){ %>
								   <option selected="selected">选择年级</option>
								   <%}else{ %>
								   <option selected="selected"><%=grade %></option>
								   <%} %>
								   
								</select>
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-2 col-md-offset-1">
							  <label>&nbsp;</label>
							  <button type="submit" class="btn btn-primary btn-block btn-flat">查询</button>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</form>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			
			
			<%if(resultList!=null){if(resultList.size()>0){ %>
			<div class="col-xs-12 col-md-6">
				 <div class="box box-info">
				<div class="box-header with-border">
				      <%if(department!=null && grade!=null &&major!=null){ %>
					  <h3 class="box-title"><%=department %><%=grade %>级 <%=major %>专业 学生消费评分分布图</h3>
					  <div class="pull-right">				    
						(单位:人数)
					  </div>
					  <%}else{ %>
					  <h3 class="box-title">学生消费评分分布图</h3>
					  <div class="pull-right">				    
						(单位:人数)
					  </div>
					  <%} %>
				   <div class="pull-right">
					<h3 class="box-title"></h3>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					    <canvas id="barChart1" style="height:350px"></canvas>					
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			
			<div class="col-xs-12 col-md-6">
				 <div class="box box-info">
				<div class="box-header with-border">
				      <%if(department!=null && grade!=null){ %>
					  <h3 class="box-title"><%=department %><%=grade %>级  <%=major %>专业 学生消费评级结果</h3>
					  <%}else{ %>
					  <h3 class="box-title">学生消费评级</h3>
					  <%} %>
				   <div class="pull-right">
					<h3 class="box-title"></h3>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					    <table id="result2" class="table table-bordered table-hover">
						<thead>
						  <tr>
							<th>学号</th>
							<th>姓名</th>
							<th>学院</th>
							<th>专业</th>
							<th>年级</th>
							<th>消费指数</th>
							<th>备注</th>
						  </tr>
						</thead>
						<tbody>
						    
						    <%Iterator it=resultList.iterator();
						      while(it.hasNext()){
						    	  RatingResult r=(RatingResult)it.next();%>
							<tr>
								<td><%=r.getSno()%></td>
								<td><%=r.getSname()%></td>
								<td><%=r.getDepartment()%></td>
						        <td><%=r.getMajor()%></td>
								<td><%=r.getGrade()%></td>
								<td><%=r.getPoint()%></td>
								<td><%=r.getInfo()%></td>
						    </tr>
						    <%}%>

						</tbody>
                  </table>					
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			<%}else{%>
			<h4><b>&nbsp;&nbsp;&nbsp;不存在该项数据！</b></h4>
			<% } }%>
			
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
    
	<!-- my js -->
    <script src="dist/js/my.js"></script>
    <!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
	<!-- Page script -->
	<%if(resultList!=null){if(resultList.size()>0){ %>
	<%
	   String labels="";
	   String counts="";
	   int i=0;
	   for(i=0;i<20;i++)
	   {
		   labels+="\""+(40+i*3)+"-"+(43+i*3)+"\",";
	   }
	   for(i=0;i<20;i++)
	   {
		   counts+=countList[i]+",";
	   }
	%>
    <script>
     $(function () {
        //Initialize Select2 Elements
        $(".select2").select2();
        
        $("#result2").DataTable({
			"aLengthMenu": [[5,10,20,50,-1], [5, 10, 20, 50, "All"]],
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
        var barChartCanvas1 = $("#barChart1").get(0).getContext("2d");
        var barChart1 = new Chart(barChartCanvas1);
        var barChartData1 = {
          labels: [<%=labels%>],
          datasets: [
            {

              fillColor: "rgba(60,141,188,0.9)",
              strokeColor: "rgba(60,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: [<%=counts%>]
            }
          ]
        };
        var barChartOptions = {

                //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
                scaleBeginAtZero: true,
                //Boolean - Whether grid lines are shown across the chart
                scaleShowGridLines: true,
                //String - Colour of the grid lines
                scaleGridLineColor: "rgba(0,0,0,.05)",
                //Number - Width of the grid lines
                scaleGridLineWidth: 1,
                //Boolean - Whether to show horizontal lines (except X axis)
                scaleShowHorizontalLines: true,
                //Boolean - Whether to show vertical lines (except Y axis)
                scaleShowVerticalLines: true,
                //Boolean - If there is a stroke on each bar
                barShowStroke: true,
                //Number - Pixel width of the bar stroke
                barStrokeWidth: 2,
                //Number - Spacing between each of the X value sets
                barValueSpacing: 5,
                //Number - Spacing between data sets within X values
                barDatasetSpacing: 1,
            
                //Boolean - whether to make the chart responsive
                responsive: true,
                maintainAspectRatio: true
              };

              barChartOptions.datasetFill = false;
              barChart1.Bar(barChartData1, barChartOptions);
      });
    </script>
    <%}} %>
  </body>
</html>
<%}%>