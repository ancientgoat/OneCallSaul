package org.saul.gradle.datadefinition.helper;

import com.google.common.collect.Lists;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.util.List;
import javax.sql.DataSource;
import org.saul.gradle.datadefinition.model.conversion.DataTypeToJavaType;
import org.saul.gradle.datadefinition.model.conversion.JavaTypePacket;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDdField;
import org.saul.gradle.property.SaulDataSource;

/**
 *
 */
public class SaulDdHelper {

	//
	private final SaulDataDefinition definitionDto;

	/**
	 *
	 */
	public SaulDdHelper(final SaulDataDefinition inSaulDataDefinition) {
		this.definitionDto = inSaulDataDefinition;
	}

	/**
	 *
	 */
	public boolean needsFilling() {
		return null == definitionDto.getIdentity() //
				|| null == definitionDto.getSource()//
				|| null == definitionDto.getName()//
				|| null == definitionDto.getShortName()//
				|| null == definitionDto.getDataSourceName()//
				;
	}

	/**
	 *
	 */
	public boolean canFillFromSql() {
		return null != definitionDto.getDataSourceName() && null != definitionDto.getSql();
	}

	/**
	 *
	 */
	public SaulDataDefinition fillDataDefFromSql() {

		if (!canFillFromSql()) {
			System.out.println(
					//log.warn(
					String.format("Can not fill from SQL, for DataDefinition name '%s'", this.definitionDto.getName()));
			return null;
		}

		final SaulDataDefinition.Builder builder = new SaulDataDefinition.Builder(this.definitionDto);

		// private SaulIdentity identity;
		// private String source;
		// private String name;
		// private String shortName;

		final String sql = this.definitionDto.getSql();

		SaulDataSource saulDataSource = this.definitionDto.getDataSource();
		DataSource dataSource = saulDataSource.getDataSource();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (Exception e) {
			throw new IllegalArgumentException(saulDataSource.dumpToString(), e);
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSetMetaData metaData = preparedStatement.getMetaData();
			List<SaulDdField> newFieldList = Lists.newArrayList();

			for (int i = 1; i <= metaData.getColumnCount(); i++) {

				JavaTypePacket javaTypePacket =
						DataTypeToJavaType.toJavaTypePacket(metaData.getColumnType(i), metaData.getColumnTypeName(i));

				SaulDdField field = new SaulDdField.Builder()  //
						.setColumnName(metaData.getColumnName(i)
								.toUpperCase())
						.setColumnClassName(metaData.getColumnClassName(i))
						.setColumnType(metaData.getColumnType(i))
						.setColumnTypeName(metaData.getColumnTypeName(i))
						.setColumnLabel(metaData.getColumnLabel(i))
						.setJavaTypePacket(javaTypePacket)
						.setColumnDisplaySize(metaData.getColumnDisplaySize(i))
						.setPrecision(metaData.getPrecision(i))
						.setScale(metaData.getScale(i))
						//
						.setIsAutoIncrement(metaData.isAutoIncrement(i))
						.setIsCaseSensitive(metaData.isCaseSensitive(i))
						.setIsCurrency(metaData.isCurrency(i))
						.setIsDefinitelyWritable(metaData.isDefinitelyWritable(i))
						.setIsNullable(metaData.isNullable(i))
						.setIsReadOnly(metaData.isReadOnly(i))
						.setIsSearchable(metaData.isSearchable(i))
						.setIsSigned(metaData.isSigned(i))
						.setIsWritable(metaData.isWritable(i))
						.build();

				newFieldList.add(field);
			}

			builder.setFields(newFieldList);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return builder.build();
	}
}
