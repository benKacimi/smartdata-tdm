package org.accelerate.tdm.smartdata.plugin.sql.worker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.plugin.sql.ThreadContext;
import org.accelerate.tdm.smartdata.plugin.sql.datasource.DBUnitDataTypeFactory;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Table;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableDaoImpl implements ITableDao {

    private static final String SELECT = "SELECT ";
    private static final String ALL_COLUMNS = " * ";
    private static final String FROM = " FROM ";
    private static final String WHERE = " WHERE ";
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(TableDaoImpl.class);
   
    @Override
    public Table read(final String tableName, final String condition) {
        try (   Connection jdbcConn = ThreadContext.getConnection(); 
                PreparedStatement statement = jdbcConn.prepareStatement(buildSqlExtractionRequest(tableName, condition))) {
            
            LOGGER.info("{} - Starting SQLWorker run...",  tableName);
            ResultSet resultset = statement.executeQuery();
            Column[] columnNameTab =  getColumnListForAGiventTable(tableName);
            List<Map<Column,String>> rows = new ArrayList<>();
            while (resultset.next()) { 
                Map<Column,String> aRow = new HashMap<>();
                for(int i=0;i < columnNameTab.length; i++){
                    aRow.put(columnNameTab[i], resultset.getString(columnNameTab[i].getColumnName())); 
                }
                rows.add(aRow);
            }
            Table tableResult = new Table();
            tableResult.setRows(rows);
            LOGGER.info("{} - SQLWorker run - completed.", tableName);
            return tableResult;
        } catch (SQLException  e) {
            LOGGER.error("SQLWorker.run error - {}", e.getMessage());
        }
        return null;
    }

    private String buildSqlExtractionRequest (final String tableName, final String condition){
        StringBuilder query = new StringBuilder(SELECT);

        query.append(ALL_COLUMNS).append(FROM).append(tableName);  
        if (condition != null && !"".equals(condition))
            query.append(WHERE).append(condition);
        LOGGER.debug("{} - SQLWorker buildSqlExtractionRequest - sqlRequest  :", query);
        return query.toString();
    }

    private Column[] getColumnListForAGiventTable(final String tableName) {
        try {
            LOGGER.info("{} - Starting getColumnList run...", tableName);
            Connection jdbcConn =  ThreadContext.getConnection();
            IDatabaseConnection dbUnitDatabaseConnection = new DatabaseConnection(ThreadContext.getConnection());
            DatabaseConfig dbConfig = dbUnitDatabaseConnection.getConfig();
            // added this line to get rid of the warning
            dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, DBUnitDataTypeFactory.getInstance(jdbcConn.getMetaData().getDatabaseProductName()));
            IDataSet dataSet = dbUnitDatabaseConnection.createDataSet();
            return dataSet.getTableMetaData(tableName).getColumns();
        }
        catch (SQLException | DatabaseUnitException e) {
            LOGGER.error("SQLWorker.getColumnList error - {}", e.getMessage());
        }
        return new Column[]{};
    }
}
