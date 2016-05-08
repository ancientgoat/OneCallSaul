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

	private static final String DD_NEW = "src/main/resources/saul/datadef/";
	private static final String DS_NEW = "src/main/resources/saul/datasource/";
	private static final String DT_NEW = "src/main/resources/saul/templates/";

	//	private static final Path DEFAULT_GEN_JAVA_PATH = Paths.get("generated-sources/src/main/java/");
	//	private static final Path DEFAULT_GEN_RESOURCE_PATH =
	//			Paths.get("generated-sources/src/main/resources/");

	private static final String GENERATED = "generated";
	private static final String RESOURCES = "resources";
	private static final String SOURCE_SETS = "sourceSets";
	private static final String MAIN = "main";

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
					//					if (absolutePath.matches(GENERATED)) {
					//						this.dataGenProperties.generatedJavaDir = path;
					//					}
				});

				//				if (null == this.dataGenProperties.generatedJavaDir) {
				//					this.dataGenProperties.generatedJavaDir = DEFAULT_GEN_JAVA_PATH;
				//				}

				// Done adding mainJavaDirs

				SourceDirectorySet allSource = main.getAllSource();
				Set<File> allSourceDirs = allSource.getSrcDirs();

				allSourceDirs.stream()
						.forEach(f -> {
							String absolutePath = f.getAbsolutePath();
							if (absolutePath.matches(RESOURCES)) {
								Path path = Paths.get(absolutePath);
								this.dataGenProperties.mainResourceDirs.add(path);
								//								if (absolutePath.matches(GENERATED)) {
								//									this.dataGenProperties.generatedResourceDir = path;
								//								}
							}
						});

				//				if (null == this.dataGenProperties.generatedResourceDir) {
				//					this.dataGenProperties.generatedResourceDir = DEFAULT_GEN_RESOURCE_PATH;
				//				}
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
