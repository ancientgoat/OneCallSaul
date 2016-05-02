package org.saul.gradle.property;

import org.saul.gradle.datadefinition.inf.SaulHasName;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 *
 */
public class SaulDataSource implements SaulHasName {

    private String name;
    private String username;
    private String password;
    private String url;
    private String driverClassName;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        if (null == this.name) {
            this.name = name;
        }
    }

    public void setUrl(String url) {
        if (null == this.url) {
            this.url = url;
        }
    }

    public void setPassword(String inPassword) {
        if (null == this.password) {
            this.password = inPassword;
        }
    }

    public void setUsername(String inUsername) {
        if (null == this.username) {
            this.username = inUsername;
        }
    }

    public void setDriverClassName(String driverClassName) {
        if (null == this.driverClassName) {
            this.driverClassName = driverClassName;
        }
    }

    public DataSource getDataSource() {
        validateDataSource();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(this.driverClassName);
        dataSourceBuilder.url(this.url);
        return dataSourceBuilder.build();
    }

    private void validateDataSource() {
        final StringBuilder sb = new StringBuilder();
        if (null == this.driverClassName) {
            sb.append("Missing 'driverClassName', please fix.\n");
        }
        if (null == this.driverClassName) {
            sb.append("Missing 'url', please fix.\n");
        }
        if (0 < sb.length()) {
            throw new IllegalArgumentException(sb.toString());
        }

    }

    public final String dumpToString() {
        final StringBuilder sb = new StringBuilder() //
                .append("\n")
                .append(String.format("name            : %s \n", name))
                .append(String.format("username        : %s \n", username))
                .append(String.format("password        : %s \n", "*NotShown*"))
                .append(String.format("url             : %s \n", url))
                .append(String.format("driverClassName : %s \n", driverClassName))
                .append("\n")//
                ;
        return sb.toString();
    }
}
