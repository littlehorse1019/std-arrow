package com.std.framework.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.std.framework.core.util.JsonUtil;
import com.std.framework.view.ViewSetting;
import com.std.framework.view.handle.CoreAction;

/**
 * @author Luox response 表现形式方法类
 */
public class Render {

	private static int convertDepth = 15;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private CoreAction action;

	public Render(HttpServletRequest request, HttpServletResponse response, CoreAction action) {
		this.request = request;
		this.response = response;
		this.action = action;
		settingResponse();
	}
	
	public void setReqAttr(String name, Object obj) {
		request.setAttribute(name, obj);
	}

	/** 重定向到指定的页面 */
	public void redirectJsp(String fwJsp) {
		String forwardPath = ResponseHandler.getResponseJsp(fwJsp, action.getClass().getName());
		try {
			/** 回传action属性值自动填充到表单 */
			ResponseHandler.prepareFormAttribute(action);
			response.sendRedirect(forwardPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 转发到指定的页面 */
	public void forwardJsp(String fwJsp) {
		String forwardPath = ResponseHandler.getResponseJsp(fwJsp, action.getClass().getName());
		try {
			/** 回传action属性值自动填充到表单 */
			ResponseHandler.prepareFormAttribute(action);
			request.getRequestDispatcher(forwardPath).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回Json字符串
	 */
	public void toJson(Object obj) {
		setEncoding(ViewSetting.getRespEncoding());
		writeString(JsonUtil.toJson(obj, convertDepth));
	}

	/**
	 * 返回Json字符串，指定编码
	 */
	public void toJson(Object obj, String encoding) {
		setEncoding(encoding);
		writeString(JsonUtil.toJson(obj, convertDepth));
	}

	/** 返回普通text字符串 */
	public void toText(String text) {
		setEncoding(ViewSetting.getRespEncoding());
		writeString(text);
	}

	/**
	 *  返回普通text字符串 ，指定编码
	 */
	public void toText(String text, String encoding) {
		setEncoding(encoding);
		writeString(text);
	}

	private void writeString(String returnString) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(returnString);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private void setEncoding(String encoding) {
		response.setCharacterEncoding(encoding);
	}

	private void settingResponse() {
		response.setHeader("Pragma", ViewSetting.getRespPragma());
		response.setHeader("Cache-Control", ViewSetting.getRespCacheControl());
		response.setDateHeader("Expires", ViewSetting.getRespExpires());
	}

}
