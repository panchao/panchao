<!doctype html>
<html lang="zh-CN">
<head>
  <title>评价管理</title>
	<meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="/css/layout.css" />
  <link rel="stylesheet" href="/css/timeline.css" />
  <link rel="stylesheet" href="/css/order-details.css" />

</head>

<body class="container well">

<header class="header-user">
  <img src="/img/logo.png" />

  <span class="pull-right">
    <span id="photographer" data-photographer='{"id":"${photographers_id}"}'>XXX</span>，您好<a class="btn btn-warning" href="/users/logout">退出</a><div>
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
  <div class="row row-order">
    <span class="col-md-5">客户姓名：${order.customerName}</span><span class="col-md-7">联系方式：${order.phoneNumber}</span>
  </div>

  <div class="row row-order">
    <div class="row-order-inner">
      <span class="col-md-12">订单类型：${order.orderType}</span>
    </div>
    <div class="row-order-inner">
      <span class="col-md-5">精修张数：<span class="display-label">${order.selectedCount}</span><input class="edit-input" type="number" value="${order.selectedCount}" style="display:none;" />张<button class="btn btn-success edit-btn" data-post-param="selectedCount">修改</button><span class="alert alert-danger" style="display:none;">修改失败</span></span>

      <span class="col-md-7">额外精修价格：<span class="display-label">${order.pricePerPhoto}</span><input class="edit-input" type="number" value="${order.pricePerPhoto}" style="display:none;" />元/张<button class="btn btn-success edit-btn" data-post-param="extraPrice">修改</button><span class="alert alert-danger" style="display:none;">修改失败</span></span>
    </div>
  </div>
  <input type="hidden" id="order-id" value="${order.orderId}" />

  <div class="row row-order">
    <ol class="timeline">
      <li class="timeline-step ">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">上传原片</span>
      </li>

      <li class="timeline-step ">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">用户选定修片</span>
      </li>

      <li class="timeline-step">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">上传精修片</span>
      </li>

      <li class="timeline-step ">
        <i class="timeline-step-marker"></i>
        <span class="timeline-step-title">完成评价</span>
      </li>
    </ol>

    <div class="row-order-inner text-center">
      <a href="/pictures/unSelectManager.do?photographerId=${order.photographerId}&customerId=${order.userId}" class="btn btn-info">管理原片</a>
      <a href="/pictures/checkSelectedList.do?photographers_id=${order.photographerId}&customerId=${order.userId}" class="btn btn-info">查看用户选择修片列表</a>
      <a href="/pictures/selectedManager.do?photographerId=${order.photographerId}&customerId=${order.userId}" class="btn btn-info">管理精修片</a>
    </div>

    <div class="row-order-inner text-center">
        <img src="${order.unSelectedUrl}" alt="原片地址二维码" width="200" height="200" />
        <img src="${order.selectedUrl}" alt="精修片地址二维码" width="200" height="200" />
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