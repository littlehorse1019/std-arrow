package com.std.framework.core.proxy;

import java.net.URI;
import java.net.URISyntaxException;
import javax.tools.SimpleJavaFileObject;

/**
 * @author Luox A file object used to represent source coming from a string.
 */
public class JavaSourceFromString extends SimpleJavaFileObject {

    final String code;

    public JavaSourceFromString (String className, String classSrc) {
        super(uriFromString("mfm:///" + className.replace('.', '/')
                                + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = classSrc;
    }

    private static URI uriFromString (String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public CharSequence getCharContent (boolean ignoreEncodingErrors) {
        return code;
    }

}
