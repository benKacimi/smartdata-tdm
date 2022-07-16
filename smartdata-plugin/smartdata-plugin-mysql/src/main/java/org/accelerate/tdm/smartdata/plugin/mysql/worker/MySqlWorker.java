package org.accelerate.tdm.smartdata.plugin.mysql.worker;

import org.accelerate.tdm.smartdata.plugin.sql.worker.DefaultSQLWorker;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public class MySqlWorker extends DefaultSQLWorker {

    @Override
    protected IDataTypeFactory getDataFactory(){
        return new MySqlDataTypeFactory();
    }
}
