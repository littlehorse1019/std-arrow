package com.std.framework.core.filefilter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Jar�����ļ�������
 */
public class JarFileFilter implements FilenameFilter {

    private static List<String> includeJars = new ArrayList<String>();

    public JarFileFilter (List<String> includeJars) {
        JarFileFilter.includeJars = includeJars;
    }

    public boolean accept (File dir, String name) {
        //����List֮�е�Jar���Ž���ɨ��
        if (includeJars.contains(name)) {
            return true;
        } else {
            return false;
        }
    }
}
