
require(['jquery', 'ejs'], function ($, EJS) {
  var photographerId = $('#photographer').data('photographer').id;

  $.get('/home/storage?photographerId=' + photographerId, function (msg) {
    if (msg.retCode == 200) {
      new EJS({ 'url': '/template/storage' }).update('storage', msg);
    }
  });

  $.get('/order/getOrder.do?photographerId=' + photographerId, function (msg) {
    if (msg.retCode == 200) {
      new EJS({ 'url': '/template/order_list' }).update('order-list', msg);
    }
  });
});