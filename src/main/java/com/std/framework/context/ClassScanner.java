package com.std.framework.context;


import com.std.framework.core.extraction.Extraction;
import com.std.framework.core.filefilter.ClassFileFilter;
import com.std.framework.core.filefilter.JarFileFilter;
import com.std.framework.core.util.PathUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Luox 工程类扫描器 扫描工程下所有（包括指定jar包）中的类文件，按照Extraction接口实现规则抽取并实例化返回类集合。
 */
public class ClassScanner {

    public static final  String       SCAN_FILE_NAME  = "scanclass.properties";
    public static final  String       MODEL_SCAN      = "model";
    public static final  String       VIEW_SCAN       = "view";
    public static final  String       CONTROLLER_SCAN = "controller";
    private final static Object       syncLock        = new Object();
    private static       ClassScanner scanner         = null;
    private static Properties scannerProp;
    private static List<String> includeJars  = new ArrayList<String>();
    private        URL          classPathUrl = ClassScanner.class.getResource("/");
    private        String       jarPathUrl   = new File(classPathUrl.getFile()).getParent() + "/lib/";

    private ClassScanner () {
    }

    public static ClassScanner instance () {
        if (scanner == null) {
            synchronized (syncLock) {
                scanner = new ClassScanner();
            }
        }
        return scanner;
    }

    public static void loadProperties () {
        try {
            File file = new File(PathUtil.getRootClassPath() + SCAN_FILE_NAME);
            if (file.exists()) {
                scannerProp = new Properties();
                FileInputStream   fileInputStream = new FileInputStream(file);
                InputStreamReader in              = new InputStreamReader(fileInputStream);
                scannerProp.load(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shiftModelJars () {
        shiftJars(MODEL_SCAN);
    }

    public void shiftViewJars () {
        shiftJars(VIEW_SCAN);
    }

    public void shiftControllerJars () {
        shiftJars(CONTROLLER_SCAN);
    }

    private void shiftJars (String jarType) {
        includeJars.clear();
        if (scannerProp != null) {
            String   toScanJars = scannerProp.getProperty(jarType);
            String[] jarsArray  = toScanJars.split(",");
            if (jarsArray != null && jarsArray.length > 0) {
                includeJars.addAll(Arrays.asList(jarsArray));
            }
        }
    }

    public List<Class<?>> findMacthedClass (Extraction extraction) throws Exception {
        List<String> classFileList = new ArrayList<String>();
        classFileList.addAll(findFiles(classPathUrl.getFile()));
        classFileList.addAll(findjarFiles(jarPathUrl, includeJars));
        List<Class<?>> clazzList = extraction.extract(classFileList);
        return clazzList;
    }

    /**
     * 递归查找class文件
     */
    private List<String> findFiles (String baseDirName) throws Exception {
        List<String> classFiles = new ArrayList<String>();
        File[]       filelist   = new File(baseDirName).listFiles(new ClassFileFilter());
        for (File file : filelist) {
            if (file.isDirectory()) {
                classFiles.addAll(findFiles(file.getAbsoluteFile().toString()));
            } else {
                classFiles.add(getClassNameByFile(file));
            }
        }
        return classFiles;
    }

    private String getClassNameByFile (File readfile) {
        String classname = "";
        String temppath  = readfile.getAbsoluteFile().toString().replaceAll("\\\\", "/");
        classname = temppath.substring(temppath.indexOf("/classes") + "/classes".length(), temppath.indexOf(".class"));
        if (classname.startsWith("/")) {
            classname = classname.substring(classname.indexOf("/") + 1);
        }
        classname = classname.replaceAll("\\\\", "/").replaceAll("/", ".");
        return classname;
    }

    /**
     * 查找jar包中的class
     */
    private List<String> findjarFiles (String baseDirName, final List<String> includeJars) throws Exception {
        List<String> classFiles = new ArrayList<String>();
        File         baseDir    = new File(baseDirName);
        File[]       filelist   = baseDir.listFiles(new JarFileFilter(includeJars));
        for (int i = 0; i < filelist.length; i++) {
            classFiles.addAll(getClassListByJar(filelist[i]));
        }
        return classFiles;
    }

    private List<String> getClassListByJar (File file) throws Exception {
        List<String> classFiles   = new ArrayList<String>();
        JarFile      localJarFile = new JarFile(file);
        try {
            Enumeration<JarEntry> entries = localJarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry  = entries.nextElement();
                String   entryName = jarEntry.getName();
                if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                    String className = entryName.replaceAll("/", ".").substring(0, entryName.length() - 6);
                    classFiles.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            localJarFile.close();
        }
        return classFiles;
    }

}
