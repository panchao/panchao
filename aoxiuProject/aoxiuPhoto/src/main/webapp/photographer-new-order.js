
;(function ($, BreadCrumb) {
  'use strict'
  var $customers = $('#customers'),
      $customerIdInput = $('#customer-id'),
      photographerId = $('#photographer').data('photographer').id;

  var customerList = new EJS({ url: '/template/customer_list.ejs' });
  // 获取用户列表
  $.get('query/getPhotographerUsers?photographers_id='+ photographerId, customerList.update('customers'));

  $customers.on('click', 'tr', function () {
    $customers.find('tr').removeClass('selected');
    $customerIdInput.val($(this).addClass('selected').data('customer').id);
    $next.prop('disabled', false);
  });

  var $next = $('.next'),
      $prev = $('.prev'),
      $entry = $('#to-entry-code'),
      $orderForm = $('#new-order'),
      $requiredInput = $orderForm.find('input[required]'),
      allFilled,
      //orderHasSubmitted,
      orderId,
      mainAlbumId,
      breadCrumb;

  $next.on('click', function () {
    var $currentStep = $('.step-details.current');
    // 第二步“新增订单”，确保input都填写
    if ($currentStep.data('step') === 'new-order') {
      $.each($requiredInput, function (i, input) {
        allFilled = true;
        if ($.trim(input.value) === '') {
          input.focus();
          allFilled = false;
          return false;
        }
      });

      if (!allFilled) {
        return false; // 阻止进入下一步
      }
      else { // '提交订单'
        //orderHasSubmitted = true;

        $.ajax({
          type: "POST",
          url: "order/addOrder.do",
          data: $orderForm.serialize(),
          success: function(msg){
              if (msg.retCode == '200') {
                orderId = msg.data.orderId;
                mainAlbumId = msg.data.albumId;

                breadCrumb = new BreadCrumb({
                  hook: '#step-photo-upload-breadcrumb',
                  ajax: true,
                  dirs: [{
                    url: 'album?photograper_id='+ photographerId + '&album_id=' + mainAlbumId,
                    text: '主相册'
                  }],
                  callback: updateCustomerList
                });

                $.getJSON('album?photograper_id='+ photographerId +'&orderId='+ orderId, updateCustomerList);


              }
          }
        });

      }
    }

    var $nextStep = $currentStep.removeClass('current').next().addClass('current');
    if ($nextStep.next().length === 0) { // 最后一个
      $next.prop('disabled', true);
      $('#prev-next').addClass('hide');
    }

    $prev.prop('disabled', false);
    $('.step.step-current').removeClass('step-current').next().addClass('step-current');

    if ($nextStep.data('step') === 'photo-upload') { // 当前是第三步，上传图片
      $entry.removeClass('hide');
    }
    else {
      $entry.addClass('hide');
    }

    if ($nextStep[0].id === 'entry-code') {
      $.get('/admin/entry-code?orderId=' + orderId, function (msg) {
        if (msg.retCode == '200') {
          new EJS({ url: '/template/entry_code' }).update('entry-code', msg);
        }
      });
    }
  });

  $prev.on('click', function () {
    var $prevStep = $('.step-details.current').removeClass('current').prev();
    if ($prevStep.prev().length === 0) {
        $prev.prop('disabled', true);
    }
    $prevStep.addClass('current');

    $next.prop('disabled', false);
    $('.step.step-current').removeClass('step-current').prev().addClass('step-current');

    $entry.addClass('hide');
  });

  // 新建目录，添加文件夹
  var $addAlbum = $('#new-album'),
      $photoBox = $('#photo-box'),
      albumViewTemplate = '<div class="item"><a class="file-wrapper album" href="javascript:;" data-album={"id":"{{id}}"} title={{name}}><div class="item-icon item-icon-album"><span class="item-checked"></span></div><div class="file-name-box"><span class="file-name {{hide_file_name}}">{{name}}</span></div></a><div class="edit-name {{hide_edit}}"><input class="box" type="text" data-name="{{name}}"/><span class="widget sure"></span><span class="widget cancel"></span></div></div>',
      photoViewTemplate = '<div class="item"><a class="file-wrapper" href="javascript:;" title="{{name}}"><div class="item-icon" style="background-image:url({{src}});"><span class="item-checked"></span></div><div class="file-name"><span>{{name}}</span></div></a><div class="edit-name hide"><input class="box" type="text" /><span class="widget sure"></span><span class="widget cancel"></span></div></div>',
      $albumView = $(albumViewTemplate.replace('{{hide_file_name}}', 'hide'));

  //var albumsTemplate = new EJS({ url: '/template/albums' });

  $addAlbum.click(function () {
    $photoBox.append($albumView.clone());
    //$albumView.find('.box').focus(); // todo
    //albumsTemplate.update('photo-box', {id: undefined, hide_file_name: 'hide'});
  });
  function sure($this) {
    var $item = $this.parents('.item'),
        $album = $item.find('.album'),
        $input = $item.find('.box'),
        name = $.trim($input.val());
    if (name === '') {
      name = '未命名-'+Date.now();
    }
    //$input.val(name);

    $album.attr('title', name);

    $item.find('.edit-name').addClass('hide');
    $item.find('.file-name').text(name).removeClass('hide');

    // 发送目录的名字和摄影师id
    $.post('/album', {
      orderId: orderId,
      albumName: name
    },
    function success(data) {
      var albumId = data.album_id;
      $album.data('album', {id: albumId});//.attr('href', '#'+albumId);
    });
  }
  $photoBox.on('keydown', '.box', function (e) {
    e.which === 13 && sure($(this));
  });
  $photoBox.on('click', '.sure', function () {
    sure($(this));
  });
  $photoBox.on('click', '.cancel', function () {
    $(this).parents('.item').remove();
  });

  function updateCustomerList(result) {
    var html = '';
    if (result.retCode == 200) {

      $.each(result.data.albums || [], function (i, album) {
        html += albumViewTemplate.replace('{{hide_edit}}', 'hide').replace(/{{id}}/g, album.id).replace(/{{name}}/g, album.name);
      });
      $.each(result.data.photos || [], function (i, photo) {
        html += photoViewTemplate.replace('{{src}}', photo.src).replace(/{{name}}/g, photo.name);
      });

      $photoBox.html(html);
    }

  }

  $photoBox.on('click', '.album', function () {
    var $this = $(this);
    var $input = $this.parents('.item').find('.box');
    if ($this.data('album').id === '{{id}}') {
      $input.focus();
      return false;
    }
    var ajaxUrl = 'album?photograper_id='+ photographerId +'&album_id='+ $this.data('album').id;
    $.getJSON(ajaxUrl, updateCustomerList);

    breadCrumb.append(ajaxUrl, this.title);
  });

  // 新建客户 2015/5/17 13:34
  // 依赖 formValidation.js
  $('#createCustomerForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: 'The phone is required'
                    }
                }
            }
        }
    });
    // 提交表单
    $('#createCustomerForm').submit(function () {
      if ($('.has-error').length === 0) {
        $('input[name="photographersId"]').val(photographerId);

        $.ajax({
          type: "POST",
          url: "/admin/customer",
          data: $('#createCustomerForm').serialize(),
          success: function(msg){
              console.log(msg);
              msg.retCode == '200' && $customers.append(customerList.render('customers', msg));
          },
          complete: function () {
            $("#createCustomerModal").modal('hide');
          }
        });

        return false;
      }
    });

    $("#sendUserInfo").click(function() {
      $('#createCustomerForm').submit();
    });

    // 第三步，上传图片，面包屑导航 step-photo-upload

}(jQuery, window.legend.helper.BreadCrumb));
