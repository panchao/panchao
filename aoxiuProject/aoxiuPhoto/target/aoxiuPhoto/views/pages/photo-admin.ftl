<!doctype html>
<html lang="zh-CN">
<head>
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/photo-list.css" />
  <link rel="stylesheet" href="/css/retouching-admin.css" />
</head>

<body class="container well">

<#include "partials/header.ftl">
<#include "partials/nav.ftl">

<div class="pull-left op-panel">
  <input type="hidden" value="${ totalPages }"/>
  <div class="row">
     <div class="col-md-6">共${ totalPhotos }张</div>
    <div class="col-md-6">
      <button class="btn btn-info" id="pickfiles">上传照片</button>
      <input type="hidden" class="domain" value="http://qiniu-plupload.qiniudn.com/">
      <input type="hidden" class="uptoken-url" value="http://localhost:8080/pictures/getToken?type=3">
      <input type="hidden" class="album-id" value="${masterContentId}">
     </div>
  </div>

  <div class="photos container-fluid clearfix">
    <#list data as photo>
    <div class="col-md-4">
      <img src="${ photo.src }" width="110" height="95" data-img='{"id": ${ photo.id }}' />
      <span class="delete-icon" title="删除该相片">×</span>
      <div class="pull-right photo-info">
        <div>名称：${ photo.name }</div>
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