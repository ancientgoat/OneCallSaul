package org.saul.gradle;

import java.util.Map;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.tasks.TaskAction;
import org.saul.gradle.datadefinition.helper.DxSetup;

/**
 *
 */
public class GenDataDef extends DefaultTask {
	@TaskAction
	public void genDataDef() {
		Project project = OneCallSaul.getProject();
		System.out.println("----------------------------------------------------------");
		DxSetup.genDataDef(project);
		System.out.println("----------------------------------------------------------");
	}
}
