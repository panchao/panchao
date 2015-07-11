require(['breadCrumb'], function(BreadCrumb) {
  breadCrumb = new BreadCrumb({
    hook: '#step-photo-upload-breadcrumb',
    ajax: true,
    dirs: [{
      url: albumAjaxUrl + photographerId + '&album_id=' + mainAlbumId,
      text: '主相册'
    }],
    callback: updateCustomerList
  });
});
