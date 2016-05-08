package org.saul.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

/**
 *
 */
public class GenFromTemplates extends DefaultTask {
	@TaskAction
	public void genFromTemplates() {

		Project project = OneCallSaul.getProject();

		System.out.println("----------------------------------------------------------");

		throw new IllegalArgumentException("OUCH!!!!!!!!!!!!");
		//SaulSetup.genFromTemplates(project);
//
//		System.out.println("----------------------------------------------------------");
//
//
//
//
//		System.out.println("-----------------------------------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----: GenFromTemplates : ---------");
//		System.out.println("-----------------------------------");
	}
}
