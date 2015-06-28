<!doctype html>
<html lang="zh-CN">
<head>
  <title>评价管理</title>
	<meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/layout.css" />
  <link rel="stylesheet" href="/css/filter.css">
</head>

<body class="container well">

<header class="header-user">
  <img src="/img/logo.png" />

  <span class="pull-right">
    <span id="photographer" data-photographer='{"id":"${photographers_id}"}'>XXX</span>，您好
    <a class="btn btn-warning" href="/users/logout">退出</a><div>
    </div>
  </span>

</header>

<nav class="pull-left op-nav">
  <ul>
      <li class="aside-tab-current"><a href="/users/gotoPhotoNewOrder.do?photographers_id=${photographers_id}">新增订单</a></li>
      <li><a href="/users/gotoPhotographerHome.do?photographers_id=${photographers_id}">首页</a></li>
      <li><a href="/customer/gotoCustomer.do?photographerId=${photographers_id}">客户管理</a></li>
      <li><a href="/appraisal/getAllAppraisal.do?photographerId=${photographers_id}">评价管理</a></li>
      <li><a href="/order/getOrder.do?photographerId=${photographers_id}">订单管理</a></li>
      <li>兑换券管理</li>
      <li>水印设置</li>
      <li>个人账户</li>
  </ul>
</nav>

<div class="pull-left op-panel">
  <div class="form-inline filter">
    <div class="form-group col-md-2 filter-item">
      <input type="text" class="form-control" placeholder="客户姓名">
      <button class="btn btn-sm btn-info btn-search" id="search-by-name">搜索</button>
    </div>

    <div class="form-group col-sm-offset-2">
      <select name="filter" class="form-control">
        <option value="all">全部</option>
        <option value="uncommented">未评价</option>
        <option value="commented">已评价</option>
      </select>
      <button class="btn btn-sm btn-info btn-search" id="search-by-comment">搜索</button>
    </div>
  </div>


  <input type="hidden" id="comment-count" value="${appraisals.totalPages}"/>
  <#--<input type="hidden" id="commentCount" value="<%= totalPages %>"/>-->
  <table class="table table-bordered">
    <thead>
      <th>订单ID</th>
      <th>客户姓名</th>
      <th>联系方式</th>
      <th>评价状态</th>
      <th>评价星级</th>
      <th>评价内容</th>
    </thead>
    
    <!-- 由ajax动态生成 -->
    <tbody id="comment-info">
      <#list appraisals.commentInfo as info>
        <tr>
          <td>${info.orderId}</td>
          <td>${info.customerName}</td>
          <td>${info.customerPhone}</td>
          <td>${info.status}</td>
          <td>${info.starNum}</td>
          <td><#if (user.name)??>
              ${info.comment}
          </#if></td>
        </tr>
      </#list>
    <#--<% for (var i = 0; i < commentInfo.length; ++i) { %>-->
    <#--<%   var info = commentInfo[i]; %>-->
    <#--<tr>-->
      <#--<td><%= info.orderId %></td>-->
      <#--<td><%= info.customerName %></td>-->
      <#--<td><%= info.customerPhone %></td>-->
      <#--<td><%= info.status %></td>-->
      <#--<td><%= info.starNum %></td>-->
      <#--<td><%= info.comment %></td>-->
    <#--</tr>-->
    <#--<% } %>-->
    </tbody>
  </table>
  <!-- pagination-sm -->
  <div class="text-center">
    <div id="comment-info-pagination"></div>
  </div>

</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
require(['/js/helper-config.js'], function () {
  require(['/js/comment-admin.js']);
});
</script>
</body>
</html>