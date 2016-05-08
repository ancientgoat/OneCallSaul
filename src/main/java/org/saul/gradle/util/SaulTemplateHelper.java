package org.saul.gradle.util;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.util.IllegalFormatCodePointException;
import org.saul.gradle.property.DataGenProperties;
import org.springframework.core.io.ClassPathResource;

import static org.apache.commons.io.FileUtils.getFile;

/**
 *
 */
public class SaulTemplateHelper {
	private SaulTemplateHelper() {
	}

	/**
	 *
	 * @param inProps
	 */
	public static Configuration getConfiguration(DataGenProperties inProps) {

		Configuration cfg = null;

		try {
			// Create your Configuration instance, and specify if up to what FreeMarker
			// version (here 2.3.24) do you want to apply the fixes that are not 100%
			// backward-compatible. See the Configuration JavaDoc for details.
			cfg = new Configuration(Configuration.VERSION_2_3_23);

			// Specify the source where the template files come from. Here I set a
			// plain directory for it, but non-file-system sources are possible too:
			ClassPathResource resource = new ClassPathResource("resources");

			System.out.println(inProps.dumpToString());

			cfg.setDirectoryForTemplateLoading(inProps.getDtDirPath().toFile());

			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			cfg.setDefaultEncoding("UTF-8");

			// Sets how errors will appear.
			// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
			cfg.setLogTemplateExceptions(false);

		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return cfg;
	}
}
