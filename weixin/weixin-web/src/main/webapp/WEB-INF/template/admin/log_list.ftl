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
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
              			
    		$("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display:'操作名称',name:'operation', minWidth: 60,width:180, align: 'center' },
		            { display: '操作事件', name: 'action', minWidth: 60, width: 380, heightAlign: 'center' },
		            { display: '访问IP', name: 'ip', minWidth: 80, width: 180, align: 'center' },
		            { display: '访问时间', name: 'createDate',type:'datetime', align: 'center'}
		        ], 
	            pageSizeOptions: [20,25,30,40,50],
	            pageSize:20,
				headerRowHeight: 30,  
				rowHeight: 28,
				rownumbers: true,
				frozenRownumbers: true,  
				dateFormat:'yyyy-MM-dd hh:mm:ss',
            	checkbox: true,
            	switchPageSizeApplyComboBox: true,
            	selectRowButtonOnly: false,
            	allowUnSelectRow:false,
            	dataAction:'server',
            	pageParmName: 'pageNumber',
        		pagesizeParmName: 'pageSize',
        		sortnameParmName: 'orderBy',
        		sortorderParmName: 'orderType',
    			url:'${base}/admin/log/ajax?mid=${(mid)!}',
    			parms:[{name:'property', value:''},{name:'keyword', value:''}]
            });
    		
    		$("#property").ligerComboBox({width:90});
    		$("#keyword").ligerTextBox({width:220});    		
    		$("#btnSearch").ligerButton({ click: onSearchClick});
    	
    		dataGrid = $("#dataGrid").ligerGetGridManager();
    		
    		$("#pageloading").hide();
    	});
    	
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
							url:"${base}/admin/log/delete",
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
	</script>
</head>
<body>
	<div id="pageloading"></div>	
	<div class="l-panel-search">
	    <div class="l-panel-search-item">
	        <select id="property" name="property">
	        	<option value="operation" selected>操作名称</option>
				<option value="createUser">操作员</option> 
				<option value="ip">访问IP</option>
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