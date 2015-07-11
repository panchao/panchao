<!doctype html>
<html lang="zh-CN">
<head>
  <title>${ title }</title>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
  <link rel="stylesheet" href="/css/layout.css" />
  <link rel="stylesheet" href="/css/photo-list.css" />
  <link rel="stylesheet" href="/css/retouching-admin.css" />
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
  <!-- 1.1 去掉下面input的注释 -->
   <input type="hidden" value="${totalPages }"/>
  <!-- 1.2 注释掉下面input -->
  <#--<input type="hidden" value="<%= totalPages %>"/>-->
  <div class="row">
    <!-- 2.1 取消注释 -->
     <div class="col-md-6">共${totalPhotos}张</div>
    <!-- 2.2 注释掉下面一行 -->
    <#--<div class="col-md-6">共<%= totalPhotos %>张</div>-->
    <div class="col-md-6">
      <button class="btn btn-info" id="pickfiles">上传照片</button>
      <input type="hidden" class="domain" value="http://qiniu-plupload.qiniudn.com/">
      <input type="hidden" class="uptoken-url" value="http://localhost:8080/pictures/getToken?type=3">
      <input type="hidden" class="album-id" value="${masterContentId}">
     </div>
  </div>

  <div class="photos container-fluid clearfix">
    <!-- 2.3 取消下面注释-->
    <#--<#list data as photo>-->
      <#--<div class="col-md-4">-->
        <#--<img src="${ photo.photoSrc }/${photo.photoNameOld}" width="110" height="95" data-img='{"id": ${ photo.photoId }}' />-->
        <#--<span class="delete-icon" title="删除该相片">×</span>-->
        <#--<div class="pull-right photo-info">-->
          <#--<div>名称：${ photo.photoNameOld }</div>-->
        <#--</div>-->
      <#--</div>-->
    <#--</#list>-->
  <#list data as photo>
      <div class="col-md-4">
          <img src="${ photo.src }" width="110" height="95" data-img='{"id": ${ photo.id }}' />
          <span class="delete-icon" title="删除该相片">×</span>
          <div class="pull-right photo-info">
              <div>名称：${ photo.name }</div>
          </div>
      </div>
  </#list>
    <!-- 2.4 注释掉下面 -->
    <!--<% data.forEach(function (photo) { %>-->
        <!--<div class="col-md-4">-->
          <!--<img src="<%= photo.src %>" width="110" height="95" alt="<%= photo.name %>" data-img='{"id": "<%= photo.id %>"}' />-->
          <!--<span class="delete-icon" title="删除该相片">×</span>-->
          <!--<div class="pull-right photo-info">-->
            <!--<div>名称：<%= photo.name %></div>-->
            <!--<div>拍摄日期：<%= photo.shootTime %></div>-->
          <!--</div>-->
        <!--</div>-->
    <!--<% }); %>-->

  </div>

  <div class="text-center">
    <div class="my-pager"></div>
  </div>
</div>

<script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
<script>
require(['/js/libs/requirejs/config.js'], function () {
  require(['main/admin-selected-original-common']);
});
</script>
</body>
</html>