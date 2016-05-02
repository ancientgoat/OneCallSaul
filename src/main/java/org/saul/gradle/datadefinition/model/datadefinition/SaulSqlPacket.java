package org.saul.gradle.datadefinition.model.datadefinition;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class SaulSqlPacket {

	public static final String DELIMITER = ":";

	private String originalSql;
	private String adjustedSql;
	private Set<String> parameterNameSet = Sets.newHashSet();

	public SaulSqlPacket(final String inSql) {
		if (null == inSql) {
			throw new IllegalArgumentException("Input SQL not allowed to be null.");
		}
		this.originalSql = inSql;
		// Find any parameters between two delimiters, and adjust the SQL to work with JdbcTemplate.
		parseSqlAndParameters(this.originalSql);
	}

	public String getOriginalSql() {
		return this.originalSql;
	}

	public String getAdjustedSql() {
		return this.adjustedSql;
	}

	public Set<String> getParameterNameSet() {
		return this.parameterNameSet;
	}

	/**
	 * Find any parameters between two delimiters, and adjust the SQL to work with JdbcTemplate.
	 *
	 * @param inOriginalSql The raw SQL as input into this class.
	 */
	private void parseSqlAndParameters(final String inOriginalSql) {
		// Let's talk about code documenting itself, or not.
		this.parameterNameSet.addAll(separateParametersBetweenTwoDelimiters(DELIMITER, inOriginalSql));
		this.adjustedSql = replaceOriginalSqlWithJdbcTemplateSqlParameters(DELIMITER, inOriginalSql,
				parameterNameSet);
	}

	/**
	 * @param inDelimiter The String DELIMITER that splits parameter names in the input SQL.
	 * @param inOriginalSql The original input SQL.
	 * @return List<String> A list of parameter names found in the Input SQL.
	 */
	private List<String> separateParametersBetweenTwoDelimiters(final String inDelimiter,
																final String inOriginalSql) {

		final Pattern pattern = Pattern.compile(String.format("%s\\w+?\\%s", inDelimiter, inDelimiter));
		final Matcher matcher = pattern.matcher(inOriginalSql);
		final List<String> list = Lists.newArrayList();

		while (matcher.find()) {
			String textInBetween = matcher.group(0).replace(inDelimiter, ""); // Since (.*?) is capturing group 1
			// You can insert match into a List/Collection here
			list.add(textInBetween);
		}
		return list;
	}

	/**
	 * @param inDelimiter The DELIMITER that was used to find the parameter names.
	 * @param inOriginalSql The raw SQL as input into this class.
	 * @param inParamSet A set of Parameter names that was found inside the SQL.
	 * @return String Return SQL adjusted so that it works with JdbcTemplate and any found parameters inside the
	 * 			SQL.
	 */
	private String replaceOriginalSqlWithJdbcTemplateSqlParameters(final String inDelimiter,
			final String inOriginalSql, final Set<String> inParamSet) {

		String tempSql = this.originalSql;
		for (final String param : inParamSet) {
			// Turn all ':param:' into ':param' (without the trailing DELIMITER)
			tempSql = tempSql.replace(String.format("%s%s", param, inDelimiter), param);
		}
		return tempSql;
	}
}
