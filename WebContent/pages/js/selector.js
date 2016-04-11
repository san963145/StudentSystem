    var x=null;
    var y=null;
    var z=null;
    function init()      //数据初始化
    {
    	getDepartment();    	    	
    }
	function sendDepartment(department)     //根据选择的学院发送异步请求
	{
		  if(department=="选择学院")
		  {
			  document.getElementById("majors").options.length=0;
			  var all=document.createElement("option");
			  all.setAttribute("selected","selected");		
			  all.innerHTML="选择专业";
			  document.getElementById("majors").add(all);
			  
			  document.getElementById("grades").options.length=0;
			  var all=document.createElement("option");
			  all.setAttribute("selected","selected");		
			  all.innerHTML="选择年级";
			  document.getElementById("grades").add(all);
			  return ;
		  }
			 
		  if(window.XMLHttpRequest)
		  {
		    x=new XMLHttpRequest();
		  }
		  else
	      {
			x=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  x.open("GET","CardSelector?department="+encodeURI(encodeURI(department)),true);
		  x.onreadystatechange=update;
		  x.send();

	}
	function getDepartment()        //读取学院列表
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
	function getMajor(department)    //根据学院读取专业列表
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
	function getGrade(department)    //根据学院读取年级列表
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
	function updateDepartment()      //更新学院下拉列表数据
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
	function updateMajor()             //更新专业下拉列表数据
	{

		if(y.readyState==4 && y.status==200)
		{
			var selectedItem=document.getElementById("majors").value;
			document.getElementById("majors").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="选择专业")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="选择专业";
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
	function updateGrade()       //更新年级下拉列表数据
	{

		if(z.readyState==4 && z.status==200)
		{
			var selectedItem=document.getElementById("grades").value;
			document.getElementById("grades").options.length=0;
			var all=document.createElement("option");
			if(selectedItem=="选择年级")
			{
				all.setAttribute("selected","selected");
			}			
			all.innerHTML="选择年级";
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
	function update()          //根据选择的学院更新专业、年级列表
	{

		if(x.readyState==4 && x.status==200)
		{
			document.getElementById("majors").options.length=0;
			var all=document.createElement("option");
			all.setAttribute("selected","selected");
			all.innerHTML="选择专业";
			document.getElementById("majors").add(all);
			document.getElementById("grades").options.length=0;
			var all2=document.createElement("option");
			all2.setAttribute("selected","selected");
			all2.innerHTML="选择年级";
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
		else if(m=="选择专业")
		{
			alert("请选择专业!");
			return false;
		}
		else if(g=="选择年级")
		{
			alert("请选择年级!");
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