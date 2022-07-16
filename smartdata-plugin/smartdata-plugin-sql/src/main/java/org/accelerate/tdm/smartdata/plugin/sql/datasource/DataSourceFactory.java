package org.accelerate.tdm.smartdata.plugin.sql.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.accelerate.tdm.smartdata.core.configuration.ISmartDataConfiguration;
import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.core.manager.SmartDataManager;
import org.accelerate.tdm.smartdata.core.security.RSASecurityTool;
import org.accelerate.tdm.smartdata.plugin.sql.configuration.DefaultSQLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceFactory {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceFactory.class);
    private static final String DATASOURCE_POOL_NAME ="SmartdataPool";

    @Autowired(required = true)
    ISmartDataConfiguration inputParameters;

    protected DataSource dataSource;

    @Bean (name = "dataSource")
    public  DataSource getDataSource() {
        LOGGER.info("Starting getDataSource...");
        if (dataSource  == null){
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(((DefaultSQLConfiguration)inputParameters).getJdbcUrl());
            hikariConfig.setUsername( ((DefaultSQLConfiguration)inputParameters).getUser());
            hikariConfig.setPassword( RSASecurityTool.decrypt(  ((DefaultSQLConfiguration)inputParameters).getPrivateKeyFile(),
                                                                ((DefaultSQLConfiguration)inputParameters).getPasswordFile()));
            hikariConfig.setPoolName(DATASOURCE_POOL_NAME);
            hikariConfig.setMaximumPoolSize(SmartDataManager.getOptimizeThreadNumber());
            hikariConfig.setConnectionTimeout(3000);
            dataSource = new HikariDataSource(hikariConfig);
        }
        LOGGER.info("getDataSource- completed.");
        return dataSource;
    }

    @Bean (name = "dataBaseProductName")
    public String getDatabaseProductName() throws TdmException {
        try ( Connection jdbcConn = dataSource.getConnection()){
            String dataBaseProductName = jdbcConn.getMetaData().getDatabaseProductName();
            LOGGER.info("DataSourceFactory database type : {}", dataBaseProductName);
            return dataBaseProductName;
        } catch (SQLException e) {
            throw new TdmException(e.getMessage());
        }
    }
}
