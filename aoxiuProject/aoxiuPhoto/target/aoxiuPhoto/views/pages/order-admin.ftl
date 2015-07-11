<!doctype html>
<html lang="zh-CN">
<head>
  <#-- <title>订单管理</title> -->
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/filter.css" />
</head>

<body class="container well">

<#include "partials/header.ftl">
<#include "partials/nav.ftl">

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
  <input type="hidden" id="order-count" value="${ totalPages }"/>
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
    </tbody>
  </table>

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