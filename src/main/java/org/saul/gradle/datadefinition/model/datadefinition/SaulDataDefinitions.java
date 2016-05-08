package org.saul.gradle.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

/**
 *
 */
@JsonRootName("DataDefinitions")
public class SaulDataDefinitions {

	@JsonProperty("dataDefinition")
	private Set<SaulDataDefinition> saulDataDefinitionSet = Sets.newHashSet();

	public Set<SaulDataDefinition> getSaulDataDefinitionSet() {
		return saulDataDefinitionSet;
	}

	@JsonProperty("dataDefinition")
	public List<SaulDataDefinition> getSaulDataDefinitionList() {
		return Lists.newArrayList(saulDataDefinitionSet);
	}

	public SaulDataDefinitions setSaulDataDefinitionSet(final Set<SaulDataDefinition> inSaulDataDefinitionSet) {
		saulDataDefinitionSet = inSaulDataDefinitionSet;
		return this;
	}

	public SaulDataDefinitions addSaulDataDefinition(final SaulDataDefinition inSaulDataDefinition) {
		saulDataDefinitionSet.add(inSaulDataDefinition);
		return this;
	}

	/**
	 *
	 */
	public String dumpToString() {

		if (null == saulDataDefinitionSet) {
			throw new IllegalArgumentException("Data Definitions can NOT be null.");
		}
		final StringBuilder sb = new StringBuilder();

		for (final SaulDataDefinition dataDef : saulDataDefinitionSet) {
			sb.append(dataDef.dumpToString());
		}

		return sb.toString();
	}
}
