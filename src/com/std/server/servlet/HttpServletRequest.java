package com.std.server.servlet;

import com.std.server.routes.URL;
import java.util.Map;

/**
 * @author luoxiao
 */
public interface HttpServletRequest {

    /**
     * 1. ��ÿͻ�����Ϣ
     */
    public URL getRequestURL (); //�������ؿͻ��˷�������ʱ������URL

    public String getRequestURI (); //���������������е���Դ������

    public String getQueryString (); //���������������еĲ�������

    public String getRemoteAddr (); //�������ط�������Ŀͻ�����IP��ַ

    public String getRemoteHost (); //�������ط�������Ŀͻ���������������

    public String getRemotePort (); //�������ؿͻ�����ʹ�õ�����˿ں�

    public String getLocalAddr (); //��������WEB��������IP��ַ

    public String getLocalName (); //��������WEB��������������

    public String getMethod (); //�õ��ͻ�������ʽ

    /**
     * 2.��ÿͻ�������ͷ
     */
    public String[] getHeader (String name);

    public Map<String, String[]> getHeaders (String name);

    public String[] getHeaderNames ();

    /**
     * 3. ��ÿͻ����������(�ͻ����ύ������)
     */
    public String getParameter (String name);

    public String[] getParameterValues (String name);

    public String[] getParameterNames ();

    public Map<String, String[]> getParameterMap ();

}
