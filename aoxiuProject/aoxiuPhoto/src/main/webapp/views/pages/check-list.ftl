<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 查看用户选修列表 -->
  <#include "../partials/head.ftl">
  <link rel="stylesheet" href="/css/photo-list.css" />
</head>

<body class="container well">

<#include "../partials/header.ftl">
<#include "../partials/nav.ftl">

<div class="pull-left op-panel">
  <input type="hidden" value="${ totalPages }"/>

  <div class="row">
    <div class="col-md-6">共${ totalPhotos }张</div>
    <div class="col-md-6"><a class="btn btn-info" href="/pictures/downloadName.do?customerId=${ customerId }" target="_blank" role="button">导出为excel</a></div>
  </div>

  <div class="photos container-fluid clearfix">
    <#list data as photo>
      <div class="col-md-6">
        <img src="${ photo.photoSrc }/${ photo.photoNameOld }"/>
        <div class="pull-right">
          <div>原文件名：<span>${ photo.photoNameOld }</span></div>
          <div>新文件名：<span></span></div>
        </div>
      </div>
    </#list>
  </div>

  <div class="text-center">
    <div class="my-pager"></div>
  </div>
</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
require(['/js/libs/requirejs/config.js'], function () {
  require(['main/photo-list']);
});
</script>
</body>
</html>