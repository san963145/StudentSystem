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
   Map<String,ArrayList<CategoryNameNum>> networkMap=(Map<String,ArrayList<CategoryNameNum>>)request.getAttribute("networkMap");
   String major=(String)request.getAttribute("major");
   String grade=(String)request.getAttribute("grade");
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
  <style>
    .left{float:left;width:75%;height:100%}
    .right{float:right;width:25%;height:100%}
   </style><!-- 截止 -->
  
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

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
        	<h1>网络日志数据按学院细分</h1>
			<ol class="breadcrumb">
				<li class="active"><a href="index.jsp"><i class="fa fa-dashboard"></i> 主页</a></li>
				<li>网络日志</li>
				<%if(!"".equals(str)){ %>
				<%
				   String[] s=str.split("#");
				   for(int i=0;i<s.length;i++)
				   {
					   String item=(String)request.getAttribute(s[i]);
			    %>
			           <li><a class="str" id="<%=s[i]%>" href="javascript:networkGet(<%=i%>,'<%=s[i]%>')"><%=item%></a></li>
			    <% 
				   }
				%>
				<%} %>
				<%if(cur!=null){ 
					String[] s=str.split("#");
				%>
				<li><a class="str" id="<%=cur%>" href="javascript:networkGet(<%=s.length%>,'<%=cur%>')"><%=(String)request.getAttribute(cur)%></a></li>
				<%} %>
			    <li class="active">按学院划分</li>
			</ol>
		</section>
		<script>
         function getByMajor(department)
         {        	 
        	 var grade="";
        	 if(document.getElementById("grade"))
        		 grade=document.getElementById("grade").innerHTML;
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
        	 document.cookie='grade='+encodeURI(grade); 
        	 document.cookie='sex='+encodeURI(sex); 
        	 document.cookie='str='+encodeURI(str);    
        	 location="NetworkGroupByMajor?department="+encodeURI(encodeURI(department));
         }
         function getByGrade(department)
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
        	 location="NetworkGroupByGrade?department="+encodeURI(encodeURI(department));
         }
         function getBySex(department)
         {
        	 var grade="";
        	 if(document.getElementById("grade"))
        		 grade=document.getElementById("grade").innerHTML;     	
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<list.length;i++)
        	 {
        		 if(list[i].id)
        		  str+=(list[i].id+'#');
        	 }        	 
        	 document.cookie='grade='+encodeURI(grade); 
        	 document.cookie='str='+encodeURI(str);  
        	 location="NetworkGroupBySex?department="+encodeURI(encodeURI(department));
         }
        </script>
        <div class="row">
        <%
          if(networkMap!=null){
          Set<String>set=networkMap.keySet();
          Iterator it=set.iterator();
          int i=-1;
          while(it.hasNext()){
        	  String key=(String)it.next();
        	  i++;
        %>
        	<div class="col-md-12">	
        		<section class="content-header">
		          <h4><%=key %></h4>
		        </section>

		        <section class="content">
					<div class="col-md-6 col-md-offset-3">	
						<div class="box box-success">
				            <div class="box-header">
				              <h3 class="box-title"><small>最常访问的10类站点（单位：次）</small></h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">  	
				            	<div class="left">
									<canvas id="pieChart<%=i%>"></canvas>
								</div>
								<div class="right">
								<table style="align: center">
								    <tr>
									<th align="left" valign="top"><i id="color0<%=i%>" class="fa fa-square">&nbsp;</i></th>
									<td id="data0<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color1<%=i%>" class="fa fa-square"></i></th>
									<td id="data1<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color2<%=i%>" class="fa fa-square"></i></th>
									<td id="data2<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color3<%=i%>" class="fa fa-square"></i></th>
									<td id="data3<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color4<%=i%>" class="fa fa-square"></i></th>
									<td id="data4<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color5<%=i%>" class="fa fa-square"></i></th>
									<td id="data5<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color6<%=i%>" class="fa fa-square"></i></th>
									<td id="data6<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color7<%=i%>" class="fa fa-square"></i></th>
									<td id="data7<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color8<%=i%>" class="fa fa-square"></i></th>
									<td id="data8<%=i%>"></td></tr>
									<tr>
									<th align="left" valign="top"><i id="color9<%=i%>" class="fa fa-square"></i></th>
									<td id="data9<%=i%>"></td></tr>
									</table>
								</div>
				            </div>
				            <div class="box-footer">
				            	<div class="pull-right">
				                <div class="pull-right">
				                <a class="btn btn-info btn-sm" onclick="getByMajor('<%=key%>')"><small>按专业细分</small></a>
				                <%if(grade==null){ %>
				                <a class="btn btn-info btn-sm" onclick="getByGrade('<%=key%>')"><small>按年级细分</small></a>
				                <%} %>
				                <%if(sex==null){ %>
				                <a class="btn btn-info btn-sm" onclick="getBySex('<%=key%>')"><small>按性别细分</small></a>
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
    <%
    if(networkMap!=null){
    Set<String>set=networkMap.keySet();
    Iterator it=set.iterator();
    int i=-1;
    while(it.hasNext()){
  	  String key=(String)it.next();
  	  i++;
  	  ArrayList<CategoryNameNum>list=networkMap.get(key);
  %>
    var pieChartCanvas<%=i%> = $("#pieChart<%=i%>").get(0).getContext("2d");
    var pieChart<%=i%> = new Chart(pieChartCanvas<%=i%>);
    
    var PieData<%=i%> = [
      {
        value: <%=list.get(0).getNum()%>,
        color: "#f56954",
        highlight: "#f56954",
        label: <%="\""+list.get(0).getName()+"\""%>
      },
      {
        value: <%=list.get(1).getNum()%>,
        color: "#00a65a",
        highlight: "#00a65a",
        label: <%="\""+list.get(1).getName()+"\""%>
      },
      {
        value: <%=list.get(2).getNum()%>,
        color: "#f39c12",
        highlight: "#f39c12",
        label: <%="\""+list.get(2).getName()+"\""%>
      },
      {
        value: <%=list.get(3).getNum()%>,
        color: "#00c0ef",
        highlight: "#00c0ef",
        label: <%="\""+list.get(3).getName()+"\""%>
      },
      {
        value: <%=list.get(4).getNum()%>,
        color: "#3c8dbc",
        highlight: "#3c8dbc",
        label: <%="\""+list.get(4).getName()+"\""%>
      },
      {
        value: <%=list.get(5).getNum()%>,
        color: "#c2b690",
        highlight: "#c2b690",
        label: <%="\""+list.get(5).getName()+"\""%>
      },
      {
          value: <%=list.get(6).getNum()%>,
          color: "#959994",
          highlight: "#959994",
          label: <%="\""+list.get(6).getName()+"\""%>
       },
       {
           value: <%=list.get(7).getNum()%>,
           color: "#70b6ba",
           highlight: "#70b6ba",
           label: <%="\""+list.get(7).getName()+"\""%>
       },
       {
           value: <%=list.get(8).getNum()%>,
           color: "#b2d1b1",
           highlight: "#b2d1b1",
           label: <%="\""+list.get(8).getName()+"\""%>
       },
       {
           value: <%=list.get(9).getNum()%>,
           color: "#e1e2e3",
           highlight: "#e1e2e3",
           label: <%="\""+list.get(9).getName()+"\""%>
       }                    
    ];
    
    //Create pie or douhnut chart
    // You can switch between pie and douhnut using the method below.
    pieChart<%=i%>.Doughnut(PieData<%=i%>, pieOptions);
    
  //新增
    document.getElementById("color0<%=i%>").style.color=PieData<%=i%>[0].color;
    document.getElementById("color1<%=i%>").style.color=PieData<%=i%>[1].color;
    document.getElementById("color2<%=i%>").style.color=PieData<%=i%>[2].color;
    document.getElementById("color3<%=i%>").style.color=PieData<%=i%>[3].color;
    document.getElementById("color4<%=i%>").style.color=PieData<%=i%>[4].color;
    document.getElementById("color5<%=i%>").style.color=PieData<%=i%>[5].color;
    document.getElementById("color6<%=i%>").style.color=PieData<%=i%>[6].color;
    document.getElementById("color7<%=i%>").style.color=PieData<%=i%>[7].color;
    document.getElementById("color8<%=i%>").style.color=PieData<%=i%>[8].color;
    document.getElementById("color9<%=i%>").style.color=PieData<%=i%>[9].color;
    document.getElementById("data0<%=i%>").innerHTML=PieData<%=i%>[0].label;
 		document.getElementById("data1<%=i%>").innerHTML=PieData<%=i%>[1].label;
 		document.getElementById("data2<%=i%>").innerHTML=PieData<%=i%>[2].label;
 		document.getElementById("data3<%=i%>").innerHTML=PieData<%=i%>[3].label;
 		document.getElementById("data4<%=i%>").innerHTML=PieData<%=i%>[4].label;
 		document.getElementById("data5<%=i%>").innerHTML=PieData<%=i%>[5].label;
 		document.getElementById("data6<%=i%>").innerHTML=PieData<%=i%>[6].label;
 		document.getElementById("data7<%=i%>").innerHTML=PieData<%=i%>[7].label;
 		document.getElementById("data8<%=i%>").innerHTML=PieData<%=i%>[8].label;
 		document.getElementById("data9<%=i%>").innerHTML=PieData<%=i%>[9].label;//截止
    
    <%} %><!-- while -->
   <%} %><!-- if -->  

 
    </script>
	<!-- /2015年12月1日 添加的地方-->
  </body>
</html>
<%}%>