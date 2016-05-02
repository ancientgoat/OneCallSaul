package org.saul.gradle.datadefinition.helper;

import java.nio.file.Path;
import java.util.Set;
import org.saul.gradle.datadefinition.model.datadefinition.SaulMasterDefinitions;
import org.saul.gradle.property.DataGenProperties;

/**
 *
 */
public class SaulTemplates {

	/**
	 *
	 */
	public static void runTemplates(DataGenProperties inPropertyPacket){

		SaulMasterDefinitions masterDefinitions = SaulMasterDefinitions.getMasterDefinitions();
		Set<Path> templates = masterDefinitions.getTemplates();



	}
}
