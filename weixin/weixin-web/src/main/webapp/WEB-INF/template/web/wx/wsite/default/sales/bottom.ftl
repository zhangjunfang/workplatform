<#escape x as x?html>
<!-- 网站Bottom 
<div class="i_client">
	<div class="i_client_s">
	<ul>
		<li class="fl"><a href="http://www.wp99.cn/vipeng/web/lottery/get_lottery.do">幸运转盘</a></li>
		<li class="fl"><a href="http://www.wp99.cn/vipeng/web/lottery/get_scratch.do">刮刮乐劵</a></li>
		<li class="fl"><a href="http://smallfishsky.duapp.com/Dannisi/web/tuan.html">爆款团购</a></li>
		<li class="fl"><a href="http://smallfishsky.duapp.com/Dannisi/web/gift_sales.html">秒杀专享</a></li>
		<li class="fl"><a href="${base}/web/wx/sales/newdm">DM分享</a></li>
	</ul>
  </div>
</div>
-->
<div class="c-bottom"></div>
<div id="gotop"></div>
<script type="text/javascript">
	var redirectionUrl = top.document.URL;
	var index = redirectionUrl.indexOf("redirectionUrl");
	if(index>0){
		index = index+"redirectionUrl".length+1;
		redirectionUrl = redirectionUrl.substring(index);
	}else{
		redirectionUrl = escape(redirectionUrl);
	}
	 <!-- var content = [
	  <#if shopList??>
	  <#list shopList as list>
	  	{text:'${list.name}',icon:'grid',href:'${base}/web/wx/casement/shop/${list.id}?redirectionUrl='+redirectionUrl}<#if list>,</#if>
	  </#list>
	  </#if>
    ];-->
     var content = [
	  <#if articleList??>
	  <#list articleList as list>
	  	{text:'${list.title}',icon:'grid',href:'${base}/web/wx/casement/shop/${list.shopId}?redirectionUrl='+redirectionUrl}<#if list_has_next>,</#if>
	  </#list>
	  </#if>
    ];
	 var nav=[
    	{text:'服务中心',icon:'grid',href:'${base}/web/wx/casement/${(wid)!}'},
    	<!--{text:'会员之家',icon:'grid',href:'${base}/web/wx/member/${(wid)!}'},-->
    	{text:'促销活动广场',icon:'grid',href:'${base}/web/wx/sales/${(wid)!}'}
    ];
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