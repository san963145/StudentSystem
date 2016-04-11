<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.index.dao.*" import="com.sys.network.servlet.AccessReport" import="com.sys.network.bean.*" import="com.wiscom.is.*, java.net.*"%>
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
<jsp:forward page="error.jsp"/>	
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
		}
	}
  }
%>

<% if(curUser.length()!=0){%>
<%
  if(AccessReport.indexUrlPath==null)
  {
   String path = this.getServletContext().getRealPath("/indexUrl.properties");
   AccessReport.indexUrlPath=path;
  }
   ArrayList<CategoryNameNum> categoryList=NetworkDao.get();
   List cardList=CardDao.get();
   List gradesList=GradesDao.get();
%>
<!DOCTYPE html>
<html>
  <head>
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

    <!-- bootstrap wysihtml5 - text editor 文本格式编辑框-->
    <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->



  </head>
<style>
    .left{float:left;width:75%;height:100%}
    .right{float:right;width:25%;height:100%}
   </style><!-- 截止 -->
  
  <body class="skin-black-light sidebar-mini" >
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>学生行为分析平台<span class="caret"></span></b>
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

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
			<ol class="breadcrumb">
				<li class="active"><a href="index.jsp"><i class="fa fa-dashboard"></i> 主页</a></li>
			</ol>
		</section>
        <div class="row">
        	<div class="col-md-12">	
        		<section class="content-header">
		          <h1>一卡通数据概况</h1>
		        </section>

		        <section class="content">
					<div class="col-md-6 col-md-offset-3">	
						<div class="box box-info">
				            <div class="box-header">
				              <h3 class="box-title"><small>全校学生消费均值（单位：元）</small></h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				            	<canvas id="barChart"></canvas>
				            </div>
				            <div class="box-footer">
				            	<div class="pull-right">
				                <a href="CardGroupByCollege" class="btn btn-info btn-sm"><small>按学院细分</small></a>
				                <a href="CardGroupByGrade" class="btn btn-info btn-sm"><small>按年级细分</small></a>
				                <a href="CardGroupBySex" class="btn btn-info btn-sm"><small>按性别细分</small></a>
				            	</div>
				             </div>
			            </div>
			            <!-- /.box -->
					</div>
		        </section><!-- /.content -->
		    </div>

		    <div class="col-md-12">	
        		<section class="content-header">
		          <h1>网络日志数据概况</h1>
		        </section>

		        <section class="content">
					<div class="col-md-6 col-md-offset-3">	
						<div class="box box-success">
				            <div class="box-header">
				              <h3 class="box-title"><small>最常访问的10类站点（单位：次）</small></h3>
				            </div>
				            <!-- /.box-header -->
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
				            <div class="box-footer">
				            	<div class="pull-right">
				                <a href="NetworkGroupByCollege" class="btn btn-success btn-sm"><small>按学院细分</small></a>
				                <a href="NetworkGroupByGrade" class="btn btn-success btn-sm"><small>按年级细分</small></a>
				                <a href="NetworkGroupBySex" class="btn btn-success btn-sm"><small>按性别细分</small></a>
				            	</div>
				             </div>
			            </div>
			            <!-- /.box -->
					</div>
		        </section><!-- /.content -->
		    </div>

		    <div class="col-md-12">	
        		<section class="content-header">
		          <h1>成绩数据概况</h1>
		        </section>

		        <section class="content">
					<div class="col-md-6 col-md-offset-3">	
						<div class="box box-warning">
				            <div class="box-header">
				              <h3 class="box-title"><small>全校学生平均GPA</small></h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				            	<canvas id="lineChart"></canvas>
				            </div>
				            <div class="box-footer">
				            	<div class="pull-right">
				                <a href="GradesGroupByCollege" class="btn btn-warning btn-sm"><small>按学院细分</small></a>
				                <a href="GradesGroupByGrade" class="btn btn-warning btn-sm"><small>按年级细分</small></a>
				                <a href="GradesGroupBySex" class="btn btn-warning btn-sm"><small>按性别细分</small></a>
				            	</div>
				             </div>
			            </div>
			            <!-- /.box -->
					</div>
		        </section><!-- /.content -->
		    </div>
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
    <%if(cardList!=null){if(cardList.size()>0){ %>
    <%
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
    <!-- Page script -->
    <script>
    	//一卡通数据概况
		var bardata={
			labels : ["午餐均值","晚餐均值","总消费均值"],
			datasets : [
				{
					fillColor : "#0099CC",
					strokeColor : "#0099CC",
					data : [<%=((Object[])cardList.get(0))[0].toString()%>,<%=((Object[])cardList.get(0))[1].toString()%>,<%=((Object[])cardList.get(0))[2].toString()%>]
				},
			]
		};
		var barOptions = {
          responsive: true,
        };
        var barChartCanvas = document.getElementById("barChart").getContext("2d");
        var barChart = new Chart(barChartCanvas).Bar(bardata,barOptions);

        //网络日志数据概况
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
            color: "#c2b690",
            highlight: "#c2b690",
            label: <%="\""+categoryList.get(5).getName()+"\""%>
          },
          {
              value: <%=categoryList.get(6).getNum()%>,
              color: "#959994",
              highlight: "#959994",
              label: <%="\""+categoryList.get(6).getName()+"\""%>
           },
           {
               value: <%=categoryList.get(7).getNum()%>,
               color: "#70b6ba",
               highlight: "#70b6ba",
               label: <%="\""+categoryList.get(7).getName()+"\""%>
           },
           {
               value: <%=categoryList.get(8).getNum()%>,
               color: "#b2d1b1",
               highlight: "#b2d1b1",
               label: <%="\""+categoryList.get(8).getName()+"\""%>
           },
           {
               value: <%=categoryList.get(9).getNum()%>,
               color: "#e1e2e3",
               highlight: "#e1e2e3",
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
        
       
        //成绩数据概况
        var linedata = {
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
		var lineChartCanvas = document.getElementById("lineChart").getContext("2d");
        var lineChart = new Chart(lineChartCanvas).Line(linedata,lineChartOptions);
        
        
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

    </script>
	<!-- /2015年12月1日 添加的地方-->
	<%} }%>
  </body>
</html>
<%} %>
