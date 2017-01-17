package com.wsh.base.util;


public final class Kit {
	
	public static String generateRandomCode() {
		String str = "";
		str += (int) (Math.random() * 9 + 1);
		for (int i = 0; i < 5; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;

	}
}
