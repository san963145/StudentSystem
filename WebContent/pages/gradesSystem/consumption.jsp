<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.sys.card.bean.StudentPoint" %>
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
   StudentPoint studentPoint=(StudentPoint)request.getAttribute("studentPoint");
   String sname=(String)request.getAttribute("sname");	 
   String accessFlag=(String)request.getAttribute("accessFlag");
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

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="skin-yellow-light sidebar-mini">
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
				成绩预测系统
				<small>消费记录</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/gradesSystem/gradesSystem.jsp"> 成绩预测</a></li>
			<li class="active"> 消费特征</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
			
			<!-- SELECT2 EXAMPLE -->
			  <div class="box box-default">
				<div class="box-header with-border">
				  <h3 class="box-title">按学生查询</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form class="form-horizontal" action="Consumption" method="post">
						<div class="row">
							<div class="col-md-6">
							    <div class="form-group">
								  <label for="studentID" class="col-sm-2 control-label">学号</label>
								  <div class="col-sm-10">
									<input type="text" class="form-control" id="studentID" name="sno" placeholder="输入学生学号">
								  </div>
								</div><!-- /.form-group -->
							</div><!-- /.col -->
							
							<div class="col-md-2 col-md-offset-2">
							  <button type="submit" class="btn btn-warning btn-block btn-flat">查询</button>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</form>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			  <%if(result!=null){ %>
			  <div class="box box-warning">
				<div class="box-header with-border">
				  <h3 class="box-title">查询结果</h3>
				</div><!-- /.box-header -->
				<%if(studentPoint!=null){ %>
				   <%if(accessFlag.equals("1")){ %>
				<div class="box-body">
				  <table id="result" class="table table-bordered table-hover">
					<thead>
					  <tr>
						<th>学号</th>
						<th>姓名</th>
						<th>性别</th>
						<th>去年得过助学金</th>
						<th>总值</th>
						<th>用餐次数</th>
						<th>每餐均值</th>
						<th>午餐总值</th>
						<th>午餐次数</th>
						<th>午餐均值</th>
						<th>晚餐总值</th>
						<th>晚餐次数</th>
						<th>晚餐均值</th>
						<th>分数</th>
					  </tr>
					</thead>
					<tbody>
						<tr>
							<td><%=studentPoint.getSno() %></td>
							<td><%=sname %></td>
							<td><%=studentPoint.getGender() %></td>
							<td><%=studentPoint.getHasScholarship() %></td>
						    <td><%=studentPoint.getTotal() %></td>
							<td><%=studentPoint.getCount() %></td>
							<td><%=studentPoint.getAverage() %></td>
							<td><%=studentPoint.getLunchTTL() %></td>
							<td><%=studentPoint.getLunchCNT() %></td>
							<td><%=studentPoint.getLunchAVG() %></td>
							<td><%=studentPoint.getSupperTTL() %></td>
							<td><%=studentPoint.getSupperCNT() %></td>
							<td><%=studentPoint.getSupperAVG() %></td>
							<td><%=studentPoint.getPoint() %></td>							
						</tr>
					</tbody>
				  </table>
				</div><!-- /.box-body -->
				
				<%}else{ %><h4><b>&nbsp;该学生不在访问权限内！</b></h4><%} %>
				<%}else{ %><h4><b>&nbsp;无该项数据或学号不存在！</b></h4><%} %>
				
			  </div><!-- /.box -->
			   <%if(studentPoint!=null && accessFlag.equals("1")){ %>
			  <div class="col-md-6">
				 <!-- DONUT CHART -->
				  <div class="box box-warning">
					<div class="box-header with-border">
					  <h3 class="box-title">各餐消费比例饼状图(单位:元)</h3>
					  <div class="pull-right">				    
						<i class="fa fa-square" style="color:#f56954"></i>午餐
						<i class="fa fa-square" style="color:#00a65a"></i>晚餐
					  </div>
					</div>
					<div class="box-body">
						<canvas id="pieChart" style="height:250px"></canvas>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
			  </div>
			  <div class="col-md-6">
				<!-- BAR CHART -->
				  <div class="box box-warning">
					<div class="box-header with-border">
					  <h3 class="box-title">学生消费统计柱状图(单位:元)</h3>
					  <div class="pull-right">				    
						<i class="fa fa-square" style="color:rgba(60,141,188,0.9)"></i>个人消费水平
						<i class="fa fa-square" style="color:#00a65a"></i>全校平均水平
					  </div>
					</div>
					<div class="box-body">
					  <div class="chart">
						<canvas id="barChart" style="height:250px"></canvas>
					  </div>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
			  </div>
			  <%} %>
			  <%} %>
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
	<!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
	<!-- my js -->
    <script src="dist/js/my.js"></script>
	
	<!-- Page script -->
	<%if(result!=null && studentPoint!=null){ %>
    <script>
     $(function () {        
    	 //Initialize Select2 Elements
        $(".select2").select2();
		
		$("#result").DataTable({
		  "bSort": false,
		  "bPaginage":false,
		  "sInfo":"",
          "lengthChange": false,
          "searching": false,
          "autoWidth": false,
		});
		
		var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
        var pieChart = new Chart(pieChartCanvas);
        var PieData = [
          {
            value: <%=studentPoint.getLunchTTL() %>,
            color: "#f56954",
            highlight: "#f56954",
            label: "午餐"
          },
          {
            value: <%=studentPoint.getSupperTTL() %>,
            color: "#00a65a",
            highlight: "#00a65a",
            label: "晚餐"
          }
        ];
        var pieOptions = {
          //Boolean - Whether we should show a stroke on each segment
          segmentShowStroke: false,
          //Number - Amount of animation steps
          animationSteps: 10,
          //String - Animation easing effect
          animationEasing: "easeOutBounce",
          //Boolean - Whether we animate the rotation of the Doughnut
          animateRotate: true,
          //Boolean - Whether we animate scaling the Doughnut from the centre
          animateScale: false,
          //Boolean - whether to make the chart responsive to window resizing
          responsive: true,
          // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
          maintainAspectRatio: true,
		  scaleShowLabels: true
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        pieChart.Pie(PieData, pieOptions);
		
		//-------------
        //- BAR CHART -
        //-------------
        var barChartCanvas = $("#barChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas);
        var barChartData = {
          labels: ["总平均值","午餐平均值","晚餐平均值"],
          datasets: [
            {
              label: "Electronics",
              fillColor: "rgba(60,141,188,0.9)",
              strokeColor: "rgba(60,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: [<%=studentPoint.getAverage()%>,<%=studentPoint.getLunchAVG()%>,<%=studentPoint.getSupperAVG()%>]
            },
            {
              label: "Digital Goods",
              fillColor: "rgba(210, 214, 222, 1)",
              strokeColor: "rgba(210, 214, 222, 1)",
              pointColor: "rgba(210, 214, 222, 1)",
              pointStrokeColor: "#c1c7d1",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(220,220,220,1)",
              data: [10,10,10]
            }
          ]
        };
        barChartData.datasets[1].fillColor = "#00a65a";
        barChartData.datasets[1].strokeColor = "#00a65a";
        barChartData.datasets[1].pointColor = "#00a65a";
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
        barChart.Bar(barChartData, barChartOptions);
		

      });
    </script>
    <%}%>
  </body>
</html>

<%} %>
