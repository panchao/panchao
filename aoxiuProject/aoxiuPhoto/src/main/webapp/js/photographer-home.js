
;(function () {
  // @global photographerId
  var photographerId = $('#photographer').data('photographer').id;


  $.get('/home/storage?photographerId=' + photographerId, function (msg) {
    if (msg.retCode == 200) {
      //console.log(msg.data);
      new EJS({ 'url': '../../template/storage' }).update('storage', msg);
    }
  });

  $.get('/order/getOrder.do?photographerId=' + photographerId, function (msg) {
    if (msg.retCode == 200) {
      //console.log(msg.data);
      new EJS({ 'url': '../../template/order_list' }).update('order-list', msg);
    }
  });
}());