<%@page import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page import="com.sys.manager.bean.*" import="com.sys.grades.dao.*" import="com.sys.card.dao.*" import="com.sys.network.bean.*"%>
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
  List<String>departmentList=(List<String>)DepartmentPointDao.getDepartmentsByUser(user);
  String department=(String)request.getAttribute("department");
  String major=(String)request.getAttribute("major");
  String grade=(String)request.getAttribute("grade");
  String sex=(String)request.getAttribute("sex");
  Double onlineTime_School=(Double)request.getAttribute("onlineTime_School");
  Double gameTime_School=(Double)request.getAttribute("gameTime_School");
  Double videoTime_School=(Double)request.getAttribute("videoTime_School");
  Double onlineTime_grade=(Double)request.getAttribute("onlineTime_grade");
  Double gameTime_grade=(Double)request.getAttribute("gameTime_grade");
  Double videoTime_grade=(Double)request.getAttribute("videoTime_grade");
  ArrayList<CategoryNameNum> categoryList=(ArrayList<CategoryNameNum>)request.getAttribute("categoryList");
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
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  
  <style>
    .left{float:left;width:75%;height:100%}
    .right{float:right;width:25%;height:100%}
   </style><!-- 截止 -->
  
<script charset="UTF-8">
   var x=null;
   var y=null;
   var z=null;
   function init()
   {
	getDepartment();
	updateSex();    	
   }
	function sendDepartment(department)
	{
		if(department!="选择学院")
		{
		  if(window.XMLHttpRequest)
		  {
		    x=new XMLHttpRequest();
		  }
		  else
	      {
			x=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  x.open("GET","SelectorByDMG?department="+encodeURI(encodeURI(department)),true);
		  x.onreadystatechange=update;
		  x.send();
		}
		else
		{
			document.getElementById("majors").options.length=0;
			var all=document.createElement("option");
			all.setAttribute("selected","selected");
			all.innerHTML="全部";
			document.getElementById("majors").add(all);
			document.getElementById("grades").options.length=0;
			var all2=document.createElement("option");
			all2.setAttribute("selected","selected");
			all2.innerHTML="全部";
			document.getElementById("grades").add(all2);
		}
	}
	function getDepartment()
	{
		  if(window.XMLHttpRequest)
		  {
		    x=new XMLHttpRequest();
		  }
		  else
	      {
			x=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  x.open("GET","SelectorDepartment",true);
		  x.onreadystatechange=updateDepartment;
		  x.send();

	}
	function getMajor(department)
	{
		  if(window.XMLHttpRequest)
		  {
		    y=new XMLHttpRequest();
		  }
		  else
	      {
			y=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  y.open("GET","SelectorMajor?department="+encodeURI(encodeURI(department)),true);
		  y.onreadystatechange=updateMajor;
		  y.send();

	}
	function getGrade(department)
	{
		  if(window.XMLHttpRequest)
		  {
		    z=new XMLHttpRequest();
		  }
		  else
	      {
			z=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  z.open("GET","SelectorGrade?department="+encodeURI(encodeURI(department)),true);
		  z.onreadystatechange=updateGrade;
		  z.send();

	}
	function updateDepartment()
	{
		if(x.readyState==4 && x.status==200)
		{
			var selectedItem=document.getElementById("departments").value;
			document.getElementById("departments").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="选择学院")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="选择学院";
			document.getElementById("departments").add(all);
			var departments=new Array();
			departments=x.responseText.split("#");
			var i=0;
			var opt;
			for(i=0;i<departments.length;i++)
			{
				if(departments[i]=="")
				{
					document.getElementById("departments").options.length=0;
					opt=document.createElement("option");
					opt.innerHTML="无对应数据";
					document.getElementById("departments").add(opt);
					continue;
				}
				opt=document.createElement("option");
				if(selectedItem==departments[i])
				{
					opt.setAttribute("selected","selected");
				}
				opt.innerHTML=departments[i];
				document.getElementById("departments").add(opt);
			}
		}
		var department=document.getElementById("departments").value;
    	if(department!="选择学院")
    	{
    		getMajor(department);       	
    	}
	}
	function updateMajor()
	{

		if(y.readyState==4 && y.status==200)
		{
			var selectedItem=document.getElementById("majors").value;
			document.getElementById("majors").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="全部")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="全部";
			document.getElementById("majors").add(all);
			var majors=new Array();
			majors=y.responseText.split("#");
			var i=0;
			var opt;
			for(i=0;i<majors.length;i++)
			{
				if(majors[i]=="")
				{
					document.getElementById("majors").options.length=0;
					opt=document.createElement("option");
					opt.innerHTML="无对应数据";
					document.getElementById("majors").add(opt);
					continue;
				}
				opt=document.createElement("option");
				if(selectedItem==majors[i])
				{
					opt.setAttribute("selected","selected");
				}
				opt.innerHTML=majors[i];
				document.getElementById("majors").add(opt);
			}
		}
		var department=document.getElementById("departments").value;
		if(department!="选择学院")
    	{
    		getGrade(department);       	
    	}
	}
	function updateGrade()
	{

		if(z.readyState==4 && z.status==200)
		{
			var selectedItem=document.getElementById("grades").value;
			document.getElementById("grades").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="全部")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="全部";
			document.getElementById("grades").add(all);
			var grades=new Array();
			grades=z.responseText.split("#");
			var i=0;
			var opt;
			for(i=0;i<grades.length;i++)
			{
				if(grades[i]=="")
				{
					document.getElementById("grades").options.length=0;
					opt=document.createElement("option");
					opt.innerHTML="无对应数据";
					document.getElementById("grades").add(opt);
					continue;
				}
				opt=document.createElement("option");
				if(selectedItem==grades[i])
				{
					opt.setAttribute("selected","selected");
				}
				opt.innerHTML=grades[i];
				document.getElementById("grades").add(opt);
			}
		}
	}
	
	function updateSex()
	{
			var selectedItem=document.getElementById("sex").value;
			document.getElementById("sex").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="全部")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="全部";
			document.getElementById("sex").add(all);
			
			var male=document.createElement("option");
			if(selectedItem=="男")
			{
				male.setAttribute("selected","selected");
			}			
			male.innerHTML="男";
			document.getElementById("sex").add(male);
			
			var female=document.createElement("option");
			if(selectedItem=="女")
			{
				female.setAttribute("selected","selected");
			}			
			female.innerHTML="女";
			document.getElementById("sex").add(female);
			
	}
	function update()
	{

		if(x.readyState==4 && x.status==200)
		{
			document.getElementById("majors").options.length=0;
			var all=document.createElement("option");
			all.setAttribute("selected","selected");
			all.innerHTML="全部";
			document.getElementById("majors").add(all);
			document.getElementById("grades").options.length=0;
			var all2=document.createElement("option");
			all2.setAttribute("selected","selected");
			all2.innerHTML="全部";
			document.getElementById("grades").add(all2);
			var array=new Array();
			var majors=new Array();
			var grades=new Array();
			array=x.responseText.split("|");
			majors=array[0].split("#");
			grades=array[1].split("#");
			var i=0;
			var opt;
			for(i=0;i<majors.length;i++)
			{
				if(majors[i]=="")
				{
					document.getElementById("majors").options.length=0;
					opt=document.createElement("option");
					opt.innerHTML="无对应数据";
					document.getElementById("majors").add(opt);
					continue;
				}
				opt=document.createElement("option");
				opt.innerHTML=majors[i];
				document.getElementById("majors").add(opt);
			}
			for(i=0;i<grades.length;i++)
			{
				if(grades[i]=="")
				{
					document.getElementById("grades").options.length=0;
					opt=document.createElement("option");
					opt.innerHTML="无对应数据";
					document.getElementById("grades").add(opt);
					continue;
				}
				opt=document.createElement("option");
				opt.innerHTML=grades[i];
				document.getElementById("grades").add(opt);
			}
		}
	}
    
	function check()
	{
		var d=document.getElementById("departments").value;
		var m=document.getElementById("majors").value;
		var g=document.getElementById("grades").value;
		if(d=="选择学院")
	    {
			alert("请选择学院!");
			return false;
	    }
		else if(g=="无对应数据")
		{
			alert("请重新选择!");
			return false;
		}
		else if(m=="无对应数据")
		{
			alert("请重新选择!");
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
  </script>
  
  <body class="skin-green-light sidebar-mini" onload="init()">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>网络日志分析系统&nbsp;&nbsp;<img src="img/arrow.gif" height="10" width="10"/></b>
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
                <i class="fa fa-list-ul"></i> 
				<span>网站分类</span> 
				<i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="Category"><i class="fa fa-circle-o"></i> 分层浏览</a></li>
                <li><a href="pages/network/queryCategory.jsp"><i class="fa fa-circle-o"></i> 按类别查询</a></li>
                <li><a href="pages/network/queryCategoryByURL.jsp"><i class="fa fa-circle-o"></i> 按网址查询</a></li>
              </ul>
            </li>
			<li>
              <a href="pages/network/accessReport.jsp">
                 <i class="fa fa-edit"></i> <span>网络访问报告</span>
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-pie-chart"></i>
                <span>个人网络数据统计</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="pages/network/searchByPerson.jsp"><i class="fa fa-circle-o"></i> 按学号查询</a></li>
				<li><a href="pages/network/searchByGroup.jsp"><i class="fa fa-circle-o"></i> 按条件查询</a></li>
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
				
				<small>网络访问报告</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/network/network.jsp" > 网络日志</a></li>
			<li class="active"> 网络访问报告</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
			
			<div class="col-md-12">
			<!-- SELECT2 EXAMPLE -->
			  <div class="box box-success">
				<div class="box-header with-border">
				  <h3 class="box-title">查询选项</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form action="AccessReport" method="post" onsubmit="return check()">
						<div class="row">
							<div class="col-md-3">
							  <div class="form-group">
								<label>学院</label>
								<select class="form-control select2" id="departments" style="width: 100%;" name="department" onchange="sendDepartment(this.value)" onclick="sendDepartment(this.value)" >
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
								<select class="form-control select2" id="majors" name="major" style="width: 100%;" >
								
								 <%if(major==null){ %>
								   <option selected="selected">全部</option>
								   <%}else{ %>
								   <option selected="selected"><%=major %></option>
								   <%} %>
								   
								</select>
								
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-3">
							  <div class="form-group">
								<label>年级</label>
								<select class="form-control select2" id="grades" name="grade" style="width: 100%;" >
								  
								  <%if(grade==null){ %>
								   <option selected="selected">全部</option>
								   <%}else{ %>
								   <option selected="selected"><%=grade %></option>
								   <%} %>
                                 
								</select>
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							<div class="col-md-1">
							  <label>性别</label>
							  <select class="form-control" style="width: 100%;" name="sex" id="sex">
							      <%if(sex==null){ %>
								   <option selected="selected">全部</option>
								   <%}else{ %>
								   <option selected="selected"><%=sex %></option>
								   <%} %>
								   
							  </select>
							</div><!-- /.col -->
							<div class="col-md-2">
							  <label>&nbsp;</label>
							  <button type="submit" class="btn btn-success btn-block btn-flat">查询</button>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</form>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			
			<%if(categoryList!=null){if(categoryList.size()>0){ %>
			<div class="col-md-6">
				 <!-- DONUT CHART -->
				  <div class="box box-success">
					<div class="box-header with-border">

					  <h3 class="box-title">该范围平均上网图(单位:小时)</h3>
					  <div class="pull-right">
						<i class="fa fa-square" style="color:rgba(210, 214, 222, 1)"></i>该学院平均值
						<i class="fa fa-square" style="color:#00a65a"></i>校平均值
					  </div>
					</div>
					<div class="box-body">
						<div class="chart">
							<canvas id="barChart" ></canvas>
						</div>
					</div><!-- /.box-body -->
				  </div><!-- /.box -->
			  </div>
			  <div class="col-md-6">
				<!-- BAR CHART -->
				  <div class="box box-success">
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
	<!-- ChartJS 1.0.1 -->
    <script src="plugins/chartjs/Chart.min.js"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="dist/js/demo.js"></script>
	<!-- my js -->
    <script src="dist/js/my.js"></script>
	<!-- Page script -->
	<%if(categoryList!=null){if(categoryList.size()>0){ %>
    <script>
     $(function () {
        //Initialize Select2 Elements
        //$(".select2").select2();
		
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
            color: "#5500de",
            highlight: "#5500de",
            label: <%="\""+categoryList.get(5).getName()+"\""%>
          },
          {
              value: <%=categoryList.get(6).getNum()%>,
              color: "#11d606",
              highlight: "#11d606",
              label: <%="\""+categoryList.get(6).getName()+"\""%>
           },
           {
               value: <%=categoryList.get(7).getNum()%>,
               color: "#c2006d",
               highlight: "#c2006d",
               label: <%="\""+categoryList.get(7).getName()+"\""%>
           },
           {
               value: <%=categoryList.get(8).getNum()%>,
               color: "#e2d600",
               highlight: "#e2d600",
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
		
		//-------------
        //- BAR CHART -
        //-------------
        var barChartCanvas = $("#barChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas);
        var barChartData = {
          labels: ["上网总时间", "视频时间", "游戏时间"],
          datasets: [
            {
              label: "该学院平均值",
              fillColor: "rgba(210, 214, 222, 1)",
              strokeColor: "rgba(210, 214, 222, 1)",
              pointColor: "rgba(210, 214, 222, 1)",
              pointStrokeColor: "#c1c7d1",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(220,220,220,1)",
              data: [<%=onlineTime_grade.doubleValue()%>, <%=videoTime_grade.doubleValue()%>, <%=gameTime_grade.doubleValue()%>]
            },
            {
              label: "校平均值",
              fillColor: "rgba(60,141,188,0.9)",
              strokeColor: "rgba(60,141,188,0.8)",
              pointColor: "#3b8bba",
              pointStrokeColor: "rgba(60,141,188,1)",
              pointHighlightFill: "#fff",
              pointHighlightStroke: "rgba(60,141,188,1)",
              data: [<%=onlineTime_School.doubleValue()%>, <%=videoTime_School.doubleValue()%>, <%=gameTime_School.doubleValue()%>]
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
