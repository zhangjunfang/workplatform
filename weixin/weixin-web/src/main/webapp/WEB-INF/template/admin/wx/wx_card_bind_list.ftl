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
            	bottomHeight:5,
            });
            
             $("#actionMenu").ligerMenuBar({items: [
	            ]
            });
            
            actionMenu = $("#actionMenu").ligerGetMenuBarManager();
             var wid = $("#wid").val(); 
             
            $("#dataGrid").ligerGrid({
                columns: [
		            { display:'ID',name:'id',align:'center',width:27,hide:true},
		            { display:'会员卡号',name:'hykNo', minWidth: 60,width:100,align: 'center'},
		            { display:'会员名称',name:'hykName', minWidth: 60,width:150, align: 'center' },
		            { display:'会员类型',name:'hykType', minWidth: 60,width:150,align: 'center' },
		            { display:'门店id',name:'hykShop', minWidth: 60,width:150,align: 'center' },
		            { display: '绑定时间', name: 'createDate',type:'datetime', align: 'center'}
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
    			url:'${base}/admin/wx/cardbind/ajax',
    			parms:[{name:'property', value:''},{name:'keyword', value:''},{name:'wid', value:wid}],
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
    	
		function onCloseDialog(dialog){
    		dialog.close();
    	}
    	
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
			        	<option value="hykNo" selected>会员卡号</option>
			        	<option value="hykType" selected>会员类型</option>
			        	<option value="hykShop" selected>会员卡门店</option>
			        </select>
			    </div>
			    <div class="l-panel-search-item">
			    	<input id="keyword" name="keyword" type="text" value=""/>
			    </div>
			    <div class="l-panel-search-item">
			        <div id="btnSearch" class="liger-search-button">搜索</div>
			    </div>	    
			</div>
			<div id="dataGrid"></div>
        </div>  
    </div>	
	<div style="display:none;"></div>	
</body>
</html>