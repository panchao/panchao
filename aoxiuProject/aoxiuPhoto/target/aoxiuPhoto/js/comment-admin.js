;(function ($, EJS, Pagination, undef) {
  
  if ($ === undef || EJS === undef || Pagination === undef) {
    throw new TypeError('commentAdmin.js depends on jQuery, EJS and pagination.js');
  }

  var page = new Pagination({
    hook: '#comment-info-pagination',
    template: '/js/helper/pagination/template/pagination.ejs',
    total: $('#comment-count').val(),
    onPageClick: function (event, page) {
      updateCommentInfo(ajaxUrls.current + page);
    }
  });

  var photographerId = $('#photographer').data('photographer').id;
  var ajaxUrls = {
    byName: '/appraisal/getAppraisal.do?photographerId='+ photographerId +'&customerName=',
    filter: '/appraisal/getAppraisal.do?photographerId='+ photographerId +'&filter=' // all, uncommented, commented
  };
  ajaxUrls.current = ajaxUrls.filter + 'all&page=';

  var commentTemplate = new EJS({ url: '/template/commentInfo' });

  // 成功调用 callback
  function updateCommentInfo(ajaxUrl, callback) {

    $.get(ajaxUrl).done(function (msg) {
      if (msg.retCode == 200) {
        commentTemplate.update('comment-info', msg);
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

  $('#search-by-name').on('click', function () {
    var name = $.trim($('.filter input').val());
    if (name === '') {
      return ;
    }

    ajaxUrls.current = ajaxUrls.byName + name + '&page=1';

    updateCommentInfo(ajaxUrls.current, function (totalPages) {
      page.update({current: 1, total: totalPages});
    });

  });

  var $select = $('.filter select');
  $('#search-by-comment').on('click', function () {
    var filter = $select.find('option:selected').val();

    ajaxUrls.current = ajaxUrls.filter + filter +'&page=1';
    updateCommentInfo(ajaxUrls.current, function (totalPages) {
      page.update({current: 1, total: totalPages});
    });
  });

}(window.jQuery, window.EJS, window.legend && window.legend.helper.Pagination));