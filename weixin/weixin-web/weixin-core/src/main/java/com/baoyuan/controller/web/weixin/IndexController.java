package com.baoyuan.controller.web.weixin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baoyuan.constant.weixin.WxGlobal;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "IndexController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/index")
public class IndexController extends BaseWebController {

	@RequestMapping(value = "")
	public String index(Model model) {
		
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/index";
	}
}
