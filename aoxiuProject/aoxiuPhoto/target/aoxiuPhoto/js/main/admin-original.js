require(['breadCrumb', 'ejs'], function(BreadCrumb, EJS) {
  var photographerId = $('#photographer').data('photographer').id;
  var albumAjaxUrl = '/photographer/'+ photographerId +'/original&albumId=';
  var $photos = $('.photo-box .photos');

  function TemplateController(hook, templateUrl) {
    this.$box = $(hook);
    this.box = this.$box[0];
    this.template = new EJS({ url: templateUrl });
  }
  TemplateController.prototype.update = function (ajaxUrl, callback) {
    var self = this;

    $.get(ajaxUrl)
      .done(function (msg) {
        if (msg.retCode == 200) {
          self._update(msg.data);
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
  TemplateController.prototype._update = function (msg) {
    this.template.update(this.box, msg);
  };

  function update (result) {
    album.update
  }

  // start it
  var album = new TemplateController('.albums', '/template/album.ejs');
  var photo = new TemplateController('.photos', '/template/photo.ejs');
  var pager = new Pagination({
    hook: '.my-pager'
  });
  breadCrumb = new BreadCrumb({
    hook: '.my-breadcrumb',
    ajax: true,
    dirs: [{
      url: albumAjaxUrl + $('.album-box input').val(),
      text: '主相册'
    }],
    callback: update;
  });

  $('.photo-box .albums').on('click', 'a', function () {
    updateAlbum(albumAjaxUrl + $(this).data('album').id);
  });

});
