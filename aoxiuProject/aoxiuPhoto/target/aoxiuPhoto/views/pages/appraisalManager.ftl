<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 评价管理 -->
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/filter.css">
</head>

<body class="container well">

<#include "partials/header.ftl">
<#include "partials/nav.ftl">

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

  <input type="hidden" id="comment-count" value="${ appraisals.totalPages }"/>
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
          <td>${ info.orderId }</td>
          <td>${ info.customerName }</td>
          <td>${ info.customerPhone }</td>
          <td>${ info.status }</td>
          <td>${ info.starNum }</td>
          <td>
          <#if (user.name)??>
              ${ info.comment }
          </#if>
          </td>
        </tr>
      </#list>
    </tbody>
  </table>

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