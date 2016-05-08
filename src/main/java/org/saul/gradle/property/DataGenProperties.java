package org.saul.gradle.property;

import com.google.common.collect.Sets;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.tasks.DefaultSourceSetContainer;
import org.gradle.api.tasks.SourceSet;

/**
 *
 */
public class DataGenProperties {

	public static final String DD_SHORT = "saul/datadef/";
	public static final String DS_SHORT = "saul/datasource/";
	public static final String DT_SHORT = "saul/templates/";

	public static final String DD_NEW = String.format("src/main/resources/%s", DD_SHORT);
	public static final String DS_NEW = String.format("src/main/resources/%s", DS_SHORT);
	public static final String DT_NEW = String.format("src/main/resources/%s", DT_SHORT);

	public static final String GENERATED = "generated";
	public static final String RESOURCES = "resources";
	public static final String SOURCE_SETS = "sourceSets";
	public static final String MAIN = "main";

	private Project project;
	private String ddDir;
	private Path ddDirPath;
	private String dsDir;
	private Path dsDirPath;
	private String dtDir;
	private Path dtDirPath;

	private Set<Path> mainJavaDirs = Sets.newHashSet();
	private Set<Path> mainResourceDirs = Sets.newHashSet();
	private Set<Path> templateFiles = Sets.newHashSet();

	private DataGenProperties() {
	}

	public Path getBuildPath() {
		return Paths.get(this.project.getBuildDir()
				.getAbsolutePath());
	}

	public Project getProject() {
		return project;
	}

	public String getDtDir() {
		return dtDir;
	}

	public Path getDdDirPath() {
		return ddDirPath;
	}

	public Path getDsDirPath() {
		return dsDirPath;
	}

	public Path getDtDirPath() {
		return dtDirPath;
	}

	public String getDdDir() {
		return ddDir;
	}

	public String getDsDir() {
		return dsDir;
	}

	public Set<Path> getMainJavaDirs() {
		return mainJavaDirs;
	}

	public Set<Path> getMainResourceDirs() {
		return mainResourceDirs;
	}

	public String dumpToString() {
		return new StringBuilder()    //
				.append("---------------------------------------------\n")
				.append(String.format("ddDirPath : '%s' \n", ddDirPath))
				.append(String.format("dsDirPath : '%s' \n", dsDirPath))
				.append(String.format("dtDirPath : '%s' \n", dtDirPath))
				.toString();
	}

	/**
	 *
	 */
	public static class Builder {

		private DataGenProperties dataGenProperties = new DataGenProperties();

		/**
		 *
		 */
		public Builder(Project inProject) {

			this.dataGenProperties.project = inProject;

			try {
				File buildRootDir = inProject.getBuildDir()
						.getParentFile();

				String topDir = String.format("%s%s", buildRootDir.getAbsolutePath(), File.separator);

				this.dataGenProperties.ddDir = String.format("%s%s", topDir, DD_NEW)
						.replace("/", File.separator);
				this.dataGenProperties.dsDir = String.format("%s%s", topDir, DS_NEW)
						.replace("/", File.separator);
				this.dataGenProperties.dtDir = String.format("%s%s", topDir, DT_NEW)
						.replace("/", File.separator);

				Map<String, ?> properties = inProject.getProperties();

				Object sourceSets = properties.get(SOURCE_SETS);

				SourceSet main = ((DefaultSourceSetContainer) sourceSets).getByName(MAIN);

				SourceDirectorySet java = main.getJava();

				Set<File> srcDirs = java.getSrcDirs();

				srcDirs.forEach(f -> {
					String absolutePath = f.getAbsolutePath();
					Path path = Paths.get(absolutePath);
					this.dataGenProperties.mainJavaDirs.add(path);
				});

				// Done adding mainJavaDirs

				SourceDirectorySet allSource = main.getAllSource();
				Set<File> allSourceDirs = allSource.getSrcDirs();

				allSourceDirs.stream()
						.forEach(f -> {
							String absolutePath = f.getAbsolutePath();
							if (absolutePath.matches(RESOURCES)) {
								Path path = Paths.get(absolutePath);
								this.dataGenProperties.mainResourceDirs.add(path);
							}
						});
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			setPaths();
		}

		/**
		 *
		 */
		private void setPaths() {

			this.dataGenProperties.ddDirPath = Paths.get(this.dataGenProperties.ddDir);
			this.dataGenProperties.dsDirPath = Paths.get(this.dataGenProperties.dsDir);
			this.dataGenProperties.dtDirPath = Paths.get(this.dataGenProperties.dtDir);

			exists(this.dataGenProperties.ddDirPath.toFile());
			exists(this.dataGenProperties.dsDirPath.toFile());
			exists(this.dataGenProperties.dtDirPath.toFile());
		}

		public void exists(File inFile) {
			if (!inFile.exists())
				throw new IllegalArgumentException(
						String.format("Path '%s' does NOT exist.", inFile.getAbsolutePath()));
		}

		public DataGenProperties build() {
			this.dataGenProperties.ddDir = this.dataGenProperties.ddDir.replace("/", File.separator);
			this.dataGenProperties.dsDir = this.dataGenProperties.dsDir.replace("/", File.separator);
			this.dataGenProperties.dtDir = this.dataGenProperties.dtDir.replace("/", File.separator);
			return this.dataGenProperties;
		}
	}
}
