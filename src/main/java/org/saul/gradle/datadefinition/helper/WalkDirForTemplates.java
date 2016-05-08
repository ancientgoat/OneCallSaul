package org.saul.gradle.datadefinition.helper;

/**
 *
 */

import com.google.common.collect.Sets;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

import static java.nio.file.FileVisitResult.CONTINUE;
import static org.saul.gradle.datadefinition.helper.SaulSetup.FTP_EXTENSION;

/**
 * We walk a directory tree and save the relative path , from the top path, with each dataDefinition.
 */
public class WalkDirForTemplates implements FileVisitor<Path> {

	private Set<Path> templates = Sets.newHashSet();

	private WalkDirForTemplates() {
	}

	public WalkDirForTemplates(File inTopDir) {
		if (!inTopDir.exists()) {
			throw new IllegalArgumentException(
					String.format("The directory '%s' doesn't exist", inTopDir.getAbsolutePath()));
		}
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
	@Override
	public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult visitFile(final Path inPath, final BasicFileAttributes attrs) throws IOException {
		String filename = inPath.toFile()
				.getName();
		if (filename.endsWith(FTP_EXTENSION)) {
			this.templates.add(inPath);
		}
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
		return CONTINUE;
	}

	/**
	 *
	 */
	@Override
	public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
		return CONTINUE;
	}
}
