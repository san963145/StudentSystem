<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
  String department="无";
  String grade="无";
  String comments="无";
  if(user!=null){
  if(user.getDepartment()!=null)
  {
	  department=user.getDepartment();
  }
  if(user.getGrade()!=null)
  {
	  grade=user.getGrade();
  }
  if(user.getComments()!=null)
  {
	  comments=user.getComments();
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
    <title>用户及系统管理</title>
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
  <body class="skin-red-light sidebar-mini">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>用户及系统管理<span class="caret"></span></b>
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
		</div>
		
		<!-- sidebar: style can be found in sidebar.less -->
        <section id="sidebar" class="sidebar collapse in">
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="active">
              <a href="pages/manager/info.jsp">
                 <i class="fa fa-user"></i> <span>个人信息</span>
              </a>
            </li>
			<li class="admin dean">
              <a href="pages/manager/addUser.jsp">
                 <i class="fa fa-user-plus"></i> <span>添加用户</span>
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
				<small>个人信息</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li class="active"> 个人信息</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
			
			<!-- Horizontal Form -->
              <div class="box box-danger">
                <div class="box-header with-border">
                  <h3 class="box-title">账户资料</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <form class="form-horizontal">
                  <div class="box-body">
                    <div class="form-group">
                      <label for="inputAdmin" class="col-sm-2 col-md-2 col-md-offset-1 control-label">账号：</label>
                      <div class="col-sm-10 col-md-6">
                        <input type="text" class="form-control" id="inputAdmin" disabled="disabled" value=<%=curUser %>>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="inputCollege" class="col-sm-2 col-md-2 col-md-offset-1 control-label">学院：</label>
                      <div class="col-sm-10 col-md-6">
                        <input type="text" class="form-control" id="inputCollege" disabled="disabled" value=<%=department %>>
                      </div>
                    </div>
					<div class="form-group">
                      <label for="inputGrade" class="col-sm-2 col-md-2 col-md-offset-1 control-label">年级：</label>
                      <div class="col-sm-10 col-md-6">
                        <input type="text" class="form-control" id="inputGrade" disabled="disabled" value=<%=grade %>>
                      </div>
                    </div>
					<div class="form-group">
                      <label for="inputAuthority" class="col-sm-2 col-md-2 col-md-offset-1 control-label">权限：</label>
                      <div class="col-sm-10 col-md-6">
                        <input type="text" class="form-control" id="inputAuthority" disabled="disabled" value=<%=authority %>>
                      </div>
                    </div>
                    
                    <div class="form-group">
                      <label for="inputAuthority" class="col-sm-2 col-md-2 col-md-offset-1 control-label">备注：</label>
                      <div class="col-sm-10 col-md-6">
                        <input type="text" class="form-control" id="inputAuthority" disabled="disabled" value=<%=comments %>>
                      </div>
                    </div>
                    
                  </div><!-- /.box-body -->

                </form>
              </div><!-- /.box -->
			
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
	<!-- my js -->
    <script src="dist/js/my.js"></script>
  </body>
</html>

<%} %>
