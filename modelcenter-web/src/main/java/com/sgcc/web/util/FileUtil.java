package com.sgcc.web.util;

import java.io.File;

public class FileUtil {

	/**
	 * 获取文件后缀
	 * @param file
	 * @return
	 */
	public static String getFileExtName(File file) {
		String fileName=file.getName();
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		return suffix;
	}
}
