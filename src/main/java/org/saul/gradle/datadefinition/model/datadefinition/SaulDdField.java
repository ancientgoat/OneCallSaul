package org.saul.gradle.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.saul.gradle.datadefinition.model.conversion.JavaTypePacket;
import org.saul.gradle.util.SaulStringHelper;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaulDdField {

	private String columnName;
	private String columnClassName;
	private Integer columnType;
	private String columnLabel;
	private String columnTypeName;
	private String catalogName;
	private String schemaName;
	private String tableName;
	private JavaTypePacket javaTypePacket;
	private Integer precision;
	private Integer scale;
	private Integer columnDisplaySize;

	private Boolean isAutoIncrement;
	private Boolean isCaseSensitive;
	private Boolean isCurrency;
	private Boolean isDefinitelyWritable;
	private Integer isNullable;
	private Boolean isReadOnly;
	private Boolean isSearchable;
	private Boolean isSigned;
	private Boolean isWritable;

	public Boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public Boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public Boolean isCurrency() {
		return isCurrency;
	}

	public Boolean isDefinitelyWritable() {
		return isDefinitelyWritable;
	}

	public int getIsNullable() {
		return isNullable;
	}

	public Boolean isReadOnly() {
		return isReadOnly;
	}

	public Boolean isSearchable() {
		return isSearchable;
	}

	public Boolean isSigned() {
		return isSigned;
	}

	public Boolean isWritable() {
		return isWritable;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public Integer getColumnType() {
		return columnType;
	}

	public String getColumnTypeName() {
		return columnTypeName;
	}

	@JsonIgnore
	public String getPropertyName() {
		return SaulStringHelper.toLowerCamel(columnName);
	}

	@JsonIgnore
	public String getPropertyUpper() {
		return SaulStringHelper.toUpperCamel(columnName);
	}

	@JsonIgnore
	public String getInName() {
		return String.format("in%s", getPropertyUpper());
	}

	@JsonIgnore
	public String getGetterName() {
		return String.format("get%s", getPropertyUpper());
	}

	@JsonIgnore
	public String getSetterName() {
		System.out.println(String.format("Setter : %s : %s : %s", columnName, getPropertyUpper(), getPropertyName()));
		return String.format("set%s", getPropertyUpper());
	}

	public JavaTypePacket getJavaTypePacket() {
		return javaTypePacket;
	}

	@JsonIgnore
	public String getJavaTypeName() {
		validateJavaTypePacket();
		return javaTypePacket.getJavaTypeName();
	}

	@JsonIgnore
	public Class getJavaTypeClass() {
		validateJavaTypePacket();
		return javaTypePacket.getJavaClass();
	}

	private void validateJavaTypePacket() {
		if (null == javaTypePacket) {
			throw new IllegalArgumentException(String.format("JavaTypePacket is null!?\n%s", dumpToString()));
		}
	}

	public Integer getPrecision() {
		return precision;
	}

	public Integer getScale() {
		return scale;
	}

	public Integer getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public String getColumnClassName() {
		return columnClassName;
	}

	public String dumpToString() {
		return new StringBuilder()                                                          //
				.append(String.format("--- Column Start ---\n", ""))                        //
				.append(String.format("columnName           : %s\n", columnName))           //
				.append(String.format("columnClassName      : %s\n", columnClassName))      //
				.append(String.format("columnTypeName       : %s\n", columnTypeName))       //
				.append(String.format("columnType           : %s\n", columnType))           //
				.append(String.format("javaTypePacket       : %s\n", javaTypePacket))       //
				.append(String.format("precision            : %s\n", precision))            //
				.append(String.format("scale                : %s\n", scale))                //
				.append(String.format("columnDisplaySize    : %s\n", columnDisplaySize))    //
				.append(String.format("catalog              : %s\n", this.catalogName))     //
				.append(String.format("schemaName           : %s\n", this.schemaName))      //
				.append(String.format("tableName            : %s\n", this.tableName))       //
				//
				.append(String.format("isAutoIncrement      : %s\n", isAutoIncrement))      //
				.append(String.format("isCaseSensitive      : %s\n", isCaseSensitive))      //
				.append(String.format("isCurrency           : %s\n", isCurrency))           //
				.append(String.format("isDefinitelyWritable : %s\n", isDefinitelyWritable)) //
				.append(String.format("isNullable           : %s\n", isNullable))           //
				.append(String.format("isReadOnly           : %s\n", isReadOnly))           //
				.append(String.format("isSearchable         : %s\n", isSearchable))         //
				.append(String.format("isSigned             : %s\n", isSigned))             //
				.append(String.format("isWritable           : %s\n", isWritable))           //
				.append(String.format("--- Column End ---\n", ""))                          //
				.toString();
	}

	public void setColumnName(String columnName) {
		if (null == this.columnName)
			this.columnName = columnName;
	}

	public void setColumnClassName(String columnClassName) {
		if (null == this.columnClassName)
			this.columnClassName = columnClassName;
	}

	public void setColumnType(Integer columnType) {
		if (null == this.columnType)
			this.columnType = columnType;
	}

	public void setColumnLabel(String columnLabel) {
		if (null == this.columnLabel)
			this.columnLabel = columnLabel;
	}

	public void setColumnTypeName(String columnTypeName) {
		if (null == this.columnTypeName)
			this.columnTypeName = columnTypeName;
	}

	public void setCatalogName(String catalogName) {
		if (null == this.catalogName)
			this.catalogName = catalogName;
	}

	public void setSchemaName(String schemaName) {
		if (null == this.schemaName)
			this.schemaName = schemaName;
	}

	public void setTableName(String tableName) {
		if (null == this.tableName)
			this.tableName = tableName;
	}

	public void setJavaTypePacket(JavaTypePacket javaTypePacket) {
		if (null == this.javaTypePacket)
			this.javaTypePacket = javaTypePacket;
	}

	public void setPrecision(Integer precision) {
		if (null == this.precision)
			this.precision = precision;
	}

	public void setScale(Integer scale) {
		if (null == this.scale)
			this.scale = scale;
	}

	public void setColumnDisplaySize(Integer columnDisplaySize) {
		if (null == this.columnDisplaySize)
			this.columnDisplaySize = columnDisplaySize;
	}

	public void setAutoIncrement(Boolean autoIncrement) {
		if (null == isAutoIncrement)
			isAutoIncrement = autoIncrement;
	}

	public void setCaseSensitive(Boolean caseSensitive) {
		if (null == isCaseSensitive)
			isCaseSensitive = caseSensitive;
	}

	public void setCurrency(Boolean currency) {
		if (null == isCurrency)
			isCurrency = currency;
	}

	public void setDefinitelyWritable(Boolean definitelyWritable) {
		if (null == isDefinitelyWritable)
			isDefinitelyWritable = definitelyWritable;
	}

	public void setIsNullable(Integer isNullable) {
		if (null == this.isNullable)
			this.isNullable = isNullable;
	}

	public void setReadOnly(Boolean readOnly) {
		if (null == isReadOnly)
			isReadOnly = readOnly;
	}

	public void setSearchable(Boolean searchable) {
		if (null == isSearchable)
			isSearchable = searchable;
	}

	public void setSigned(Boolean signed) {
		if (null == isSigned)
			isSigned = signed;
	}

	public void setWritable(Boolean writable) {
		if (null == isWritable)
			isWritable = writable;
	}

	/**
	 *
	 */
	public static final class Builder {

		private SaulDdField field = new SaulDdField();

		public Builder setColumnLabel(String columnLabel) {
			this.field.columnLabel = columnLabel;
			return this;
		}

		public Builder setCatalogName(String catalogName) {
			this.field.catalogName = catalogName;
			return this;
		}

		public Builder setSchemaName(String schemaName) {
			this.field.schemaName = schemaName;
			return this;
		}

		public Builder setTableName(String tableName) {
			this.field.tableName = tableName;
			return this;
		}

		public Builder setColumnTypeName(String columnTypeName) {
			this.field.columnTypeName = columnTypeName;
			return this;
		}

		public Builder setColumnType(Integer columnType) {
			this.field.columnType = columnType;
			return this;
		}

		public Builder setPrecision(final Integer inPrecision) {
			this.field.precision = inPrecision;
			return this;
		}

		public Builder setColumnClassName(String columnClassName) {
			this.field.columnClassName = columnClassName;
			return this;
		}

		public Builder setScale(final Integer inScale) {
			this.field.scale = inScale;
			return this;
		}

		public Builder setColumnDisplaySize(final Integer inColumnDisplaySize) {
			this.field.columnDisplaySize = inColumnDisplaySize;
			return this;
		}

		public Builder setColumnName(final String inColumn) {
			this.field.columnName = inColumn;
			return this;
		}

		public Builder setDataType(final String inDataType) {
			this.field.columnTypeName = inDataType;
			return this;
		}

		public Builder setJavaTypePacket(JavaTypePacket javaTypePacket) {
			this.field.javaTypePacket = javaTypePacket;
			return this;
		}

		public Builder setIsAutoIncrement(Boolean autoIncrement) {
			this.field.isAutoIncrement = autoIncrement;
			return this;
		}

		public Builder setIsCaseSensitive(Boolean caseSensitive) {
			this.field.isCaseSensitive = caseSensitive;
			return this;
		}

		public Builder setIsCurrency(Boolean currency) {
			this.field.isCurrency = currency;
			return this;
		}

		public Builder setIsDefinitelyWritable(Boolean definitelyWritable) {
			this.field.isDefinitelyWritable = definitelyWritable;
			return this;
		}

		public Builder setIsNullable(int isNullable) {
			this.field.isNullable = isNullable;
			return this;
		}

		public Builder setIsReadOnly(Boolean readOnly) {
			this.field.isReadOnly = readOnly;
			return this;
		}

		public Builder setIsSearchable(Boolean searchable) {
			this.field.isSearchable = searchable;
			return this;
		}

		public Builder setIsSigned(Boolean signed) {
			this.field.isSigned = signed;
			return this;
		}

		public Builder setIsWritable(Boolean writable) {
			this.field.isWritable = writable;
			return this;
		}

		public final SaulDdField build() {
			return this.field;
		}
	}
}
