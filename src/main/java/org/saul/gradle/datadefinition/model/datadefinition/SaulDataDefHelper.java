package org.saul.gradle.datadefinition.model.datadefinition;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.saul.gradle.datadefinition.helper.SkWherePacket;

import static aQute.bnd.properties.LineType.entry;

/**
 *
 */
public class SaulDataDefHelper {

	private SaulDataDefinition dataDef;
	private Map<String, SaulDdField> fieldNameMap = Maps.newHashMap();

	/**
	 *
	 */
	public SaulDataDefHelper(SaulDataDefinition inDataDef) {
		this.dataDef = inDataDef;
		this.dataDef.getFields()
				.forEach(f -> {
					this.fieldNameMap.put(f.getColumnNameUpper(), f);
				});
	}

	/**
	 *
	 */
	public String getSelectSql(List<SkWherePacket> inParamList) {

		StringBuilder sql = new StringBuilder(this.dataDef.getSql()).append("\n");
		boolean where = true;

		for (SkWherePacket packet : inParamList) {

			String name = packet.getName();
			SaulDdField field = this.fieldNameMap.get(name);
			if (null == field) {
				throw new IllegalArgumentException(String.format("No such field '%s'.", name));
			}
			if (where) {
				where = false;
				sql.append(String.format("WHERE  %s\n", packet.getSql()));
			} else {
				sql.append(String.format("AND    %s\n", packet.getSql()));
			}
		}
		return sql.toString();
	}
}
