package com.qianshanding.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作相关类
 * @author Fish
 *
 */
public class FileHelper {
	/**
	 * 文件夹的大小
	 * 
	 * @param directory
	 * @return 大小（单位:比特）
	 * @author Fish Exp 2012-12-5 下午2:33:32
	 */
	public static long sizeOfDirectory(File directory) {
		if (!directory.exists()) {
			String message = directory + " 不存在";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			String message = directory + " 不是目录";
			throw new IllegalArgumentException(message);
		}

		long size = 0;

		File[] files = directory.listFiles();
		if (files == null) { // null if security restricted
			return 0L;
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.isDirectory()) {
				size += sizeOfDirectory(file);
			} else {
				size += file.length();
			}
		}

		return size;
	}

	/**
	 * 强制删除指定目录下所有文件，但所有的目录都不删除
	 * 
	 * @param file
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午2:46:35
	 */
	public static void forceDeleteOnExit(File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectoryOnExit(file);
		} else {
			file.deleteOnExit();
		}
	}

	private static void deleteDirectoryOnExit(File directory) throws IOException {
		if (!directory.exists()) {
			return;
		}

		cleanDirectoryOnExit(directory);
		directory.deleteOnExit();
	}

	/**
	 * 清除目录下所有文件，但保存目录
	 * 
	 * @param directory
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午2:36:47
	 */
	private static void cleanDirectoryOnExit(File directory) throws IOException {
		if (!directory.exists()) {
			String message = directory + " 不存在";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			String message = directory + " 不是一个目录";
			throw new IllegalArgumentException(message);
		}

		File[] files = directory.listFiles();
		if (files == null) { // null if security restricted
			throw new IOException("內容列表为空：" + directory);
		}

		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				forceDeleteOnExit(file);
			} catch (IOException ioe) {
				exception = ioe;
			}
		}

		if (null != exception) {
			throw exception;
		}
	}

	/**
	 * 强制删除目录及目录下所有文件，或删除指定文件
	 * 
	 * @param file
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午2:53:48
	 */
	public static void forceDelete(File file) throws IOException {
		if (file.isDirectory()) {
			deleteDirectory(file);
		} else {
			if (!file.exists()) {
				throw new FileNotFoundException("文件不存在：" + file);
			}
			if (!file.delete()) {
				String message = "不能删除文件" + file;
				throw new IOException(message);
			}
		}
	}

	/**
	 * 删除目录及该目录下所有内容
	 * 
	 * @param directory
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午2:54:47
	 */
	public static void deleteDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			return;
		}

		cleanDirectory(directory);
		if (!directory.delete()) {
			String message = "不能删除文件夹为： " + directory;
			throw new IOException(message);
		}
	}

	/**
	 * 清除目录下所有文件及文件夹，该目录存在
	 * 
	 * @param directory
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:02:15
	 */
	public static void cleanDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			String message = directory + "不存在";
			throw new IllegalArgumentException(message);
		}

		if (!directory.isDirectory()) {
			String message = directory + "不是一个目录";
			throw new IllegalArgumentException(message);
		}

		File[] files = directory.listFiles();
		if (files == null) { // null if security restricted
			throw new IOException("该目录内容列表为：" + directory);
		}

		IOException exception = null;
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
				forceDelete(file);
			} catch (IOException ioe) {
				exception = ioe;
			}
		}

		if (null != exception) {
			throw exception;
		}
	}

	/**
	 * 拷贝文件到指定目录下
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目标目录
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:11:17
	 */
	public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
		copyFileToDirectory(srcFile, destDir, true);
	}

	/**
	 * 拷贝文件到指定目录下
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destDir
	 *            目标目录
	 * @param preserveFileDate
	 *            是否要修改文件的更新时间(true-修改；false-不修改)
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:17:14
	 */
	public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws IOException {
		if (destDir == null) {
			throw new NullPointerException("destDir 不能为空");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException("destDir： '" + destDir + "' 不是一个目录");
		}
		copyFile(srcFile, new File(destDir, srcFile.getName()), preserveFileDate);
	}

	/**
	 * 拷贝文件到指定文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:17:24
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		copyFile(srcFile, destFile, true);
	}

	/**
	 * 文件拷贝
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param preserveFileDate
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:41:34
	 */
	public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
		if (srcFile == null) {
			throw new NullPointerException("源文件不能为空");
		}
		if (destFile == null) {
			throw new NullPointerException("目标目录不能为空");
		}
		if (srcFile.exists() == false) {
			throw new FileNotFoundException("源文件 '" + srcFile + "' 不存在");
		}
		if (srcFile.isDirectory()) {
			throw new IOException("源文件 '" + srcFile + "' 存在，但是一个目录");
		}
		if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
			throw new IOException("源文件 '" + srcFile + "' and 目標文件 '" + destFile + "' 是相同的");
		}
		if (destFile.getParentFile() != null && destFile.getParentFile().exists() == false) {
			if (destFile.getParentFile().mkdirs() == false) {
				throw new IOException("目标文件 '" + destFile + "' 目录不能创建");
			}
		}
		if (destFile.exists() && destFile.canWrite() == false) {
			throw new IOException("目标文件 '" + destFile + "' 存在，但是只读的");
		}
		doCopyFile(srcFile, destFile, preserveFileDate);
	}

	/**
	 * 文件拷貝
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param preserveFileDate
	 *            是否要修改文件的更新时间(true-修改；false-不修改)
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:14:34
	 */
	private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
		if (destFile.exists() && destFile.isDirectory()) {
			throw new IOException("目标 '" + destFile + "' 存在，但是个目录");
		}

		FileInputStream input = new FileInputStream(srcFile);
		try {
			FileOutputStream output = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[1024 * 4];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}
			} finally {
				try {
					if (output != null) {
						output.close();
					}
				} catch (IOException ioe) {
					// ignore
				}
			}
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ioe) {
				// ignore
			}
		}

		if (srcFile.length() != destFile.length()) {
			throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
		}
		if (preserveFileDate) {
			destFile.setLastModified(srcFile.lastModified());
		}
	}

	/**
	 * 拷贝目录到指定的目录下
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目标目录
	 * @throws IOException
	 *             是否要修改文件的更新时间(true-修改；false-不修改)
	 * @author Fish Exp 2012-12-5 下午3:26:54
	 */
	public static void copyDirectoryToDirectory(File srcDir, File destDir) throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("源目录不能为空");
		}
		if (srcDir.exists() && srcDir.isDirectory() == false) {
			throw new IllegalArgumentException(srcDir + "' 不是一个目录");
		}
		if (destDir == null) {
			throw new NullPointerException("目标目录不能为空");
		}
		if (destDir.exists() && destDir.isDirectory() == false) {
			throw new IllegalArgumentException(destDir + "' 不是一个目录");
		}
		copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
	}

	/**
	 * 拷贝目录下所有文件到指定的目录下
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目标目录
	 * @param preserveFileDate
	 *            是否要修改文件的更新时间(true-修改；false-不修改)
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:43:40
	 */
	public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
		if (srcDir == null) {
			throw new NullPointerException("源目录不能为空");
		}
		if (destDir == null) {
			throw new NullPointerException("目标目录不能为");
		}
		if (srcDir.exists() == false) {
			throw new FileNotFoundException(srcDir + "' 不存在");
		}
		if (srcDir.isDirectory() == false) {
			throw new IOException(srcDir + "' 存在，但 不是一个目录");
		}
		if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
			throw new IOException(srcDir + "和" + destDir + "是一样的");
		}
		doCopyDirectory(srcDir, destDir, preserveFileDate);
	}

	/**
	 * 
	 * 拷贝目录到指定的目录下
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目标目录
	 * @param preserveFileDate
	 *            是否要修改文件的更新时间(true-修改；false-不修改)
	 * @throws IOException
	 * @author Fish Exp 2012-12-5 下午3:31:10
	 */
	private static void doCopyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
		if (destDir.exists()) {
			if (destDir.isDirectory() == false) {
				throw new IOException(destDir + "' 存在，但是一个目录");
			}
		} else {
			if (destDir.mkdirs() == false) {
				throw new IOException(destDir + "' 目录不能被创建");
			}
			if (preserveFileDate) {
				destDir.setLastModified(srcDir.lastModified());
			}
		}
		if (destDir.canWrite() == false) {
			throw new IOException(destDir + "' 不能写入");
		}
		File[] files = srcDir.listFiles();
		if (files == null) {
			throw new IOException("文件列表为空：" + srcDir);
		}
		for (int i = 0; i < files.length; i++) {
			File copiedFile = new File(destDir, files[i].getName());
			if (files[i].isDirectory()) {
				doCopyDirectory(files[i], copiedFile, preserveFileDate);
			} else {
				doCopyFile(files[i], copiedFile, preserveFileDate);
			}
		}
	}
}
