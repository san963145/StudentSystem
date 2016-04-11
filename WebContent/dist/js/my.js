(function ($, My) {

  setup();
  var authority="";
  var x=null;
  send();
  function send()
  {

		  if(window.XMLHttpRequest)
		  {
		    x=new XMLHttpRequest();
		  }
		  else
	      {
			x=new ActiveXObject("Microsoft.XMLHTTP");
	      }
		  x.open("GET","RoleIdentity",false);
		  x.onreadystatechange=update;
		  x.send();

  }
  function update()
  {

		if(x.readyState==4 && x.status==200)
		{
         authority=x.responseText;
		}
  }
  if(authority=="Admin")
  {
      $(".dean").addClass("hidden");
      $(".instruct").addClass("hidden");
      $(".admin").removeClass("hidden");
  }
  else if(authority=="Dean")
  {
	  $(".instruct").addClass("hidden");
	  $(".admin").addClass("hidden");
	  $(".dean").removeClass("hidden");
  }  
  else if(authority=="Instructor")
  {
	  $(".dean").addClass("hidden");
	  $(".admin").addClass("hidden");
	  $(".instruct").removeClass("hidden");
  }
  function setup() {
    $('.logo').on('click', function (e) {
      e.preventDefault();
      $('#sidebar').collapse('toggle')
    });
  }
  
})(jQuery, $.My);
