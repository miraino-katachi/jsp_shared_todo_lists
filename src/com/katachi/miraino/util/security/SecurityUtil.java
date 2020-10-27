package com.katachi.miraino.util.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * セキュリティ対策クラス
 */
public class SecurityUtil {

	/** 生成するワンタイムトークンの長さ */
	private static final int TOKEN_LENGTH = 16;
	/** ワンタイムトークンの生成に利用される暗号化アルゴリズム */
	private static final String ENCRYPTION_ALGORITHM = "SHA1PRNG";

	/**
	 * ワンタイムトークン用の文字列を生成します。
	 * @return ワンタイムトークン用の文字列
	 */
	public static String generateToken() {
		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer buf = new StringBuffer();
		SecureRandom random = null;

		try {
			random = SecureRandom.getInstance(ENCRYPTION_ALGORITHM);
			random.nextBytes(token);

			for (int i = 0; i < token.length; i++) {
				// "%02x"は16進数に変換
				buf.append(String.format("%02x", token[i]));
			}

			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
