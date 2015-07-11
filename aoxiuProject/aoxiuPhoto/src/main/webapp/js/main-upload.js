/*global Qiniu */
/*global plupload */
/*global FileProgress */
/*global hljs */
;(function($, BreadCrumb) {
  var username = getUserame(); //获取方法mock，username 修改上传图片名称为 customerType-Time-helo.jpg
  var filenames = []; // 记录修改后的文件名
  //var files = [];
  var $photoBox = $('#photo-box'),
    photoViewTemplate = '<div class="item"><a class="file-wrapper" href="javascript:void(0);" title="{{name}}"><div class="item-icon"><span class="item-checked"></span></div><div class="file-name"><span>{{name}}</span><div class="edit-name hide"><input class="box" type="text" value=""><span class="widget sure"></span><span class="widget cancel"></span></div></div></a></div>';

  function getUserame() {
    return 'legend80s';
  }

  function getAlbumId() {
    var id = $('.breadcrumb > .active').data('url').match(/&album_id=([^&]*)&?/);
    return id ? id[1] : '';
  }

  function itemNotExits(arr, item) {
    return arr.indexOf(item) === -1;
  }



  var uploader = Qiniu.uploader({
    runtimes: 'html5,flash,html4',
    browse_button: 'pickfiles',
    container: 'container',
    drop_element: 'container',
    max_file_size: '100mb',
    flash_swf_url: 'js/plupload/Moxie.swf',
    dragdrop: true,
    chunk_size: '4mb',
    uptoken_url: $('#uptoken_url').val(),
    domain: $('#domain').val(),
    // downtoken_url: '/downtoken',
    // unique_names: true,
    // save_key: true,
    // x_vars: {
    //     'id': '1234',
    //     'time': function(up, file) {
    //         var time = (new Date()).getTime();
    //         // do something with 'time'
    //         return time;
    //     },
    // },
    auto_start: true,
    init: {
      'FilesAdded': function(up, files) {
        $('table').show();
        $('#success').hide();
        plupload.each(files, function(file) {
          var progress = new FileProgress(file, 'fsUploadProgress');
          progress.setStatus("等待...");
          //console.log('FilesAdded:', file.name);
        });
      },
      'BeforeUpload': function(up, file) {
        var progress = new FileProgress(file, 'fsUploadProgress');
        var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
        if (up.runtime === 'html5' && chunk_size) {
          progress.setChunkProgess(chunk_size);
        }
      },
      'UploadProgress': function(up, file) {
        var progress = new FileProgress(file, 'fsUploadProgress');
        var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
        progress.setProgress(file.percent + "%", up.total.bytesPerSec, chunk_size);

      },
      'UploadComplete': function() {
        $('#success').show();

        /*$.post('/uploadfiles', JSON.stringify({
              album_id: getAlbumId(),
              names: filenames
            }),
                        'json' // 返回数据类型
                    );*/

        $.ajax({
          url: '/pictures/uploadPictures.do', //TODO
          type: 'POST',
          data: JSON.stringify({
            //albumName: albumBreadCrumb.$current().text(),
            // $('#step-photo-upload .breadcrumb .active').text()
            album_id: getAlbumId(),
            names: filenames
          }),
          dataType: "json",
          contentType: 'application/json; charset=utf-8'
        });
      },
      'FileUploaded': function(up, file, info) {
        var progress = new FileProgress(file, 'fsUploadProgress'),
          name = file.name,
          notExits = itemNotExits(filenames, name),
          defaultSrc = 'default.png';

        progress.setComplete(up, info, function(src) {
          if (notExits) {
            // 添加photo view
            var $photoView = $(photoViewTemplate.replace(/{{name}}/g, name));
            var img = new Image();
            img.src = src;
            img.onerror = function() {
              $photoView.find('.item-icon')[0].style.backgroundImage = 'url(' + defaultSrc + ')';
            };
            $photoView.find('.item-icon')[0].style.backgroundImage = 'url(' + src + ')';
            $photoBox.append($photoView);
          }
        });

        if (notExits) {
          filenames.push(name);
          //files.push(name);
        }

      },
      'Error': function(up, err, errTip) {
          $('table').show();
          var progress = new FileProgress(err.file, 'fsUploadProgress');
          progress.setError();
          errTip.indexOf('614') !== -1 && (errTip += '请重命名(建议:' + username + Date.now() + err.file.name + ')，然后上传')
          progress.setStatus(errTip);
        }
        // ,
        // 'Key': function(up, file) {
        //     var key = "";
        //     // do something with key
        //     return key
        // }
    }
  });

  uploader.bind('FileUploaded', function() {
    console.log('hello man,a file is uploaded');
  });
  $('#container').on(
    'dragenter',
    function(e) {
      e.preventDefault();
      $('#container').addClass('draging');
      e.stopPropagation();
    }
  ).on('drop', function(e) {
    e.preventDefault();
    $('#container').removeClass('draging');
    e.stopPropagation();
  }).on('dragleave', function(e) {
    e.preventDefault();
    $('#container').removeClass('draging');
    e.stopPropagation();
  }).on('dragover', function(e) {
    e.preventDefault();
    $('#container').addClass('draging');
    e.stopPropagation();
  });


  $('body').on('click', 'table button.btn', function() {
    $(this).parents('tr').next().toggle();
  });


  var getRotate = function(url) {
    if (!url) {
      return 0;
    }
    var arr = url.split('/');
    for (var i = 0, len = arr.length; i < len; i++) {
      if (arr[i] === 'rotate') {
        return parseInt(arr[i + 1], 10);
      }
    }
    return 0;
  };

  $('#myModal-img .modal-body-footer').find('a').on('click', function() {
    var img = $('#myModal-img').find('.modal-body img');
    var key = img.data('key');
    var oldUrl = img.attr('src');
    var originHeight = parseInt(img.data('h'), 10);
    var fopArr = [];
    var rotate = getRotate(oldUrl);
    if (!$(this).hasClass('no-disable-click')) {
      $(this).addClass('disabled').siblings().removeClass('disabled');
      if ($(this).data('imagemogr') !== 'no-rotate') {
        fopArr.push({
          'fop': 'imageMogr2',
          'auto-orient': true,
          'strip': true,
          'rotate': rotate,
          'format': 'png'
        });
      }
    } else {
      $(this).siblings().removeClass('disabled');
      var imageMogr = $(this).data('imagemogr');
      if (imageMogr === 'left') {
        rotate = rotate - 90 < 0 ? rotate + 270 : rotate - 90;
      } else if (imageMogr === 'right') {
        rotate = rotate + 90 > 360 ? rotate - 270 : rotate + 90;
      }
      fopArr.push({
        'fop': 'imageMogr2',
        'auto-orient': true,
        'strip': true,
        'rotate': rotate,
        'format': 'png'
      });
    }

    $('#myModal-img .modal-body-footer').find('a.disabled').each(function() {

      var watermark = $(this).data('watermark');
      var imageView = $(this).data('imageview');
      var imageMogr = $(this).data('imagemogr');

      if (watermark) {
        fopArr.push({
          fop: 'watermark',
          mode: 1,
          image: 'http://www.b1.qiniudn.com/images/logo-2.png',
          dissolve: 100,
          gravity: watermark,
          dx: 100,
          dy: 100
        });
      }

      if (imageView) {
        var height;
        switch (imageView) {
          case 'large':
            height = originHeight;
            break;
          case 'middle':
            height = originHeight * 0.5;
            break;
          case 'small':
            height = originHeight * 0.1;
            break;
          default:
            height = originHeight;
            break;
        }
        fopArr.push({
          fop: 'imageView2',
          mode: 3,
          h: parseInt(height, 10),
          q: 100,
          format: 'png'
        });
      }

      if (imageMogr === 'no-rotate') {
        fopArr.push({
          'fop': 'imageMogr2',
          'auto-orient': true,
          'strip': true,
          'rotate': 0,
          'format': 'png'
        });
      }
    });

    var newUrl = Qiniu.pipeline(fopArr, key);

    var newImg = new Image();
    img.attr('src', 'loading.gif');
    newImg.onload = function() {
      img.attr('src', newUrl);
      img.parent('a').attr('href', newUrl);
    };
    newImg.src = newUrl;
    return false;
  });

}(jQuery, window.legend.helper.BreadCrumb));
