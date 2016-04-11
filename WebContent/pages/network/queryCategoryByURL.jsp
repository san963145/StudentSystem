<%@page import="com.sys.manager.dao.*" import="com.sys.network.servlet.AccessReport" import="com.wiscom.is.*, java.net.*"%>
<%@ page import="com.sys.manager.bean.*" import="com.sys.network.dao.*"%>
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
  List list=(List)request.getAttribute("list");
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
  <script charset="UTF-8">   
	function check()
	{
		var d=document.getElementById("text").value;		
		if(d=="")
	    {
			alert("请输入网址!");
			return false;
	    }
		else
		{
			return true;
		}
	}
	
	
  </script>
  
  <body class="skin-green-light sidebar-mini">
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
				
				<small>网站分类</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="index.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li><a href="pages/network/network.jsp" > 网络日志</a></li>
			<li><a href="#" > 网站分类</a></li>
			<li class="active"> 查询网站分类</li>
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
				  <h3 class="box-title">网站查询</h3>
				  <div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				  </div>
				</div><!-- /.box-header -->
				<div class="box-body">
					<form class="form-horizontal" action="CategoryByURL" method="post" onsubmit="return check()">
						<div class="row">
							<div class="col-md-4">
							    <div class="form-group">
								  <label for="studentID" class="col-sm-2 control-label">网址</label>
								  <div class="col-sm-10">
									<input type="text" class="form-control" id="text" name="text" placeholder="输入网址">
								  </div>
								</div><!-- /.form-group -->
							</div><!-- /.col -->
							
							<div class="col-md-2 col-md-offset-5">
							  <button type="submit" class="btn btn-success btn-block btn-flat">查询</button>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</form>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			
			<%if(list!=null){if(list.size()>0){ %>
			<div class="col-md-12">
			<div class="box box-success">
				<div class="box-header with-border">
				  <h3 class="box-title">查询结果</h3>
				</div><!-- /.box-header -->
				<div class="box-body">
				  <div class="col-md-12">
					<table id="result" class="table table-bordered table-hover">
						<thead>
						  <tr>
							<th>网址</th>
							<th>类别分支</th>
							<th>访问次数</th>
							<!--<th>访问率</th>-->
						  </tr>
						</thead>
						<tbody>
						   <%if(list.size()>0){
							   Iterator t=list.iterator();
							   while(t.hasNext()){
								   Object[] obj=(Object[])t.next();%>  
							   <tr>
							     <td><%=obj[0].toString()%></td>
							     <td><%=obj[1].toString()%></td>
							     <td><%=obj[2].toString()%></td>
							   </tr>
							   <%} %>
						    <%} %>
														
						</tbody>
					</table>
				  </div>
				</div><!-- /.box-body -->
			  </div><!-- /.box -->
			</div>
			  <%}else{ %>
			  <h4><b>&nbsp;&nbsp;&nbsp;不存在该项数据！</b></h4>
				  <%}} %>
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
	<!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
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
	<!-- Page script -->
    <script>
     $(function () {
        //Initialize Select2 Elements
		
		//Initialize datatables
		$("#result").DataTable({
			"aLengthMenu": [[5,10,20,50, -1], [5, 10,20,50,"全部"]],
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
		});		
      });
    </script>
  </body>
</html>

<%} %>
