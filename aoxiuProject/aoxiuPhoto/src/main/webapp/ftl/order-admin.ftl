<!doctype html>
<html lang="zh-CN">
<head>
  <title>订单管理</title>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
  <link rel="stylesheet" href="/css/layout.css" />
  <link rel="stylesheet" href="/css/filter.css" />
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
      <button class="btn btn-sm btn-info btn-search" data-filter-type="byName">搜索</button>
    </div>

    <div class="form-group col-sm-offset-2">
      <select name="filter" class="form-control">
        <option value="originNotUploaded">未上传原片</option>
        <option value="originPageNotGenerated">未生成原片页面</option>
        <option value="selectTrimInfoNotSubmitted">用户未提交选修信息</option>
        <option value="selectedNotUploaded">未上传精修片</option>
        <option value="uncommented">用户未评价</option>
      </select>
      <button class="btn btn-sm btn-info btn-search" data-filter-type="byType">筛选</button>
    </div>
  </div>
    <input type="hidden" id="order-count" value="${totalPages}"/>
  <#--<input type="hidden" id="order-count" value="<%= totalPages %>"/>-->
  <table class="table table-bordered">
    <thead>
      <th>客户名称</th>
      <th>开始日期</th>
      <th>当前状态</th>
      <th>操作</th>
    </thead>


    <!-- 初始化由服务器带入，筛选和分页由ajax动态生成 -->
    <tbody id="order-info">
      <#list data as info>
        <tr>
          <td>${ info.customerName }</td>
          <td>${ info.createTime }</td>
          <td>${ info.status }</td>
          <td><a href="/order/gotoOrderDetail.do?orderId=${info.orderId}">查看</a></td>
        </tr>
      </#list>
     <!-- 2.2 把下面代码注释掉 -->
    <#--<% for (var i = 0; i < data.length; ++i) { %>-->
    <#--<%   var info = data[i]; %>-->
    <#--<tr>-->
      <#--<td><%= info.customerName %></td>-->
      <#--<td><%= info.createTime %></td>-->
      <#--<td><%= info.status %></td>-->
      <#--&lt;#&ndash;<td><a href="/users/<%= info.customerId %>/order-details/">查看</a></td>&ndash;&gt;-->
    <#--</tr>-->
    <#--<% } %>-->
    </tbody>
  </table>
  <!-- pagination-sm -->
  <div class="text-center">
    <div id="order-admin-pager"></div>
  </div>

</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
require(['/js/libs/requirejs/config.js'], function () {
  require(['main/order-admin']);
});
</script>
</body>
</html>