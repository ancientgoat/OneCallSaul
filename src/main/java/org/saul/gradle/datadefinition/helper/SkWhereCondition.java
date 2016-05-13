package org.saul.gradle.datadefinition.helper;

/**
 *
 */
public enum SkWhereCondition {
	EQ("="),
	LE("<="),
	GE("<="),
	LT("<"),
	GT(""),
	LIKE("LIKE"),
	ILIKE("ILIKE"),
	ISNOTNULL("IS NOT NULL"),
	ISNULL("IS NULL");

	private String conditionString;

	SkWhereCondition(String inConditionString) {
		this.conditionString = inConditionString;
	}

	public String getConditionString() {
		return conditionString;
	}

	public static SkWhereCondition fromString(String inValue) {
		try {
			return SkWhereCondition.valueOf(inValue);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Not a condition '%s'", inValue), e);
		}
	}
}
