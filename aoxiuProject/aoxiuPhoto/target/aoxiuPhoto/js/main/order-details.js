// 订单详情入口文件
// 2015/6/3
require(['jquery'], function ($) {

  var orderId = $('#order-id').val();
  var edited = false;
  var editedValue;
  var $editBtn;
  var labels = {edit: '修改', done: '完成'};
  var $editInput;
  var editSelectedCountUrl = '/order/updateOrder.do'; // TODO

  $('.edit-btn').on('click', function () {
    if (!edited) {
      $(this)
          .siblings('.display-label').hide().end()
          .siblings('.edit-input').show().end()
          .text(labels.done);

      edited = !edited;
    }
    else { // 修改完毕提交数据
      $editBtn = $(this);
      $editInput = $editBtn.siblings('.edit-input');
      editedValue = $editInput.val();
      if (editedValue === '') {
        $editInput.focus();
        return ;
      }

      $editBtn
        .siblings('.display-label').show().end()
        .siblings('.edit-input').hide().end()
        .text(labels.edit);

      $.post(editSelectedCountUrl, {
        orderId: orderId,
        type: $editBtn.data('post-param'),
        maxCount: editedValue
      }).done(function () {
        // do nothing
      });
        
      edited = !edited;
    }
    
  });

})