require(['jquery', 'ejs', 'pagination', 'qiniu'], function ($, EJS, Pagination, Qiniu) {
  // hook: jquery selector, only apply to the first match one
  // templateUrl: ejs path
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
  TemplateController.prototype.append = function (msg) {
    console.log('msg', msg);
    console.log(this.template);
    this.$box.append(this.template.render(msg));
  };

  function getAlbumId() {
    var id = $('.breadcrumb > .active').data('url').match(/&album_id=([^&]*)/);
    return id? id[1] : '';
  }

  // start it
  var photographerId = $('#photographer').data('photographer').id;
  var adminPhotoType = $('input.admin-photo-type').val(); // selected or original

  console.log('adminPhotoType', adminPhotoType);
  var basicUrl;
  switch (adminPhotoType) {
    case 'original': 
      basicUrl = '/photographer/'+ photographerId +'/original';
      break;
    case 'selected': 
      basicUrl = '/photographer/'+ photographerId +'/selected-photos';
      break;
    default:
      throw new Error('只能是精修片或者原片管理');
  }

  var ajaxUrl = basicUrl + '?count=4&page=';
  var photos = new TemplateController('.photos', '/template/selected-admin.ejs');
  var pager = new Pagination({
    hook: '.my-pager',
    total: $('.total-pages').val(),
    onPageClick: function (event, page) {
      var url = ajaxUrl + page;
      adminPhotoType === 'original' && (url += '&albumId=' + getAlbumId());
      photos.update(url);
    }
  });

  // delete photos
  var tips = {
    deleteErrorTip: '删除失败，请刷新重试',
    uploadSuccess: '上传成功',
    errorClass: 'alert-danger',
    successClass: 'alert-success',
    error: function _error(errorTip, cls) {
      return tips.tipFactory(errorTip, cls, 'error');
    },
    success: function (successTip, cls) {
      return tips.tipFactory(successTip, cls, 'success');  
    },
    
    // with cache
    tipFactory: function _tipFactory(tip, cls, type) {
      var cache = _tipFactory[tip + cls + type];

      if (!cls) {
        cls = tips.errorClass;
        if (type === 'success') {
          cls = tips.successClass;
        }
      }

      if (!cache) {
        // alert('make it fresh');
        cache = _tipFactory[tip + cls + type] = $('<div title="' + tip + '" class="' + cls + '">' + tip + '</div>');
      }

      return cache.clone();
    }
  };
  var $photoBox = $('.photos');
  $photoBox.on('click', '.delete-icon', function () {
    var $deleter = $(this);
    var deleteTried = $deleter.data('deleteTried');
    if (deleteTried) {
      return false;
    }
    
    var photoId = $deleter.prev('img').data('img').id;
    var $photoInfo = $deleter.next('.photo-info');
    var undef;
    //console.log('photoId', photoId);
    if (photoId === '' || photoId == undef) {
      if (!deleteTried) {
        $photoInfo.append(tips.error(tips.deleteErrorTip));
        $deleter.data('deleteTried', true);
      }
      
      throw new TypeError('img id should not be empty', photoId);
    }

    $.ajax({
      url: basicUrl,
      method: 'DELETE',
      data: {id: photoId}
    })
      .fail(function (jqXHR, textStatus) {
        //deleteTried = $deleter.data('deleteTried');
        if (!deleteTried) {
          $photoInfo.append(tips.error(tips.deleteErrorTip));
          $deleter.data('deleteTried', true);
        }
      })
      .done(function (data, textStatus) {
        if (data.retCode == 200) {
          $deleter.parent().fadeOut(function () {
            $(this).remove();
          });
        }
        else { // error
          //deleteTried = $deleter.data('deleteTried');
          if (!deleteTried) {
            $photoInfo.append(tips.error(data.retDesc || tips.deleteErrorTip));
            $deleter.data('deleteTried', true);
          }
        }
      });

  });
  // 上传图片 https://github.com/qiniu/js-sdk
  function itemExists(arr, item) {
	  return arr.indexOf(item) >= 0;
	}
  var filenames = [];
  var uploadedFiles = [];
  var albumId = $('.album-id').val();
  var defaultSrc = '/img/default.png';
  var loadingSrc = '/img/loading.gif';
  var uploader = Qiniu.uploader({
    runtimes: 'html5,flash,html4', //上传模式，依次退化
    browse_button: 'pickfiles', //上传选择的点选按钮，**必需**
    max_file_size: '100mb',
    flash_swf_url: 'js/plupload/Moxie.swf',
    chunk_size: '4mb',
    uptoken_url: $('.uptoken-url').val(),
    domain: $('.domain').val(),
    auto_start: true,
    init: {
      'FilesAdded': function(up, files) {
        var uploadPhotos = [];
        var name;
        var uploadedBefore;

        files.forEach(function (file) {
          uploadedBefore = itemExists(uploadedFiles, name = file.name);
          if (uploadedBefore) {
            up.removeFile(file);
            alert(name + '已经上传！');
          }
          else {
            uploadPhotos.push({
              src: loadingSrc,
              name: name,
              shootTime: 'xxxx-x-x xx:xx',
              id: null,
              boxClass: 'uploading'
            });
          }
        });

        photos.append({
          data: uploadPhotos
        });
        $photoBox.find('.uploading .delete-icon').addClass('hidden');
      },
      'UploadComplete': function() {
        if (filenames.length === 0) {
          return ;
        }

        var $uploading = $photoBox.find('.uploading');
        var photo;
        var uploadUrl = '/pictures/uploadPictures.do';
        $.ajax({
          url: uploadUrl,
          type: 'POST',
          data: JSON.stringify({
            album_id: albumId,
            names: filenames,
            type:"selected"
          }),
          contentType: 'application/json; charset=utf-8'
        })
          .fail(function (jqXHR, textStatus) {
            // do nothing
          })
          .done(function (result) {
            console.log('result:', result);
              alert(result);
              data = result['data'];
              console.log('data:', data);
              alert(data);
              $uploading.find('img').each(function (index) {
              var $img = $(this);
              photo = data[index];
              if (!photo) {
                throw new TypeError('POST ' + uploadUrl + ' 返回数据没有包含上传照片的id');
              }

              $img.data('img').id = photo.photoId;
              $img.siblings('.photo-info').find('.shootTime').text(photo.shootTime);
            });
          });

        filenames = [];
        $uploading.removeClass('uploading')
          .find('.delete-icon').removeClass('hidden');
      },
      'FileUploaded': function(up, file, info) {
        //console.log('\nFileUploaded: ' + file.name);
        var name = file.name;
        var uploadedBefore = itemExists(uploadedFiles, name);
        if (uploadedBefore) {
          return ;
        }

        filenames.push(name)
        uploadedFiles.push(name);
        // 获取上传成功后的文件的Url
        // domain http://qiniu-plupload.qiniudn.com/
        // http://7xis67.com2.z0.glb.qiniucdn.com/
        var domain = up.getOption('domain'); 
        var sourceLink = domain + name + '?imageView2/1/w/100/h/100'; 
        var uploadedImg = $('img[alt="'+ name +'"]')[0];
        uploadedImg.onerror = function () {
          this.onerror = null;
          this.src = defaultSrc;
        };
        uploadedImg.src = sourceLink;

        $(uploadedImg).siblings('.photo-info').append(tips.success(tips.uploadSuccess));
      },
      'Error': function(up, err, errTip) {
        errTip.indexOf('614') !== -1 && (errTip += '；请重命名（建议：' + username+ Date.now() + err.file.name + '），然后上传');
        console.error(errTip);
        $photoBox.find('.uploading .photo-info').append(tips.error(errTip));
      }
    }
  });

});