package org.saul.gradle.enums;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 */
public enum SaulFileType {

	JAVA("generated-sources/src/main/java/"),
	GROOVY("generated-sources/src/main/groovy/"),
	RESOURCES("generated-sources/src/main/resources/");

	private String dirString;
	private Path dirPath;

	SaulFileType(String inDirStirng) {
		this.dirString = inDirStirng;
		this.dirPath = Paths.get(this.dirString);
	}

	public String getDirString() {
		return dirString;
	}

	public Path getDirPath() {
		return dirPath;
	}

	/**
	 *
	 */
	public static SaulFileType getFileType(final Path inPath) {
		String nameString = inPath.toFile()
				.getAbsolutePath()
				.replace(".ftl", "");
		int i = nameString.lastIndexOf(".");
		String extension = nameString.substring(i + 1);
		extension = extension.toUpperCase();
		SaulFileType fileType = null;

		try {
			fileType = SaulFileType.valueOf(extension);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("No SaulFileType of '%s'", extension), e);
		}
		return fileType;
	}
}
