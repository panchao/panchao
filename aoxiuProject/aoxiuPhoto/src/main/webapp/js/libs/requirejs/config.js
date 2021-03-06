require.config({
  baseUrl: '/js',
  paths: {
    jquery: 'libs/jquery/1.11.2/jquery.min',
    ejs: 'libs/ejs/1.0/ejs_production',
    // helper
    pagination: 'helper/pagination/pagination',
    breadCrumb: 'helper/bread-crumb/bread-crumb',

    // 上传图片
    qiniu: 'libs/qiniu/qiniu',
    plupload: 'libs/plupload/plupload.full.min'
  },

  shim: {
    ejs: {
      exports: 'EJS'
    },
    qiniu: {
      deps: ['plupload'],
      exports: 'Qiniu'
    }
  }
});