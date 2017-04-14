package com.std.server.routes;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * URIPattern
 * <p>
 * A lightweight utility class replicating {@code Pattern} static method functionality while escaping patterns to be
 * capable of matching url values.
 *
 * @author LUOXIAO
 * @since 1.0
 */
public class URLPattern {

    private Pattern pattern;

    public URLPattern (String patternStr) {
        this.pattern = URLPattern.compile(patternStr);
    }

    /**
     * Escapes the query string and compiles the given regular expression into a value.
     *
     * @param pattern The expression to be compiled
     * @return the given regular expression compiled into a value
     * @throws PatternSyntaxException indicating the expression's syntax is invalid
     */
    public static Pattern compile (String pattern) {
        return Pattern.compile(URLPattern.escape(pattern));
    }

    /**
     * Returns a {@code Pattern} string capable of using core Java regular expressions with a query string.
     * <p>
     * The question mark trailing the initial forward slash is escaped to treat it as a literal value.
     *
     * @param pattern the url value
     * @return an escaped url value
     */
    public static String escape (String pattern) {
        return pattern.replace("/?", "/\\?").replace('{', '(').replace('}', ')');
    }

    /**
     * url grammar as " <scheme>://<user>:<password>@<host>:<port>/<path>;<params>?<query>#<frag>
     * decode url <path> and test matches with pattern
     */
    public boolean matches (String url) {
        int pathEndIdx = url.indexOf(";") > 0 ? url.indexOf(";") : url.indexOf("?") > 0 ? url.indexOf("?")
                                                                                        : url.indexOf("#") > 0 ? url
                                                                                            .indexOf("#") : url
                                                                                              .length();
        String path = url.substring(0, pathEndIdx);
        return this.pattern.matcher(path).matches();
    }

}
