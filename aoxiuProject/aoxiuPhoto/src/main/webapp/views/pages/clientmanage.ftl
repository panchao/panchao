<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>客户管理</title>
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="../css/pagination.css">
<link rel="stylesheet" type="text/css" href="../css/client.css">
</head>
<body>
	<header class="navbar" id="header">
		<div class="container">
			<div class="navbar-brand">logo</div>			
			<ul class="ul-pull-right">
				<li class="user-li" id="header-user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<img alt="头像" src="../img/avatar.jpg">
						<span class="username">xxxx</span>
						<i class="fa fa-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="fa fa-user"></i>个人信息</a></li>
						<li><a href="#"><i class="fa fa-cog"></i>账户设置</a></li>
						<li><a href="#"><i class="fa fa-eye"></i>隐私设置</a></li>
						<li><a href="注册页main.html"><i class="fa fa-power-off"></i>退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</header>

	<section id="page">
		<div id="sidebar" class="sidebar">
			<div class="sidebar-menu nav-collapse">
				<ul>
					<li>
						<a href="home.html">
							<i class="fa fa-tachometer fa-fw"></i> 
							<span class="menu-text">首页</span>
						</a>					
					</li>
						
					<li class="active" photographersId="${photographerId}" totalPages="${customers.totalPages}" totalCount="${customers.totalCount}">
						<a href="#">
							<i class="fa fa-desktop fa-fw"></i> 
							<span class="menu-text">客户管理</span>
							<span class="selected"></span>
						</a>
					</li>
					<li>
						<a href="摄影师管理.html">
							<i class="fa fa-envelope-o fa-fw"></i> 
							<span class="menu-text">摄影师管理</span>
						</a>
					</li>
					<li>
						<a href="订单管理.html">
							<i class="fa fa-th-large fa-fw"></i> 
							<span class="menu-text">订单管理</span>
						</a>
					</li>
					<li>
						<a href="评价管理.html">
							<i class="fa fa-calendar fa-fw"></i> 
							<span class="menu-text">评价管理</span>
						</a>
					</li>
					<li>
						<a href="代金卷管理.html">
							<i class="fa fa-picture-o fa-fw"></i> 
							<span class="menu-text">代金卷管理</span>
						</a>
					</li>
					<li>
						<a href="水印设置.html">
							<i class="fa fa-picture-o fa-fw"></i> 
							<span class="menu-text">水印设置</span>
						</a>
					</li>
					<li>
						<a href="我的资金.html">
							<i class="fa fa-picture-o fa-fw"></i> 
							<span class="menu-text">我的资金</span>
						</a>
					</li>
					<li>
						<a href="设置.html">
							<i class="fa fa-picture-o fa-fw"></i> 
							<span class="menu-text">设置</span>
						</a>
					</li>
				</ul>
			</div>
		</div>

		<div id="main-content">
			<div class="container">
				<div class="row">
					<div class="content">
						<div class="content-form">
							<form class="form-inline" role="form">
							   <div class="form-group">
							      <input type="text" class="form-control" id="search_username" 
							         placeholder="客户名字">
							   </div>
							   <a type="submit" class="btn btn-default" id="search-user" href="javasrcipt:void(0);">搜索</a>
							   <button type="submit" class="btn btn-default" style="margin-left:50%" id="add-dan">新增订单</button>
							   <button type="submit" class="btn btn-default" id="add-user">新增客户</button>
							</form>
							<div class="alert alert-info" id="search-info" style="display:none;margin-top:3%">
					   			<a href="javasrcipt:void(0);" class="alert-link"></a>
							</div>
							<table class="table table-bordered" id="table-user" style="margin-top:3%">
							   <thead>
							      	<tr>
								        <th>姓名</th>
								        <th>手机号</th>
								        <th>微信号</th>
								        <th>QQ号</th>
								        <th>电子邮</th>
								        <th>备注</th>
								        <th>操作</th>
							      	</tr>
							   </thead>
							   <tbody id="user-tbody">
							      	<!--tr>
								        <td>111</td>
								        <td>111</td>
								        <td>11</td>
								        <td></td>
								        <td></td>
								        <td></td>
								        <td>
								        	<button type="submit" class="btn btn-default" data="look-info" customerId="1">查看订单</button>
								        	<button type="submit" class="btn btn-default" data="edit-info" customerId="1">修&nbsp&nbsp&nbsp&nbsp改</button>
								        	<button type="submit" class="btn btn-default" data="cancel-info" customerId="1">删&nbsp&nbsp&nbsp&nbsp除</button>
								        </td>
							      	</tr-->
							      	<#-- 使用FTL指令 循环输出数据-->
									<#list customers.data as customer><br>
									   <tr>
								        <td>${customer.realName}</td>
								        <td>${customer.phoneNumber}</td>
								        <td>${customer.weChat}</td>
								        <td>${customer.QQNumber}</td>
								        <td>${customer.email}</td>
								        <td></td>
								        <td>
								        	<button type="submit" class="btn btn-default" data="look-info" customerId="${customer.customerId}">查看订单</button>
								        	<button type="submit" class="btn btn-default" data="edit-info" customerId="${customer.customerId}">修&nbsp&nbsp&nbsp&nbsp改</button>
								        	<button type="submit" class="btn btn-default" data="cancel-info" customerId="${customer.customerId}">删&nbsp&nbsp&nbsp&nbsp除</button>
								        </td>
							      	</tr>
									</#list>
							   </tbody>
							</table>
							<div id="Pagination" class="pagination" style="margin-left:30%"><!-- 这里显示分页 --></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- 模态框（Modal） 新增客户-->
	<div class="modal fade" id="modal-user" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	           		<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
		            <h4 class="modal-title" id="user-title">新增客户</h4>
	        	</div>
	        <div class="modal-body">
	        	 <div class="alert alert-success" id="user-success" style="display: none;">
					   <a href="javasrcipt:void(0);" class="alert-link"></a>
					</div>
					<div class="alert alert-info" id="user-info" style="display: none;">
					   <a href="javasrcipt:void(0);" class="alert-link"></a>
					</div>
					<div class="alert alert-warning" id="user-warn" style="display: none;">
					   <a href="javasrcipt:void(0);" class="alert-link"></a>
					</div>
					<div class="alert alert-danger" id="user-err" style="display: none;">
					   <a href="javasrcipt:void(0);" class="alert-link"></a>
				</div>
	            <form class="form-horizontal" role="form">
				   <div class="form-group">
				      <label for="firstname" class="col-sm-2 control-label">姓名 <b style="color:red;">*</b></label>
				      <div class="col-sm-10">
				        <input type="text" class="form-control" id="username"  placeholder="请输入名字">
				      </div>
				   </div>
				   <div class="form-group">
				      <label for="lastname" class="col-sm-2 control-label">手机号 <b style="color:red;">*</b></label>
				      <div class="col-sm-10">
				         <input type="text" class="form-control" id="tel" 
				            placeholder="请输入手机号">
				      </div>
				   </div>
				    <div class="form-group">
				      <label for="lastname" class="col-sm-2 control-label">微信号</label>
				      <div class="col-sm-10">
				         <input type="text" class="form-control" id="weixin" 
				            placeholder="请输入微信号">
				      </div>
				   </div>
				    <div class="form-group">
				      <label for="lastname" class="col-sm-2 control-label">QQ号</label>
				      <div class="col-sm-10">
				         <input type="text" class="form-control" id="qq" 
				            placeholder="请输入QQ号">
				      </div>
				   </div>
				    <div class="form-group">
				      <label for="lastname" class="col-sm-2 control-label">电子邮箱</label>
				      <div class="col-sm-10">
				         <input type="text" class="form-control" id="mail" 
				            placeholder="请输入电子邮箱">
				      </div>
				   </div>
				    <div class="form-group">
				      <label for="lastname" class="col-sm-2 control-label">备注</label>
				      <div class="col-sm-10">
				         <input type="textarea" class="form-control" id="beizhu" 
				            placeholder="">
				      </div>
				   </div>
				</form>
	        </div>
	        <div class="modal-footer">
	            <button type="button" class="btn btn-primary" id="user-save">保存</button>
	            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div>
	</div>
	</div>
	<!-- /.modal -->

	<!-- 模态框（Modal） 查看订单详情-->
	<div class="modal fade" id="modal-order-detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content" style="width:140%;margin-left:-15%">
	            <div class="modal-header">
	           		<button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
		            <h4 class="modal-title">订单详情</h4>
	        	</div>
	        <div class="modal-body" style="overflow:scroll;">
	        	<table class="table table-striped" id="table-order">
				    <thead>
				        <tr>
					        <th>orderId</th>
					        <th>userId</th>
					        <th>addWaterMark</th>
					        <th>maxSelectCount</th>
					        <th>photographersId</th>
					        <th>giftAlbumsType</th>
					        <th>getCode</th>
					        <th>getCodeSelected</th>
					        <th>pricePerPhoto”</th>
					        <th>selectPhotoType</th>
					        <th>orderStep</th>
					        <th>createTime</th>
					        <th>updateTime</th>
				        </tr>
				    </thead>
				    <tbody id="order-tbody">
				        <!--tr>
					        <td>orderId</td>
					        <td>orderId</td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					        <td></td>
					    </tr-->
				   </tbody>
				</table>
	        </div>
	        <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        </div>
	    </div>
	</div>
	</div>
	<!-- /.modal -->
	
<script src="../js/jquery/jquery-2.0.3.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
<script src="../js/jquery/jquery.pagination.js"></script>
<script src="../js/add-client.js"></script>
</body>
</html>
