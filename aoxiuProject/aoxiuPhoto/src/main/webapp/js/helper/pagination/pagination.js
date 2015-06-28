//;(function ($, EJS, helper, undef) {
define(['jquery', 'ejs'], function ($, EJS) {

  /*if ($ === undef || EJS === undef || helper === undef) {
    throw new TypeError('pagination.js depends on jQuery, EJS and helper-config.js');
  }*/

  function Pagination(customerSettings) {

    this._$pageBox = $(customerSettings.hook);
    if (this._$pageBox.length === 0) {
      throw new TypeError('the pagination must be attached to an existing element');
    }

    var settings = $.extend({}, Pagination.defaults, customerSettings);

    this._init(settings);
    this._addEvent();
  }

  // public: _init, _addEvent, _update
  // private: update
  Pagination.prototype = {
    constructor: Pagination,

    // 初始化界面
    _init: function (settings) {
      this._$pageBox.addClass(settings.boxClass);
      this._config = {total: +settings.total, current: +settings.current};
      this._templateController = new EJS({ url: settings.template });
      this._onPageClick = settings.onPageClick;

      this.update(this._config);
    },

    // 添加事件
    _addEvent: function () {
      var self = this;
      // 点击页码
      self._$pageBox.on('click', '.my-pager:not(.active)', function (event) {
        var current = +this.textContent; // 转换成数字, 特别重要
        self._onPageClick(event, current);

        self._config.current = current;
        self.update(self._config);
      });

      // 点击前一页
      self._$pageBox.on('click', '.prev:not(.disabled)>a', function () {
        self._onPageClick(event, --self._config.current);
        self._update(self._config);
      });

      // 点击后一页
      self._$pageBox.on('click', '.next:not(.disabled)>a', function () {
        self._onPageClick(event, ++self._config.current);
        self._update(self._config);
      });
    },

    // config = {
    //   total: 10, // 总页数
    //   current: 1 // 当前第几页
    // }
    // 内部接口不会出界
    _update: function () {
      if (this._config.current < 1 || this._config.current > this._config.total) {
        throw new RangeError('this._config.current out of range');
      }
      this._templateController.update(this._$pageBox[0], this._config);
    },
    // 外部接口必须严格保证边界条件
    // 参数可选
    update: function (myConfig) {
      if (!myConfig) {
        return ;
      }

      var config = $.extend({}, this._config, myConfig);
      config.current = +config.current || 0;
      config.total = +config.total || 0;

      if (config.current < 1) {
        config.current = 1;
      }
      else if (config.current > config.total) {
        config.current = config.total;
      }

      // 保持同步
      this._config = config;

      this._templateController.update(this._$pageBox[0], config);
    }

  };


  Pagination.defaults = {
    // hook: '', // jquery selector and only the first one applied
    total: 10,
    current: 1,
    template: '/js/helper/pagination/template/pagination.ejs',
    onPageClick: function (event, page) {
      console.log(page);
    },
    boxClass: 'pagination'

  };

  // make Pagination public
  //helper.Pagination = Pagination;

  return Pagination;

});
//}(window.jQuery, window.EJS, window.legend && window.legend.helper));