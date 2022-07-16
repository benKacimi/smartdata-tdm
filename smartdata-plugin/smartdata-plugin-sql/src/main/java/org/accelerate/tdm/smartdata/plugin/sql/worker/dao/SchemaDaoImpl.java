package org.accelerate.tdm.smartdata.plugin.sql.worker.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.plugin.sql.ThreadContext;
import org.accelerate.tdm.smartdata.plugin.sql.datasource.DBUnitDataTypeFactory;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Schema;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaDaoImpl implements ISchemaDao {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SchemaDaoImpl.class);

    @Override
    public Schema read() {
        Schema schemaResult = new Schema();
        schemaResult.setTableList(getTablesNameList() );
        return schemaResult;
    }
    
    private List<String> getTablesNameList() throws TdmException {
        try (Connection jdbcConn =  ThreadContext.getConnection()) { 
            IDatabaseConnection conn = new DatabaseConnection(jdbcConn);
            DatabaseConfig dbConfig = conn.getConfig();
            // added this line to get rid of the warning
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, DBUnitDataTypeFactory.getInstance(jdbcConn.getMetaData().getDatabaseProductName()));
            IDataSet dataSet = conn.createDataSet();
            String[] tablesName = dataSet.getTableNames();
            return  Arrays.asList(tablesName);
        } catch (SQLException | DatabaseUnitException e) {
            LOGGER.error(e.getMessage(),"getTablesListName");
            throw (new TdmException(e.getMessage()));
        }
    }
}
