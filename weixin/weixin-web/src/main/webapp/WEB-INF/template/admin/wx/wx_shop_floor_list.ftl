<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8" />
	<title>楼层信息列表 - <@spring.message "admin_title"/></title>
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
		var accordion = null;
		var mainTree;
		var dataGrid;
		var actionMenu;
		 
		$(function (){
	 
            $("#layout").ligerLayout({
            	leftWidth:200,
            	bottomHeight:5
            });
            actionMenu = $("#actionMenu").ligerGetMenuBarManager();
            var height = $(".l-layout-center").height();
            
			$("#shopFloorAccordion").ligerAccordion({ height: height, speed: null});

			$("#actionMenu").ligerMenuBar({items: [
            		<@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">{ text: '添加',click:onAddClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">{ text: '修改',click:onEditClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['khxm'])!}">{ text: '考核项目',click:onAsseObjectClick},</@shiro.hasPermission>
	                <@shiro.hasPermission name="${(msign)!}:${(oper['delete'])!}">{ text: '删除',click:onDeleteClick}</@shiro.hasPermission>
	            ]
            });
            
             var height = $(".l-layout-center").height();
			$("#shopFloorAccordion").ligerAccordion({ height: height, speed: null});
			
            var treeId = $("#treeId").val();
            $("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display:'楼层编号',name:'no', minWidth: 60,width:150, align: 'center' },
		            { display:'楼层名称',name:'name', minWidth: 60, align: 'center' },
		            { display:'楼层图片', name: 'logoPath', minWidth: 60,width:60, heightAlign: 'center',render:function(rowdata){
		            	return rowdata.logoPath?'<a href="${base}'+rowdata.logoPath+'" target="_blank">查看</a>':'';
		            }},
		            { display:'添加时间', name: 'createDate',type:'datetime', align: 'center'}
		        ], 
		        height:'97%',
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
    			url:'${base}/admin/wx/shopfloor/ajax',
    			parms:[{name:'property', value:''},{name:'keyword', value:''},{name:'mid', value:'${(mid)!}'},{name:'shopId',value:treeId}],
    			<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">onDblClickRow:onEditDoubleClick</@shiro.hasPermission>
            });
            
            dataGrid = $("#dataGrid").ligerGetGridManager();
            
            $("#mainTree").ligerTree({  
	            data:${tree}, 
	        	idFieldName :'id',
    			parentIDFieldName :'pid',
            	checkbox:false,
            	attribute:['id','type'],
                slide:false,
                nodeWidth:200,
                isExpand: 2, 
                onSelect:function(node){ 
                	if (!dataGrid) {
		            	return;
		            }
		            
                    if(node.data.id==1){
                    	return;
                    }  
                       
                	$("#treeId").val(node.data.id);
                	$("#treeType").val(node.data.type);
                	
		            var tenantId = $("#tenantId").val();
		            var treeId = $("#treeId").val();
		            var treeType = $("#treeType").val();
		            
		            var property = $("#property").val(); 
            		var keyword = $("#keyword").val(); 
            		
            		if(treeType == "WXSHOP"){
			            dataGrid.setOptions(
			                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'shopId', value: treeId}] }
			            );
	            		dataGrid.loadData(true);
            		}
            		
                }
            });
			shopFloorAccordion = $("#shopFloorAccordion").ligerGetAccordionManager();
			mainTree = $("#mainTree").ligerGetTreeManager();
			
			mainTree.selectNode('${treeId}');
  			
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
            var treeId = $("#treeId").val();
            var treeType = $("#treeType").val();
            
            var property = $("#property").val(); 
            var keyword = $("#keyword").val(); 
            
            if(treeType == "WXSHOP"){
	            dataGrid.setOptions(
	                { parms:[{ name: 'property', value: property},{ name: 'keyword', value: keyword},{ name: 'tenantId', value: tenantId},{ name: 'shopId', value: treeId}] }
	            );
	            dataGrid.loadData(true);
            }else{
            	$.ligerDialog.warn('请选择要查询楼层的门店！');
            }
            
    	}
    	
    	function onCloseDialog(dialog){
    		dialog.close();
    	}
    	
  <@shiro.hasPermission name="${(msign)!}:${(oper['add'])!}">
    	function onAddClick(){
    		var tenantId = $("#tenantId").val();
    		var treeId = $("#treeId").val();
    		var treeType = $("#treeType").val();
    		if(tenantId==1 || treeType=="CONFIG"){
				$.ligerDialog.warn('请选择要添加楼层的门店！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/shopfloor/add/'+tenantId+'/'+treeId+'?mid=${(mid)!}', 
					width: 450, 
					height: 300,
					title:'添加门店楼层',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick: doSaveUpload}, 
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
    	function doSaveUpload(item, dialog) {
    	    
     		var result=dialog.frame.doSerialize();
     		//执行上传文件操作的函数  
		  	$.ajaxFileUpload({  
		        url:result.url+"?"+result.data,
		        secureuri:true,//是否启用安全提交,默认为false   
		        fileElementId:[result.imageFile],//文件选择框的对象
		        dataType:'text',
		        async:true,
		        success:function(data, status){//服务器响应成功时的处理函数  
		            data = data.replace(/<pre.*?>/g, '');
		            data = data.replace(/<PRE.*?>/g, '');  
		            data = data.replace("<PRE>", '');
		            data = data.replace("</PRE>", '');  
		            data = data.replace("<pre>", '');  
		            data = data.replace("</pre>", '');
							            
		            var result = 'error,信息发送中';
		            if(data.startWith("true")){
		            	data = data.split(',');
		            	result = 'success,'+data[1];
		            }else if(data.startWith("false")){
		            	data = data.split(',');
		            	result = 'error,'+data[1];
		            }else{
		            	result = 'error,服务端异常';
		            }
		            
		            if(result.startWith("success")){
						dialog.close();
					}else{
						$.ligerDialog.error(result.split(',')[1]);
					}
		        }, 
		        error:function(data, status, e){ //服务器响应失败时的处理函数  
		           $.ligerDialog.error("服务端发生错误");
		        },
		        complete:function(){
				   dataGrid.loadData(true);
		        }
		    });
			     		
    	}
    	
    	function deleteLogo(id,logoPath)
		{
		    $.ajax({
                url: '${base}/admin/wx/shopfloor/deleteLogo',
                type: 'POST',
                data:{'id':id,'logoPath':logoPath},
                dataType:'text',
                async:true,
                 success:function(data, status){//服务器响应成功时的处理函数  
		            data = data.replace(/<pre.*?>/g, '');
		            data = data.replace(/<PRE.*?>/g, '');  
		            data = data.replace("<PRE>", '');
		            data = data.replace("</PRE>", '');  
		            data = data.replace("<pre>", '');  
		            data = data.replace("</pre>", '');
							            
		            var result = 'error,信息发送中';
		            if(data.startWith("true")){
		            	data = data.split(',');
		            	result = 'success,'+data[1];
		            }else if(data.startWith("false")){
		            	data = data.split(',');
		            	result = 'error,'+data[1];
		            }else{
		            	result = 'error,服务端异常';
		            }
		            
		            if(result.startWith("success")){
						$.ligerDialog.success('删除图片成功');
					}else{
						$.ligerDialog.error(result.split(',')[1]);
					}
		        }, 
		        error:function(data, status, e){ //服务器响应失败时的处理函数  
		           $.ligerDialog.error("服务端发生错误");
		        }
              });
		}
    	
    	String.prototype.startWith=function(str){     
		  var reg=new RegExp("^"+str);     
		  return reg.test(this);        
		}  
		
		String.prototype.endWith=function(str){     
		  var reg=new RegExp(str+"$");     
		  return reg.test(this);        
		}
    	</@shiro.hasPermissions>
    	
    	<@shiro.hasPermission name="${(msign)!}:${(oper['edit'])!}">
    	function onEditClick(){
    		var id = dataGrid.selectCheckedRow('id');
    		if(id==""){
				$.ligerDialog.warn('请选择要修改的记录！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/shopfloor/edit/'+id+'?mid=${(mid)!}', 
					width: 450, 
					height: 300,
					title:'修改门店楼层',
					isResize: true,
					showMax: false,
		            showToggle: false,
		            showMin: false,
					buttons: [
						{text: '保存', onclick:doSaveUpload}, 
						{text: '取消', onclick: function (item, dialog) {onCloseDialog(dialog);} } 
					]
				});
			}
		}
		
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
				url: '${base}/admin/wx/shopfloor/edit/'+data.id+'?mid=${(mid)!}', 
				width: 450, 
				height: 300,
				title:'修改门店楼层',
				isResize: true,
				showMax: false,
	            showToggle: false,
	            showMin: false,
				buttons: [
					{text: '保存', onclick:doSaveUpload}, 
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
							url:"${base}/admin/wx/shopfloor/delete",
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
        <div position="left" id="shopFloorAccordion" style="height:100%;">
        	<div title="微信列表" class="l-scroll">
        		<ul id="mainTree"></ul>
        	</div>
        </div>
        <div position="center">
        	<div class="l-panel-search">
        		<input id="tenantId" name="tenantId" type="hidden" value="${(tenantId)!}" />
        		<input id="treeId" name="treeId" type="hidden" value="${(treeId)!}" />
        		<input id="treeType" name="treeType" type="hidden" value="${(treeType)!}" />
			    <div class="l-panel-search-item">
			        <select id="property" name="property">
			        	<option value="name" selected>楼层名称</option>
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