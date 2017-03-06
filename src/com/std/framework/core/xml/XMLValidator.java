package com.std.framework.core.xml;


import com.std.framework.core.util.PathUtil;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox
 *         XSD->XML配置文件格式校验器
 */
public class XMLValidator {

    public static final String MVC_CONFIG_XSD = "mvc_config.xsd";
    public static final String DEFAULT_MVC_FILE_NAME = "mvc_config.xml";

    /**
     * 多xsd规则验证xml
     */
    public static boolean valid(List<String> xsdpaths, String xmlpath) throws SAXException, FileNotFoundException {
        InputStream xml = new FileInputStream(new File(xmlpath));
        List<File> schemas = new ArrayList<File>();
        for (String xsdpath : xsdpaths) {
            schemas.add(new File(xsdpath));
        }
        return XMLParser.validateWithMultiSchemas(xml, schemas);
    }

    /**
     * 单xsd规则验证xml
     */
    public static boolean valid(String xsdpath, String xmlpath) throws SAXException, FileNotFoundException {
        File xsdfile = new File(xsdpath);
        File xmlfile = new File(xmlpath);
        return XMLParser.validateWithSingleSchema(xmlfile, xsdfile);
    }

    /**
     * mvc_config xml配置验证
     */
    public static boolean validMVCConfig() throws SAXException, FileNotFoundException {
        String rootClassPath = PathUtil.getRootClassPath();
        File xsdfile = new File(rootClassPath + "com/std/framework/core/" + XMLValidator.MVC_CONFIG_XSD);
        File xmlfile = new File(rootClassPath + XMLValidator.DEFAULT_MVC_FILE_NAME);
        return XMLParser.validateWithSingleSchema(xmlfile, xsdfile);
    }

}
