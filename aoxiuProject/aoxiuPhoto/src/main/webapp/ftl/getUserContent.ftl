<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		 <link rel="stylesheet" href="../bootstrap/css/bootstrap.css">
		 <link rel="stylesheet" type="text/css" href="../css/style.css" />
		 <link rel="stylesheet" type="text/css" href="../css/gridNavigation.css" />
		 <link rel="stylesheet" type="text/css" href="../css/login.css" />
		<style type="text/css">
			.tj_gallery .details {
				position: absolute;
				bottom: 1px;
				left: 5px;
				background: #fff;
				background: rgba(255, 255, 255, 0.87);
				border-top: 1px solid #fff;
				width: 190px;
				text-align: center;
				opacity:1;
				z-index: 99999;
				border-bottom: 1px solid rgba(0, 0, 0, 0.25);
				padding: 10px;
				-webkit-border-radius:  0 0 5px 5px;
				-moz-border-radius: 0 0 5px 5px;
				border-radius: 0 0 5px 5px;
				-webkit-box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.35);
				-moz-box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.35);
				box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.35);
			}
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
			<div id="maincontent">
				<div id="tj_container" class="tj_container">
					<div class="tj_nav">
						<span id="tj_prev" class="tj_prev">Previous</span>
						<span id="tj_next" class="tj_next">Next</span>
					</div>
					<div class="tj_wrapper">
						<ul class="tj_gallery">
							<#list resultMap['urls']?keys as key>

								<#list resultMap['urls'][key]?split("=") as fileUrl>
								<#if fileUrl_index==1>
								<#assign id=fileUrl>
                                    <li>
                                        <div class="details">
                                            <h3>${key}</h3>
                                        </div>
                                        <a href="/pictures/getPictures?contentId=${fileUrl}"><img style="width:190px;" src=${resultMap['urls'][key]} /></a>    //TODO
                                    </li>
								</#if>
								</#list>
							</#list>

							<!-- <li>
						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div>
							<a href="http://localhost/photo_list.html"><img src="images/1.jpg" alt="image01" /></a></li>
							<li>						           
							<div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/2.jpg" alt="image02" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/3.jpg" alt="image03" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/4.jpg" alt="image04" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/5.jpg" alt="image05" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/6.jpg" alt="image06" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/7.jpg" alt="image07" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/8.jpg" alt="image08" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/9.jpg" alt="image09" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/10.jpg" alt="image10" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/11.jpg" alt="image11" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/12.jpg" alt="image12" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/13.jpg" alt="image13" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/14.jpg" alt="image14" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/15.jpg" alt="image15" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/16.jpg" alt="image16" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/17.jpg" alt="image17" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/18.jpg" alt="image18" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/19.jpg" alt="image19" /></a></li>
							<li>						           <div class="details">
						                     <h3>Lanterns</h3>
						             </div><a href="#"><img src="images/20.jpg" alt="image20" /></a></li> -->
						</ul>
					</div>
				</div>
			</div>
			<footer class="footer" style="margin-top:223px">
			   <p>Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.baidu.com/">傲秀</a></p>
			</footer>
		</div>
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script type="text/javascript" src="../js/jquery.easing.1.3.js"></script>
		<script type="text/javascript" src="../js/jquery.mousewheel.js"></script>
		<script type="text/javascript" src="../js/jquery.gridnav.js"></script>
		<script type="text/javascript">
			$(function() {

				$('#tj_container').gridnav({
					rows	: 3,
					type	: {
						mode		: 'sequpdown', 		// use def | fade | seqfade | updown | sequpdown | showhide | disperse | rows
						speed		: 500,				// for fade, seqfade, updown, sequpdown, showhide, disperse, rows
						easing		: '',				// for fade, seqfade, updown, sequpdown, showhide, disperse, rows	
						factor		: 50,				// for seqfade, sequpdown, rows
						reverse		: false				// for sequpdown
					}
				});
			})
		</script>
	</body>
</html>
