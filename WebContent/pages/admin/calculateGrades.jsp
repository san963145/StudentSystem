<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Admin admin=(Admin)session.getAttribute("admin");
    String adminName="";
    if(admin==null)
    {
    	response.sendRedirect("../../adminLogin.jsp");
    }
    else
    {
    	adminName=admin.getUserName();
    }
%>  
<%
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
    
    <script>
					var x=null;
					var step=0;
					var flag=true;
					function send()
					{						
                        if(flag)
                        {
                          step=0;
                          document.getElementById("textarea").innerHTML="";
                          flag=false;
						  if(window.XMLHttpRequest)
						  {
						    x=new XMLHttpRequest();
						  }
						  else
					      {
							x=new ActiveXObject("Microsoft.XMLHTTP");
					      }
						  x.open("GET","CalculateGrades?step=0",true);
						  x.onreadystatechange=update;
						  x.send();
                        }
                        else
                        {
                        	alert("正在进行计算，请等待计算完成。");
                        }
					}
					//添加
					function sleep(numberMillis) 
					{
					  var now = new Date();
					  var exitTime = now.getTime() + numberMillis;
					  while (true) 
					  {
					    now = new Date();
					    if (now.getTime() > exitTime)
					      return;
					  }
					}
					//
					function update()
					{

						if(x.readyState==4 && x.status==200)
						{
							//添加
							if(x.responseText=="0")      
							{
								//document.getElementById("textarea").innerHTML+=x.responseText;
								sleep(1000);
								x.open("GET","CalculateGrades?step="+step,true);
								x.onreadystatechange=update;
								x.send();
								return ;
							}
							//
							step++;
							document.getElementById("textarea").innerHTML+=x.responseText;
							if(step==7)
							{
								flag=true;
							}
							if(step==1)
						    {
							 x.open("GET","CalculateGrades?step=1",true);
							 x.onreadystatechange=update;
							 x.send();
						    }
							else if(step==2)
						    {
								x.open("GET","CalculateGrades?step=2",true);
								x.onreadystatechange=update;
								x.send();
						    }
							else if(step==3)
						    {
								x.open("GET","CalculateGrades?step=3",true);
								x.onreadystatechange=update;
								x.send();
						    }
							else if(step==4)
						    {
								x.open("GET","CalculateGrades?step=4",true);
								x.onreadystatechange=update;
								x.send();
						    }
							else if(step==5)
						    {
								x.open("GET","CalculateGrades?step=5",true);
								x.onreadystatechange=update;
								x.send();
						    }
							else if(step==6)
						    {
								x.open("GET","CalculateGrades?step=6",true);
								x.onreadystatechange=update;
								x.send();
						    }
						}
					}
					</script>
    
  </head>
  <body class="skin-red-light sidebar-mini">
    <div class="wrapper">

      <header class=" main-header" id="my-menu">
        <!-- Logo -->
		<a class="logo" href="#systems" data-toggle="collapse" data-parent="#my-menu">
			<b>系统计算<span class="caret"></span></b>
		</a>
		
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle visible-xs" data-toggle="offcanvas" role="button">
            <span class="sr-only"><%=adminName %></span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
			  <li>
			    <a href="Logout"><i class="fa fa-mail-forward"></i></a>
			  </li>
              
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
	  
		
		<!-- sidebar: style can be found in sidebar.less -->
        <section id="sidebar" class="sidebar collapse in">
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li>
              <a href="pages/admin/calculateCard.jsp">
                 <i class="fa fa-pie-chart"></i> <span>一卡通系统计算</span>
              </a>
            </li>
			<li >
              <a href="pages/admin/calculateGrades.jsp">
                 <i class="fa fa-pie-chart"></i> <span>成绩预测系统计算</span>
              </a>
            </li>
            <li >
              <a href="pages/admin/calculateNet.jsp">
                 <i class="fa fa-pie-chart"></i> <span>网络日志系统计算</span>
              </a>
            </li>
            <li >
              <a href="pages/admin/config.jsp">
                 <i class="fa fa-user"></i> <span>系统安全设置</span>
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
				<small>成绩统计</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="pages/admin/calculate.jsp" ><i class="fa fa-dashboard"></i> 主页</a></li>
			<li class="active"> 系统计算</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Main row -->
          <div class="row">
			
			<div class="col-md-12">
				<div class="box box-danger">
					<div class="box-header with-border">
					  <h3 class="box-title">系统计算</h3>
					</div><!-- /.box-header -->
					<div class="box-body">
					  <!--from-->
					
					    <div class="row">
							<div class="col-md-8">
							  <div class="form-group">
								<label >计算进度</label>
								<textarea  id="textarea" class="form-control"  rows="12"></textarea>
							  </div><!-- /.form-group -->
							</div><!-- /.col -->
							
							<div class="col-md-2 col-md-offset-1">
							  <label>&nbsp;</label>
							  <button onclick="send()" class="btn btn-danger btn-block btn-flat">开始计算</button>
							</div><!-- /.col -->
							
							<div class="col-md-3 col-md-offset-1">
							  <label>&nbsp;</label>
							  <h4><b>操作注意事项：</b></h4>
							<h4>计算过程中不能离开此页面，不能刷新页面。</h4>
							</div><!-- /.col -->
							
						</div><!-- /.row -->
					  
					</div><!-- /.box-body -->
				</div><!-- /.box -->
			</div>
			
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
