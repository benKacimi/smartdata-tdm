package org.accelerate.tdm.smartdata.plugin.mysql.manager;

import org.accelerate.tdm.smartdata.plugin.mysql.worker.MySqlWorker;
import org.accelerate.tdm.smartdata.plugin.sql.manager.SQLDataExporterManager;
import org.accelerate.tdm.smartdata.plugin.sql.worker.IDefaultSQLWorker;
import org.springframework.stereotype.Component;

@Component("mysql")
public class MySqlDataExporterManager extends SQLDataExporterManager {

    @Override
    protected IDefaultSQLWorker getWorkerInstance() {
        return new MySqlWorker();
    }

}
