package org.saul.gradle.datadefinition.helper;

import java.util.List;

/**
 *
 */
public class SkWherePacketHelper {

	private SkWherePacketHelper() {
	}

	/**
	 *
	 */
	public static String buildWhereClause(List<SkWherePacket> inWhere) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (SkWherePacket packet : inWhere) {

			if (0 == i) {
				i++;
				sb.append(String.format("?%s", packet.getRestParameters()));
			} else {
				sb.append(String.format("&%s", packet.getRestParameters()));
			}
		}
		return sb.toString();
	}
}
