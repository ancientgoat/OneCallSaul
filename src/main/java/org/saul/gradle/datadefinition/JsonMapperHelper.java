package org.saul.gradle.datadefinition;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.databind.DeserializationFeature.READ_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING;
import static com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.MINIMIZE_QUOTES;
import static com.fasterxml.jackson.dataformat.yaml.YAMLGenerator.Feature.SPLIT_LINES;

// import com.fasterxml.jackson.

/**
 *
 */
public class JsonMapperHelper {

	private static final Logger LOG = LoggerFactory.getLogger(JsonMapperHelper.class);

	private JsonMapperHelper() {
	}

	public static final ObjectMapper newInstanceJson() {
		ObjectMapper mapper = new CustomMapper();
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
		// mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
		// mapper.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);
		return mapper;
	}

	public static final ObjectMapper newInstanceYaml() {
		final YAMLFactory yamlFactory = new YAMLFactory();
		yamlFactory.configure(SPLIT_LINES, true);
		yamlFactory.configure(MINIMIZE_QUOTES, true);
		// yamlFactory.configure(CANONICAL_OUTPUT, true);
		final ObjectMapper mapper = new CustomMapper(yamlFactory);
		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.enable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
		// mapper.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
		// mapper.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);

		return mapper;
	}

	//~~ public static final Yaml yamlForOutput() {
	//~~     DumperOptions printOptions = new DumperOptions();
	//~~     printOptions.setLineBreak(DumperOptions.LineBreak.UNIX);
	//~~     printOptions.setPrettyFlow(true);
	//~~     printOptions.setWidth(120);
	//~~     printOptions.setSplitLines(true);
	//~~     final Yaml yaml = new Yaml(printOptions);
	//~~     return yaml;
	//~~ }

	/**
	 *
	 */
	public static final String writeBeanToYaml(final Object inObject) {
		return writeMapperToString(inObject, newInstanceYaml());
		//~~ final Yaml yaml = yamlForOutput();
		//~~ final String dumpString = yaml.dump(inObject);
		//~~ return dumpString;
	}

	/**
	 *
	 */
	public static final String writeBeanToJson(final Object inObject) {
		return writeMapperToString(inObject, newInstanceJson());
	}

	private static String writeMapperToString(Object inObject, ObjectMapper inMapper) {
		try {
			final String outputString = inMapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(inObject);
			return outputString;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 *
	 */
	public static final String writeBeanToYamlFile(String inOutputDir, String inFileName, Object inObject) {
		new File(inOutputDir).mkdirs();
		String fullFileName = String.format("%s%s", inOutputDir, inFileName);
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Full Filename : '5s'", fullFileName));
		}
		final String yamlString = writeMapperToString(inObject, newInstanceYaml());
		writeMapperToFile(fullFileName, yamlString);
		return fullFileName;
	}

	/**
	 *
	 */
	public static final void writeBeanToJsonFile(final String inOutputFileName, final Object inObject) {
		writeMapperToFile(inOutputFileName, writeMapperToString(inObject, newInstanceJson()));
	}

	/**
	 *
	 */
	private static final void writeMapperToFile(final String inOutputFileName, final String inOutput) {
		try {
			final FileWriter fileWriter = new FileWriter(inOutputFileName);
			fileWriter.write(inOutput);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static class CustomMapper extends ObjectMapper {

		public CustomMapper() {
			super();
		}

		public CustomMapper(final JsonFactory inFactory) {
			super(inFactory);
		}

		@PostConstruct
		public void customConfiguration() {
			// Uses Enum.toString() for serialization of an Enum
			this.enable(WRITE_ENUMS_USING_TO_STRING);
			// Uses Enum.toString() for deserialization of an Enum
			this.enable(READ_ENUMS_USING_TO_STRING);

			// this.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
			// this.setPropertyNamingStrategy(new CamelCaseNamingStrategy());
			// this.getDeserializationConfig().findMixInClassFor(InpNodeBase.class);
			// this.configure(SerializationFeature.INDENT_OUTPUT, true);
		}
	}

	/**
	 *
	 */
	public static final <C> C yamlToBean(final File inFile, final Class inClazz) {
		try {
			final ObjectMapper mapper = JsonMapperHelper.newInstanceYaml();
			final C yamlFile = (C) mapper.readValue(inFile, inClazz);
			return yamlFile;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
			// System.out.println ExceptionHelper.toString(e)
		}
	}
}
