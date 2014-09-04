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
	<script src="${base}/static/js/jquery.ajaxfileupload-2.js" type="text/javascript"></script>
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
		            { display:'门店名称',name:'name', minWidth: 60, align: 'center' },
		            { display:'门店类别',name:'categoryName', minWidth: 60, align: 'center' },
		            { display:'门店图片', name: 'logoPath', minWidth: 60,width:60, heightAlign: 'center',render:function(rowdata){
		            	return rowdata.logoPath?'<a href="${base}'+rowdata.logoPath+'" target="_blank">查看</a>':'';
		            }},
		            { display:'地理位置', name: 'gps', minWidth: 60,width:60, heightAlign: 'center',render:function(rowdata){
		            	return rowdata.gps?'<a href="${base}'+rowdata.gps+'" target="_blank">查看</a>':'';
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
    			url:'${base}/admin/wx/shop/ajax',
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
		            
                    if(node.data.id==1){
                    	return;
                    }  
                       
                	$("#wid").val(node.data.id);
                	
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
    		
    		if(tenantId==1){
				$.ligerDialog.warn('请选择要添加门店的微信账号！')
			}else{
				$.ligerDialog.open({ 
					url: '${base}/admin/wx/shop/add/'+tenantId+'/'+wid+'?mid=${(mid)!}', 
					width: 680, 
					height: 370,
					title:'添加门店',
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
		        fileElementId:[result.attachFile,result.imageFile],//文件选择框的对象
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
                url: '${base}/admin/wx/shop/deleteLogo',
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
		
		function deleteGps(id,logoPath)
		{
		    $.ajax({
                url: '${base}/admin/wx/shop/deleteGps',
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
					url: '${base}/admin/wx/shop/edit/'+id+'?mid=${(mid)!}', 
					width: 680, 
					height: 370,
					title:'修改门店',
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
		
    	function onEditDoubleClick(data){
    		$.ligerDialog.open({ 
				url: '${base}/admin/wx/shop/edit/'+data.id+'?mid=${(mid)!}', 
				width: 680, 
				height:370,
				title:'修改门店',
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
							url:"${base}/admin/wx/shop/delete",
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
			        	<option value="name" selected>门店名称</option>
			        	<option value="categoryName" selected>门店类别</option>
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