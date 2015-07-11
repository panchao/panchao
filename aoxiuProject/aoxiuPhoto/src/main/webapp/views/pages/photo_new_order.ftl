<!doctype html>
<html lang="zh-CN">
<head>
	<#-- <title>摄影师新增订单</title> -->
	<#include "../partials/head.ftl">
  <link rel="stylesheet" href="/css/album.css" />
  <link rel="stylesheet" href="/css/new-order-steps.css" />
</head>

<body class="container well">

<#include "../partials/header.ftl">
<#include "../partials/nav.ftl">

<div class="pull-left op-panel">
  <div id="steps">
    <span class="step step-current">新建客户</span>
    <span class="step">新增订单</span>
    <span class="step">上传图片</span>
    <span class="step">入口文件</span>
  </div>

  <div class="step-details-box">
    <!-- 第一步 -->
    <div class="step-details current">
      <table class="table table-hover table-bordered">
        <thead>
          <th>姓名</th>
          <th>电话号码</th>
          <th>email</th>
          <th>微信</th>
          <th>QQ</th>
        </thead>

        <!-- 由js动态生成 -->
        <tbody id="customers"></tbody>
      </table>
      <!-- TODO -->
      <button id="create-customer" class="btn btn-info" data-toggle="modal" data-target="#createCustomerModal">新建客户</button>

    </div>
    <!-- 第二步 新建订单-->
    <div class="step-details" data-step="new-order">
      <form id="new-order">
        <div class="form-group">
          <label for="order-type">订单类型：</label>
          <input type="text" id="order-type" name="order-type" value="test" placeholder="必填" required/>
        </div>

        <div class="form-group">
          <label for="fine-count">精修张数：</label>
          <input type="text" id="fine-count" name="max_count" value="test" placeholder="必填" required/>
        </div>

        <div class="form-group">
          <label for="extra-price">额外精修价格：</label>
          <input type="text" id="extra-price" name="price_per_photo" value="test" placeholder="必填" required/>
        </div>
        <div class="form-group">
          <label for="watermark-type">水印类型：</label>
          <select id="watermark-type" name="watermark_type">
            <option value="">不添加水印</option>
            <option value="A">类型A</option>
            <option value="B">类型B</option>
            <option value="C">类型C</option>
          </select>
        </div>

        <input type="hidden" name="album_id" value="1"/>
        <input type="hidden" name="photographers_id" value="${photographers_id}"/>
        <input type="hidden" id="customer-id" name="user_id"/>
        <input type="hidden" name="type" value="cameraman"/>
      </form>
    </div>
    <!-- 第三步 图片上传-->
    <div class="step-details" data-step="photo-upload">

      <div id="step-photo-upload-breadcrumb"></div>

      <div id="photo-box"></div>

      <div>
        <div>
          <input type="hidden" id="domain" value="http://qiniu-plupload.qiniudn.com/">
          <input type="hidden" id="uptoken_url" value="/pictures/getToken?type=3">
        </div>
          <div class="body">
              <div class="col-md-12">
                  <div id="container">
                    <a class="btn btn-default btn-lg" id="new-album" href="javascript:;" role="button">
                      <i class="glyphicon glyphicon-plus"></i>
                      <sapn>新建目录</sapn>
                    </a>
                    <a class="btn btn-default btn-lg" id="pickfiles" href="javascript:;" role="button">
                      <sapn>上传</sapn>
                    </a>
                  </div>
              </div>

              <div style="display:none" id="success" class="col-md-12">
                  <div class="alert-success">
                      队列全部文件处理完毕
                  </div>
              </div>
              <div class="col-md-12 ">
                  <table class="table table-striped table-hover text-left"   style="margin-top:40px;display:none">
                      <thead>
                        <tr>
                          <th class="col-md-4">Filename</th>
                          <th class="col-md-2">Size</th>
                          <th class="col-md-6">Detail</th>
                        </tr>
                      </thead>
                      <tbody id="fsUploadProgress">
                      </tbody>
                  </table>
              </div>
          </div>

          <div class="modal fade body" id="myModal-img" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                  <h4 class="modal-title" id="myModalLabel">图片效果查看</h4>
                </div>
                <div class="modal-body">
                  <div class="modal-body-wrapper text-center">
                      <a href="" target="_blank" >
                          <img src="" alt="" data-key="" data-h="">
                      </a>
                  </div>
                  <div class="modal-body-footer">
                      <div class="watermark">
                          <span>水印控制：</span>
                          <a href="#" data-watermark="NorthWest" class="btn btn-default">
                              左上角
                          </a>
                          <a href="#" data-watermark="SouthWest" class="btn btn-default">
                              左下角
                          </a>
                          <a href="#" data-watermark="NorthEast" class="btn btn-default">
                              右上角
                          </a>
                          <a href="#" data-watermark="SouthEast" class="btn btn-default disabled">
                              右下角
                          </a>
                          <a href="#" data-watermark="false" class="btn btn-default">
                              无水印
                          </a>
                      </div>
                       <div class="imageView2">
                          <span>缩略控制：</span>
                          <a href="#" data-imageview="large" class="btn btn-default disabled">
                              大缩略图
                          </a>
                          <a href="#" data-imageview="middle" class="btn btn-default">
                              中缩略图
                          </a>
                          <a href="#" data-imageview="small" class="btn btn-default">
                              小缩略图
                          </a>
                      </div>
                      <div class="imageMogr2">
                          <span>高级控制：</span>
                          <a href="#" data-imagemogr="left" class="btn btn-default no-disable-click" >
                              逆时针
                          </a>
                          <a href="#" data-imagemogr="right" class="btn btn-default no-disable-click">
                              顺时针
                          </a>
                          <a href="#" data-imagemogr="no-rotate" class="btn btn-default">
                              无旋转
                          </a>
                      </div>
                      <div class="text-warning">
                          备注：小图片水印效果不明显，建议使用大图片预览水印效果
                      </div>
                  </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
                </div>
              </div>
            </div>
          </div>
      </div>

    </div>

    <!-- 第四步 生成入口文件 js 生成-->
    <div id="entry-code" class="step-details"></div>
  </div>
</div>

  <div id="controller">
    <div id="prev-next">
      <button class="prev btn btn-lg btn-primary my-btn" disabled>上一步</button><button class="next btn btn-lg btn-primary my-btn" disabled>下一步<span id="to-entry-code" class="to-entry-code hide">：生成入口文件</span></button>
    </div>

  </div>

</div>

<!-- modal -->
<div>
  <!-- 1 新建客户 -->
  <div class="modal fade" id="createCustomerModal" tabindex="-1" role="dialog" aria-labelledby="Login" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">新建客户</h4>
            </div>

            <div class="modal-body">
                <!-- The form is placed inside the body of modal -->
                <form id="createCustomerForm" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">姓名</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="username" placeholder="姓名" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">手机</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="phone" placeholder="手机" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">微信</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="wechat" placeholder="微信号"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">QQ</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="qq" placeholder="QQ号" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">邮箱</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="email" placeholder="邮箱" />
                        </div>
                    </div>

                    <input type="hidden" name="photographersId" value="${photographers_id}"/>
                </form>
            </div>

            <div class="modal-footer">
              <button type="submit" class="btn btn-primary" id="sendUserInfo">确定</button>
              <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
</div>

<!-- 依赖项 jquery, ejs, config, bootstrap-->
<script src="/js/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="/js/libs/ejs/1.0/ejs_production.js"></script>
<script src="/js/helper-config.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>

<!--
表单验证
formValidation
framework/bootstrap.min.js
bread-crumb
-->
<script src="/js/formvalidation/js/formValidation.min.js"></script>
<script src="/js/formvalidation/js/framework/bootstrap.min.js"></script>

<script src="/js/helper/bread-crumb/bread-crumb.js"></script>
<script src="/js/photographer-new-order.js"></script>

<!--
上传文件至七牛
plupload.full.min.js
zh_CN.js
ui.js
qiniu.js
highlight.js
-->
<script src="/js/plupload/plupload.full.min.js"></script>
<script src="/js/plupload/i18n/zh_CN.js"></script>
<script src="/js/ui.js"></script>
<script src="/js/qiniu.js"></script>
<!-- <script src="/js/highlight/highlight.js"></script> -->
<script src="/js/main-upload.js"></script>
</body>
</html>