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
	<link href="${base}/static/assets/skins/<@spring.message "default.skin"/>/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/assets/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/css/base.css" rel="stylesheet" type="text/css" /> 
	<script src="${base}/static/js/jquery-1.8.1.min.js" type="text/javascript"></script>
	<script src="${base}/static/assets/js/ligerui.min.js" type="text/javascript"></script>
	<script src="${base}/static/js/ligerui.expand.js" type="text/javascript"></script>
	<script src="${base}/static/js/json2.js" type="text/javascript"></script>
	<style type="text/css"> 
		
	</style>
	<script type="text/javascript">
		var mainTree;
		var dataGrid;
		var actionMenu;
		$(function (){
			$("#layout").ligerLayout({
            	allowLeftCollapse: false,
            	leftWidth:180,
            	bottomHeight:5
            });
            
             $("#actionMenu").ligerMenuBar({items: [
            		<@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">{ text: '添加',click:onAddClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">{ text: '修改',click:onEditClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
            
            actionMenu = $("#actionMenu").ligerGetMenuBarManager();
            
            $("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display:'商户名称',name:'name', minWidth: 60, align: 'center' },
		            { display:'商户类别',name:'typeName', minWidth: 60, align: 'center' },
		            { display:'添加时间', name: 'createDate',type:'datetime', align: 'center'}
		        ], 
		        height:'97%',
	            pageSizeOptions: [20,25,30,40,50],
	            pageSize:20,
				headerRowHeight: 30,  
				rowHeight: 28,
				rownumbers: true,
				frozenRownumbers: true,  
				dateFormat:'yyyy-MM-dd',
            	checkbox: true,
            	switchPageSizeApplyComboBox: true,
            	selectRowButtonOnly: false,
            	allowUnSelectRow:false,
            	dataAction:'server',
            	pageParmName: 'pageNumber',
        		pagesizeParmName: 'pageSize',
        		sortnameParmName: 'orderBy',
        		sortorderParmName: 'orderType',
    			url:'${base}/admin/wx/merchant/ajax',
    			parms:[{name:'property', value:''},{name:'keyword', value:''},{name:'mid', value:'${(mid)!}'}],
    			<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">onDblClickRow:onEditDoubleClick</@shiro.hasPermission>
            });
            
            dataGrid = $("#dataGrid").ligerGetGridManager();
            
            $("#mainTree").ligerTree({  
	            data:${tree}, 
	        	idFieldName :'id',
    			parentIDFieldName :'pid',
            	checkbox:false,
            	attribute:['id'],
                slide:false,
                nodeWidth:200,
                onSelect:function(node){ 
                	if (!dataGrid) {
		            	return;
		            }
		            $("#wid").val(node.data.id);
                	
                    if(node.data.id==1){
                    	return;
                    }  
                       
		            var tenantId = $("#tenantId").val();
		            var wid = $("#wid").val();
		            
		            var property = $("#property").val(); 
            		var keyword = $("#keyword").val(); 
            		
		            dataGrid.setOptions(
		                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'wid', value: wid}] }
		            );
            		dataGrid.loadData(true);
                }
            });

			mainTree = $("#mainTree").ligerGetTreeManager();
			
			mainTree.selectNode('${wid}');
  			
            $("#property").ligerComboBox({width:90});
    		$("#keyword").ligerTextBox({width:220});    		
    		$("#btnSearch").ligerButton({ click: onSearchClick});
    		
    		$("#pageloading").hide();
		});
		
		function onSearchClick(){
    		if (!dataGrid) {
            	return;
            }
            var tenantId = $("#tenantId").val();
            var wid = $("#wid").val();
            
            var property = $("#property").val(); 
            var keyword = $("#keyword").val(); 
            
            dataGrid.setOptions(
                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'wid', value: wid}] }
            );
            dataGrid.loadData(true);
    	}
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">
    	function onAddClick(){
    		var tenantId = $("#tenantId").val();
    		var wid = $("#wid").val();
    		if(wid==1){
				$.ligerDialog.warn('请选择要添加商户信息的微信账号！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/merchant/add/'+tenantId+'/'+wid+'?mid=${(mid)!}', 
					width: 800,
					height: 420, 
					title:'添加商户信息',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick: doSave}, 
						{text: '取消', onclick: function (item, dialog) {onCloseDialog(dialog);} } 
					]
				});
			}
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
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    	function onEditClick(){
    		var id = dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要修改的记录！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/merchant/edit/'+id+'?mid=${(mid)!}', 
					width: 800,
					height: 420, 
					title:'修改商户信息',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick: doSave}, 
						{text: '取消', onclick: function (item, dialog) {onCloseDialog(dialog);} } 
					]
				});
			}
		}
		
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
				url: '${base}/admin/wx/merchant/edit/'+data.id+'?mid=${(mid)!}', 
				width: 800,
				height: 420, 
				title:'修改商户信息',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick: doSave}, 
					{text: '取消', onclick: function (item, dialog) {onCloseDialog(dialog);} } 
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
							url:"${base}/admin/wx/merchant/delete",
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
	<div id="layout">
        <div position="left" title="微信列表">
        	<ul id="mainTree"></ul>
        </div>
        <div position="center">
        	<div class="l-panel-search">
        		<input id="tenantId" name="tenantId" type="hidden" value="${(tenantId)!}" />
        		<input id="wid" name="wid" type="hidden" value="${(wid)!}" />
			    <div class="l-panel-search-item">
			        <select id="property" name="property">
			        	<option value="name" selected>商户名称</option>
			        	<option value="typeName" selected>商户类别</option>
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
        </div>  
    </div>	
	<div style="display:none;"></div>	
</body>
</html>