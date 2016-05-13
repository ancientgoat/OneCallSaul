package org.saul.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;
import org.saul.gradle.datadefinition.helper.SaulSetup;

/**
 *
 */
public class GenDataDef extends DefaultTask {
	@TaskAction
	public void genDataDef() {
		Project project = OneCallSaul.getProject();
		//System.out.println("----------------------------------------------------------");
		SaulSetup.genDataDef(project);
		//System.out.println("----------------------------------------------------------");
	}
}
