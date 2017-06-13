package com.ai.core.util.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: ClassUtil
 * @Description: java的class帮助工具类
 * @author linyl linyuliang.85@gmail.com
 */
public abstract class ClassUtil {
    /**
     * @Fields LOGGER : 日志操作类
     */
    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * @Title: getPackageAllClassName
     * @Description: 取得某一类所在包的所有类名 不含迭代
     * @param classLocation 类装载的路径
     * @param className 类的全名，含包路径
     * @return 类所在包下的所有类名，不包含子包
     */
    public static String[] getPackageAllClassName(String classLocation, String className) {
        // 将packageName分解
        String[] packagePathSplit = className.split("[.]");
        StringBuilder realClassLocation = new StringBuilder();
        realClassLocation.append(classLocation);
        for (int i = 0, j = packagePathSplit.length - 1; i < j; i++) {
            realClassLocation.append(File.separator).append(packagePathSplit[i]);
        }
        File packeageDir = new File(realClassLocation.toString());
        if (packeageDir.isDirectory()) {
            String[] allClassName = packeageDir.list();
            return allClassName;
        }
        return null;
    }

    /**
     * 从包package中获取所有的Class
     * @param packageName 包名全路径
     * @return 包下的所有类
     */
    public static List<Class<?>> getClasses(String packageName) {
        // 第一个class类的集合
        List<Class<?>> classes = new Vector<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        findAndAddClassesInJarFile(jar, packageName, recursive, classes);
                    } catch (IOException e) {
                        log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * @Title: findAndAddClassesInJarFile
     * @Description: 从jar包中获取所有class类
     * @param jar jar包
     * @param packageName 包名全路径
     * @param recursive 是否循环子目录
     * @param classes 添加查询到的class到该集合类
     */
    private static void findAndAddClassesInJarFile(JarFile jar, String packageName, final boolean recursive, List<Class<?>> classes) {
        String packageDirName = packageName.replace('.', '/');
        // 从此jar包 得到一个枚举类
        Enumeration<JarEntry> entries = jar.entries();
        // 同样的进行循环迭代
        while (entries.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }
            // 如果前半部分和定义的包名相同
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                // 如果以"/"结尾 是一个包
                if (idx != -1) {
                    // 获取包名 把"/"替换成"."
                    packageName = name.substring(0, idx).replace('/', '.');
                }
                // 如果可以迭代下去 并且是一个包
                if ((idx != -1) || recursive) {
                    // 如果是一个.class文件 而且不是目录
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        // 去掉后面的".class" 获取真正的类名
                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                        try {
                            // 添加到classes
                            classes.add(Class.forName(packageName + '.' + className));
                        } catch (ClassNotFoundException e) {
                            log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * @Title: findAndAddClassesInPackageByFile
     * @Description: 以文件的形式来获取包下的所有Class
     * @param packageName 包名
     * @param packagePath 包路径
     * @param recursive 是否遍历子目录
     * @param classes 获取到的所有类集合
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Title: isInterface
     * @Description: 判断类是否实现了某个接口
     * @param c 需要判断的类
     * @param szInterface 是否实现的接口
     * @return 是否实现了接口
     */
    public static boolean isInterface(Class<?> c, Class<?> szInterface) {
        Class<?>[] face = c.getInterfaces();
        for (int i = 0, j = face.length; i < j; i++) {
            if (face[i].equals(szInterface)) {
                return true;
            } else {
                Class<?>[] face1 = face[i].getInterfaces();
                for (int x = 0; x < face1.length; x++) {
                    if (face1[x].equals(szInterface) || isInterface(face1[x], szInterface)) {
                        return true;
                    }
                }
            }
        }
        boolean isImpl = false;
        if (null != c.getSuperclass()) {
            isImpl = isInterface(c.getSuperclass(), szInterface);
        }
        return isImpl;

    }
}
