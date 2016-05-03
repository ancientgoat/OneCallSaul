package org.saul.gradle.datadefinition.helper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import org.saul.gradle.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.gradle.enums.SaulFileType;
import org.saul.gradle.property.DataGenProperties;

/**
 *
 */
public class SaulTemplates {

	/**
	 *
	 */
	public static void runTemplates(DataGenProperties inPropertyPacket) {

		SaulMasterDefinitions masterDefinitions = SaulMasterDefinitions.getMasterDefinitions();
		Set<Path> templates = masterDefinitions.getTemplates();
		String templateBaseDirString = inPropertyPacket.getDtDir();
		String buildDirString = inPropertyPacket.getBuildPath()
				.toFile()
				.getAbsolutePath();

		templates.forEach(t -> {

			Path outputPath = SaulFileType.getFileType(t)
					.getDirPath();

			String newPathString = String.format("%s%s%s", buildDirString, File.separator, outputPath);
			new File(newPathString).mkdirs();

			String newFileName = getNewFileName(t);
			File newPath = new File(String.format("%s%s%s", newPathString, File.separator, newFileName));



			System.out.println("============================4===============");
			System.out.println("templateBaseDirString  : " + templateBaseDirString);
			System.out.println("outputPath             : " + outputPath.toFile()
					.getAbsolutePath());
			System.out.println("newPathString          : " + newPathString);
			System.out.println("buildDirString         : " + buildDirString);
			System.out.println("============================4===============");

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
}

