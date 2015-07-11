<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 管理精修片或原片 -->
  <#include "../partials/head.ftl">
  <link rel="stylesheet" href="/css/album.css" />
  <link rel="stylesheet" href="/css/photo-list.css" />
  <link rel="stylesheet" href="/css/selected-admin.css" />
</head>

<body class="container well">

<#include "../partials/header.ftl">
<#include "../partials/nav.ftl">
</nav>

<div class="pull-left op-panel">
  <!-- <input class="type" type="hidden" value="${ type }" /> -->
  <input class="type" type="hidden" value="<%= type %>" />

  <!-- <#if type == "original"> -->
  <% if (type == "original") { %>
  <div class="my-breadcrumb"></div>
  <div class="album-box">
    <input type="hidden" value="<%= mainAlbumId %>" />
    <div class="albums">
      <% data.albums.forEach(function (album) { %>
        <% include ../partials/album.ejs %>
      <% }); %>
    </div>
  </div>
  <% } %>
  <!-- </#if> -->

  <div class="row">
    <!-- 2.1 取消注释 -->
    <!-- <div class="col-md-6">共${ totalPhotos }张</div>
    <!-- 2.2 注释掉下面一行 -->
    <div class="col-md-6">共<%= totalPhotos %>张</div>
    <div class="col-md-6">
      <button class="btn btn-info" id="pickfiles">上传照片</button>
      <input type="hidden" class="domain" value="<%= domain %>">
      <input type="hidden" class="uptoken-url" value="<%= uptokenUrl %>">
      <input type="hidden" class="album-id" value="<%= albumId %>">
    </div>
  </div>


  <div class="photo-box container-fluid clearfix">
    <!-- 2.3 取消下面注释
    <#list photo as data>
      <div class="col-md-4">
        <img src="${ photo.src }" width="110" height="95" alt="${ photo.name }" data-img="{&quot;id&quot;: &quot;${ photo.id }&quot;}" />
        <span class="delete-icon" title="删除该相片">×</span>
        <div class="pull-right photo-info">
          <div>名称：${ photo.name }</div>
          <div>拍摄日期：${ photo.shootTime }</div>
        </div>
      </div>
    </#list>
    -->
    <!-- 2.4 注释掉下面 -->
    <div class="photos">  
      <% data.photos.forEach(function (photo) { %>
        <div class="col-md-4">
          <% include ../partials/photo.ejs %>
        </div>
      <% }); %>
    </div>

  </div>

  <!-- 1.1 去掉下面input的注释 -->
  <!-- <input type="hidden" value="${ totalPages }"/> -->
  <!-- 1.2 注释掉下面input -->
  <div class="text-center page-box">
    <input type="hidden" value="<%= totalPages %>"/>
    <div class="my-pager"></div>
  </div>
</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
  require(['/js/libs/requirejs/config.js'], function () {
    require(['main/admin-selected-original-common']);
    <% if (type == "original") { %>
    require(['main/admin-original']);
    <% } %>
  });
</script>
</body>
</html>