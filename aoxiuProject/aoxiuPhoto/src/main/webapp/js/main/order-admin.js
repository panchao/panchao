require(['jquery', 'ejs', 'pagination'], function ($, EJS, Pagination) {
  function AdminOrder(customerSettings) {
    var settings = $.extend({}, AdminOrder.defaults, customerSettings);

    this.orderInfo = settings.orderInfo;
    this.onSearch = settings.onSearch;

    this.$filter = $(settings.filter.box);
    this.$filterSelect = this.$filter.find('select');
    this.ajaxUrls = settings.filter.ajaxUrls;
    this.ajaxUrls.current = settings.filter.ajaxUrls.byType + 'all&page=';

    this._filter();
  }

  AdminOrder.prototype = {
    constructor: AdminOrder,

    _filter: function () {
      var self = this;
      var value;
      var type;

      // press Enter key to send request
      $('.filter input').keydown(function (event) {
        if (event.which === 13) {
          if ((value = $.trim(this.value)) === '') {
            return false;
          }

          type = $(this).siblings('.btn-search').data('filterType');
          self._update(type, value);
        }
      });
      // press search button to send request
      self.$filter.on('click', '.btn-search', function () {

        type = $(this).data('filterType');

        if (type === 'byName') {
          var $nameInput = $('.filter input');
          value = $.trim($nameInput.val());
          if (value === '') {
            $nameInput.focus();
            return ;
          }
        }
        else if (type === 'byType') {
          value = self.$filterSelect.find('option:selected').val();
        }

        self._update(type, value);

      });
    },

    _update: function (type, value) {
      var self = this;
      self.ajaxUrls.current = self.ajaxUrls[type] + value + '&page=';

      self.orderInfo.update(self.ajaxUrls.current + 1, function (totalPages) {
        self.onSearch(totalPages);
      });
    }
  };

  AdminOrder.defaults = {
    filter: {
      box: '.filter' // ,
      // ajaxUrls: {
      //   byName: 'ajaxurl/...',
      //   byType: 'ajaxurl/...'
      // }
    }
  };

  // hook: jquery selector, only apply to the first match one
  // templateUrl: ejs path
  function OrderInfo(hook, templateUrl) {
    this.box = $(hook)[0];
    this.template = new EJS({ url: templateUrl });
  }
  OrderInfo.prototype.update = function (ajaxUrl, callback) {
    var self = this;
    $.get(ajaxUrl).done(function (msg) {
      if (msg.retCode == 200) {
        self.template.update(self.box, msg);

        callback && callback(msg.totalPages);
      }
      else {
        console.error('retCode != 200');
      }
    })
    .fail(function () {
      console.error('failed GET ' + ajaxUrl);
    });
  }


  // start it
  var photographerId = $('#photographer').data('photographer').id;
  var orderInfo = new OrderInfo('#order-info', '/template/order-admin-info.ejs');
  var pager = new Pagination({
    hook: '#order-admin-pager',
    total: $('#order-count').val(),
    onPageClick: function (event, page) {
      orderInfo.update(app.ajaxUrls.current + page);
    }
  });
  // main app
  var app = new AdminOrder({
    orderInfo: orderInfo,
    onSearch: function (totalPages) {
      pager.update({current: 1, total: totalPages});
    },
    filter: {
      box: '.filter',
      ajaxUrls: {
        byName: '/order/searchOrder.do?photographerId='+ photographerId +'&customerName=',
        byType: '/order/filter.do?photographerId='+ photographerId +'&filter='
      }
    }
  });


});