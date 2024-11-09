package org.accelerate.tdm.smartdata.plugin.sql.worker;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.core.configuration.IWorkerConfiguration;
import org.accelerate.tdm.smartdata.core.exporter.IExporter;
import org.accelerate.tdm.smartdata.plugin.sql.exporter.CVSDataMap;
import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.TableParameter;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Table;
import org.accelerate.tdm.smartdata.plugin.sql.worker.repository.TableRepositoryImpl;


public class DefaultSQLWorker implements IDefaultSQLWorker {
    
   
    protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultSQLWorker.class);
    
    private TableParameter tableConfig;
 
    private IExporter exportFormat;

    public DefaultSQLWorker( TableParameter config) {
        this.tableConfig = config;
    }

    public DefaultSQLWorker() {
    }
    
    @Override
    public void run() {
        TableRepositoryImpl tableRepo = new TableRepositoryImpl();
        Table tableResult = tableRepo.get(tableConfig);
        List<Map<Column,String>> rows = tableResult.getRows();
        writeResultFile(rows);
    }

    protected void writeResultFile( List<Map<Column,String>> rows ){ 
        CVSDataMap map = new CVSDataMap();
        map.setRows(rows);
        exportFormat.export(map);
    }

    protected IDataTypeFactory getDataFactory(){
        return new DefaultDataTypeFactory();
    }

    @Override
    public void setConfig(IWorkerConfiguration config) {
        tableConfig = (TableParameter) config;
    }

    @Override
    public void setExporter(IExporter exporter) {
        exportFormat = exporter;
    }
}