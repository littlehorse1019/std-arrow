package com.std.framework.core.xml;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class XMLParser {

    private XMLParser() {
    }

    public static boolean validateWithSingleSchema(File xml, File xsd) {
        boolean legal = false;

        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(xsd);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));

            legal = true;
        } catch (Exception e) {
            legal = false;
            e.printStackTrace();
        }

        return legal;
    }

    public static boolean validateWithMultiSchemas(InputStream xml, List<File> schemas) {
        boolean legal = false;

        try {
            Schema schema = createSchema(schemas);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));

            legal = true;
        } catch (Exception e) {
            legal = false;
            e.printStackTrace();
        }

        return legal;
    }

    private static Schema createSchema(List<File> schemas) throws ParserConfigurationException, SAXException,
            IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        SchemaResourceResolver resourceResolver = new SchemaResourceResolver();
        sf.setResourceResolver(resourceResolver);

        Source[] sources = new Source[schemas.size()];

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setValidating(false);
        docFactory.setNamespaceAware(true);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        for (int i = 0; i < schemas.size(); i++) {
            org.w3c.dom.Document doc = docBuilder.parse(schemas.get(i));
            DOMSource stream = new DOMSource(doc, schemas.get(i).getAbsolutePath());
            sources[i] = stream;
        }

        return sf.newSchema(sources);
    }

}
