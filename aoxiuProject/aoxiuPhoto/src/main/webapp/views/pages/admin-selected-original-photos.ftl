<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 管理精修片或原片 -->
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/album.css" />
  <link rel="stylesheet" href="/css/photo-list.css" />
  <link rel="stylesheet" href="/css/selected-admin.css" />
</head>

<body class="container well">

<#include "partials/header.ftl">
<#include "partials/nav.ftl">
</nav>

<div class="pull-left op-panel">
  <input class="admin-photo-type" type="hidden" value="${ type }" />

  <div class="row">
    <div class="col-md-6">共${ totalPhotos }张</div>
    <div class="col-md-6">
      <button class="btn btn-info" id="pickfiles">上传照片</button>
      <input type="hidden" class="domain" value="${ domain }">
      <input type="hidden" class="uptoken-url" value="${ uptokenUrl }">
      <input type="hidden" class="album-id" value="${ albumId }">
        <input type="hidden" class="customer-id" value="${ customerId }">

    </div>
  </div>
  <div class="photo-box">
    <#--<#if type == "original">-->
    <#--<div class="my-breadcrumb"></div>-->
    <#--<div class="albums">-->
      <#--<input type="hidden" value="${ mainAlbumId }" />-->
      <#--<div class="albums">-->
        <#--<#list album as data.albums>-->
          <#--<#include ../partials/album.ftl>-->
        <#--</#list>-->
      <#--</div>-->
    <#--</div>-->
    <#--</#if>-->

    <div class="photos container-fluid clearfix">
      <#list data as photo>
        <div class="col-md-4">
          <img src="${ photo.photoSrc }/${photo.photoNameOld}" width="110" height="95" alt="${ photo.photoNameOld }" data-img="{&quot;id&quot;: &quot;${ photo.photoId }&quot;}" />
          <span class="delete-icon" title="删除该相片">×</span>
          <div class="pull-right photo-info">
            <div>名称：${ photo.photoNameOld }</div>
            <#--<div>拍摄日期：${ photo.shootTime }</div>-->
          </div>
        </div>
      </#list>
    </div>
  </div>

  <div class="text-center page-box">
    <input class="total-pages" type="hidden" value="${ totalPages }"/>
    <div class="my-pager"></div>
  </div>
</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
  require(['/js/libs/requirejs/config.js'], function () {
    require(['main/admin-selected-original-common']);
    <#if type == "original">
    require(['main/admin-original']);
    </#if>
  });
</script>
</body>
</html>