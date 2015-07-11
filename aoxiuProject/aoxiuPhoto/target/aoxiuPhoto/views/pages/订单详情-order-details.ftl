<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 订单详情 -->
  <#include "../partials/head.ftl">
  <link rel="stylesheet" href="/css/timeline.css" />
  <link rel="stylesheet" href="/css/order-details.css" />
</head>

<body class="container well">

<#include "../partials/header.ftl">
<#include "../partials/nav.ftl">

<div class="pull-left op-panel">
  <div class="row row-order">
    <span class="col-md-5">客户姓名：${order.customer.name}</span><span class="col-md-7">联系方式：${order.customer.contact}</span>
  </div>

  <div class="row row-order">
    <div class="row-order-inner">
      <span class="col-md-12">订单类型：${order.type}</span>
    </div>
    <div class="row-order-inner">
      <span class="col-md-5">精修张数：<span class="display-label">${order.selectedCount}</span><input class="edit-input" type="number" value="${order.selectedCount}" style="display:none;" />张<button class="btn btn-success edit-btn" data-post-param="selectedCount">修改</button><span class="alert alert-danger" style="display:none;">修改失败</span></span>

      <span class="col-md-7">额外精修价格：<span class="display-label">${order.extraPrice}</span><input class="edit-input" type="number" value="${order.extraPrice}" style="display:none;" />元/张<button class="btn btn-success edit-btn" data-post-param="extraPrice">修改</button><span class="alert alert-danger" style="display:none;">修改失败</span></span>
    </div>
  </div>
  <input type="hidden" id="order-id" value="${order.id}" />

  <div class="row row-order">
    <ol class="timeline">
      <li class="timeline-step <#if order.orginUploaded>done</#if>">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">上传原片</span>
      </li>

      <li class="timeline-step <#if order.trimDetermined>done</#if>">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">用户选定修片</span>
      </li>

      <li class="timeline-step <#if order.selectedUploaded>done</#if>">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">上传精修片</span>
      </li>

      <li class="timeline-step <#if order.commented>done</#if>">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">完成评价</span>
      </li>
    </ol>

    <div class="row-order-inner text-center">
      <a href="users/manageOrigin?photographerId=${order.photographerId}" class="btn btn-info">管理原片</a>
      <a href="users/checkTrimList?photographerId=${order.photographerId}" class="btn btn-info">查看用户选择修片列表</a>
      <a href="users/manageSelected?photographerId=${order.photographerId}" class="btn btn-info">管理精修片</a>
    </div>

    <div class="row-order-inner text-center">
      <#if order.originQrcode>
        <img src="${order.originQrcode}" alt="原片地址二维码" width="200" height="200" />
      </#if>
      <#if order.selectedQrcode>
        <img src="${order.selectedQrcode}" alt="精修片地址二维码" width="200" height="200" />
      </#if>
    </div>
  </div>

</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
require(['/js/libs/requirejs/config.js'], function () {
  require(['main/order-details']);
});
</script>
</body>
</html>