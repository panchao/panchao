// "���۹���" ����ļ�
// 2015/6/3
require(['jquery', 'ejs', 'pagination'], function ($, EJS, Pagination) {

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
    byName: '/appraisal/getAllAppraisal.do?photographerId='+ photographerId +'&customerName=', 
    filter: '/appraisal/getAllAppraisal.do?photographerId='+ photographerId +'&filter=' // all, uncommented, commented
  };
  ajaxUrls.current = ajaxUrls.filter + 'all&page=';

  var commentTemplate = new EJS({ url: '/template/commentInfo' });

  // �ɹ����� callback������ totalPage ������Ⱦ pagination
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

    ajaxUrls.current = ajaxUrls.byName + name + '&page=';

    updateCommentInfo(ajaxUrls.current, function (totalPages) {
      page.update({current: 1, total: totalPages});
    });

  });

  var $select = $('.filter select');
  $('#search-by-comment').on('click', function () {
    var filter = $select.find('option:selected').val();

    ajaxUrls.current = ajaxUrls.filter + filter +'&page=';
    updateCommentInfo(ajaxUrls.current + 1, function (totalPages) {
      page.update({current: 1, total: totalPages});
    });
  });

});
