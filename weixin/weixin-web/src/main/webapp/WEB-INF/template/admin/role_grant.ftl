<!DOCTYPE html>
<html>
<head>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${base}/static/assets/skins/<@spring.message "default.skin"/>/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/assets/skins/gray/css/all.css" rel="stylesheet" type="text/css" /> 
	<link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" /> 
	<script src="${base}/static/js/jquery-1.8.1.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.form.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.validate.js" type="text/javascript"></script>
	<script src="${base}/static/js/jquery.validate.methods.js" type="text/javascript"></script>
    <script src="${base}/static/js/jquery.metadata.js" type="text/javascript"></script>
    <script src="${base}/static/js/jquery.validate.cn.js" type="text/javascript"></script>
	<script src="${base}/static/assets/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/ligerui.expand.js" type="text/javascript"></script>
	
	<style type="text/css">
		
	</style>
	<script type="text/javascript">
		var dataGrid;

    	$(function (){
    		$("#dataGrid").ligerGrid({
    			columns: [
		            { display:'ID',name:'id',align:'left',width:27,hide:true},
		            { display:'系统模块',name:'name', minWidth:120,width:150, align:'left'},
		            { display:'可用权限', minWidth: 80, align: 'left',render:function(item){
		            	var html='';
		            	if(item.grant){
		            		$.each(item.grant, function(key, value){
		            			html+="<input name='grants' value='"+item.code+"_"+value.id+"_"+value.sign+"' type='checkbox' "+value.checked+"/>&nbsp;"+value.name+"&nbsp;&nbsp;";
		            		});
		            	}          	
		            	return html;
		            }}
		        ],
		        usePager:false,
				headerRowHeight: 30,  
				rowHeight: 28,
				frozenRownumbers: true,
            	selectRowButtonOnly:false,
            	allowUnSelectRow:false, 
            	isScroll: false,
            	dataAction:'server',
            	url:'${base}/admin/role/${roleId}/permissions',
    			parms:[{name:'mid', value:'${(mid)!}'}],
    			tree: {columnName:'name'}
    		});
    		
    		dataGrid = $("#dataGrid").ligerGetGridManager();
    		
    		$("#pageloading").hide();
    	});
    	
    	function doSerialize(){
			return {'data':$("#validateForm").formSerialize(),'url':$("#validateForm").attr("action")};
    	}
	</script>
</head>
<body style="padding:10px">
	<div id="pageloading"></div>
	<form id="validateForm" name="validateForm" action="${base}/admin/role/grant/save">
		<input id="roleId" name="roleId" type="hidden" value="${roleId}" />
		<input type="hidden" id="mid" name="mid" value="${(mid)!}" />
		<div id="dataGrid"></div>
	</form>
	<div style="display:none;"></div>	
</body>
</html>