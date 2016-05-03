package org.saul.gradle.template;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Helper bit specifically for the Freemarker templating system.
 */
public class FreemarkerHelper {

	public static final String TEMPLATE_CLASSPATH_DIR = File.separator;

	/**
	 *
	 */
	private FreemarkerHelper() {
	}

	/**
	 * @return Template Find a Freemarker template based on the AlertType (for now).
	 */
	public static Template findTemplate(File inTemplateFile) {
		Template template = null;
		try {
			Configuration cfg = getFreemarkerConfiguration();
			template = cfg.getTemplate(inTemplateFile.getAbsolutePath());
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return template;
	}

	/**
	 * @return String Run a Freemarker Template against a model and return the results asw a String.
	 */
	public static String templateAndModelToString(Template inTemplate, Object inModel) {
		StringWriter sw = new StringWriter();
		try {
			inTemplate.process(inModel, sw);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return sw.toString();
	}

	/**
	 * @return Configuration Return a Freemarker Configuration.
	 */
	private static Configuration getFreemarkerConfiguration() {
		Configuration cfg = new Configuration(Configuration.getVersion());
		ClassTemplateLoader templateLoader = new ClassTemplateLoader(FreemarkerHelper.class, TEMPLATE_CLASSPATH_DIR);
		cfg.setTemplateLoader(templateLoader);
		return cfg;
	}
}
