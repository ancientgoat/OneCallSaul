package org.saul.gradle.datadefinition.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 */
public class SkWherePacket {

	public static final String REST_DIVIDER = "~";

	private String name;
	private SkWhereCondition condition = SkWhereCondition.EQ;
	private Object value;

	private SkWherePacket() {
	}

	public SkWherePacket(String inName, Object inValue) {
		this.name = inName;
		this.condition = SkWhereCondition.EQ;
		this.value = inValue;
	}

	public SkWherePacket(String inName, SkWhereCondition inCondition, Object inValue) {
		this.name = inName;
		this.condition = inCondition;
		this.value = inValue;
	}

	public String getName() {
		return (null != name ? name.toUpperCase() : null);
	}

	public SkWhereCondition getCondition() {
		return condition;
	}

	@JsonIgnore
	public String getConditionString() {
		return condition.getConditionString();
	}

	public Object getValue() {
		return value;
	}

	@JsonIgnore
	public String getSql() {
		return String.format("%s %s %s", this.name, this.condition.getConditionString(),
				(null != this.value ? this.value : ""));
	}

	@JsonIgnore
	public String getRestParameters() {
		return String.format("%s%s%s=%s", this.name, REST_DIVIDER, this.condition.toString(),
				(null != this.value ? this.value : ""));
	}

	/**
	 *
	 */
	public static SkWherePacket parseRestParameters(String inName, Object inValue) {
		String[] parts = inName.split(REST_DIVIDER);
		if (2 != parts.length) {
			throw new IllegalArgumentException(
					String.format("Expected a name like 'columnName%sEQ', but instead got '%s'.", REST_DIVIDER,
							inName));
		}
		SkWherePacket.Builder builder = new SkWherePacket.Builder()//
				.setName(parts[0])
				.setCondition(SkWhereCondition.fromString(parts[1]))
				.setValue(inValue);
		return builder.build();
	}

	/**
	 *
	 */
	public static class Builder {
		private SkWherePacket packet = new SkWherePacket();

		public Builder setName(final String inName) {
			this.packet.name = inName;
			return this;
		}

		public Builder setCondition(final SkWhereCondition inCondition) {
			this.packet.condition = inCondition;
			return this;
		}

		public Builder setValue(final Object inValue) {
			this.packet.value = inValue;
			return this;
		}

		public SkWherePacket build() {
			return this.packet;
		}
	}
}
