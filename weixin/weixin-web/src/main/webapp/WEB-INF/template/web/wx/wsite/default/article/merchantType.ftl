<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>商户分类</title>
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1,width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no,maximum-scale=1" media="(device-height: 568px)" />
<meta name="apple-mobile-web-app-capable" content="yes"  />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />	
<link rel="stylesheet" href="${base}/static/wechat/css/style.css">
<script type="text/javascript" src="${base}/static/wsite/skins/default/js/zepto.js"></script>
<style type="text/css">
    .main1 {position:relative;padding:0 60px;background:url(${base}/static/wechat/img/13.png) repeat-y top}
    .type {position:relative;height:90px;line-height:110px;padding:0 82px;margin:10px 0 0;font-size:35px;font-weight:bold;text-align:center;}
    #typeTable{width:98%;font-size:40px;font-weight:normal;}
    .text{font-size:30px;font-weight:normal;}
</style>
<script type="text/javascript">
 $(function(){
       var arry = new Array();
       <#list allLIST as mer>
         arry.push('${mer.name}');
       </#list>
       $("#typeTable").html('');
       var str='';
       for(var i=0; i<arry.length;i++)
       {
           str +='<tr><td style="padding-right:20px;"><a href="${base}/web/wx/casement/merchantList/${wid}?name='+arry[i]+'"> '+arry[i]+'</a></td>';
           if(++i < arry.length)
           {
              str +='<td><a href="${base}/web/wx/casement/merchantList/${wid}?name='+arry[i]+'"> '+arry[i]+'</a></td>';
           }
           str +='</tr>';
       }
       $("#typeTable").append(str);
   })
   
</script>

</head>
<body>
<a href="http://www.hnxym.com/" class="logo" title="爱家">爱家</a>
<p class="entry">

<div class="main1">
        <h2 style="font-weight:bold;font-size:35px;">联盟商户介绍</h2>
        <div class="text">
          &nbsp;&nbsp;&nbsp;&nbsp;爱家联盟商户为爱家公司的商业合作伙伴，在诚信合作的基础上资源共享，为顾客提供更多样、便利的购物体验。<br/>
          &nbsp;&nbsp;&nbsp;&nbsp;凡持爱家联赢通VIP卡、福礼卡、家庭卡的顾客，均可到下列联盟商户处刷卡消费。<br/>
          &nbsp;&nbsp;&nbsp;&nbsp;商户加盟热线：15239650017（孙经理）&nbsp;15239650181（张经理）		
        </div>
        <div class="type">商&nbsp;户&nbsp;分&nbsp;类</div>
		<article class="intro">
		      <table id="typeTable"></table>
		      <br>
			<p>点击左上角“返回”，继续体验爱家服务！
		</article>		
</div>
<footer>
    <p class="cr">2013 微朋  All Rights Reserved Baoyuan Tech Co.,Ltd.
</footer>
</body>
</html>