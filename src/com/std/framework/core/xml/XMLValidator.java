package com.std.framework.core.xml;


import com.std.framework.core.util.PathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.SAXException;

/**
 * @author Luox XSD->XML�����ļ���ʽУ����
 */
public class XMLValidator {

    public static final String MVC_CONFIG_XSD        = "mvc_config.xsd";
    public static final String DEFAULT_MVC_FILE_NAME = "mvc_config.xml";

    /**
     * ��xsd������֤xml
     */
    public static boolean valid (List<String> xsdpaths, String xmlpath) throws SAXException, FileNotFoundException {
        InputStream xml     = new FileInputStream(new File(xmlpath));
        List<File>  schemas = new ArrayList<File>();
        for (String xsdpath : xsdpaths) {
            schemas.add(new File(xsdpath));
        }
        return XMLParser.validateWithMultiSchemas(xml, schemas);
    }

    /**
     * ��xsd������֤xml
     */
    public static boolean valid (String xsdpath, String xmlpath) throws SAXException, FileNotFoundException {
        File xsdfile = new File(xsdpath);
        File xmlfile = new File(xmlpath);
        return XMLParser.validateWithSingleSchema(xmlfile, xsdfile);
    }

    /**
     * mvc_config xml������֤
     */
    public static boolean validMVCConfig () throws SAXException, FileNotFoundException {
        String rootClassPath = PathUtil.getRootClassPath();
        File   xsdfile       = new File(rootClassPath + "com/std/framework/core/" + XMLValidator.MVC_CONFIG_XSD);
        File   xmlfile       = new File(rootClassPath + XMLValidator.DEFAULT_MVC_FILE_NAME);
        return XMLParser.validateWithSingleSchema(xmlfile, xsdfile);
    }

}
