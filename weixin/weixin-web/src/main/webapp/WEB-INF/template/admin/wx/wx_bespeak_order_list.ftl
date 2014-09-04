<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8" />
	<title>门店预约服务列表 - <@spring.message "admin_title"/></title>
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
	<script src="${base}/static/js/jquery.ajaxfileupload-2.js" type="text/javascript"></script>
	<style type="text/css"> 
	
	</style>
	<script type="text/javascript">
		var dataGrid;
		var mainTree;
		var accordion = null;
		
    	$(function (){
  			    
  			$("#layout").ligerLayout({
            	leftWidth:180,
            	bottomHeight:5
            });
                      	
            $("#actionMenu").ligerMenuBar({items: [
	                <@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">{ text: '查看',click:onEditClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
             var shopId = $("#shopId").val(); 			
    		$("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display:'预约名称',name:'bespeakName', minWidth: 60,width:150, align: 'center' },
		            { display:'联系人',name:'name', minWidth: 60,width:150, align: 'center' },
		            { display:'联系电话',name:'tel', minWidth: 60,width:100,align: 'center' },
		            { display:'预订人数',name:'number', minWidth: 60,width:100, align: 'center' },
		            { display:'预订时间', name: 'orderDate',type:'datetime', align: 'center'},
		            { display:'备注',name:'memo', minWidth: 60,width:150,align: 'center',render:function(rowdata){
		            	var memo = rowdata.memo;
		            	if(rowdata.memo.length>10){
		            		memo = memo.substring(0,10)+"...";
		            	}
		            	return memo;
		            }}
		        ], 
		        height:'97%',
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
    			url:'${base}/admin/wx/bespeakorder/ajax?mid=${(mid)!}',
    			parms:[{name:'property', value:''},{name:'keyword', value:''},{name:'shopId',value:shopId}],
    			tree: {columnName:'name',idField: 'id',parentIDField: 'parentId'},
    			<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">onDblClickRow:onEditDoubleClick</@shiro.hasPermission>
            });
    		
    		$("#property").ligerComboBox({width:90});
    		$("#keyword").ligerTextBox({width:220});    		
    		$("#btnSearch").ligerButton({ click: onSearchClick});
    	
    		dataGrid = $("#dataGrid").ligerGetGridManager();
    		
    		$("#pageloading").hide();
    		var height = $(".l-layout-center").height();
			$("#bespeakorderAccordion").ligerAccordion({ height: height, speed: null});
    		 $("#mainTree").ligerTree({  
	            data:${tree}, 
	        	idFieldName :'id',
    			parentIDFieldName :'pid',
            	checkbox:false,
            	attribute:['id','type'],
                slide:false,
                nodeWidth:320,
                onSelect:function(node){ 
                	if (!dataGrid) {
		            	return;
		            }
		            
                    if(node.data.id==1){
                    	return;
                    }  
                       
                	$("#shopId").val(node.data.id);
		            $("#treeType").val(node.data.type);
                	
		            var tenantId = $("#tenantId").val();
		            var shopId = $("#shopId").val();
		            var treeType = $("#treeType").val();
		            
		            var property = $("#property").val(); 
            		var keyword = $("#keyword").val(); 
            		
            		if(treeType == "WXSHOP"){
			            dataGrid.setOptions(
			                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'shopId', value: shopId}] }
			            );
	            		dataGrid.loadData(true);
            		}
            		
                }
            });

			accordion = $("#bespeakorderAccordion").ligerGetAccordionManager();
			mainTree = $("#mainTree").ligerGetTreeManager();
			
			mainTree.selectNode('${wid}');
    	});
    	
    	
    	
    	function onSearchClick(){
    		if (!dataGrid) {
            	return;
            }
            var tenantId = $("#tenantId").val();
            var shopId = $("#shopId").val();
            var treeType = $("#treeType").val();
            
            var property = $("#property").val(); 
            var keyword = $("#keyword").val(); 
            
             if(treeType == "WXSHOP"){
	            dataGrid.setOptions(
	                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'shopId', value: shopId}] }
	            );
	            dataGrid.loadData(true);
            }else{
            	$.ligerDialog.warn('请选择要查询门店预约服务的门店！');
            }
            
    	}
    	
    	function onCloseDialog(dialog){
    		dialog.close();
    	}
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    	function onEditClick(){
    		var id=dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要查看的记录！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/bespeakorder/edit/'+id+'?mid=${(mid)!}', 
					width: 620,
					height: 320,
					title:'查看门店服务预订',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doSave4Upload}, 
						{text: '取消', onclick: function (item, dialog) { dialog.close();} } 
					]
				});
			}
    	}
    	
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
				url: '${base}/admin/wx/bespeakorder/edit/'+data.id+'?mid=${(mid)!}', 
				width: 620,
				height: 320,
				title:'查看门店服务预订',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick:doSave4Upload}, 
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
							url:"${base}/admin/wx/bespeakorder/delete",
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
	<input id="tenantId" name="tenantId" type="hidden" value="${(tenantId)!}" />
	<input id="shopId" name="shopId" type="hidden" value="${(shopId)!}" />
	<input id="treeType" name="treeType" type="hidden" value="${(treeType)!}" />
	<div id="layout">
        <div position="left" id="bespeakorderAccordion" style="height:100%;" >
        	<div title="微信列表" class="l-scroll"><ul id="mainTree"></ul></div>
        </div>
        <div position="center">
			<div class="l-panel-search">
			    <div class="l-panel-search-item">
			        <select id="property" name="property">
						<option value="bespeakName">服务名称</option>
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
	<div style="display:none;"></div>
</body>
</html>