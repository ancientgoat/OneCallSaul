package org.saul.gradle.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;

/**
 *
 */
@JsonRootName("DataDefinitions")
public class SaulDataDefinitions {

    @JsonProperty("dataDefinition")
    private Set<SaulDataDefinition> saulDataDefinitionSet;

    public Set<SaulDataDefinition> getSaulDataDefinitionSet() {
        return saulDataDefinitionSet;
    }

    public List<SaulDataDefinition> getSaulDataDefinitionList() {
        return Lists.newArrayList(saulDataDefinitionSet);
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
