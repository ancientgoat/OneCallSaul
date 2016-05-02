package org.saul.gradle.datadefinition.model.datadefinition;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SaulFieldMaster {

	private List<SaulDdField> fieldList = Lists.newArrayList();
	private Map<String, SaulDdField> fieldMap = Maps.newHashMap();

	public SaulFieldMaster(final List<SaulDdField> inFieldList) {
		if (null == inFieldList || 0 == inFieldList.size()) {
			throw new IllegalArgumentException("field list can NOT be null or of zero (0) size.");
		}
		this.fieldList = inFieldList;
		makeMap(this.fieldList);
	}

	/**
	 *
	 * @param inFieldList
	 */
	private void makeMap(final List<SaulDdField> inFieldList) {
		for (final SaulDdField field : inFieldList) {
			final String fieldName = field.getColumnName();
			if (null == fieldName || 0 == fieldName.length()) {
				throw new IllegalArgumentException("Field 'name' can NOT be null or of zero (0) size.");
			}
			this.fieldMap.put(fieldName, field);
		}
	}

	public List<SaulDdField> getFieldList() {
		return fieldList;
	}

	public SaulDdField getField(final String inName) {
		if (null == inName || 0 == inName.length()) {
			throw new IllegalArgumentException("Input field 'name' can NOT be null or of zero (0) size.");
		}
		final SaulDdField saulDdField = this.fieldMap.get(inName);
		if (null == saulDdField) {
			throw new IllegalArgumentException(String.format("No such field name '%s'.", inName));
		}
		return saulDdField;
	}
}
