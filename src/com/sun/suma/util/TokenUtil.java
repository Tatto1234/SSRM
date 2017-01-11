package com.sun.suma.util;

import java.io.IOException;

/**
 * 生成文件token，并且为唯一值
 * @author Administrator
 *
 */
public class TokenUtil {

	/**
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String name, String size)
			throws IOException {
		if (name == null || size == null)
			return "";
		int code = name.hashCode();
		try {
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
			IoUtil.storeToken(token);
			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}
