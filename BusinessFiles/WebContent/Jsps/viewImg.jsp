<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String str = request.getParameter("names");
	// if(str!=null)
	//	str=new String(str.getBytes("ISO-8859-1"),"utf-8");
	
	 String []names = str.split(",");
	String dir = request.getParameter("dir");
	String s = request.getParameter("files");
	String []files = s.split(",");
	String id=request.getParameter("id");
	 if(id!=null)
		 dir=dir+"/"+id;
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看附件</title>
<link rel="Shortcut Icon" href="images/favicon.ico">
<link rel="stylesheet" href="css/scrollImg.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/viewer.css">
 <script src="js/jquery.min.js"></script>
 <script src="js/viewer.js"></script>
 <script type="text/javascript" src="js/pagescroller.min.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="js/mytool.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<style>
* { margin: 0; padding: 0;}
.dowebok { width: 700px;  margin: 0 auto; font-size: 0;}
.dowebok li { display: inline-block; width: 32%; margin-left: 1%; padding-top: 1%;}
.dowebok li img { width: 100%;}

.btns { position: relative; z-index: 10000; width: 700px; margin: 0 auto; text-align: center;}
.btns a { display: inline-block; width: 70px; margin-top: 15px; line-height: 26px; font-size: 12px; color: #fff; background-color: #21b384; text-decoration: none;}
.btns a:hover { background-color: #1fa67a;}
</style>


</head>

<script type="text/javascript" charset="utf-8">

$(function() {
	var label = "<%=str%>";
	var navLabel = label.split(',');
	$('#main').pageScroller({ navigation: navLabel });
	$('.jumpTo').click(function(e){
		e.preventDefault();
		//pageScroller.goTo(4);
	});
	$('.next').click(function(e){
		e.preventDefault();
		pageScroller.next();
	});
		
	$('.prev').click(function(e){
		e.preventDefault();
		pageScroller.prev();
	});
	});


</script>

<body>

<div id="lrzj" class="light">
		<a  class="prev"><img src="./images/icon_arrow-up_light.png" alt="" width="28" height="31" /></a>
		<a  class="next"><img src="./images/icon_arrow-down_light.png" alt="" width="28" height="31" /></a>	
		
</div>
<div id="lrzjmy" class="light">
		<button style="width:50px;height:30px;" onclick="closeWindow()">返回</button>
</div>
	<div id="wrapper">
		<div id="main">
			<%
				for(int i=0;i<names.length; i++){
					String file =  i<files.length?files[i]:"noExist";
					String imgPath = String.format("attachments_Img/%s/%s",dir,file);
			%>
			<div class="section">
				<h1><%=names[i] %></h1>
				<ul class="dowebok">
					<li><img style="width:300px;height:400px;" data-original="<%=imgPath %>" alt="请上传"></li>
				</ul>			
			</div>
		<%} %>
		</div>
		
	</div> 

	
</body>
</html>


 
