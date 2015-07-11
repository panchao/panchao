<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		 <link rel="stylesheet" href="../bootstrap/css/bootstrap.css">
		 <link rel="stylesheet" href="../css/login.css">
		 <style type="text/css">
			#verify{width:400px;height:80px;position:absolute;top:30%;left:40%;}
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
			<div class="banner">
				<div id="verify">
					<form class="navbar-form navbar-left" role="search">
					  <label>手机号：</label>
					  <div class="form-group">
					    <input id="mobile"type="text" class="form-control" placeholder="请输入手机号">
					            <input type="hidden" id="photoType" value="<#if type??>${type}</#if>">
        						 <input type="hidden" id="getCode" value="<#if getCode??>${getCode}</#if>">
					  </div>
					  <button id="btnVerify" type="button" class="btn btn-default">验证</button>
					</form>
				</div> 
			</div>
			<footer class="footer">
			   <p>Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.baidu.com/">傲秀</a></p>
			</footer>
		</div>
		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function() {

				function checkMobile(str) {
					var re = /^1\d{10}$/
					if (re.test(str)) {
						return true;
					} else {
						return false;
					}	
				}

				var photoType = null,getCode = 0;

				// $.ajax({
			 //                	url:"192.168.1.103/web/interfaces/users/validPhoneNumber.do ",
			 //               	type: 'post',
			 //               	dataType: 'json',
			 //               	cache: false,
			 //              	async: false,
			 //              	data: {

			 //            	},
			 //                	success: function (data) {
			 //                   		 if (data.err.code == 0) {
			 //                   		 	photoType =
			 //                   		 	getCode = 
				//                     } else {
				//                        	 _this._alerError(data.err.msg, _this.eRebackModal);
				//                     }
			 //                     },
			 //                	error: function (xhr) {
			 //                		//
			 //               	 }
			 //            });
				$("#btnVerify").click(function() { alert("222");
					var strMobile = $("#mobile").val();
					if(checkMobile(strMobile)){
						$.ajax({
					                url:"users/validPhoneNumber.do",            // TODO
					                type: 'post',
					                dataType: 'json',
					                cache: false,
					                async: true,
                            		timeout:20000,
					                data: {
					                    type:"unselected",
					                    getCode:123456,
					                    phoneNumber : strMobile
					                },
					                success: function (data) {
					                    if (data.retCode == 200) {
                                            window.location = "" + data.data + "&flag=1";    //TODO

					                    } else {
					                     		alert("验证不通过");
					                    }
					                },
					                error: function (xhr) {alert("error");
					                	alert("error" + xhr);
                                        alert("验证失败");
					                }
				            });
					}else{
						alert("请输入正确的手机号");
					}
				});
			})
		</script>
	</body>
</html>
