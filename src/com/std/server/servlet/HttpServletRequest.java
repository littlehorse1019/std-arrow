package com.std.server.servlet;

import com.std.server.routes.URL;
import java.util.Map;

/**
 * @author luoxiao
 */
public interface HttpServletRequest {

    /**
     * 1. 获得客户机信息
     */
    public URL getRequestURL (); //方法返回客户端发出请求时的完整URL

    public String getRequestURI (); //方法返回请求行中的资源名部分

    public String getQueryString (); //方法返回请求行中的参数部分

    public String getRemoteAddr (); //方法返回发出请求的客户机的IP地址

    public String getRemoteHost (); //方法返回发出请求的客户机的完整主机名

    public String getRemotePort (); //方法返回客户机所使用的网络端口号

    public String getLocalAddr (); //方法返回WEB服务器的IP地址

    public String getLocalName (); //方法返回WEB服务器的主机名

    public String getMethod (); //得到客户机请求方式

    /**
     * 2.获得客户机请求头
     */
    public String[] getHeader (String name);

    public Map<String, String[]> getHeaders (String name);

    public String[] getHeaderNames ();

    /**
     * 3. 获得客户机请求参数(客户端提交的数据)
     */
    public String getParameter (String name);

    public String[] getParameterValues (String name);

    public String[] getParameterNames ();

    public Map<String, String[]> getParameterMap ();

}
