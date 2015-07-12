require(['breadCrumb', 'ejs'], function(BreadCrumb, EJS) {
  var photographerId = $('#photographer').data('photographer').id;
  var customerId = $('input.customer-id').val();
  //var albumAjaxUrl = '/photographer/'+ photographerId +'/original&albumId=';
  // 获取相片和目录
  var basicUrl = '/pictures/originalManagerJson.do?count=10&photographerId=' + photographerId + "&customerId=" + customerId + '&albumId=';

  function TemplateController(hook, templateUrl) {
    this.$box = $(hook);
    this.box = this.$box[0];
    this.template = new EJS({ url: templateUrl });
  }
  //TemplateController.cache = ;
  TemplateController.prototype.updateByUrl = function (ajaxUrl, callback) {
    var self = this;

    $.get(ajaxUrl)
      .done(function (msg) {
        if (msg.retCode == 200) {
          self.updateByResult(msg);
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
  TemplateController.prototype.updateByResult = function (msg) {
    this.template.update(this.box, msg);
  };



  // start it
  var albumController = new TemplateController('.albums', '/views-node/templates/album.ejs');
  var photoController = new TemplateController('.photos', '/views-node/templates/photo.ejs');
  // 原片没有分页
  // var pager = new Pagination({
  //   hook: '.my-pager',
  //   total: $('.total-pages').val(),
  //   onPageClick: function (event, page) {
  //     var url = ajaxUrl + '&albumId=' + getAlbumId() + '&page=' + page;
  //     albumController.update(url);
  //     photoController.update(url);
  //   }
  // });
  function updateByResult(result) {
    console.log(result);
    if (result.retCode == '200') {
      result.albums && albumController.updateByResult(result);
      result.photos && photoController.updateByResult(result);
    }
    else {
      console.error('获取图片和目录返回数据的retCode != 200');
    }
    
  }
  var breadCrumb = new BreadCrumb({
    hook: '.my-breadcrumb',
    dirs: [{
      url: basicUrl + $('.album-box input').val(),
      text: '主相册'
    }],
    callback: updateByResult
  });

  $('.albums').on('click', 'a', function () {
      // updateAlbum(basicUrl + $(this).data('album').id);
      var url = basicUrl + $(this).data('album').id;
      albumController.updateByUrl(url);
      photoController.updateByUrl(url);

      breadCrumb.append(url + '&page=1', this.title);
  });

});
