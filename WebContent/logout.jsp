<%@page import="com.sys.manager.bean.*" import="com.sys.manager.dao.*" import="com.wiscom.is.*, java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*" import="java.io.*"%>
<%
	String is_config = this.getServletContext().getRealPath("/client.properties");
	IdentityFactory factory = IdentityFactory.createFactory( is_config );
	IdentityManager im = factory.getIdentityManager();	
	Cookie[] cookies=request.getCookies();
	for(Cookie c : cookies)
	{
			c.setMaxAge(0);
			response.addCookie(c);
	}
	session.invalidate();
%>
<%
    String gotoURL ="";
    Properties prop=new Properties();
    String path = this.getServletContext().getRealPath("/indexUrl.properties");
    InputStream in=new BufferedInputStream(new FileInputStream(path));
    prop.load(in);
	gotoURL = prop.getProperty("index");
    String logoutURL = im.getLogoutURL() +"?goto=" + java.net.URLEncoder.encode(gotoURL,"UTF-8");
    
%>
<script>
  location="<%=logoutURL%>"
</script>