<!doctype html>
<html lang="zh-CN">
<head>
  <#-- 代金券管理 -->
  <#include "partials/head.ftl">
  <link rel="stylesheet" href="/css/coupon-admin.css" />
</head>

<body class="container well">

  <#include "partials/header.ftl">
  <#include "partials/nav.ftl">

  <div class="pull-left op-panel coupon">

    <div class="form-inline clearfix">
      <div class="form-group col-md-12">
        <div class="col-md-3">
          <input type="text" class="form-control" placeholder="代金券兑换码">
        </div>
        <button class="btn btn-info btn-exchange" data-target-class="accessible">兑换</button>
      </div>
    </div>
    
    <#list coupons as coupon>
      <table class="table table-bordered table-striped table-hover">
        <caption>${ coupon.type }</caption>
        <thead>
          <tr>
            <#list coupon.heads as title>
              <th>${ title }</th>
            </#list>
          </tr>
        </thead>
        <tbody class="${ coupon.tag }">
          <#list coupon.body as items>
            <tr>
              <#list items as item>
                <td>${ item }</td>
              </#list>
          </#list>
        </tbody>
      </table>
    </#list> 

  </div>

  <script src="/js/libs/requirejs/2.1.18/require.min.js"></script>
  <script>
    require(['/js/libs/requirejs/config.js'], function () {
      require(['main/coupon-admin']);
    });
  </script>
</body>
</html>
