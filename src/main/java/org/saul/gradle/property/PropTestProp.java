package org.saul.gradle.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationProperties(locations = "classpath:prop.properties", prefix = "test")
public class PropTestProp {

    private String namex;

    public String getNamex() {
        return namex;
    }

    public void setNamex(String namex) {
        this.namex = namex;
    }
}
