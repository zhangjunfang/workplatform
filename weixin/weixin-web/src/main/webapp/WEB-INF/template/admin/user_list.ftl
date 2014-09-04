<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8" />
	<title>用户列表 - <@spring.message "admin_title"/></title>
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
	                <@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">{ text: '修改',click:onEditClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['role'])!}">{ text: '设置角色',click:onRoleClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
              			
    		$("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display: '登录名称',name:'userName', minWidth: 60,width:180, align: 'center' },
		            { display: '电子信箱', name: 'email', minWidth: 80, width: 180,heightAlign: 'center' },
		            { display: '用户昵称', name: 'nickName', minWidth: 60, heightAlign: 'center' },
		            { display: '用户姓名', name: 'realName', minWidth: 60, heightAlign: 'center' },
		            { display: '性别', name: 'sex', minWidth: 80, width: 80, align: 'center',render:function(rowdata){
		            	var sexArray=new Array();
		            	sexArray[0]="保密";
						sexArray[1]="女";
						sexArray[2]="男";
		            	return sexArray[rowdata.sex+1];
		            }},
		            { display: '用户状态', name: 'status', minWidth: 80, width: 80, align: 'center',render:function(rowdata){
		            	var statusArray=new Array();
		            	statusArray[0]='未启用';
						statusArray[1]='<font color="green">启用</font>';
						statusArray[2]='<font color="red">禁用</font>';
		            	return statusArray[rowdata.status];
		            }},
		            { display: '创建日期', name: 'createDate', minWidth: 100,type:'datetime', align: 'center'}
		        ], 
	            pageSizeOptions: [20,25,30,40,50],
	            pageSize:20,
				headerRowHeight: 30,  
				rowHeight: 28,
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
    			url:'${base}/admin/user/ajax?mid=${(mid)!}',
    			parms:[{name:'property', value:''},{name:'keyword', value:''}],
    			<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">onDblClickRow:onEditDoubleClick</@shiro.hasPermission>
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
    	
    	function onCloseDialog(dialog){
    		dialog.close();
    	}
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">
    	function onAddClick(){
    		$.ligerDialog.open({ 
				url: '${base}/admin/user/add?mid=${(mid)!}', 
				width: 700,
				height: 420, 
				title:'添加用户',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick: doSave}, 
					{text: '取消', onclick: function (item, dialog) { dialog.close();} } 
				]
			});
    	}
    	</@shiro.hasPermission>
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    	function onEditClick(){
    		var id=dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要修改的记录！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/user/edit/'+id+'?mid=${(mid)!}', 
					width: 700,
					height: 420,
					title:'修改用户',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doSave}, 
						{text: '取消', onclick: function (item, dialog) { dialog.close();} } 
					]
				});
			}
    	}
    	
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
				url: '${base}/admin/user/edit/'+data.id+'?mid=${(mid)!}', 
				width: 700,
				height: 420,
				title:'修改用户',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick:doSave}, 
					{text: '取消', onclick: function (item, dialog) { dialog.close();} } 
				]
			});
    	}
    	</@shiro.hasPermission>
		    	
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
							url:"${base}/admin/user/delete",
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
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['role'])!}">
    	function onRoleClick(){
    		var id = dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要设置角色的用户！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/user/'+id+'/roles?mid=${(mid)!}', 
					width: 500, 
					height:300,
					title:'角色授权',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doSave}, 
						{text: '取消', onclick: function (item, dialog) {onCloseDialog(dialog);} } 
					]
				});
			}
		}
		</@shiro.hasPermission>
		
    	<@shiro.hasPermissions name="${(msign)!}:${(oper['add'])!},${(msign)!}:${(oper['edit'])!},${(msign)!}:${(oper['role'])!}">
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
	</script>
</head>
<body>
	<div id="pageloading"></div>
	
	<div class="l-panel-search">
	    <div class="l-panel-search-item">
	        <select id="property" name="property">
	        	<option value="userName" selected>登录名称</option>
				<option value="realName">用户姓名</option> 
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