package org.saul.gradle.datadefinition.model.datadefinition;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import org.saul.gradle.datadefinition.inf.SaulHasName;
import org.saul.gradle.property.DataGenProperties;
import org.saul.gradle.property.SaulDataSource;

/**
 *
 */
public class SaulMasterDefinitions {

	private Set<SaulDataDefinition> dataDefinitionSet;
	private Set<SaulDataDefinition> generatedDataDefinitionSet = Sets.newHashSet();
	private Set<SaulDataSource> dataSourceSet;
	private Set<Path> templates = Sets.newHashSet();
	private final Map<String, SaulDataDefinition> dataDefinitionMap = Maps.newHashMap();
	private final Map<String, SaulDataSource> dataSourceMap = Maps.newHashMap();

	private static SaulMasterDefinitions masterDefinitions = null;

	/**
	 *
	 */
	public SaulMasterDefinitions(Set<SaulDataDefinition> inDataDefinitionSet, Set<SaulDataSource> inDataSourceSet,
			Set<Path> inTemplates, DataGenProperties inProps) {

		this.dataDefinitionSet = inDataDefinitionSet;
		this.dataSourceSet = inDataSourceSet;
		this.templates = inTemplates;

		validateDataDefinitions();
		buildMaps();
		masterDefinitions = this;
	}

	//	/**
	//	 *
	//	 */
	//	private Set<Path> makeGeneratedDataDefinitionSet(Set<SaulDataDefinition> inTemplates, DataGenProperties inProps) {
	//
	//		Set<Path> generatedTemplates = Sets.newHashSet();
	//
	//		inTemplates.forEach(t -> {
	//
	//			String relativeDirString = t.toString()
	//					.replace(inProps.getDtDir(), "");
	//
	//			String newDirString = SaulDataDefinition.getTemplateOutputDirectory();
	//			String relativeFileName = String.format("%s%s", newDirString,relativeDirString );
	//
	//			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//			System.out.println(relativeFileName);
	//			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	//
	//			generatedTemplates.add(Paths.get(relativeDirString));
	//		});
	//
	//		return generatedTemplates;
	//	}

	/**
	 *
	 */
	public static SaulMasterDefinitions getMasterDefinitions() {
		return masterDefinitions;
	}

	/**
	 *
	 */
	public Set<Path> getTemplates() {
		return templates;
	}

	/**
	 *
	 */
	private void validateDataDefinitions() {

		final StringBuilder sb = new StringBuilder();

		// Insure everyone has an Identity.
		sb.append(validateIdentityNames());

		// We can only have one of each Identity
		if (0 == sb.length()) {
			sb.append(validateUniqueIdentityNames());
		}

		if (0 < sb.length()) {
			throw new IllegalArgumentException(sb.toString());
		}
	}

	private String validateUniqueIdentityNames() {
		final StringBuilder sb = new StringBuilder();
		final Set<String> nameSet = Sets.newHashSet();
		this.dataDefinitionSet.stream()
				.forEach(d -> {
					final String name = d.getIdentity()
							.getName();
					if (nameSet.contains(name)) {
						sb.append(String.format("Identity Name '%s', already exists.", name));
					} else {
						nameSet.add(name);
					}
				});

		return sb.toString();
	}

	private String validateIdentityNames() {
		final StringBuilder sb = new StringBuilder();
		for (final SaulDataDefinition dataDef : this.dataDefinitionSet) {
			final String validateString = validateIdentity(dataDef);
			if (0 < validateString.length()) {
				sb.append(validateString);
			}
		}
		return sb.toString();
	}

	/**
	 *
	 */
	private String validateIdentity(final SaulDataDefinition inDataDef) {

		final StringBuilder sb = new StringBuilder();

		final SaulIdentity identity = inDataDef.getIdentity();

		if (null == identity) {
			sb.append("Identity is null, please fix.");
		} else if (null == identity.getName()) {
			sb.append("Identity Name is null, please fix.");
		}

		return sb.toString();
	}

	/**
	 *
	 */
	public Set<SaulDataDefinition> getDataDefinitionSet() {
		return dataDefinitionSet;
	}

	/**
	 *
	 */
	public Set<SaulDataDefinition> getGeneratedDataDefinitionSet() {
		return generatedDataDefinitionSet;
	}

	/**
	 *
	 */
	public void addGeneratedDataDefinition(SaulDataDefinition inDataDef) {
		generatedDataDefinitionSet.add(inDataDef);
	}

	/**
	 *
	 */
	public Set<SaulDataSource> getDataSourceSet() {
		return dataSourceSet;
	}

	/**
	 *
	 */
	private void buildMaps() {
		if (null != this.dataDefinitionSet) {
			makeMap(this.dataDefinitionMap, this.dataDefinitionSet);
		}
		if (null != this.dataSourceSet) {
			makeMap(this.dataSourceMap, Sets.newHashSet(this.dataSourceSet));
		}
	}

	/**
	 *
	 */
	private <C extends SaulHasName> void makeMap(final Map<String, C> inMap, final Set<C> inSet) {
		inSet.forEach(s -> {
			inMap.put(s.getName(), s);
		});
	}

	/**
	 *
	 */
	public SaulDataDefinition getDataDefinition(final String inName) {
		return get(inName, this.dataDefinitionMap);
	}

	/**
	 *
	 */
	public SaulDataSource getDataSource(final String inName) {
		return get(inName, this.dataSourceMap);
	}

	/**
	 *
	 */
	private <C extends SaulHasName> C get(final String inName, final Map<String, C> inMap) {
		final C entity = inMap.get(inName);
		if (null == entity) {
			String simpleName = "";
			if (0 < inMap.size()) {
				final C value = Lists.newArrayList(inMap.values())
						.get(0);
				simpleName = value.getClass()
						.getName();
			} else {
				simpleName = "Elements, as they are empty ";
			}
			throw new IllegalArgumentException(String.format("No such %s '%s'", simpleName, inName));
		}
		return entity;
	}
}
