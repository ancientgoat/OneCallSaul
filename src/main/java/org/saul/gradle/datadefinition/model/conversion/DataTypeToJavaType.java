package org.saul.gradle.datadefinition.model.conversion;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

/**
 *
 */
public class DataTypeToJavaType {

	private DataTypeToJavaType() {
	}

	/**
	 *
	 */
	public static final JavaTypePacket toJavaTypePacket(final Integer inDatabaseType, final String inDatabaseTypeName) {

		JavaTypePacket javaTypePacket = null;

		switch (inDatabaseType) {

		case Types.VARCHAR:
			javaTypePacket = new JavaTypePacket(String.class);
			break;

		case Types.SMALLINT:
		case Types.INTEGER:
			javaTypePacket = new JavaTypePacket(Integer.class);
			break;

		case Types.BIGINT:
			javaTypePacket = new JavaTypePacket(Long.class);
			break;

		case Types.NUMERIC:
			javaTypePacket = new JavaTypePacket(BigDecimal.class);
			break;

		case Types.DATE:
			javaTypePacket = new JavaTypePacket(Date.class);
			break;

		case Types.TIME:
		case Types.TIME_WITH_TIMEZONE:
		case Types.TIMESTAMP:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			javaTypePacket = new JavaTypePacket(Timestamp.class);
			break;

		case Types.BLOB:
		case Types.CLOB:
		case Types.ARRAY:
		case Types.BINARY:
		case Types.BIT:
		case Types.BOOLEAN:
		case Types.CHAR:
		case Types.DATALINK:
		case Types.DECIMAL:
		case Types.DISTINCT:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.JAVA_OBJECT:
		case Types.LONGNVARCHAR:
		case Types.LONGVARBINARY:
		case Types.LONGVARCHAR:
		case Types.NCHAR:
		case Types.NCLOB:
		case Types.NULL:
		case Types.NVARCHAR:
		case Types.OTHER:
		case Types.REAL:
		case Types.REF:
		case Types.REF_CURSOR:
		case Types.ROWID:
		case Types.SQLXML:
		case Types.STRUCT:
		case Types.TINYINT:
		case Types.VARBINARY:
		default:
			throw new IllegalArgumentException(
					String.format("(1)Unknown, not implemented java type '%d'/'%s'.", inDatabaseType,
							inDatabaseTypeName));
		}

		return javaTypePacket;
	}
}
