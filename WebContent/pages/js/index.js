         function cardGet(index,name)
         {
        	 var department="";
        	 var major="";
        	 var grade="";
        	 var sex="";
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<index;i++)
        	 {
        		 if(list[i].id)
        	     {
        			 str+=(list[i].id+'#'); 
        			 if(list[i].id=="department")
        			 {
        				department=list[i].innerHTML; 
        			 }
        			 if(list[i].id=="major")
        			 {
        				 major=list[i].innerHTML;
        			 }
        			 if(list[i].id=="grade")
        			 {
        				 grade=list[i].innerHTML;
        			 }
        			 if(list[i].id=="sex")
        			 {
        				 sex=list[i].innerHTML;
        			 }
        	     }        		  
        	 }          	
        	 document.cookie='department='+encodeURI(department); 
        	 document.cookie='major='+encodeURI(major); 
        	 document.cookie='grade='+encodeURI(grade);
        	 document.cookie='sex='+encodeURI(sex);
        	 document.cookie='str='+encodeURI(str); 
        	 if(name=='department')
        	   location="CardGroupByCollege";
        	 if(name=='major')
          	   location="CardGroupByMajor";
        	 if(name=='grade')
          	   location="CardGroupByGrade";
        	 if(name=='sex')
          	   location="CardGroupBySex";
         }
         function networkGet(index,name)
         {
        	 var department="";
        	 var major="";
        	 var grade="";
        	 var sex="";
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<index;i++)
        	 {
        		 if(list[i].id)
        	     {
        			 str+=(list[i].id+'#'); 
        			 if(list[i].id=="department")
        			 {
        				department=list[i].innerHTML; 
        			 }
        			 if(list[i].id=="major")
        			 {
        				 major=list[i].innerHTML;
        			 }
        			 if(list[i].id=="grade")
        			 {
        				 grade=list[i].innerHTML;
        			 }
        			 if(list[i].id=="sex")
        			 {
        				 sex=list[i].innerHTML;
        			 }
        	     }        		  
        	 }          	
        	 document.cookie='department='+encodeURI(department); 
        	 document.cookie='major='+encodeURI(major); 
        	 document.cookie='grade='+encodeURI(grade);
        	 document.cookie='sex='+encodeURI(sex);
        	 document.cookie='str='+encodeURI(str); 
        	 if(name=='department')
        	   location="NetworkGroupByCollege";
        	 if(name=='major')
          	   location="NetworkGroupByMajor";
        	 if(name=='grade')
          	   location="NetworkGroupByGrade";
        	 if(name=='sex')
          	   location="NetworkGroupBySex";
         }
         function gradesGet(index,name)
         {
        	 var department="";
        	 var major="";
        	 var grade="";
        	 var sex="";
        	 var list=document.getElementsByClassName("str");
        	 var str="";
        	 for(var i=0;i<index;i++)
        	 {
        		 if(list[i].id)
        	     {
        			 str+=(list[i].id+'#'); 
        			 if(list[i].id=="department")
        			 {
        				department=list[i].innerHTML; 
        			 }
        			 if(list[i].id=="major")
        			 {
        				 major=list[i].innerHTML;
        			 }
        			 if(list[i].id=="grade")
        			 {
        				 grade=list[i].innerHTML;
        			 }
        			 if(list[i].id=="sex")
        			 {
        				 sex=list[i].innerHTML;
        			 }
        	     }        		  
        	 }          	
        	 document.cookie='department='+encodeURI(department); 
        	 document.cookie='major='+encodeURI(major); 
        	 document.cookie='grade='+encodeURI(grade);
        	 document.cookie='sex='+encodeURI(sex);
        	 document.cookie='str='+encodeURI(str); 
        	 if(name=='department')
        	   location="GradesGroupByCollege";
        	 if(name=='major')
          	   location="GradesGroupByMajor";
        	 if(name=='grade')
          	   location="GradesGroupByGrade";
        	 if(name=='sex')
          	   location="GradesGroupBySex";
         }