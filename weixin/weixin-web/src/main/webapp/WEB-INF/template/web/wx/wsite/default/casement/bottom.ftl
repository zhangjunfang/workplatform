<#escape x as x?html>
<!-- 网站Bottom -->
<div id="gotop"></div>
<style>
.head_nav{left:90%;}
</style>
<script type="text/javascript">
	var redirectionUrl = top.document.URL;
	var index = redirectionUrl.indexOf("redirectionUrl");
	if(index>0){
		index = index+"redirectionUrl".length+1;
		redirectionUrl = redirectionUrl.substring(index);
	}else{
		redirectionUrl = escape(redirectionUrl);
	}
	  var content = [
	  <#if shopList??>
	  <#list shopList as list>
	  	{text:'${list.name}',icon:'grid',href:'${base}/web/wx/casement/shop/${list.id}?redirectionUrl='+redirectionUrl}
	  </#list>
	  </#if>
    ];
    
   <!-- var nav=[
    	{text:'服务中心 ',icon:'grid',href:'${base}/web/wx/casement/${(wid)!}'},
    	<!--{text:'会员之家',icon:'grid',href:'${base}/web/wx/member/${(wid)!}'},
    	{text:'促销活动广场',icon:'grid',href:'${base}/web/wx/sales/${(wid)!}'}
    ];-->
	$('#stores').dropmenu({
        content: content,
        placement: 'bottom',
        align: 'left'
    });
    $('#nav').dropmenu({
        content: nav,
        placement: 'bottom',
        align: 'right'
    });
	
	//创建组件
	$('#gotop').gotop();
</script>
</#escape>