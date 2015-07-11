<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		 <link rel="stylesheet" href="../bootstrap/css/bootstrap.2.3.1.min.css">
		 <link rel="stylesheet" href="../bootstrap/css/bootstrap-responsive.min.css">

		 <style type="text/css">
			img { border:none; }
			.wrapper { width:1000px; margin:0 auto; }
			.wrapper h3{color:#3366cc;font-size:16px;height:35px;line-height:1.9;text-align:center;border-bottom:1px solid #E5E5E5;margin:0 0 10px 0;}
			#con1_1 { position:relative; }
			#con1_1 .product_list { position:absolute; left:0px; top:0px; padding:10px; background:#eee; margin:5px;}
			.product_list img { width:200px;}
			.product_list p { padding:5px 0px; font-size:12px; text-align:center; color:#333;  white-space:normal; width:200px;}

			.product_list p{
				background:#404743; opacity:.8;filter:alpha(opacity=80);} 
			.product_list p a{padding-left:30px; height:24px; background:url(../images/heart.png) no-repeat
				4px -25px;color:#fff; font-weight:bold; font-size:16px} 
			/*.product_list p a:hover{background-position:4px -25px;text-decoration:none}*/

			.select_list img{float: left;display: inline-block;width: 200px;height: 200px;margin-left: 20px;margin-bottom: 20px}
		</style>

	</head>
	<body>
		<div id="wrapper">
			<div id="header">
				
			</div>
			<div id="navtop">
				
			</div>
			<div id="maincontent">
				<div class="wrapper">
				  <h3>照片瀑布流</h3>
				  <button id="refineList">精修列表</button>
				  <div id="con1_1">
<!-- 				      <div class="product_list">
				       <a href="#"><img src="images/img/img6.jpg"></a>
				       <p>
				         <a href="#"  class="img_on" data-url="http://www.baidu.com"></a>
				        </p>
				     </div> -->
		     			<#list resultMap?keys as key>
			     		    <div class="product_list">
					       <a href="#"><img  src=${resultMap[key]}></a>
					       <p>
					         <a href="#"  class="img_on" data-url=${resultMap[key]} data-id= ${key}></a>
					        </p>
					     </div>
					</#list>
				  </div>
				</div>
			</div>
			<div id="footer">
				
			</div>
		</div>

		 <div class="modal hide fade" style="display: none; width: 720px;margin-left:-360px" id="view_infor">
		         <form class="form-horizontal" >
		            <div class="modal-header">
		                <a class="close" data-dismiss="modal"></a>
		                <h4>精修列表</h4>
		            </div>
		            <div class="modal-body" style="min-height:200px">
		               <div id="con1_2"></div>
		            </div>
		            <div class="modal-footer">
		                <span style="margin-left:3px">共<span id="select_count"></span>张</span>
		                <input type="button" class="btn btn-primary green" value="确认提交" id="submit_select_pic_btn"/>
		                <a class="btn btn-danger" data-dismiss="modal">关闭</a>
		            </div>
		        </form>
		    </div>

		<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="../js/masonry.pkgd.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap.2.3.1.min.js"></script>
		<script type="text/javascript" src="../bootstrap/js/bootstrap-modal.js"></script>
		<script type="text/javascript">

			$(function() {

				var $container = $('#con1_1');    
		          		$container.masonry({
		            		itemSelector: '.product_list',
		            	columnWidth: 5 //每两列之间的间隙为5像素
		        		});

		          		var  arrIDs = [];
		          		$("#refineList").click(function() {
		          			var $eViewModal = $('#view_infor');
		          			var $container1 = $('#con1_2');    
			          		var arrChoose = $(".img_on[data-flag = 1]"),
			          		     strUrl = " ",
			          		     selectid = 0,
			          		     arrHtml = [];
			          		     iCount = 0;

			          		$.each(arrChoose,function (Index,oData) {
			          			strUrl =$(oData).attr("data-url");
			          			selectid =$(oData).attr("data-id");

			          			arrHtml.push(' <div class="select_list"> <img src=" ' + strUrl + '"></div>');
			          			arrIDs.push(selectid);
			          			iCount++;	
			          		});

			          		if(iCount > 0){
			          			$("#con1_2").html(arrHtml.join(''));
			          		}else{
			          			$("#con1_2").html("未选择精修图片");
			          		}

			          		$("#select_count").html(iCount);
			        		$eViewModal.modal({backdrop:'static'});
			          	});

			          	$("#submit_select_pic_btn").click(function(){

		          			$.ajax({
				                url:"/pictures/addSelecedPictures.do",    //TODO
				                type: 'post',
				                dataType: 'json',
				                cache: false,
				                async: false,
				                data: {
				                    picIds:arrIDs.join(",")
				                },
				                success: function (data) {
				                					                   
				                    if (data.retCode == 200) {
				               
				                        window.setTimeout(function () {
				                            window.location.reload();
				                        }, .5e3);

				                    } else {
				                     	alert("提交失败");
				                    }
				                },
				                error: function (xhr) {
				                	//
				                }
				           });
			          	});

			          	$("#con1_1 .product_list p a").click(function(){
			          		var eTC = $(this),
			          		     sFlag = eTC.attr("data-flag");
			          		if (sFlag == 1) {
			          			 eTC.attr('data-flag', '0').css("background-position","4px -25px");
			          		}else{
			          			 eTC.attr('data-flag', '1').css("background-position","4px -1px");
			          		};

			          	});
			});
            $(window).load(function() {
                var $container = $('#con1_1');
                $container.masonry({
                    itemSelector: '.product_list',
                    columnWidth: 5 //每两列之间的间隙为5像素
                });
            });
		</script>
	</body>
</html>
