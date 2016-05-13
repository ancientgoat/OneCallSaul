package org.saul.gradle.datadefinition.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import org.gradle.api.Project;
import org.saul.gradle.datadefinition.JsonMapperHelper;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinitions;
import org.saul.gradle.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.gradle.property.DataGenProperties;
import org.saul.gradle.property.SaulDataSource;
import org.saul.gradle.property.SaulDataSources;
import org.saul.gradle.util.ExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SaulSetup {

	private static final Logger LOG = LoggerFactory.getLogger(SaulSetup.class);

	public static final String FTP_EXTENSION = "ftl";

	private static DataGenProperties propertyPacket;

	private SaulSetup() {
	}

	/**
	 *
	 */
	public static void genDataDef(Project inProject) {
		readProperties(inProject);
		SaulMasterDefinitions saulMasterDefinitions = getSaulMasterDefinitions(propertyPacket);
		Set<Path> templateOutputPaths = genDataDefinitions(propertyPacket, saulMasterDefinitions);
		//	}
		//
		//	/**
		//	 *
		//	 */
		//	public static void genFromTemplates(Project inProject) {
		//		readProperties(inProject);
		SaulTemplates.runTemplates(propertyPacket, saulMasterDefinitions, templateOutputPaths);
	}

	/**
	 *
	 */
	private static void readProperties(Project inProject) {
		if (null == propertyPacket) {
			try {
				propertyPacket = new DataGenProperties.Builder(inProject).build();
			} catch (Exception e) {
				LOG.error(ExceptionHelper.toString(e));
				throw new IllegalArgumentException(e);
			}
		}
	}

	/**
	 *
	 */
	private static SaulMasterDefinitions getSaulMasterDefinitions(DataGenProperties inPropertyPacket) {
		SaulMasterDefinitions masterDefinitions = SaulMasterDefinitions.getMasterDefinitions();
		if (null == masterDefinitions) {
			return readYamlSources(inPropertyPacket);
		} else {
			return masterDefinitions;
		}
	}

	/**
	 *
	 */
	private static Set<Path> genDataDefinitions(DataGenProperties inPropertyPacket,
			final SaulMasterDefinitions inSaulMasterDefinitions) {

		Set<Path> outputPaths = Sets.newHashSet();

		try {

			for (final SaulDataDefinition dataDef : inSaulMasterDefinitions.getDataDefinitionSet()) {

				// Write Data Definitions out to a set of beans.
				SaulDdHelper helper = new SaulDdHelper(dataDef);
				SaulDataDefinition newDataDef = helper.fillDataDefFromSql();

				String buildDir = inPropertyPacket.getProject()
						.getBuildDir()
						.getAbsolutePath();
				String fileName = newDataDef.getFileName();
				String outputDirectory = dataDef.getDataDefOutputDirectory();
				String outputFileDir =
						String.format("%s%s%s%s", buildDir, File.separator, outputDirectory, File.separator);

				String fullFilename =
						JsonMapperHelper.writeBeanToYamlFile(outputFileDir, fileName, newDataDef, "dataDefinition");

				// System.out.println("********************************************");
				// System.out.println("********************************************");
				// System.out.println("********************************************");
				// System.out.println("outputFileDir   : " + outputFileDir);
				// System.out.println("Filename        : " + fileName);
				// System.out.println("outputDirectory : " + outputDirectory);
				// System.out.println("FullFilename    : " + fullFilename);
				// System.out.println("********************");
				// System.out.println(JsonMapperHelper.writeBeanToYaml(newDataDef));
				// System.out.println("********************************************");
				// System.out.println("********************************************");
				// System.out.println("********************************************");

				outputPaths.add(Paths.get(fullFilename));
				SaulDataDefinition localDataDef = readDefinition(new File(fullFilename));
				// System.out.println("************************** ===========================");
				// System.out.println(localDataDef.dumpToString());
				// System.out.println("************************** ===========================");
				inSaulMasterDefinitions.addGeneratedDataDefinition(localDataDef);
			}
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
		return outputPaths;
	}

	/**
	 *
	 */
	private static SaulMasterDefinitions readYamlSources(DataGenProperties inPropertyPacket) {

		SaulMasterDefinitions masterDefinitions = null;
		try {
			Set<SaulDataSource> sourceSet = readAllSources(inPropertyPacket);
			Set<SaulDataDefinition> definitionSet = readAllDefinitions(inPropertyPacket);
			Set<Path> templates = getTemplates(inPropertyPacket.getDtDir());
			masterDefinitions = new SaulMasterDefinitions(definitionSet, sourceSet, templates, inPropertyPacket);

			for (final SaulDataDefinition dataDef : masterDefinitions.getDataDefinitionSet()) {
				String dataSourceName = dataDef.getDataSourceName();
				if (null == dataSourceName) {
					throw new IllegalArgumentException(
							String.format("Data Source Name can NOT be null for DataDef '%s'.", dataDef.getName()));
				}
				dataDef.setDataSource(masterDefinitions.getDataSource(dataSourceName));
			}

			//
			//
			//

		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
		return masterDefinitions;
	}

	/**
	 *
	 */
	private static Set<Path> getTemplates(String inTopDir) {

		Path topPath = Paths.get(inTopDir);
		WalkDirForTemplates walkDirForTemplates = new WalkDirForTemplates(topPath.toFile());
		try {
			Files.walkFileTree(topPath, walkDirForTemplates);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		return walkDirForTemplates.getTemplates();
	}

	/**
	 *
	 */
	private static Set<SaulDataSource> readAllSources(DataGenProperties inPropertyPacket) {
		return readAllSources(inPropertyPacket.getDsDirPath()
				.toFile());
	}

	/**
	 *
	 */
	public static Map<String, SaulDataSource> readAllSourcesIntoMap(File inTopDog) {
		Map<String, SaulDataSource> map = Maps.newHashMap();
		Set<SaulDataSource> set = readAllSources(inTopDog);
		set.forEach(ds -> map.put(ds.getName(), ds));
		return map;
	}

	/**
	 *
	 */
	public static Set<SaulDataSource> readAllSources(File inTopDog) {
		if (!inTopDog.exists()) {
			throw new IllegalArgumentException(String.format("The directory '%s' doesn't exist", inTopDog.getName()));
		}
		Set<SaulDataSource> SourceSet = Sets.newHashSet();
		File[] files = inTopDog.listFiles();

		for (final File file : files) {
			SourceSet.addAll(readSources(file));
		}
		return SourceSet;
	}

	/**
	 *
	 */
	public static Set<SaulDataSource> readSources(File inFile) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataSources dataSources = mapper.readValue(inFile, SaulDataSources.class);
			return Sets.newHashSet(dataSources.getSaulDataSources());
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	private static Set<SaulDataDefinition> readAllDefinitions(DataGenProperties inPropertyPacket) {

		Set<SaulDataDefinition> definitionSet = Sets.newHashSet();
		File topDir = inPropertyPacket.getDdDirPath()
				.toFile();
		File[] files = topDir.listFiles();

		if (!topDir.exists()) {
			throw new IllegalArgumentException(String.format("The directory '%s' doesn't exist", topDir.getName()));
		}

		for (final File file : files) {
			definitionSet.addAll(readDefinitions(file));
		}
		return definitionSet;
	}

	/**
	 *
	 */
	private static Set<SaulDataDefinition> readDefinitions(File inFile) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataDefinitions dataDefinitions = mapper.readValue(inFile, SaulDataDefinitions.class);
			return Sets.newHashSet(dataDefinitions.getSaulDataDefinitionList());
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	public static SaulDataDefinition readDefinition(File inFile) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataDefinition dataDefinition = mapper.readValue(inFile, SaulDataDefinition.class);

//			System.out.println(".....................................................................");
//			System.out.println(".....................................................................");
//			System.out.println("...............");
//			System.out.println("...............");
//			System.out.println(dataDefinition.dumpToString());
//			System.out.println("...............");
//			System.out.println("...............");
//			System.out.println(".....................................................................");
//			System.out.println(".....................................................................");
			return dataDefinition;
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	public static SaulDataDefinition readDefinition(String inYaml) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final SaulDataDefinition dataDefinition = mapper.readValue(inYaml, SaulDataDefinition.class);

			//System.out.println(".....................................................................");
			//System.out.println(".....................................................................");
			//System.out.println("...............");
			//System.out.println("...............");
			//System.out.println(dataDefinition.dumpToString());
			//System.out.println("...............");
			//System.out.println("...............");
			//System.out.println(".....................................................................");
			//System.out.println(".....................................................................");
			return dataDefinition;
		} catch (Exception e) {
			LOG.error(ExceptionHelper.toString(e));
			throw new IllegalArgumentException(e);
		}
	}
}

