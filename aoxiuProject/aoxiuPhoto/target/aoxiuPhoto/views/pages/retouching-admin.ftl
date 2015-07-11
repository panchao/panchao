<!doctype html>
<html lang="zh-CN">
<head>
  <#-- <title>管理精修片</title> -->
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/photo-list.css" />
  <link rel="stylesheet" href="/css/retouching-admin.css" />
</head>

<body class="container well">

<#include "partials/header.ftl">
<#include "partials/nav.ftl">

<div class="pull-left op-panel">
  <input type="hidden" value="${totalPages}"/>
  <div class="row">
    <div class="col-md-6">共${ totalPages }张</div>
    <div class="col-md-6">
      <button class="btn btn-info" id="pickfiles">上传照片</button>
      <input type="hidden" class="domain" value="http://qiniu-plupload.qiniudn.com/">
      <input type="hidden" class="uptoken-url" value="http://localhost:8080/pictures/getToken?type=3">
      <input type="hidden" class="album-id" value="<%= albumId %>">
     </div>
  </div>

  <div class="photos container-fluid clearfix">
    <#list data as photo>
      <div class="col-md-4">
        <img src="${ photo.photoSrc }/${photo.photoNameOld}" alt="${ photo.photoNameOld }" data-img='{"id":${ photo.photoId }}' />
        <div class="pull-right">
          <div>名称：${ photo.photoNameOld }</div>
          <div class="alert-danger" style="display: none;">删除失败</div>
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
  require(['main/retouching-admin']);
});
</script>
</body>
</html>