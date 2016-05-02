package org.saul.gradle.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.collect.Sets;
import java.util.Set;

/**
 *
 */
// @ConfigurationProperties(locations = "classpath:mail.properties", prefix = "datasource")
@JsonRootName("DataSources")
public class SaulDataSources {

	private String elmo;

	@JsonProperty("dataSource")
	private Set<SaulDataSource> saulDataSources = Sets.newHashSet(); //	Sets.newHashSet();

	public Set<SaulDataSource> getSaulDataSources() {
		return saulDataSources;
	}

	public void setSaulDataSources(Set<SaulDataSource> inSaulDataSources) {
		this.saulDataSources = inSaulDataSources;
	}

	public final String dumpToString() {
		final StringBuilder sb = new StringBuilder();
		for (final SaulDataSource ds : this.saulDataSources) {
			sb.append(ds.dumpToString());
		}
		return sb.toString();
	}
}
