package com.baoyuan.controller.admin;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.BaseController;
import com.baoyuan.entity.User;
import com.baoyuan.util.StringUtil;

public abstract class BaseAdminController extends BaseController {

	protected static final String STATUS = "status";

	protected static final String WARN = "warn";

	protected static final String SUCCESS = "success";

	protected static final String ERROR = "error";

	protected static final String MESSAGE = "message";

	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	public User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getSession()
				.getAttribute(WxGlobal.CURRENT_USER);
	}

	// 输出JSON警告消息，返回null
	public Map<String, String> ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		return jsonMap;
	}

	// 输出JSON成功消息，返回null
	public Map<String, String> ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		return jsonMap;
	}

	// 输出JSON错误消息，返回null
	public Map<String, String> ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		return jsonMap;
	}
	
	/**
	 * 保存上传文件，返回存储路径
	 */
	public String saveAttachFile(MultipartFile multipartFile) {
		String originalFileName = multipartFile.getOriginalFilename();
		logger.error("originalFileName:" + originalFileName);
		String contentType = multipartFile.getContentType();
		logger.error("contentType:" + contentType);
		
		/** 获得文件后缀名 **/
		String fileExtName = FilenameUtils.getExtension(multipartFile
				.getOriginalFilename());
		logger.info("文件扩展名:" + fileExtName);
		String[] fileTypes = new String[] { "jpg","jpeg","gif","bmp","png"};
		if(!StringUtil.contains(fileTypes, fileExtName.toLowerCase())){
			logger.info("上传的文件不符合格式:" + originalFileName);
			return null;
		}
		
		String newFileName = null;
		String rootPath =getServletContext().getRealPath("/");
		logger.error("rootPath----------"+rootPath);
		
		/** 拼接文件路径,以时间戳生成文件名 **/
		newFileName = "/upload/attach/ht/" + getNewFileName() + "." + fileExtName;
		String newFilePath = rootPath + newFileName;
		
		boolean saveFile = false;
		try {// 保存文件
			File logo = new File(newFilePath);
			if (!logo.getParentFile().exists()) {
				logo.getParentFile().mkdirs();
			}
			multipartFile.transferTo(logo);
			saveFile = true;
		} catch (IllegalStateException e) {
			logger.error(" saveAttachFile Method Exception:"
					+ e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(" saveAttachFile Method Exception:"
					+ e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(" saveAttachFile Method Exception:"
					+ e);
			e.printStackTrace();
		} finally {
			if (!saveFile) {
				newFileName = null;
			}
		}
		return newFileName;
	}
	
	/**
	 * 根据时间生成文件名
	 */
	public String getNewFileName() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000);
		return newFileName;
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param filePath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String filePath) {
		boolean flag = false;
		String realPath = getServletContext().getRealPath(filePath);
		File delFile = new File(realPath);
		// 路径为文件且不为空则进行删除
		if (delFile.isFile() && delFile.exists()) {
			delFile.delete();
			flag = true;
		}
		return flag;
	}
}
