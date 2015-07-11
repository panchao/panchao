// 订单详情入口文件
// 2015/6/3
require(['jquery'], function($) {

  var orderId = $('#order-id').val();
  var edited = false;
  var editedValue;
  var $editBtn;
  var labels = {
    edit: '修改',
    done: '完成'
  };
  var $editInput;
  var editSelectedCountUrl = '/order/updateOrder.do'; // TODO
  var preValue;

  $('.edit-btn').on('click', function() {
    if (!edited) {
      /*$(this)
        .siblings('.display-label').hide().end()
        .siblings('.edit-input').show().end()
        .text(labels.done);*/
      $editBtn = $(this);
      preValue = $editBtn.siblings('.display-label').hide().text();

      $editBtn.text(labels.done).siblings('.edit-input').show()
        .end().siblings('.alert-danger').hide();

    } // 修改完毕提交数据
    else {

      $editBtn = $(this);
      $editInput = $editBtn.siblings('.edit-input');
      editedValue = $editInput.val();

      if (editedValue === '') {
        $editInput.focus();
        return;
      }
      
      $editBtn.prop('disabled', true);

      $.post(editSelectedCountUrl, {
        orderId: orderId,
        type: $editBtn.data('post-param'),
        maxCount: editedValue
      }).done(function(msg) {
        // do nothing
        var $displayLabel = $editBtn.prop('disabled', false).siblings('.display-label');

        if (msg.retCode == 200) {
          $displayLabel.text(editedValue).siblings('.alert-danger').hide();
        }
        else {
          // console.log(preValue);

          $displayLabel.text(preValue).siblings('.alert-danger').show();
        }

        $displayLabel.show();
        $editBtn.siblings('.edit-input').hide().end() 
          .text(labels.edit);
      });

    }
    
    edited = !edited;

  });

})
