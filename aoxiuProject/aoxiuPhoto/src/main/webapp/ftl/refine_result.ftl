<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" type="text/css" href="../css/default.css" />
		<link rel="stylesheet" type="text/css" href="../css/component.css" />
		<link rel="stylesheet" href="../css/login.css">
		 <style type="text/css">
			
		 </style>
	</head>
	<body>
		<div id="wrapper">
			<div class="header">
				 <h1 class="headerLogo"><a title="后台管理系统" target="_blank" href="http://www.baidu.com/"><img alt="logo" src="../images/logo.gif"></a></h1>
				<div class="headerNav">
					<a target="_blank" href="http://www.baidu.com/">傲秀官网</a>
					<a target="_blank" href="http://www.baidu.com/">关于傲秀</a>
					<a target="_blank" href="http://www.baidu.com/">团队介绍</a>
					<a target="_blank" href="http://www.baidu.com/">意见反馈</a>
					<a target="_blank" href="http://www.baidu.com/">帮助</a>
				</div>
			</div>
			<div id="navtop">
				
			</div>
			<div class="main">
				<ul class="cbp_tmtimeline">
					<#list result_map?keys as key>
						 <li>
							<time class="cbp_tmtime" ><span>${result_map[key]?date}</span> <span>${result_map[key]?datetime("yyyy-MM-dd HH:mm:ss")?time}</span></time>
							<div class="cbp_tmicon cbp_tmicon-earth"></div>
							<div class="cbp_tmlabel">
								 <a href="#"><img src=${key}></a>
							</div>
						</li>
					</#list>

					<!-- <li>
						<time class="cbp_tmtime" ><span>2015-4-10</span> <span>18:30</span></time>
						<div class="cbp_tmicon cbp_tmicon-earth"></div>
						<div class="cbp_tmlabel">
							 <a href="#"><img src="images/img/img2.jpg"></a>
						</div>
					</li>
					<li>
						<time class="cbp_tmtime"><span>4/11/13</span> <span>12:04</span></time>
						<div class="cbp_tmicon cbp_tmicon-earth"></div>
						<div class="cbp_tmlabel">
							<a href="#"><img src="images/img/img3.jpg"></a>
						</div>
					</li>
					<li>
						<time class="cbp_tmtime" ><span>4/13/13</span> <span>05:36</span></time>
						<div class="cbp_tmicon cbp_tmicon-earth"></div>
						<div class="cbp_tmlabel">
							<a href="#"><img src="images/img/img4.jpg"></a>
						</div>
					</li> -->
				</ul>
			</div>
		</div>
			
			<footer class="footer">
			   <p>Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.baidu.com/">傲秀</a></p>
			</footer>
		</div>
		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript">
			$(function() {

				//
			})
		</script>
	</body>
</html>
