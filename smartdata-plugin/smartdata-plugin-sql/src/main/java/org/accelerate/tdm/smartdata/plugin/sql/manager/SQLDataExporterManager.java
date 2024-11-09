package org.accelerate.tdm.smartdata.plugin.sql.manager;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.accelerate.tdm.smartdata.core.configuration.ISmartDataConfiguration;
import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.core.exporter.IExporter;
import org.accelerate.tdm.smartdata.core.manager.SmartDataManager;
import org.accelerate.tdm.smartdata.core.tool.FileHelper;
import org.accelerate.tdm.smartdata.plugin.sql.ThreadContext;
import org.accelerate.tdm.smartdata.plugin.sql.configuration.DefaultSQLConfiguration;
import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.DataSetParameter;
import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.TableParameter;
import org.accelerate.tdm.smartdata.plugin.sql.worker.IDefaultSQLWorker;
import org.accelerate.tdm.smartdata.plugin.sql.worker.SQLWorkerFactory;
import org.accelerate.tdm.smartdata.plugin.sql.worker.repository.ISchemaRepository;
import org.accelerate.tdm.smartdata.plugin.sql.worker.repository.SchemaRepositoryImpl;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sql")
public class SQLDataExporterManager extends SmartDataManager {

    @Autowired()
    private DataSource  dataSource;

    private DataSetParameter config;

    @Autowired()
    protected String dataBaseProductName;

    @Autowired(required = true)
    ISmartDataConfiguration cmdLineParameters;

    @Autowired(required = true)
    private IExporter exportFormat;

    @Override
    public void initContext() throws TdmException {
        try {
            config = readConfigFile();
        } catch (IOException e) {
            throw new TdmException(e.getLocalizedMessage());
        }
    }

    @Override
    public void execute() throws TdmException {
        LOGGER.debug("{} - Starting execute...",SQLDataExporterManager.class);
        List<String> tablesListName = getTablesNameList();

        ThreadLocalManagerThreadPool executor = new ThreadLocalManagerThreadPool(nbThread,nbThread, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executor.setDataSource(dataSource);
        LOGGER.info("Start pool of {} threads " , " threads");

        tablesListName.forEach(aTable -> {
            TableParameter sqlWorkerConfig = config.searchExcludedTable(aTable);
            if (sqlWorkerConfig == null) {
                IDefaultSQLWorker sqlWorker = getWorkerInstance();
                sqlWorker.setExporter(exportFormat);
                sqlWorkerConfig = config.searchIncludedTable(aTable);
                if (sqlWorkerConfig == null)
                    sqlWorkerConfig = new TableParameter(aTable, null, null);
                sqlWorker.setConfig(sqlWorkerConfig); 
                executor.submit(() ->  sqlWorker.run());
            }
        });

        awaitTerminationAfterShutdown(executor);

        LOGGER.info("Thread pool stopped.");
        LOGGER.debug("{} - execute- completed.",SQLDataExporterManager.class);
    }

    @Override
    public void end() throws TdmException {
        LOGGER.debug("{} - end- completed.",SQLDataExporterManager.class);
    }
    
    protected DataSetParameter readConfigFile() throws IOException {
        String absolutePath = FileHelper.getAbsolutePath(((DefaultSQLConfiguration)cmdLineParameters).getConfigFilePath());
        try (FileReader file = new FileReader(absolutePath)) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(file, DataSetParameter.class);
        }
    }

    private List<String> getTablesNameList(){
        try (Connection jdbcConn =  dataSource.getConnection()) {
            ThreadContext.setConnexion(jdbcConn);
            ISchemaRepository schemaRepo = new SchemaRepositoryImpl();
            Schema schema = schemaRepo.get();
            return schema.getTableList();
        } catch (SQLException e) { 
            throw new TdmException(e.getLocalizedMessage());
        }
        finally {
            ThreadContext.remove();
        }
    }

    protected void setCmdLineParameters(ISmartDataConfiguration cmdLineParameters){
        this.cmdLineParameters = cmdLineParameters;
    }
    @Override
    protected IDefaultSQLWorker getWorkerInstance() {
        return SQLWorkerFactory.getWorkerInstance(dataBaseProductName);
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        LOGGER.info("{} - ThreadPool - Shutdown initiated...",this.getClass());
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(2, TimeUnit.HOURS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        LOGGER.info("{} - ThreadPool - Shutdown completed.",this.getClass());
    }
}
