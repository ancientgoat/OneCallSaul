package org.saul.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 *
 */
public class OneCallSaul implements Plugin<Project> {

	private static Project PROJECT;

	/**
	 *
	 */
	public void apply(Project inProject) {
		PROJECT = inProject;
		TaskContainer tasks = inProject.getTasks();
		tasks.create("genDataDef", GenDataDef.class);
		//tasks.create("genFromTemplate", GenFromTemplates.class);
	}

	public static Project getProject() {
		return PROJECT;
	}
}