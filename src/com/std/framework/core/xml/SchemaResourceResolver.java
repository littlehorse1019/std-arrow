package com.std.framework.core.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class SchemaResourceResolver implements LSResourceResolver {

	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		System.out.println("/n>> Resolving " + "/n" + "TYPE: " + type + "/n" + "NAMESPACE_URI: " + namespaceURI + "/n"
				+ "PUBLIC_ID: " + publicId + "/n" + "SYSTEM_ID: " + systemId + "/n" + "BASE_URI: " + baseURI + "/n");

		String schemaLocation = baseURI.substring(0, baseURI.lastIndexOf("/") + 1);

		if (systemId.indexOf("http://") < 0) {
			systemId = schemaLocation + systemId;
		}

		LSInput lsInput = new LSInputImpl();

		URI uri = null;
		try {
			uri = new URI(systemId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File file = new File(uri);
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		lsInput.setSystemId(systemId);
		lsInput.setByteStream(is);

		return lsInput;
	}

	class LSInputImpl implements LSInput {

		private String publicId;
		private String systemId;
		private String baseURI;
		private InputStream byteStream;
		private Reader charStream;
		private String stringData;
		private String encoding;
		private boolean certifiedText;

		public LSInputImpl() {
		}

		public LSInputImpl(String publicId, String systemId, InputStream byteStream) {
			this.publicId = publicId;
			this.systemId = systemId;
			this.byteStream = byteStream;
		}

		public String getBaseURI() {
			return baseURI;
		}

		public InputStream getByteStream() {
			return byteStream;
		}

		public boolean getCertifiedText() {
			return certifiedText;
		}

		public Reader getCharacterStream() {
			return charStream;
		}

		public String getEncoding() {
			return encoding;
		}

		public String getPublicId() {
			return publicId;
		}

		public String getStringData() {
			return stringData;
		}

		public String getSystemId() {
			return systemId;
		}

		public void setBaseURI(String baseURI) {
			this.baseURI = baseURI;
		}

		public void setByteStream(InputStream byteStream) {
			this.byteStream = byteStream;
		}

		public void setCertifiedText(boolean certifiedText) {
			this.certifiedText = certifiedText;
		}

		public void setCharacterStream(Reader characterStream) {
			this.charStream = characterStream;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}

		public void setPublicId(String publicId) {
			this.publicId = publicId;
		}

		public void setStringData(String stringData) {
			this.stringData = stringData;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

	}

}
