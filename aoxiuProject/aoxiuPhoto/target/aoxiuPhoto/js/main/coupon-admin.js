require(['jquery', 'ejs'], function ($, EJS) {
  var photographerId = $('#photographer').data('photographer').id;
  var trStr = '<tr><td><%= items.join("</td><td>") %></td></tr>';
  var template = new EJS({ text: trStr });

  $('.coupon .form-inline').on('click', '.btn-exchange', function () {
    var $exchangeBtn = $(this);
    var $input = $exchangeBtn.siblings().find('input');
    var code = $input.val();
    if (code.trim() === '') {
      $input.focus();
      return ;
    }
    
    $.post('/coupon/accessile', {
        id: photographerId,
        exchangeCode: code
      })
      .done(function (result) {
        console.log(result);
        var data;
        if (result.retCode == 200 ) {
          data = result.data;
          data.length > 0 && 
            $('.' + $exchangeBtn.data('targetClass')).append(template.render({items: data}));
        }
        else {
          alert('添加兑换券失败：' + result.retDesc + '，请刷新重试！');
        }
      })
      .fail(function (jqXHR, textStatus) {
        alert('添加兑换券失败: ' + textStatus + '，请刷新重试！');
      });
  });
});