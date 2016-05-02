package org.saul.gradle.datadefinition.model.datadefinition;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaulIdentity {

    private String name;
    private String shortName;

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        return shortName;
    }

    public SaulIdentity setName(final String inName) {
        if (null == this.name) {
            name = inName;
        }
        return this;
    }

    public SaulIdentity setShortName(final String inShortName) {
        if (null == shortName) {
            shortName = inShortName;
        }
        return this;
    }

    /**
     *
     */
    public String dumpToString() {
        return new StringBuilder()
                .append(String.format("  -- Identity --\n", ""))
                .append(String.format("  name      : %s\n", name))
                .append(String.format("  shortName : %s\n", this.shortName))
                .append(String.format("  -- \n", ""))
                .toString();
    }

    /**
     *
     */
    public static final class Builder {

        private SaulIdentity identity = new SaulIdentity();

        public Builder() {
        }

        public Builder(final SaulIdentity inIdentity) {
            this.identity.name = inIdentity.name;
            this.identity.shortName = inIdentity.shortName;
        }

        public Builder setName(final String inName) {
            this.identity.name = inName;
            return this;
        }

        public Builder setShortName(final String inShortName) {
            this.identity.shortName = inShortName;
            return this;
        }

        public SaulIdentity build() {
            return this.identity;
        }
    }
}
