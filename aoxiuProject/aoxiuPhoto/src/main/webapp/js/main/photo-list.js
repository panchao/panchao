require(['jquery', 'ejs', 'pagination'], function ($, EJS, Pagination) {
  // hook: jquery selector, only apply to the first match one
  // templateUrl: ejs path
  function TemplateController(hook, templateUrl) {
    this.box = $(hook)[0];
    this.template = new EJS({ url: templateUrl });
  }
  TemplateController.prototype.update = function (ajaxUrl, callback) {
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
  };

  // start it
  var photographerId = $('#photographer').data('photographer').id;
  var ajaxUrl = '/users/'+ photographerId +'/photos?count=4&page=';
  var orderInfo = new TemplateController('.photos', '/template/photo-list.ejs');
  var pager = new Pagination({
    hook: '.my-pager',
    total: $('.op-panel input:hidden').val(),
    onPageClick: function (event, page) {
      orderInfo.update(ajaxUrl + page);
    }
  });
  // main app
});