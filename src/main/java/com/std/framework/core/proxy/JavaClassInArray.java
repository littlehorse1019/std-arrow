package com.std.framework.core.proxy;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javax.tools.SimpleJavaFileObject;

/**
 * @author Luox A file object representing a Java class file stored in a byte array.
 */
public class JavaClassInArray extends SimpleJavaFileObject {

    private String              name;
    private Map<String, byte[]> classes;

    /**
     * Constructs a JavaClassInArray object.
     * @param name binary name of the class to be stored in this file object
     */
    public JavaClassInArray (String name, Map<String, byte[]> classes) {
        super(uriFromString("mfm:///" + name.replace('.', '/') + Kind.CLASS.extension),
              Kind.CLASS);
        this.name = name;
        this.classes = classes;
    }

    private static URI uriFromString (String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public OutputStream openOutputStream () {
        return new FilterOutputStream(new ByteArrayOutputStream()) {
            public void close () throws IOException {
                out.close();
                ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
                classes.put(name, bos.toByteArray());
            }
        };
    }
}
