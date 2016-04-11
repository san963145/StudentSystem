<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page import="com.sys.manager.bean.*" import="com.sys.network.bean.*"%>
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
  String sno=(String)request.getAttribute("sno");
  List onlineList=(List)request.getAttribute("onlineList");
  List gameList=(List)request.getAttribute("gameList");
  List videoList=(List)request.getAttribute("videoList");
  ArrayList<CategoryNameNum> categoryList=(ArrayList<CategoryNameNum>)request.getAttribute("categoryList");
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
    
    <style>
    .left{float:left;width:75%;height:100%}
    .right{float:right;width:25%;height:100%}
   </style><!-- 截止 -->
    
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
				<small>上网特征</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/gradesSystem/gradesSystem.jsp"> 成绩预测</a></li>
			<li class="active"> 上网特征</li>
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
				  <h3 class="box-title">上网特征查询</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form class="form-horizontal" action="NetByPerson" method="post">
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
			</div>
			
			<%if(categoryList!=null){if(categoryList.size()>0){ %>
			  <%if(accessFlag.equals("1")){ %>
			<div class="col-md-6">
				 <!-- DONUT CHART -->
				  <div class="box box-warning">
					<div class="box-header with-border">
					  <h3 class="box-title">该学生(<%=sno %>)各月上网情况(单位:小时)</h3>
					  
					  <div class="pull-right">
						<i class="fa fa-square" style="color:rgba(210, 214, 222, 1)"></i>页面时间
						<i class="fa fa-square" style="color:#008d4c"></i>视频时间
						<i class="fa fa-square" style="color:rgba(100,141,188,0.9)"></i>游戏时间
					  </div>
					</div>
					<div class="box-body">
						<div class="chart">
							<canvas id="barChart" ></canvas>
							
						</div>
						
					</div><!-- /.box-body -->
					
				  </div><!-- /.box -->
				  <div id="legendDiv" class="pull-right"></div>
			  </div>
			  <div class="col-md-6">
				<!-- BAR CHART -->
				  <div class="box box-warning">
					<div class="box-header with-border">
					  <h3 class="box-title">最常访问的10类站点(单位:次)</h3>
					</div>
					<div class="box-body">
					  <!-- 修改-->
					<div class="left">
					  <canvas id="pieChart" height="200" ></canvas>
					</div>
					  <div class="right">
					  <table style="align: center">
					    <tr>
						<th align="left" valign="top"><i id="color0" class="fa fa-square">&nbsp;</i></th>
						<td id="data0"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color1" class="fa fa-square"></i></th>
						<td id="data1"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color2" class="fa fa-square"></i></th>
						<td id="data2"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color3" class="fa fa-square"></i></th>
						<td id="data3"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color4" class="fa fa-square"></i></th>
						<td id="data4"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color5" class="fa fa-square"></i></th>
						<td id="data5"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color6" class="fa fa-square"></i></th>
						<td id="data6"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color7" class="fa fa-square"></i></th>
						<td id="data7"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color8" class="fa fa-square"></i></th>
						<td id="data8"></td></tr>
						<tr>
						<th align="left" valign="top"><i id="color9" class="fa fa-square"></i></th>
						<td id="data9"></td></tr>
						</table>
					</div><!-- 截止 -->
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
			  </div>
			  <%}else{ %><h4><b>&nbsp;该学生不在访问权限内！</b></h4><%} %>
			  <%}else{%>
			<h4><b>&nbsp;&nbsp;&nbsp;无该项数据或学号不存在！</b></h4>
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
    <!-- FastClick 
    从点击屏幕上的元素到触发元素的 click 事件，移动浏览器会有大约 300 毫秒的等待时间。
    它想看看你是不是要进行双击（double tap）操作。-->
    <script src="plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
	<!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
	<!-- my js -->
    <script src="dist/js/my.js"></script>
	<!-- Page script -->
	<%if(categoryList!=null){if(categoryList.size()>0){ %> 
	<%
	   String labels="";
	   int i;
	   for(i=0;i<gameList.size();i++)
	   {
		   String s=((Object[])gameList.get(i))[0].toString();
		   StringBuilder st=new StringBuilder(s);
		   st.insert(4,"-");
		   s=new String(st);
		   labels+="\""+s+"\""+",";
	   }
	   labels=labels.substring(0,labels.length()-1);
	   String onlines="";
	   String games="";
	   String videos="";
	   for(i=0;i<gameList.size();i++)
	   {
		   onlines+=((Object[])onlineList.get(i))[1].toString()+",";
	   }
	   onlines=onlines.substring(0,onlines.length()-1);
	   for(i=0;i<gameList.size();i++)
	   {
		   games+=((Object[])gameList.get(i))[1].toString()+",";
	   }
	   games=games.substring(0,games.length()-1);
	   for(i=0;i<videoList.size();i++)
	   {
		   videos+=((Object[])videoList.get(i))[1].toString()+",";
	   }
	   videos=videos.substring(0,videos.length()-1);
	%>
    <script>
     $(function () {
		//-------------
        //- PIE CHART -
        //-------------
        // Get context with jQuery - using jQuery's .get() method.
        var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
        var pieChart = new Chart(pieChartCanvas);
        var PieData = [
{
    value: <%=categoryList.get(0).getNum()%>,
    color: "#f56954",
    highlight: "#f56954",
    label: <%="\""+categoryList.get(0).getName()+"\""%>
  },
  {
    value: <%=categoryList.get(1).getNum()%>,
    color: "#00a65a",
    highlight: "#00a65a",
    label: <%="\""+categoryList.get(1).getName()+"\""%>
  },
  {
    value: <%=categoryList.get(2).getNum()%>,
    color: "#f39c12",
    highlight: "#f39c12",
    label: <%="\""+categoryList.get(2).getName()+"\""%>
  },
  {
    value: <%=categoryList.get(3).getNum()%>,
    color: "#00c0ef",
    highlight: "#00c0ef",
    label: <%="\""+categoryList.get(3).getName()+"\""%>
  },
  {
    value: <%=categoryList.get(4).getNum()%>,
    color: "#3c8dbc",
    highlight: "#3c8dbc",
    label: <%="\""+categoryList.get(4).getName()+"\""%>
  },
  {
    value: <%=categoryList.get(5).getNum()%>,
    color: "#d2d6de",
    highlight: "#d2d6de",
    label: <%="\""+categoryList.get(5).getName()+"\""%>
  },
  {
      value: <%=categoryList.get(6).getNum()%>,
      color: "#a2d6de",
      highlight: "#a2d6de",
      label: <%="\""+categoryList.get(6).getName()+"\""%>
   },
   {
       value: <%=categoryList.get(7).getNum()%>,
       color: "#c2d6de",
       highlight: "#c2d6de",
       label: <%="\""+categoryList.get(7).getName()+"\""%>
   },
   {
       value: <%=categoryList.get(8).getNum()%>,
       color: "#g2d6de",
       highlight: "#g2d6de",
       label: <%="\""+categoryList.get(8).getName()+"\""%>
   },
   {
       value: <%=categoryList.get(9).getNum()%>,
       color: "#d2d6ae",
       highlight: "#d2d6ae",
       label: <%="\""+categoryList.get(9).getName()+"\""%>
   }     
        ];
        var pieOptions = {
          //Boolean - Whether we should show a stroke on each segment
          segmentShowStroke: true,
          //String - The colour of each segment stroke
          segmentStrokeColor: "#fff",
          //Number - The width of each segment stroke
          segmentStrokeWidth: 2,
          //Number - The percentage of the chart that we cut out of the middle
          percentageInnerCutout: 50, // This is 0 for Pie charts
          //Number - Amount of animation steps
          animationSteps: 100,
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
        };
        //Create pie or douhnut chart
        // You can switch between pie and douhnut using the method below.
        pieChart.Doughnut(PieData, pieOptions);
		
		//-------------
        //- BAR CHART -
        //-------------
        var barChartCanvas = $("#barChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas);
        var barChartData = {
          labels: [<%=labels%>],
          datasets: [
            {
              label: "网页时间",
              fillColor: "rgba(210, 214, 222, 1)",
              strokeColor: "rgba(210, 214, 222, 1)",
              pointColor: "rgba(210, 214, 222, 1)",
              pointStrokeColor: "#c1c7d1",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(220,220,220,1)",
              data: [<%=onlines%>]
            },
            {
              label: "视频时间",
              fillColor: "rgba(60,141,188,0.9)",
              strokeColor: "rgba(60,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: [<%=videos%>]
            },
			{
              label: "游戏时间",
              fillColor: "rgba(100,141,188,0.9)",
              strokeColor: "rgba(100,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: [<%=games %>]
            }
          ]
        };
        barChartData.datasets[1].fillColor = "#00a65a";
        barChartData.datasets[1].strokeColor = "#00a65a";
        barChartData.datasets[1].pointColor = "#00a65a";
        var barChartOptions = {
          //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
          scaleBeginAtZero: false,
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
		 //legendTemplate: function(data) { return "<ul>LEGEND</ul>"; },
          maintainAspectRatio: true
        };

        barChartOptions.datasetFill = false;
		//document.getElementById("legendDiv").innerHTML = barChart.Line(barChartData).generateLegend();
        barChart.Bar(barChartData, barChartOptions);
		
      //新增
        document.getElementById("color0").style.color=PieData[0].color;
        document.getElementById("color1").style.color=PieData[1].color;
        document.getElementById("color2").style.color=PieData[2].color;
        document.getElementById("color3").style.color=PieData[3].color;
        document.getElementById("color4").style.color=PieData[4].color;
        document.getElementById("color5").style.color=PieData[5].color;
        document.getElementById("color6").style.color=PieData[6].color;
        document.getElementById("color7").style.color=PieData[7].color;
        document.getElementById("color8").style.color=PieData[8].color;
        document.getElementById("color9").style.color=PieData[9].color;
        document.getElementById("data0").innerHTML=PieData[0].label;
   		document.getElementById("data1").innerHTML=PieData[1].label;
   		document.getElementById("data2").innerHTML=PieData[2].label;
   		document.getElementById("data3").innerHTML=PieData[3].label;
   		document.getElementById("data4").innerHTML=PieData[4].label;
   		document.getElementById("data5").innerHTML=PieData[5].label;
   		document.getElementById("data6").innerHTML=PieData[6].label;
   		document.getElementById("data7").innerHTML=PieData[7].label;
   		document.getElementById("data8").innerHTML=PieData[8].label;
   		document.getElementById("data9").innerHTML=PieData[9].label;//截止
      });
   
    </script>
    <%}} %>
  </body>
</html>

<%} %>
