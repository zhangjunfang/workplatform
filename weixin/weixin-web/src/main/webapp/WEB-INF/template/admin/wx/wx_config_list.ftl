<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8" />
	<title>账号配置 - <@spring.message "admin_title"/></title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="author" content="">
	<meta name="viewport" content="width=device-width" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${base}/static/assets/skins/<@spring.message "default.skin"/>/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/assets/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" /> 
	<script src="${base}/static/js/jquery-1.8.1.min.js" type="text/javascript"></script>
	<script src="${base}/static/assets/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/ligerui.expand.js" type="text/javascript"></script>
	
	<style type="text/css">
		
	</style>
	<script type="text/javascript">
		var dataGrid;
		$(function (){
			$("#actionMenu").ligerMenuBar({items: [
            		{ text: '导出',menu:{ width: 90, items:[
            			{text:'Excel', click: function(){}},
            			{text:'PDF', click: function(){}}
            			]
            		}},
	                <@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">{ text: '添加',click:onAddClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['validate'])!}">{ text: '校验',click:onValidateClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">{ text: '修改',click:onEditClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
            // 影响单元格高度自适应效果 { display:'ID',name:'id',align:'center',width:27,hide:true},
			$("#dataGrid").ligerGrid({
                columns: [
		            { display:'账号名称',name:'name', minWidth: 60,width:150, align: 'center' },
		            { display: '账号类型', name: 'type', minWidth: 60, width: 80, align: 'center',render:function(rowdata){
		            	var array=new Array();
		            	array[0]="测试号";
						array[1]="订阅号";
						array[2]="服务号";
		            	return array[rowdata.type];
		            }},
		            { display: '是否校验', name: 'valid', type:'yesno', minWidth: 60, width:70, align: 'center'},
		            { display: '是否认证', name: 'auth', type:'yesno', minWidth: 60, width:70, align: 'center'},
		            { display: 'Token', name: 'token', minWidth: 60, width: 240, heightAlign: 'center' },
		            { display: 'URL', name: 'url', minWidth: 60, heightAlign: 'center' }
		        ], 
	            pageSizeOptions: [20,25,30,40,50],
	            pageSize:20,
				headerRowHeight: 30,  
				fixedCellHeight:false,
				rowHeight:'auto',
				rownumbers: true,
				frozenRownumbers: true,
            	checkbox: true,
            	switchPageSizeApplyComboBox: true,
            	selectRowButtonOnly: false,
            	allowUnSelectRow:false,
            	dataAction:'server',
            	pageParmName: 'pageNumber',
        		pagesizeParmName: 'pageSize',
        		sortnameParmName: 'orderBy',
        		sortorderParmName: 'orderType',
    			url:'${base}/admin/wx/config/ajax?mid=${(mid)!}',
    			parms:[{name:'property', value:''},{name:'keyword', value:''}],
    			<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    			onDblClickRow:onEditDoubleClick
    			</@shiro.hasPermission>
            });
            
			$("#property").ligerComboBox({width:90});
    		$("#keyword").ligerTextBox({width:220});    		
    		$("#btnSearch").ligerButton({ click: onSearchClick});
    	
    		dataGrid = $("#dataGrid").ligerGetGridManager();
    		
    		$("#pageloading").hide();
		});
		
		function onSearchClick(){
    		if (!dataGrid) {
            	return;
            }
            var property = $("#property").val(); 
            var keyword = $("#keyword").val(); 
            dataGrid.setOptions(
                { parms: [{ name: 'property', value: property},{ name: 'keyword', value: keyword}] }
            );
            dataGrid.loadData(true);
    	}
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">
    	function onAddClick(){
    		$.ligerDialog.open({ 
    			cls:'l-dialog-content',
				url: '${base}/admin/wx/config/add?mid=${(mid)!}', 
				width: 400, 
				height:270,
				title:'添加微信账号',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick: doSave}, 
					{text: '取消', onclick: function (item, dialog) { onCloseDialog(dialog);} } 
				]
			});
    	}
    	</@shiro.hasPermission>
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['validate'])!}">
    	function onValidateClick(){
    		var id=dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要校验的微信账号！')
			}else{
				$.ligerDialog.open({ 
					cls:'l-dialog-content',
					url: '${base}/admin/wx/config/valid/'+id+'?mid=${(mid)!}', 
					width: 500, 
					height:200,
					title:'校验账号配置',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doValidate}, 
						{text: '取消', onclick: function (item, dialog) { onCloseDialog(dialog);} } 
					]
				});
			}
    	}
    	
    	function doValidate(item, dialog) { 
    		var result=dialog.frame.doSerialize();
    		if(result!=null){
    			$.ajax({
					type:"POST",
					async: false,
					url:result.url,
					data:result.data,
					dataType:"json",
					success:function(data){
						if(data.status=='success'){
							dialog.close();
						}else{
							$.ligerDialog.error(data.message);
						}
					},
					complete:function(){
						dataGrid.loadData(true);
					}
				});   
    		}
    	}
    	</@shiro.hasPermission>
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    	function onEditClick(){
    		var id=dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要修改的记录！')
			}else{
				$.ligerDialog.open({ 
					cls:'l-dialog-content',
					url: '${base}/admin/wx/config/edit/'+id+'?mid=${(mid)!}', 
					width: 400, 
					height:270,
					title:'修改账号配置',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doSave}, 
						{text: '取消', onclick: function (item, dialog) { onCloseDialog(dialog);} } 
					]
				});
			}
    	}
    	
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
    			cls:'l-dialog-content',
				url: '${base}/admin/wx/config/edit/'+data.id+'?mid=${(mid)!}', 
				width: 400, 
				height:270,
				title:'修改账号',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick:doSave}, 
					{text: '取消', onclick: function (item, dialog) { onCloseDialog(dialog);} } 
				]
			});
    	}    	
    	</@shiro.hasPermission>
    	
    	<@shiro.hasPermissions name="${(msign)!}:${(oper['add'])!},${(msign)!}:${(oper['edit'])!}">
    	function doSave(item, dialog) { 
    		var result=dialog.frame.doSerialize();
    		if(result!=null){
    			$.ajax({
					type:"POST",
					async: false,
					url:result.url,
					data:result.data,
					dataType:"json",
					success:function(data){
						if(data.status=='success'){
							dialog.close();
						}else{
							$.ligerDialog.error(data.message);
						}
					},
					complete:function(){
						dataGrid.loadData(true);
					}
				});   
    		}
    	}
    	</@shiro.hasPermissions>
    	
    	function onCloseDialog(dialog){
    		dialog.close();
    	}
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">
    	function onDeleteClick(){
    		var ids=dataGrid.selectCheckedRows('id');
    		if(ids==""){
				$.ligerDialog.warn('请选择要删除的记录！')
			}else{
				$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?',function(yes) {
					if(yes){
						$.ajax({
							type:"POST",
							async: true,
							url:"${base}/admin/wx/config/delete",
							data:{'ids':ids,mid:'${(mid)!}'},
							dataType:"json",
							success:function(data){
								if(data.status=='success'){
									dataGrid.loadData(true);
								}else{
									$.ligerDialog.error(data.message);
								}
							}
						});
					}
				});
			}
    	}
    	</@shiro.hasPermission>
	</script>
</head>
<body>
	<div id="pageloading"></div>	
	<div class="l-panel-search">
	    <div class="l-panel-search-item">
	        <select id="property" name="property">
	        	<option value="name" selected>账号名称</option>
	        </select>
	    </div>
	    <div class="l-panel-search-item">
	    	<input id="keyword" name="keyword" type="text" value=""/>
	    </div>
	    <div class="l-panel-search-item">
	        <div id="btnSearch" class="liger-search-button">搜索</div>
	    </div>	    
	    <div class="l-panel-search-item" style="float:right;padding-right:5px;">
	        <div id="actionMenu"></div>
	    </div>
	</div>
	<div id="dataGrid"></div>
	<div style="display:none;"></div>	
</body>
</html>