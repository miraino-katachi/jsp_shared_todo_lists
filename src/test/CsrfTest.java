package test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CsrfTest {

	private static int TOKEN_LENGTH = 16;

	public static void main(String[] args) {
		byte token[] = new byte[TOKEN_LENGTH];
		StringBuffer buf = new StringBuffer();
		SecureRandom random = null;

		try {
			random = SecureRandom.getInstance("SHA1PRNG");
			random.nextBytes(token);

			for (int i = 0; i < token.length; i++) {
				buf.append(String.format("%02x", token[i]));
			}

//			System.out.println(buf.toString());
			System.out.println(buf);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}
