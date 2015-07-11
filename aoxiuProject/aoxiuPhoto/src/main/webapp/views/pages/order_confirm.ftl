<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="../bootstrap/css/bootstrap.2.3.1.min.css">
		<link rel="stylesheet" href="../bootstrap/css/bootstrap-responsive.min.css">
		<link rel="stylesheet" href="../css/login.css">
	</head>
	<body>
		<div id="wrapper">
			<div class="header">
				 <h1 class="headerLogo"><a title="后台管理系统" target="_blank" href="http://www.baidu.com/"><img alt="logo" src="images/logo.gif"></a></h1>
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
			<div class="banner">
				<div class="portlet">
					<div class="portlet-title">
						<div class="caption"><i class="icon-cog"></i>订单基本信息</div>
						<#list albumOrders as album_item>
							<div>${album_item.albumName}</div>
							<div>${album_item.albumType}</div>
							<div>数量：${album_item.albumCount}</div>
							<input class="album" type="hidden" value=${album_item.orderId}>
						</#list>
					</div>
					<div class="portlet">
						<div class="portlet-title"><div class="caption"><i class="icon-cog"></i>配送信息</div>
							北京市海淀区
						</div>
					</div>
					<div>兑换券信息：${coupon}</div>
					<a type="button" class="btn btn-primary red" value="去支付" id="go_to_pay"></a>
				</div>
			</div>
			<footer class="footer">
			   <p>Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.baidu.com/">傲秀</a></p>
			</footer>
		</div>
		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap.2.3.1.min.js"></script>
		<script type="text/javascript">

			function GetRequest(){
				var url = location.search; 
				var theRequest = new Object();
				if (url.indexOf("?") != -1){
					var str = url.substr(1);
					strs = str.split("&");
					for(var i = 0; i < strs.length; i ++) {
				        theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
					}
				}
				return theRequest;
			}

			$(function() {

				var getCode = 0;
				var arrAlbumImfor = [ ];
				var oTemp = {};
				$("#go_to_pay").click(function() {

					arrAlbumImfor = $(".album");
					getCode = GetRequest[getCode];

					$.each(arrAlbumImfor,function(index,ele){
						oTemp = {"albumOrderId":ele.attr("value"),"getCode":getCode};
						data.push(oTemp);
					});

					$.ajax({
				           	url:"/album/payAlbumOrder.do",
				                	type: 'post',
				                	dataType: 'json',
				                	cache: false,
				                	async: false,
					           data:JSON.stringify(
	                               				data
	              				),
				                	success: function (data) {
			
					                    if (data.retCode == 200) {
						                   window.location = "http://www.baidu.com";
					                    } else {
					                     	alert("支付失败");
					                    }

				                	},
				                	error: function (xhr) {
				                		//
				                	}
				           });

				});
			})
		</script>
	</body>
</html>
