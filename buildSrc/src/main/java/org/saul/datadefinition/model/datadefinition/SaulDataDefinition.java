package org.saul.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Sets;
import java.io.File;
import java.util.Collection;
import java.util.Set;
import org.saul.datadefinition.helper.SaulDdHelper;
import org.saul.datadefinition.inf.SaulHasName;
import org.saul.property.SaulDataSource;

import static aQute.bnd.osgi.Processor.append;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaulDataDefinition implements SaulHasName {

	public static final String DATA_DEF_PREFIX = "DD";
	public static final String DATA_DEF_FILE_EXTENSION = ".yml";
	public static final String DATA_DEF_FILE_PATH = "generated-sources/src/main/resources";
	public static final String DATA_DEF_FILE_PATH2 = "generated-sources/src/main/sorg/saul/datadef/dto";

	private SaulIdentity identity;
	private String source;
	private String name;
	private String shortName;
	private String dataSourceName;
	private String sql;
	private Set<SaulDdField> fields = Sets.newHashSet();

	@JsonIgnore
	private SaulFieldMaster fieldMaster;

	@JsonIgnore
	private SaulDataSource dataSource;

	@JsonIgnore
	private SaulSqlPacket sqlPacket;

	@JsonIgnore
	private final SaulDdHelper ddHelper;

	public SaulDataDefinition() {
		this.ddHelper = new SaulDdHelper(this);
	}

	public void setSql(final String inSql) {
		if (null == inSql) {
			throw new IllegalArgumentException("Can not enter SQL that is NULL.");
		}
		this.sql = inSql;
		sqlPacket = new SaulSqlPacket(this.getSql());
	}

	@JsonIgnore
	public void setDataSource(final SaulDataSource inDataSource) {
		if (null == this.dataSource) {
			dataSource = inDataSource;
		}
	}

	@JsonIgnore
	public SaulDataSource getDataSource() {
		return dataSource;
	}

	public SaulDdHelper getDdHelper() {
		return ddHelper;
	}

	public SaulSqlPacket getSqlPacket() {
		return sqlPacket;
	}

	public SaulIdentity getIdentity() {
		return identity;
	}

	public String getSource() {
		return source;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public String getSql() {
		return sql;
	}

	public Set<SaulDdField> getFields() {
		return fields;
	}

	public void setDataSourceName(String dataSourceName) {
		if (null == this.dataSourceName) {
			this.dataSourceName = dataSourceName;
		}
	}

	public void setIdentity(SaulIdentity identity) {
		if (null == this.identity) {
			this.identity = identity;
		}
	}

	public void setSource(String source) {
		if (null == this.source) {
			this.source = source;
		}
	}

	public void setName(String name) {
		if (null == this.name) {
			this.name = name;
		}
	}

	public void setShortName(String shortName) {
		if (null == this.shortName) {
			this.shortName = shortName;
		}
	}

	public void setFields(Set<SaulDdField> fields) {
		if (null == this.fields) {
			this.fields = fields;
		}
	}

	@JsonIgnore
	public String getFileName() {
		final String fullFileName =
				String.format("%s_%s%s", DATA_DEF_PREFIX, getIdentity().getName(), DATA_DEF_FILE_EXTENSION);
		return fullFileName;
	}

	@JsonIgnore
	public String getOutputDirectory() {
		return DATA_DEF_FILE_PATH.replace("/", File.separator);
	}

	public String dumpToString() {
		final StringBuilder sb = new StringBuilder()//
				.append(String.format("identity       : \n%s\n", identity.dumpToString()))
				.append(String.format("source         : %s\n", source))
				.append(String.format("name           : %s\n", name))
				.append(String.format("shortName      : %s\n", shortName))
				.append(String.format("dataSourceName : %s\n", dataSourceName))
				.append(String.format("sql            : %s\n", sql));

		for (final SaulDdField field : this.fields) {
			sb.append(field.dumpToString());
		}
		return sb.toString();
	}

	/**
	 *
	 */
	public static final class Builder {

		final SaulDataDefinition dataDefinition = new SaulDataDefinition();

		public Builder() {
		}

		public Builder(final SaulDataDefinition inDataDef) {
			this.dataDefinition.identity = inDataDef.identity;
			this.dataDefinition.source = inDataDef.source;
			this.dataDefinition.name = inDataDef.name;
			this.dataDefinition.shortName = inDataDef.shortName;
			this.dataDefinition.dataSourceName = inDataDef.dataSourceName;
			this.dataDefinition.sql = inDataDef.sql;
			this.dataDefinition.fields = Sets.newHashSet(inDataDef.fields);
		}

		public Builder setSaulDataSource(final SaulDataSource inDataSource) {
			this.dataDefinition.dataSource = inDataSource;
			return this;
		}

		public Builder setIdentity(final SaulIdentity inIdentity) {
			this.dataDefinition.identity = inIdentity;
			return this;
		}

		public Builder setSource(final String inSource) {
			this.dataDefinition.source = inSource;
			return this;
		}

		public Builder setName(final String inName) {
			this.dataDefinition.name = inName;
			return this;
		}

		public Builder setShortName(final String inShortName) {
			this.dataDefinition.shortName = inShortName;
			return this;
		}

		public Builder setDataSource(final String inDataSource) {
			this.dataDefinition.dataSourceName = inDataSource;
			return this;
		}

		public Builder setSqlPacket(final SaulSqlPacket inSqlPacket) {
			this.dataDefinition.sqlPacket = inSqlPacket;
			return this;
		}

		public Builder setFields(final Collection<SaulDdField> inFields) {
			this.dataDefinition.fields = Sets.newHashSet(inFields);
			return this;
		}

		public SaulDataDefinition build() {
			return this.dataDefinition;
		}
	}
}
