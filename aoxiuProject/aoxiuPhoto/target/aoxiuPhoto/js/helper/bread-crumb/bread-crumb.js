// require jquery, ejs

(function ($, EJS) {
  'use strict';
  
  function BreadCrumb(customerSettings) {
    this._init(customerSettings) || this._event();
  }  
  
  // 私有函数:
  // _init
  // _event
  // _update
  // 公有函数
  // append
  // $current
  BreadCrumb.prototype = {
    constructor: BreadCrumb,
    
    // 初始化界面
    _init: function (customerSettings) {
      var settings = $.extend({}, BreadCrumb.defaults, customerSettings);
      this.$target = $(settings.hook);

      if (this.$target.length === 0 || !settings.template) {
        throw new TypeError('hook or template is empty');
      }
      // 初始化一次
      this.ajax = settings.ajax;
      this.callback = settings.callback;
      this.template = new EJS({ url: settings.template });
      this.config = {
        ajax: this.ajax,
        dirs: settings.dirs
      };
      if (this.$target.data('breadCrumbStatus') === 'initialized') {
        return true;
      }

      // only process the first one
      this._update();
      
      this.$target.data('breadCrumbStatus', 'initialized');
      return false;
    },
    
    // 响应事件
    _event: function () {
      var self = this;
      if (self.ajax) {
        self.$target.on('click', 'a', function (event) {
          event.preventDefault();
          $.getJSON(this.href, self.callback);

          self.config.dirs.splice($(this).parent().index() + 1);
          self._update();
        });
      }
    },

    // 更新 breadcrumb
    _update: function () {
      this.template.update(this.$target[0], this.config);
    },

    // 添加 breadcrumb
    append: function (url, text) {
      this.config.dirs.push({url: url, text: text});
      this._update();
    },
    // 返回当前 目录的jquery dom
    $current: function () {
      return this.$target.find('.active');
    }
  };
  
  // @hook html hook
  BreadCrumb.defaults = {
    // hook : '#bread-crumb',
    /*dirs: [{
      text: '主目录',
      href: '/items'
    }, {
      text: '一级目录',
      href: '/items/1'
    }, {
      text: '二级目录',
      href: '/items/2'
    }],*/
    callback: function (msg) {
      console.log(msg);
    },
    template: '/js/helper/bread-crumb/template/bread-crumb.ejs',
    ajax: true
  };
  
  // make BreadCrumb visible
  legend.helper.BreadCrumb = BreadCrumb;
}(jQuery, EJS));