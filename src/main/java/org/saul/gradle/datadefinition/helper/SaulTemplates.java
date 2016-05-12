package org.saul.gradle.datadefinition.helper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Set;
import org.saul.gradle.datadefinition.model.datadefinition.SaulDataDefinition;
import org.saul.gradle.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.gradle.enums.SaulFileType;
import org.saul.gradle.property.DataGenProperties;
import org.saul.gradle.util.SaulTemplateHelper;

import static java.io.File.separator;

/**
 *
 */
public class SaulTemplates {

	/**
	 *
	 */
	public static void runTemplates(DataGenProperties inPropertyPacket,
			final SaulMasterDefinitions inSaulMasterDefinitions, final Set<Path> inTemplateOutputPaths) {

		Configuration configuration = SaulTemplateHelper.getConfiguration(inPropertyPacket);

		inSaulMasterDefinitions.getGeneratedDataDefinitionSet()
				.forEach(dd -> {
					System.out.println("==========================================");
					System.out.println("======= ()");
					System.out.println(dd.dumpToString());
					System.out.println("======= ()");
					System.out.println("==========================================");
					buildTempates(dd, inSaulMasterDefinitions, inPropertyPacket, configuration);
				});
	}

	/**
	 *
	 */
	private static void buildTempates(final SaulDataDefinition inDataDef,
			final SaulMasterDefinitions inSaulMasterDefinitions, final DataGenProperties inProps,
			final Configuration inConfiguration) {

		Set<Path> templatePaths = inSaulMasterDefinitions.getTemplates();
		String buildDirString = inProps.getBuildPath()
				.toFile()
				.getAbsolutePath();

		Path templateStartPath = inProps.getDtDirPath();
		String className = inDataDef.getClassName();

		templatePaths.forEach(templatePath -> {

			String templateDirString = templatePath.toString();

			Path outputPath = SaulFileType.getFileType(templatePath)
					.getDirPath();

			String templateRelativeDirString = templatePath.toString()
					.replace(templateStartPath.toString(), "");
			if (templateRelativeDirString.startsWith(separator))
				templateRelativeDirString = templateRelativeDirString.substring(1);

			String newDirString = String.format("%s%s%s", buildDirString, separator, outputPath);

			String newFileName = getNewFileName(templateRelativeDirString);
			String newerFileName = newFileName.replace("ClassName", className);

			File newPath = new File(String.format("%s%s%s", newDirString, separator, newerFileName));
			String newPathString = newPath.getAbsolutePath();
			newPath.getParentFile().mkdirs();

			System.out.println("====================================================================================");
			System.out.println("templateStartPath      : " + templateStartPath);
			System.out.println("templateRelativeDirString : " + templateRelativeDirString);
			System.out.println("templatePath           : " + templatePath);
			System.out.println("newFileName            : " + newFileName);
			System.out.println("newerFileName          : " + newerFileName);
			System.out.println("templateDirString      : " + templateDirString);
			System.out.println("outputPath             : " + outputPath.toFile()
					.getAbsolutePath());
			System.out.println("newPathString          : " + newDirString);
			System.out.println("buildDirString         : " + buildDirString);
			System.out.println("newPathString          : " + newPathString);
			System.out.println("===========");

			try {
				FileWriter fileWriter = new FileWriter(newPathString);

				Path path = templatePath.getFileName();
				String filename = path.toFile()
						.getName();
				String fullFilename = templateRelativeDirString.replace(File.separator, "/");

				String absolutePath = templatePath.toFile()
						.getAbsolutePath()
						.replace(templateStartPath.toString(), "")
						.replace(File.separator, "/");

				System.out.println("filename           : " + filename);
				System.out.println("fullFilename       : " + fullFilename);
				System.out.println("absolutePath       : " + absolutePath);

				Template template = inConfiguration.getTemplate(absolutePath);
				template.process(inDataDef, fileWriter);
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			System.out.println("====================================================================================");
		});
	}

	/**
	 *
	 */
	private static String getNewFileName(Path inPath) {
		return inPath.toFile()
				.getName()
				.replace(".ftl", "");
	}

	private static String getNewFileName(String inName) {
		return inName.replace(".ftl", "");
	}
}

