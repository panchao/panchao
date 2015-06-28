/*
 * 上传图片
 */
var qiniu = require('qiniu');
var express = require('express');
var config = require('./config');
var app = express();
var TOTAL_PAGES = 10;

app.configure(function() {
    app.use(express.static(__dirname + '/'));
});
app.set('views', __dirname + '/views');
app.engine('html', require('ejs').renderFile);
app.use(express.urlencoded());

app.listen(config.port, function() {
    console.log('Listening on port %d', config.port);
});

// 入口页面
// 1 新增订单
app.get('/users/gotoPhotoNewOrder.do', function(req, res) {
    res.render('photo_new_order.html');
});
// 2 摄影师首页
app.get('/users/gotoPhotographerHome.do', function(req, res) {
    res.render('photo_home.html');
});
// 3 评价管理
app.get('/admin/comments/', function(req, res) {
    res.render('评价管理.html', {
      totalPages: TOTAL_PAGES,
      commentInfo: [{
        orderId: '001',
        customerName: '省道',
        customerPhone: '18877639473',
        status: '未评价',
        starNum: '',
        comment: ''
      }, {
        orderId: '002',
        customerName: '套色',
        customerPhone: '18877339473',
        status: '已评价',
        starNum: 3,
        comment: '不错'
      }]

    });
});
// 4 订单详情
app.get('/order/gotoOrderDetail.do', function(req, res) {
    res.render('order-details.ftl');
});


app.get('/users/query/getPhotographerUsers', function (req, res) {
    res.json({
      retDesc: 'success',
      retCode: 200,
      data: [{
      "customerId":0,
      "realName":"xiaoming",
      "phoneNumber":"18501038260",
      "email": "liuchzong@qq.com",
      "photographersId":1,
      "weChat":"1234567",
      "qqnumber":"1234567"
    }, {
      "customerId": 1,
      "realName":"222",
      "phoneNumber":"222",
      "email":"222",
      "photographersId":1,
      "weChat":"22",
      "qqnumber":"222"
  }]});
});
app.post('/users/order/addOrder.do', function (req, res) {
  //res.json(req.body);
  res.json({
    retCode: 200,
    retDesc: 'success',
    data: {
      orderId: 1000,
      albumId: 2000
    }
  });
})

// 上传图片
// /uptoken
// /pictures/getToken?type=3
app.get('/pictures/getToken', function(req, res, next) {
    var token = uptoken.token();

    res.header("Cache-Control", "max-age=0, private, must-revalidate");
    res.header("Pragma", "no-cache");
    res.header("Expires", 0);
    if (token) {
        res.json({
            uptoken: token
        });
    }
});

app.post('/uploadfiles', function (req, res) {
    res.json({
      username: req.body.username,
      files: req.body.files
    })
});

qiniu.conf.ACCESS_KEY = config.ACCESS_KEY;
qiniu.conf.SECRET_KEY = config.SECRET_KEY;

var uptoken = new qiniu.rs.PutPolicy(config.Bucket_Name);

app.post('/album', function (req, res) {
    //console.log(req.body);
    res.json({
      album_id: 123456
    })
});

// 匹配 /album?photograper_id=111&album_id=sdfds
app.get('/users/album', function (req, res) {
  //console.log('params:', req.param);
  //console.log('query', req.query);
  //res.send('hello');
  var undef;
  var query = req.query;

  if (query.photograper_id !== undef && query.album_id !== undef) {
    // 进入子目录
    res.json({
      retDesc: 'success',
      retCode: 200,
      data: {
        albums: [{
          name: 'sub_album_name#1-' + query.album_id,
          id: 0
        }, {
          name: 'sub_album_name#2-' + query.album_id,
          id: 1
        }],
        photos: [{
          name: 'sub_lizard-girl.jpg',
          src: 'http://qiniu-plupload.qiniudn.com/lizard-girl.jpg?imageView2/1/w/100/h/100'
        }, {
          name: 'sub_lizard-girl2.jpg',
          src: 'http://qiniu-plupload.qiniudn.com/lizard-girl.jpg?imageView2/1/w/100/h/100'
        }]
      }
    });
  }
  else if (query.photograper_id !== undef && query.album_id === undef) {
    // 进入主目录
    res.json({
      retDesc: 'success',
      retCode: 200,
      data: {
        albums: [{
          name: 'main_album_name#1',
          id: 0
        }, {
          name: 'main_album_name#2',
          id: 1
        }],
        photos: [{
          name: 'main_lizard-girl.jpg',
          src: 'http://qiniu-plupload.qiniudn.com/lizard-girl.jpg?imageView2/1/w/100/h/100'
        }, {
          name: 'main_lizard-girl2.jpg',
          src: 'http://qiniu-plupload.qiniudn.com/lizard-girl.jpg?imageView2/1/w/100/h/100'
        }]
      }
    });
  }

});

// 新建客户
app.post('/admin/customer', function (req, res) {
  var reqBody = req.body;
  console.log(reqBody);

  res.json({
    retDesc: 'success',
    retCode: 200,
    data: [{
      "customerId": 3,
      "realName": reqBody.username,
      "phoneNumber": reqBody.phone,
      "email": reqBody.email,
      "photographersId": 3,
      "weChat": reqBody.wechat,
      "qqnumber": reqBody.qq
    }]
    // error
    /*data: {
      retDesc: 'error',
      retCode: '400' // 你上次好像有类似的编号，可以改
      // data 无需传输
    }*/

  });
});

// entry-code?orderId=1000

app.get('/admin/entry-code', function (req, res) {
  res.json({
    retCode: 200,
    retDesc: 'success',
    qrCode: 'http://p1.qhimg.com/t013eade5cc125a2008.jpg',
    code: req.query.orderId
  });
});

// '/home/storage?photographerId=' + photographerId
app.get('/home/storage', function (req, res) {
  res.json({
    retCode: 200,
    retDesc: 'success',
    data: [{
      total: '1024MB',
      occupied: '240MB'
    }]
  });
});

// /order/getOrder.do?photographerId=' + photographerId
// 等待修片中：客户没有选择照片，或者四步没有全部完成（后续加）
// 修片中：客户已经选择照片，摄影师正在修片
// 完成：摄影师已经完成修片和其他操作
app.get('/order/getOrder.do', function (req, res) {
  res.json({
    retCode: 200,
    retDesc: 'success',
    data: [{
      customerId: '1024',
      orderId: '240',
      customerName: '只手',
      watermarkType: 'A',
      maxSelectCount: '20',
      extraPricePerPhoto: '10',
      status: '已完成',
      createTime: '2015-5-31'
    }, {
      customerId: '1025',
      orderId: '241',
      customerName: '是飞',
      watermarkType: 'B',
      maxSelectCount: '21',
      extraPricePerPhoto: '11',
      status: '修片中',
      createTime: '2015-5-30'
    }]
  });
});

// /userComments?page=2&count=10
// /appraisal/getAllAppraisal.do?filter=commented
// /appraisal/getAllAppraisal.do?filter=uncommented

app.get('/appraisal/getAllAppraisal.do', function (req, res) {
  if (req.query.customerName) {
    res.json({
      retCode: 200,
      retDesc: 'success',
      totalPages: 1,
      commentInfo: [{
        orderId: '111',
        customerName: '张三',
        customerPhone: '18844793272',
        status: '已评价',
        starNum: '5',
        comment: '修片技术相当好'
      }]
    });
  }
  else { //
    res.json({
      retCode: 200,
      retDesc: 'success',
      totalPages: TOTAL_PAGES,
      commentInfo: [{
        orderId: '111-' + req.query.page,
        customerName: '只手#' + req.query.page,
        customerPhone: '18844793272',
        status: '已评价',
        starNum: '5',
        comment: '修片技术相当好'
      }, {
        orderId: '112-' + req.query.page,
        customerName: '的是#' + req.query.page,
        customerPhone: '18844793242',
        status: '未评价',
        starNum: '',
        comment: ''
      }]
    });
  }
});