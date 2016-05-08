package org.saul.gradle.util;

import com.google.common.base.CaseFormat;

/**
 *
 */
public class SaulStringHelper {

	private SaulStringHelper() {
	}

	/**
	 *
	 */
	public static String toUpperCamel(String inValue) {
		if (null == inValue) {
			return null;
		}
		if (0 == inValue.length()) {
			return inValue;
		}

		String returnString = "";
		if (-1 < inValue.indexOf("_")) {
			String lower = inValue.toLowerCase();
			returnString = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, lower);
		} else {
			if (inValue.equals(inValue.toUpperCase()) || inValue.equals(inValue.toLowerCase())) {
				returnString = inValue.substring(0, 1) + inValue.substring(1)
						.toLowerCase();
			} else {
				returnString = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, inValue);
			}
		}
		return returnString;
	}

	/**
	 *
	 */
	public static String toLowerCamel(String inValue) {
		if (null == inValue) {
			return null;
		}
		if (0 == inValue.length()) {
			return inValue;
		}

		String returnString = "";
		if (-1 < inValue.indexOf("_")) {
			String lower = inValue.toLowerCase();
			returnString = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, lower);
		} else {
			if (inValue.equals(inValue.toUpperCase()) || inValue.equals(inValue.toLowerCase())) {
				returnString = inValue.toLowerCase();
			} else {
				returnString = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, inValue.toUpperCase());
			}
		}
		return returnString;
	}
}
