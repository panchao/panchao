<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="../bootstrap/css/bootstrap.2.3.1.min.css">
		<link rel="stylesheet" href="../bootstrap/css/bootstrap-responsive.min.css">
		<link rel="stylesheet" href="../css/login.css">
		<style type="text/css">
			.album_pic{float:left;width:600px;height:600px;padding:50px;background-color:#fff;}
			.unchecked{  
				border: 1px solid gray;  
				padding: 6px 6px 6px 6px;  
			}  
			.checked{  
				border: 2px solid #c00;  
				padding: 6px 6px 6px 6px;  
			}  
			.div_pic{width:300px;height:90px;z-index:99999;position:absolute;left:10px;border:2px #3300FF					solid;border:0px;color:#FFFFFF;margin-left:200px;}

			.select_cover_img{float: left;display: inline-block;width: 200px;height: 200px;margin-left:
				20px;margin-bottom: 20px}

			.select_cover_img:hover{opacity:0.7}
		</style>
		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery.cookie.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap.2.3.1.min.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap-modal.js"></script>
		<script type="text/javascript">

			var albumSize = '';
			var IndexOfProdudct = 0;

			function change(span){  
				$('span[name="'+$(span).attr('name')+'"]').each(function(){  
					 if(this.checked&&this!=span)  
					 {  
						 this.className="unchecked";  
						 this.checked=false;  
					 }                 
				 });  

				albumSize=$(span).attr("data-size");  
				span.className="checked";  
				span.checked=true;  
			}  

			function GetRequest(){
				var url = location.search; 
				var theRequest = new Object();
				if (url.indexOf("?") != -1){
					var str = url.substr(1);
					strs = str.split("&");
					for(var i = 0; i < strs.length; i ++) {
				        theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
					}
				}
				return theRequest;
			}

			$(function(){
			 	var $eViewModal = $('#view_infor'),
			 	    $ePreviewModal = $('#preview'),
			 	    $eCart = $('#cart_detail');
				var album_id = 1,
				    arrHtml = [],
				    Request = new Object(),
				    iCount = 0;
		          		      
				$(".album_img").hide(); 
				$(".album_img:first").show();
				$("#choose").attr("data-album-url",$(".album_img:first").attr("src"));
				Request = GetRequest();

				$("#choose").click(function(){
					
					album_id = $(this).attr("data-album");
					

					$.ajax({
						url:"/pictures/getAlbumPictures.do",
						type: 'post',
						dataType: 'json',
						cache: false,
						async: false,
						data: {
							getCode:Request["getCode"]
						},
						success: function (data) {						   
							if (data.retCode == 200) {
								$.each(data.data,function(Index,oData){
									arrHtml.push('<div class="select_cover"><img class="select_cover_img" src="' + oData.src +
										'"name ="' + oData.name +  '"data-id = "' + oData.id + '"title="点击设为封面"></div>');
									iCount++;
								});
								if (iCount >0) {
									$("#con1_2").html(arrHtml.join(''));
								}else{
									$("#con1_2").html("暂无图片");
								}
							} else {
								alert("获取照片失败");
							}
						},
						error: function (xhr) {
							//
						}
				  	 });

					$(".select_count").html(iCount);
					$eViewModal.modal({backdrop:'static'});
				});
				
				var cover_url = ""; 
				var cover_id = null;
				var arrView = [];

				//$(".select_cover_img").click(function(){
				//	cover_url = $(this).attr("src");
				//	cover_id = $(this).attr('data-id');
				//});

				$(document).on("click", ".select_cover_img", function() {
					cover_url = $(this).attr("src");
					cover_id = $(this).attr('data-id');
				});

				var temp_id = null,
				    temp_url = "";
				      
				$("#submit_select_pic_btn").click(function(){
					$eViewModal.modal('hide');
					arrView .push('<div class="div_pic"><img src="' + cover_url + '"data-id ="' + cover_id + '"></div>');
					$.each($(".select_cover_img"),function(Index,oData){
						temp_id = $(oData).attr("data-id");
						temp_url = oData.src;
						if (temp_id != cover_id) {
							arrView.push('<div class="div_pic"><img src="' + temp_url + '" data-id = "' + temp_id + '"></div>');
						}
					});
					$("#photo_view").html(arrView.join(''));
					$ePreviewModal.modal({backdrop:'static'});
				});

				var strName = " ";
				var temp_num = 0;
				var arrProductHtml = [];

				$('#add_to_cart').click(function(){	
					strName = $("#choose").attr("data-album") + albumSize + cover_id;
					temp_num = $.cookie("album_num");
					if(temp_num == "NaN" || temp_num == undefined)
					{
						temp_num = 0;
					}
					if($.cookie("album_" + strName)  != "yes")
					{
						temp_num++;
						$.cookie("album_" + strName,"yes");
						$.cookie("album_num",temp_num);
						$.cookie('album_url_'+temp_num,$("#choose").attr("data-album-url"));
						$.cookie('cover_url_'+temp_num,cover_url);
						$.cookie('album_id_'+temp_num,$("#choose").attr("data-album"));
						$.cookie('cover_id_'+temp_num,cover_id);
						$.cookie('album_size_'+temp_num,albumSize);
						$.cookie("count_" + temp_num,1);
					}
					$ePreviewModal.modal('hide');
					$("#product_count").html(temp_num);

					if (temp_num == 0) {
						$('#product_list_body').html('<tr><td colspan="6" style="text-align: center;">购物车为空</td></tr>');
					}else{
						arrProductHtml = [];
						while(temp_num > 0)
						{
						    arrProductHtml.push('<tr>');
							arrProductHtml.push('<td><img src="' + $.cookie('album_url_'+ temp_num) +  '"></td>');
							arrProductHtml.push('<td>相册' + temp_num + '</td>');
							arrProductHtml.push('<td>' + $.cookie('album_size_'+ temp_num)+ '</td>');
							arrProductHtml.push('<td><img src="' + $.cookie('cover_url_'+ temp_num) +  '"></td>');
							arrProductHtml.push('<td><i class="icon-minus" data-action="minus"></i><input value="1" style="width:28px;padding-left:6px"><i class="icon-plus" data-action="plus"></i></td>');
							arrProductHtml.push('<td><a href="#" data-action= "delete" data-id="' + temp_num + '">删除</a></td>');
							arrProductHtml.push('</tr>');
						    temp_num--;
						}
						$('#product_list_body').html(arrProductHtml.join(''));
					}
			        		$eCart.modal({backdrop:'static'});
				});

				var z=99999;
				//$(".div_pic").click(function(){
				//	$(this).animate({left:"300px"},1000,function(){$(this).css("zIndex",z--)}).animate({left:"10px"},1000);
				//});

				$(document).on("click", ".div_pic", function() {
					$(this).animate({left:"300px"},1000,function(){$(this).css("zIndex",z--)}).animate({left:"10px"},1000);
				});

				// post:请求body
				// [{“albumId”:”id1”,”photoId”:”id2”,”albumCount”:”2”,”userId”:”id3”,”albumType”:”A"},{“albumId”:”id1”,”photoId”:”id2”,”albumCount”:”2”,”userId”:”id3”,”albumType”:”A"
				// }
				// ]
				var url   =  "/album/submitOrder.do";
				var data = [];
				var oTemp = null;
				$("#go_to_pay").click(function(){
					temp_num = $.cookie("album_num");
					if (temp_num > 0) {
						for (var i = temp_num; i > 0; i--) {
							oTemp = {"albumId" : $.cookie("album_id_" + temp_num),
								     "photoId": $.cookie("cover_id_" + temp_num),
								     "userId" :  Request["getCode"],
								      "albumCount": $.cookie("count_" + temp_num),
								      "albumType":$.cookie("album_size_" + temp_num)};
							data.push(oTemp);
						}
					}

		                        	$.ajax({
		                           		type: 'POST',
		                            		url: url,
		                            		data: JSON.stringify(
		                               			data
		              			),
		                            		contentType:"application/json;charset=utf-8",
		                            		success: function(data) {
				                                if (data.retCode == 200) {
				                                    window.location = "/album/gotoOrderConfirm.do";
				                                }
		                           		}
					});
		                     });

				 $("#album_1").click(function(){
					$(".album_img").hide(); 
					$(".album_img:first").show();
				 	$("#choose").attr("data-album",$(".album_img:first").attr("data-id"));
				 	$("#choose").attr("data-album-url",$(".album_img:first").attr("src"));
				 });

				$("#album_2").click(function(){
					$(".album_img").hide(); 
					$(".album_img:eq(1)").show();
				 	$("#choose").attr("data-album",$(".album_img:eq(1)").attr("data-id"));
				 	$("#choose").attr("data-album-url",$(".album_img:eq(1)").attr("src"));
				});

				$("#album_3").click(function(){
					$(".album_img").hide(); 
					$(".album_img:eq(2)").show();
				 	$("#choose").attr("data-album",$(".album_img:eq(2)").attr("data-id"));
				 	$("#choose").attr("data-album-url",$(".album_img:eq(2)").attr("src"));
				 });

				$("#product_list").click(function(event){
					var eTaget = $(event.target);
					var sAction = eTaget.attr("data-action");
					var sId = eTaget.attr("data-id");
					var inum = $.cookie("album_num");
					var sName = $.cookie('album_id_'+sId) + $.cookie('album_size_'+sId)+$.cookie('cover_id_'+sId) ;
					if (sAction == "delete") {
						inum--
						$.cookie("album_" + sName,null);
						$.cookie("album_num",inum);
						$.cookie('album_url_'+sId,null);
						$.cookie('cover_url_'+sId,null);
						$.cookie('album_id_'+sId,null);
						$.cookie('cover_id_'+sId,null);
						$.cookie('album_size_'+sId,null);
						$.cookie('count_'+sId,null);
						eTaget.parent().parent("tr").remove();
						if ($.cookie("album_num") == 0) {
							$('#product_list_body').html('<tr><td colspan="6" style="text-align: center;">购物车为空</td></tr>');
						}
					}
					$("#product_count").html(inum);

					var num = 1;
 					if (sAction == "plus") {
						num = 	eTaget.prev().attr("value");
						num ++;
						eTaget.prev().attr("value",num); 
						$.cookie("count_" + sId,num);
					}

					if(sAction == "minus"){
						num = eTaget.next().attr("value");
						if (num > 0) {
							num--;
							eTaget.next().attr("value",num);
						}
						$.cookie("count_" + sId,num);
					}
				});
			})

		</script>
	</head>
	<body>
		<div id="wrapper">
			<div class="header">
				 <h1 class="headerLogo"><a title="后台管理系统" target="_blank" href="http://www.baidu.com/"><img alt="logo" src="../images/logo.gif"></a></h1>
				<div class="headerNav">
					<a target="_blank" href="http://www.baidu.com/">傲秀官网</a>
					<a target="_blank" href="http://www.baidu.com/">关于傲秀</a>
					<a target="_blank" href="http://www.baidu.com/">团队介绍</a>
					<a target="_blank" href="http://www.baidu.com/">意见反馈</a>
					<a target="_blank" href="http://www.baidu.com/">帮助</a>
				</div>
			</div>
			<div id="navtop">	
			</div>
			<div class="banner">
				<div id="album">
					<a id="album_1"  class="btn btn-default" href="#" role="button">相册1</a>
					<a id="album_2"  class="btn btn-default" href="#" role="button">相册2</a>
					<a id="album_3"  class="btn btn-default" href="#" role="button">相册3</a>

					<div>
						<div class="album_pic">
							<#list albums as album_item>
								<img  class="album_img" src=${album_item.albumSrc} data-id=${album_item.id} data-name=${album_item.albumName}>
							</#list>
						</div>

						<div class="config">
							<div class="frame ztag">
								<div>  
									大小:  <span class='unchecked'  name='sizeSpan'  checked='false' data-size = "large" onclick='change(this);' >大</span>
								   <span class='unchecked' name='sizeSpan' checked='false' data-size = "medium" onclick='change(this);' >中</span> 
								   <span class='unchecked' name='sizeSpan' checked='false' data-size = "small" onclick='change(this);' >小</span>  
								</div>  
								<br />  
								<br />  
								<div>
									<a id="choose" class="btn btn-default" href="#" role="button" data-album="1">选择</a>
								</div>
							</div>
						</div>
					</div> 
				</div>
			</div>
			<footer class="footer">
			   <p>Copyright &copy; 2014.Company name All rights reserved.<a target="_blank" href="http://www.baidu.com/">傲秀</a></p>
			</footer>
		</div>

		<div class="modal hide fade" style="display: none; width: 720px;margin-left:-360px" id="view_infor">
			<form class="form-horizontal" >
				<div class="modal-header">
					<a class="close" data-dismiss="modal"></a>
					<h4>设置封面</h4>
				</div>
				<div class="modal-body" style="min-height:200px">
					<div id="con1_2"></div>
				</div>
				<div class="modal-footer">
					<span style="margin-left:3px">共<span class="select_count"></span>张</span>
					<input type="button" class="btn btn-primary green" value="确认提交" id="submit_select_pic_btn"/>
					<a class="btn btn-danger" data-dismiss="modal">关闭</a>
				</div>
			</form>
		</div>

		<div class="modal hide fade" style="display: none; width: 720px;margin-left:-360px" id="preview">
			<form class="form-horizontal" >
				<div class="modal-header">
					<a class="close" data-dismiss="modal"></a>
					<h4>相册预览</h4>
				</div>
				<div class="modal-body" style="min-height:340px;overflow-x:hidden">
					<div id="photo_view"></div>
				</div> 
				<div class="modal-footer">
					<span style="margin-left:3px">共<span class="select_count"></span>张</span>
					<input type="button" class="btn btn-primary green" value="加入购物车" id="add_to_cart"/>
					<a class="btn btn-danger" data-dismiss="modal">关闭</a>
				</div>
			</form>
		</div>

		<div class="modal hide fade" style="display: none; width: 720px;margin-left:-360px" id="cart_detail">
			<form class="form-horizontal" >
				<div class="modal-header">
					<a class="close" data-dismiss="modal"></a>
					<h4>购物车</h4>
				</div>
				<div class="modal-body" style="min-height:340px;overflow-x:hidden">
					<table class="custom-table table table-striped table-bordered table-hover"
					width="100%" border="0" cellspacing="0" cellpadding="0" id="product_list">
						<thead>
							<tr class="odd">
								<th style="text-align: center;width:100px">产品图标</th>
								<th style="text-align: center;width:70px">产品名</th>
								<th style="text-align: center;width:100px">产品信息</th>
								<th style="text-align: center;width:100px">封面</th>	
								<th style="text-align: center;width:190px">数量</th>
								<th style="text-align: center;width:100px">删除操作</th>
							</tr>
						</thead>
						<tbody id="product_list_body">
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<span style="margin-left:3px">共<span id="product_count"></span>款产品</span>
					<input type="button" class="btn btn-primary green" value="去支付" id="go_to_pay"/>
					<a class="btn btn-danger" data-dismiss="modal">关闭</a>
				</div>
			</form>
		</div>
	</body>
</html>
