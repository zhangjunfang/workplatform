<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8" />
	<title>后台主页面 - <@spring.message "admin_title"/></title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="${base}/static/img/favicon.ico"/>
	<link rel="apple-touch-icon" href="${base}/static/img/touchicon.png"/>
	<link href="${base}/static/assets/skins/<@spring.message "default.skin"/>/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<script src="${base}/static/js/jquery-1.8.1.min.js" type="text/javascript"></script>
	<script src="${base}/static/assets/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/json2.js" type="text/javascript"></script>
	<style type="text/css"> 
		body,html{height:100%;}
    	body{ padding:0px; margin:0;overflow:hidden;}
    	#pageloading{position:absolute; left:0px; top:0px; background:white url('${base}/static/img/loading.gif') no-repeat center; width:100%; height:100%;z-index:99999;}
    	/* 顶部 */ 
    	.space{ color:#E7E7E7;}
	    .header{ margin:0; padding:0; height:31px; line-height:31px; background:url('${base}/static/img/top.jpg') repeat-x bottom;  position:relative; border-top:1px solid #1D438B;  }
	    .header-logo{ color:#E7E7E7; padding-left:35px; line-height:26px;background:url('${base}/static/img/topicon.gif') no-repeat 10px 5px;}
	    .header-welcome{  position:absolute; height:24px; line-height:24px;  right:30px; top:2px;color:#070A0C;}
	    .header-welcome a{ color:#E7E7E7; text-decoration:none}
	    .header-welcome a:hover{text-decoration:underline;}
	    .header-link{text-decoration:underline; color:white; margin-left:2px;margin-right:2px;}
	</style>
	<script type="text/javascript">
		var tab = null;
		var accordion = null; 
		$(function (){
			//布局
            $("#mainLayout").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: heightChanged });
			var height = $(".l-layout-center").height();
			$("#frameCenter").ligerTab({ height: height });
            
            $.each(${menus},function(key,values){
            	$("#mainAccordion").append('<div title="'+values.name+'" class="menuScroll"><ul id="'+values.id+'" style="margin-top:3px;"></ul></div>');
			    $(values).each(function(){
			        var menu=this.menu;
			        $("#"+values.id).ligerTree({
			        	data:menu,
			        	idFieldName :'id',
            			parentIDFieldName :'pid',
		            	checkbox:false,
		                slide:false,
		                nodeWidth:120,
		                attribute:['url'],
		                onSelect:function(node){
		                	if (!node.data.url || node.data.url=='#') return;
		                	var tabid = $(node.target).attr("tabid");
		                	if (!tabid){
		                		tabid = new Date().getTime();
		                		$(node.target).attr("tabid", tabid);
		                	}
		                	addTab(tabid, node.data.text, node.data.url);
		                }
		            });
			    });
			});		
			
            $("#mainAccordion").ligerAccordion({ height: height - 24, speed: null});
            
            tab = $("#frameCenter").ligerGetTabManager();
            
            accordion = $("#mainAccordion").ligerGetAccordionManager();
				
			$("#pageloading").hide();
		});
		
		function heightChanged(options){
			if (tab){
				tab.addHeight(options.diff);
			}                    
            if (accordion && options.middleHeight - 24 > 0){
            	accordion.setHeight(options.middleHeight - 24);
            }
		}
		
		function addTab(tabid,text, url){ 
            tab.addTabItem({ tabid : tabid,text: text, url: '${base}/'+url });
        } 
	</script>
</head>
<body>
	<div id="pageloading"></div>
	<div id="header" class="header">
		<div class="header-logo"><@spring.message "project_name"/></div>
		<div class="header-welcome">
			<font color="white">HI,</font>
			<a href="#" class="header-link" target="_blank"><@shiro.principal /></a>
			<span class="space">|</span>
	        <a href="#" class="header-link" target="_blank">帮助中心</a> 
	        <span class="space">|</span>
	        <a href="${base}/admin/account/logout" class="header-link" target="_self">退出系统</a>
	    </div> 
	</div>
	<div id="mainLayout" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
		<div position="left" title="主菜单" id="mainAccordion"></div>
		<div position="center" id="frameCenter">
			<div tabid="home" title="我的主页" style="height:300px" >
                <iframe frameborder="0" name="home" id="home" src="${base}/admin/index/welcome"></iframe>
            </div> 
		</div>
	</div>
	<div  style="height:32px; line-height:32px; text-align:center;">
            Copyright © 2011-2013 www.shop8188.com
    </div>
    <div style="display:none"></div>
</body>	
</html>
