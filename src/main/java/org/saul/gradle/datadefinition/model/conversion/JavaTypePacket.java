package org.saul.gradle.datadefinition.model.conversion;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JavaTypePacket {
	private String javaTypeName;
	private Class javaClass;

	public JavaTypePacket() {
	}

	public JavaTypePacket(final Class inClazz) {
		this.javaClass = inClazz;
		final String className = this.javaClass.getName();

		// Turn 'java.lang.String' into 'String'
		this.javaTypeName = className.substring(className.lastIndexOf(".") + 1)
				.trim();
	}

	public String getJavaTypeName() {
		return javaTypeName;
	}

	public Class getJavaClass() {
		return javaClass;
	}

	public void setJavaTypeName(String javaTypeName) {
		if (null == this.javaTypeName)
			this.javaTypeName = javaTypeName;
	}

	public void setJavaClass(Class javaClass) {
		if (null == this.javaClass)
			this.javaClass = javaClass;
	}
}
