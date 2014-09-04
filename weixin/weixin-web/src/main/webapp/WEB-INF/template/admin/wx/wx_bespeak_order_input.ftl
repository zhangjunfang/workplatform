<#if !id??><#assign isAdd = true /><#else><#assign isEdit = true /></#if>
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
		var $validateForm;
		$(function(){
			$.metadata.setType("attr", "validate");
			
			$("#BespeakOrderInfo").ligerTab();
			
			$("#pageloading").hide();
			
		});
		
	</script>
</head>
<body style="padding:10px;">
	<div id="pageloading"></div>
		<div id="BespeakOrderInfo" style="overflow:hidden; border:1px solid #A3C0E8; ">
		<div title="服务预订信息" lselected="true">
		<table class="l-table-edit inputTable">
             <tr>
                <th align="right" class="l-table-edit-td">服务名称:</th>
                <td class="l-table-edit-td">
                	${(wxBespeakOrder.bespeakName)!}
                </td>
             </tr>
             <tr>
                <th align="right" class="l-table-edit-td">联系人:</th>
                <td class="l-table-edit-td">${(wxBespeakOrder.name)!}
                </td>
             </tr>
             <tr>
                <th align="right" class="l-table-edit-td">预订电话:</th>
                <td class="l-table-edit-td">
                ${(wxBespeakOrder.tel)!}
                </td>
            </tr>
               <tr>
               	<th align="right" class="l-table-edit-td">预订人数:</th>
                <td class="l-table-edit-td">
                	${(wxBespeakOrder.number)!}
                </td>
              </tr>
              <tr>
                 <th align="right" class="l-table-edit-td">预订日期:</th>
                <td class="l-table-edit-td">
                ${(wxBespeakOrder.orderDate?string("yyyy-MM-dd"))!}
                </td>
              </tr>
              <table>
            </div>
		</div>
</body>
</html>