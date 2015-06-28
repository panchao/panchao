$(function () {
	//分页函数
	/**
	**num_sum: 总页数， num_display_entries：显示的主体页数， pageselectCallback：回调函数
	**使用 http://www.zhangxinxu.com/jq/pagination_zh/插件
	**/
	var initPagination = function(num_sum, num_display_entries, pageselectCallback) {
		// 创建分页
		$("#Pagination").pagination(num_sum, {
			num_edge_entries: 1, //边缘页数
			num_display_entries: num_display_entries, 
			callback: pageselectCallback,
			items_per_page: 1//每页显示1项
		});
	}
	//公用方法
	var _self = {};
	_self.saveBtn = '#user-save';
	_self.searchBtn = '#search-user';
	_self.lookBtn = 'button[data="look-info"]';
	_self.editBtn = 'button[data="edit-info"]';
	_self.cancelBtn = 'button[data="cancel-info"]';
	_self.page_index = 0;
	//摄影师ID
	_self.photographersId = $('#sidebar li[class="active"]').attr('photographersId');
	_self.success_tip = function (info) {
		_self.tip_hide();
		$('#user-success > a').text(info);
		$('#user-success').show();
	}
	_self.info_tip = function (info) {
		_self.tip_hide();
		$('#user-info > a').text(info);
		$('#user-info').show();
	}
	_self.warn_tip = function (info) {
		_self.tip_hide();
		$('#user-warn > a').text(info);
		$('#user-warn').show();
	}
	_self.err_tip = function (info) {
		_self.tip_hide();
		$('#user-err > a').text(info);
		$('#user-err').show();
	}
	_self.main_tip = function (info) {
		_self.tip_hide();
		$('#search-info > a').text(info);
		$('#search-info').show();
	}
	_self.check_tel = function (info) {
		var reg = /^1\d{10}$/;
		if(reg.test(info)) {
			return true;
		} else {
			return false;
		}
	}
	/*
	_self.check_weixin = function (info) {
		
	}
	*/
	_self.check_qq = function (info) {
		var reg = /^[1-9][0-9]{4,9}$/;
		if(reg.test(info)) {
			return true;
		} else {
			return false;
		}
	}
	_self.check_mail = function (info) {
		var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		if(reg.test(info)) {
			return true;
		} else {
			return false;
		}
	}
	//判断是否为空
	_self.check_trim = function (info) {
		if(info === '') {
			console.log("info:" + info + "--" + "true");
			return true;
		} else {
			console.log("info:" + info + "--" + "false");
			return false;
		}
	}
	//清空新增用户弹出框的数据
	_self.user_info_clear = function () {
		$('#username').attr('disabled', false).val('');
		$('#tel').val('');
		$('#weixin').val('');
		$('#qq').val('');
		$('#mail').val('');
		$('#beizhu').val('');
		$('#user-title').text('新增客户');
	}
	//隐藏所有提示框
	_self.tip_hide = function () {
		$('#user-success, #user-info, #user-warn, #user-err, #search-info').hide();
	}
	//拼接表格数据
	_self.get_array_data = function (result) {
		var node = [];
		node.push("<tr><td>" + result.realName + "</td>");
		node.push("<td>" + result.phoneNumber + "</td>");
		node.push("<td>" + result.weChat + "</td>");
		node.push("<td>" + result.qqnumber + "</td>");
		node.push("<td>" + result.email + "</td>");
		node.push("<td></td>");
		node.push("<td><button type='submit' class='btn btn-default' data='look-info' customerId=" + result.customerId + ">查看订单</button>");
		node.push("<button type='submit' class='btn btn-default' data='edit-info' customerId=" + result.customerId + ">修&nbsp&nbsp&nbsp&nbsp改</button>");
		node.push("<button type='submit' class='btn btn-default' data='cancel-info' customerId=" + result.customerId + ">删&nbsp&nbsp&nbsp&nbsp除</button></td></tr>");
		var str = node.join('');
		return str;
	}
	//拼接订单详情数据
	_self.get_order_array_data = function (data) {
		var node = [];
		var result = data;
		node.push('<tr><td>' + result.orderId + '</td>');
		node.push('<td>' + result.addWaterMark + '</td><td>' + result.maxSelectCount + '</td>');
		node.push('<td>' + result.photographersId + '</td><td>' + result.giftAlbumsType + '</td>');
		node.push('<td>' + result.giftAlbumsType + '</td><td>' + result.getCode + '</td>');
		node.push('<td>' + result.getCodeSelected + '</td><td>' + result.pricePerPhoto + '</td>');
		node.push('<td>' + result.selectPhotoType + '</td><td>' + result.orderStep + '</td>');
		node.push('<td>' + result.createTime+ '</td><td>' + result.updateTime + '</td>');
		var str = node.join('');
		return str;
	}
	//新增客户，成功后，讲放回数据append到表格中
	_self.add_user_data_append = function (data) {
		var result = data.data;
		if(result) {
			var str = _self.get_array_data(result);
			$('#user-tbody').append(str);
		}
	}
	//新增客户成功的回调函数
	_self.add_user_success_cb = function (data) {
		if(data && data.retCode && data.retCode == 200) {
			_self.success_tip('数据保存成功！');
			window.setTimeout(function () {
				$('#modal-user').modal('hide');
				_self.add_user_data_append(data);
			}, 1000);
		} else {
			_self.err_tip('数据保存失败，请稍后重试！');
		}
	}
	//新增客户失败的回调函数
	_self.add_user_error_cb = function () {
	 	_self.err_tip('数据保存失败，请稍后重试！');
	 }
	//搜索客户信息成功后回调函数
	_self.search_success_cb = function (data) {
		_self.tip_hide();
		if(data && data.retCode && data.retCode == 200) {
			var results = data.data;
			var totalCount = data.totalCount;
			var totalPages = data.totalPages;
			if(results.length > 0) {
				var node = [];
				for(var i = 0; i < results.length; i++) {
					node.push(_self.get_array_data(results[i]));
				}
				var str = node.join('');
				$('#user-tbody').empty().append(str);
				if(_self.page_index == 0) {
					$('#Pagination').empty();
					initPagination(totalPages, 10, _self.search_page_cb);
					_self.page_index = 1;
				}
			}
		} else {
			_self.main_tip('客户搜索出错，请稍后重试！');
		}
	}
	//搜索客户信息失败后回调函数
	_self.search_error_cb = function () {
		_self.main_tip('客户搜索出错，请稍后重试！');
		var index = $('#Pagination span[class="current"]').text();
		$('#Pagination span[class="current"]').replaceWith('<a href="#">' + index + '</a>');
	}
	//删除客户成功回调函数
	_self.user_cancel_success_cb = function (data) {
		if(data && data.retCode && data.retCode == 200) {
			$('tr[class*="delete"]').remove();
			$(_self.tempNode).remove();
		} else {
			_self.main_tip('删除客户失败，请稍后重试！');
		}
	}
	//删除客户失败回调函数
	_self.user_cancel_error_cb = function () {
		_self.main_tip('删除客户失败，请稍后重试！');
	}
	//编辑客户信息成功后，replace原节点
	_self.edit_user_data_replace = function (data) {
		var result = data.data;
		if(result) {
			var str = _self.get_array_data(result);
			$('tr[class*="edit"]').replaceWith(str);
		}
	}
	//编辑客户信息保存成功回调函数
	_self.edit_user_success_cb = function (data) {
		if(data && data.retCode && data.retCode == 200) {
			_self.success_tip('数据保存成功！');
			window.setTimeout(function () {
				$('#modal-user').modal('hide');
				_self.edit_user_data_replace(data);
			}, 1000);
		} else {
			_self.err_tip('数据保存失败，请稍后重试！');
		}
	}
	//编辑客户信息保存失败回调函数
	_self.edit_user_error_cb = function () {
		_self.err_tip('数据保存失败，请稍后重试！');
	}
	//查看订单，成功回调函数
	_self.look_order_success_cb = function (data) {
		if(data && data.data) {
			var results = data.data;
			var node = [];
			for(var i = 0; i < results.length; i++) {
				node.push(_self.get_order_array_data(results[i]));
			}
			var str = node.join('');
			$('#order-tbody').empty().append(str);
			$('#modal-order-detail').modal();
		} else {
			_self.main_tip('查看订单失败，请稍后再试！');
		}
	}
	//查看订单，失败回调函数
	_self.look_order_error_cb = function () {
		_self.main_tip('查看订单失败，请稍后再试！');
	}
	//搜索分页回调函数 page_index: 选择的页数
	_self.search_page_cb = function (page_index, jq) {
		if(page_index != 0) {
			_self.page_index = page_index;
			var url = '/customer/photographersSearchCustomers.do';
			var photographerId = _self.photographerId;
			var customerName = $(_self.searchBtn).attr('customerName');
			var pageNum = page_index;
			var recordPerPage = 10;
			var obj = {};
			obj.photographerId = photographerId;
			obj.customerName = customerName;
			obj.pageNum = pageNum;
			obj.recordPerPage = recordPerPage;
			_self.ajax(url, obj, _self.search_success_cb, _self.search_error_cb);
		} else {
			return false;
		}
	} 
	//初始化页面时，分页回调函数 page_index: 选择的页数
	 _self.init_page_cb = function (page_index, jq) {
	 	if(page_index != 0) {
			_self.page_index = page_index;
			var url = '/customer/getPhotographersUser.do';
			var photographerId = _self.photographerId;
			var pageNum = page_index;
			var recordPerPage = 10;
			var obj = {};
			obj.photographerId = photographerId;
			obj.pageNum = pageNum;
			obj.recordPerPage = recordPerPage;
			_self.ajax(url, obj, _self.search_success_cb, _self.search_error_cb);
		} else {
			return false;
		}
	 }
	_self.ajax = function (url, obj, success_cb, error_cb) {
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			data: obj,
			success: function (data) {
				success_cb(data);
			},
			error: function () {
				 error_cb();
			}
		});
	}
	//弹出窗口，初始化编辑界面
	_self.init_edit_view = function () {
		$('#user-title').text('编辑客户信息');
		var node = $('tr[class*="edit"]').find('td');
		if(node.length > 0) {
			var username = node.eq(0).text();
			var tel = node.eq(1).text();
			var weixin = node.eq(2).text();
			var qq = node.eq(3).text();
			var mail = node.eq(4).text();
			var beizhu = node.eq(5).text();
			$('#username').val(username).attr('disabled', true);
			$('#tel').val(tel);
			$('#weixin').val(weixin);
			$('#qq').val(qq);
			$('#mail').val(mail);
			$('#beizhu').val(beizhu);
			$(_self.saveBtn).attr('flag', 'edit');
			$(_self.saveBtn).attr('customerId', $(_self.editBtn).attr('customerId'));
			$('#modal-user').modal();
		} else {
			_self.main_tip('数据出错，请稍后再试！');
		}
	}
	

	//点击新增客户，弹出模态框
	$('#add-user').click( function (event) {
		event.preventDefault();
		_self.user_info_clear();
		_self.tip_hide();
		$(_self.saveBtn).attr('flag', 'add');
		$('#modal-user').modal();
		//var data = {"retCode":"200","retDesc":"SUCESS","data":{"customerId":14,"realName":"111111","phoneNumber":"18501038260","email":"123@126.com","photographersId":1,"weChat":"11","qqnumber":"937113766"}};
		//_self.success_cb(data);
	});

	//新增客户，点击保存
	$(_self.saveBtn).click( function () {
		var username = $('#username').val();
		var tel = $('#tel').val();
		var weixin = $('#weixin').val();
		var qq = $('#qq').val();
		var mail = $('#mail').val();
		if(_self.check_trim(username) || _self.check_trim(tel)) {
			_self.warn_tip('姓名和手机号不能为空！');
			return false;
		} else if (!_self.check_tel(tel)) {
			_self.warn_tip('手机号格式不对！');
			return false;
		} else if (!_self.check_trim(qq) && !_self.check_qq(qq)) {
			_self.warn_tip('QQ号格式不对！');
			return false;
		} else if(!_self.check_trim(mail) && !_self.check_mail(mail)) {
			_self.warn_tip('电子邮箱格式不对！');
			return false;
		} else {
			_self.tip_hide();
			var obj = {};
			obj.username = username;
			obj.phone = tel;
			obj.wechat = weixin;
			obj.photographersId = _self.photographersId;
			obj.qq = qq;
			obj.email = mail;
			console.log('ajax_obj:' + JSON.stringify(obj));
			if($(_self.saveBtn).attr('flag') === 'edit') {
				var url = '/customer/updateCustomer.do';
				obj.customerId = $(_self.saveBtn).attr('customerId');
				//var data = {"retCode":"200","retDesc":"SUCESS","data":{"customerId":14,"realName":"111111","phoneNumber":"18501038260","email":"123@126.com","photographersId":1,"weChat":"11","qqnumber":"937113766"}};
				//_self.edit_user_success_cb(data);
				_self.ajax(url, obj, _self.edit_user_success_cb, _self.add_user_error_cb);
			} else {
				var url = '/customer/addCustomer.do';
				_self.ajax(url, obj, _self.add_user_success_cb, _self.add_user_error_cb);
			}
		}
	});

	//点击搜索
	$(_self.searchBtn).click( function (event) {
		//取消a的默认事件
		if(event && event.preventDefault) {
			event.preventDefault();
		} else {
			window.event.returnValue = false;
		}
		_self.tip_hide();
		var search_username = $('#search_username').val();
		if(_self.check_trim(search_username)) {
			_self.main_tip('客户名字不能为空！');
			$('#user-tbody').empty();	
		} else {
			$(_self.searchBtn).attr('customerName', search_username);
			var obj = {};
			obj.customerName = search_username;
			obj.photographersId = _self.photographersId;
			obj.pageNum = 1;
			obj.recordPerPage = 10;
			var url = '/customer/photographersSearchCustomers.do';
			//var data = {"retCode":200,"retDesc":"SUCESS",'totalPages':20,'totalCount':50,"data":[{"customerId":14,"realName":"111111","phoneNumber":"18501038260","email":"123@126.com","photographersId":1,"weChat":"11","qqnumber":"937113766"},{"customerId":14,"realName":"111111","phoneNumber":"18501038260","email":"123@126.com","photographersId":1,"weChat":"11","qqnumber":"937113766"}]};
			//_self.search_success_cb(data);
			_self.ajax(url, obj, _self.search_success_cb, _self.search_error_cb);
		}
	});

	//点击查看订单
	$('#user-tbody').on('click', _self.lookBtn, function () {
		$this = $(this);
		_self.tip_hide();
		var customerId = $this.attr('customerId');
		var photographerId = $this.attr('photographer');
		window.location.href = "/order/gotoOrderDetail.do?customerId=" + customerId + "&photographers_id=" + photographerId;
		//if(_self.check_trim(customerId)) {
		//	_self.main_tip('查看订单失败，请稍后再试！');
		//} else {
		//	var url = '/order/gotoOrderDetail.do';
		//	var obj = {};
		//	obj.customerId = customerId;
		//	//var data = {'retCode':200,'retDesc':'SUCESS','data':[{'orderId':0,'userId':'1','addWaterMark':'1','maxSelectCount':20,'photographersId':1,'giftAlbumsType':'1','getCode':'1234567','getCodeSelected':'2222','pricePerPhoto':'20','selectPhotoType':'',
		//		//'orderStep':'1','createTime':'20150508','updateTime':'20150508'}]};
		//	//_self.look_order_success_cb(data);
		//	_self.ajax(url, obj, _self.look_order_success_cb, _self.look_order_error_cb);
		//}
	});
	//点击删除客户
	$('#user-tbody').on('click', _self.cancelBtn, function () {
		$this = $(this);
		_self.tip_hide();
		$('tr[delte*="delete"]').removeClass('delete');
		$this.parent().parent().addClass('delete');
		var customerId = $this.attr('customerId');
		if(_self.check_trim(customerId)) {
			_self.main_tip('删除客户失败，请稍后重试！');
		} else {
			if(confirm('确定删除？')) {
				var url = '/customer/deleteCustomer.do';
				var obj = {};
				obj.customerId = customerId;
				_self.ajax(url, obj, _self.user_cancel_success_cb, _self.user_cancel_error_cb);
			} else {
				return false;
			}
		}
	});
	//点击编辑客户信息
	$('#user-tbody').on('click', _self.editBtn, function () {
		_self.tip_hide();
		$this = $(this);
		$('tr[class*="edit"]').removeClass('edit');
		$this.parent().parent().addClass('edit');
		_self.init_edit_view();
	});

	//客户信息列表翻页初始化
	var init = function () {
		var totalPages = $('li[class="active"]').attr('totalPages');
		$('#Pagination').empty();
		initPagination(totalPages, 10, _self.init_page_cb);
	}

	init();

});