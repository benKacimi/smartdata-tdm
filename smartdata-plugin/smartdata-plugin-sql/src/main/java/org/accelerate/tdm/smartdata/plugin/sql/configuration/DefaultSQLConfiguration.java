package org.accelerate.tdm.smartdata.plugin.sql.configuration;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.accelerate.tdm.smartdata.core.configuration.ISmartDataConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component("defaultSqlConfiguration")
public class DefaultSQLConfiguration implements ISmartDataConfiguration {
    @Value("${jdbcUrl}")
    String jdbcUrl;

    @Value("${configurationFile}")
    String configFilePath;

    @Value("${user}")
    String user;

    @Value("${passwordFile}")
    String passwordFile;

    @Value("${privateKeyFile}")
    String privateKeyFile;
}
