<#escape x as x?html>
<!-- 网站Header-->
<header id="header" class="header clearfix">
<div class="head_logo"><a href="${base}/web/wx/casement/${(wid)!}" class="logo" ></a></div>
<div class="head_title"><#if tencent??>${(tencent.nickname)!}，你好！</#if></div>
<div class="head_nav"><a id="nav" href="javascript:;">···</a></div>
<div class="sel_stores_tit">
	<a id="stores" href="javascript:;">${(CURRENT_SHOP.name)!"爱家百货"}
	<span class="i_up_arrow" data-changestyle="i_down_arrow"></span>
	</a>
</div>
<div id="head_nav" class="clearfix fr">
  <ul class="clearfix">
	<li><a href="${base}/web/wx/member/${(wid)!}" id="member" class="<#if shopnav=="member">nav_current</#if>"><span>会员专享</span></a></li>
	<li><a href="${base}/web/wx/member/discount" id="discount" class="<#if shopnav=="discount">nav_current</#if>"><span>会员折扣</span></a></li>
	<li><a href="${base}/web/wx/member/rights" id="rights" class="<#if shopnav=="rights">nav_current</#if>"><span>会员权益</span></a></li>
  </ul>
</div> 
</header>
</#escape>