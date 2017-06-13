package com.ai.core.util.utils;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nutz.lang.Files;
import org.nutz.lang.Lang;

/**
 * 文件工具类
 * @author peiyu
 *
 */
public abstract class FileUtils {
	/**
	 * 获取目录下所有子文件
	 * @param dirPath 指定目录路径
	 * @return 所有子文件列表
	 */
	public static List<File> getAllFilesFromDir(String dirPath) {
		requireNonNull(dirPath, "dirPath is null");
		List<File> result = new ArrayList<File>();
		File[] dirs = Files.scanDirs(new File(dirPath));
		if(!Lang.isEmpty(dirs)) {
			for(File dir : dirs) {
				File[] files = Files.files(dir, null);
				Collections.addAll(result, files);
			}
		}
		return result;
	}
	
	/**
	 * 读取文件全部内容
	 * @param file 需要读取的文件对象
	 * @return 文件内容
	 */
	public static String readFile(File file) {
		requireNonNull(file, "file is null");
		return Files.read(file);
	}
	
	/**
	 * 读取文件全部内容
	 * @param filePath 需要读取的文件路径
	 * @return 文件内容
	 */
	public static String readFile(String filePath) {
		requireNonNull(filePath, "filePath is null");
		return readFile(new File(filePath));
	}
	
	/**
	 * 将内容写进文件内
	 * @param file 需要写的文件对象
	 * @param text 内容
	 */
	public static void writeFile(File file, String text) {
		requireNonNull(file, "file is null");
		Files.write(file, text);
	}
	
	/**
	 * 将内容写进文件内
	 * @param filePath 需要写的文件路径
	 * @param text 内容
	 */
	public static void writeFile(String filePath, String text) {
		requireNonNull(filePath, "filePath is null");
		writeFile(new File(filePath), text);
	}
	
	/**
	 * 将内容写进文件内
	 * @param file 需要写的文件对象
	 * @param input 输入流内容
	 */
	public static void writeFile(File file, InputStream input) {
		requireNonNull(file, "file is null");
		requireNonNull(input, "input is null");
		Files.write(file, input);
	}
	
	/**
	 * 将内容写进文件内
	 * @param filePath 需要写的文件路径
	 * @param input 输入流内容
	 */
	public static void writeFile(String filePath, InputStream input) {
		requireNonNull(filePath, "filePath is null");
		requireNonNull(input, "input is null");
		writeFile(new File(filePath), input);
	}
	
	/**
     * 传入一个目录对象，如果目录不存在，则创建目录
     * 
     * @param dir 文件目录对象
     * @return 文件目录对象，以便调用者省略一行代码
     */
	public static File createDirIfNoExists(File dir) {
		requireNonNull(dir, "dir is null");
		return Files.createDirIfNoExists(dir);
	}
	
	/**
     * 传入一个目录对象，如果目录不存在，则创建目录
     * 
     * @param dirPath 文件目录路径
     * @return 文件目录对象，以便调用者省略一行代码
     */
	public static File createDirIfNoExists(String dirPath) {
		requireNonNull(dirPath, "dirPath is null");
		return createDirIfNoExists(new File(dirPath));
	}
}
