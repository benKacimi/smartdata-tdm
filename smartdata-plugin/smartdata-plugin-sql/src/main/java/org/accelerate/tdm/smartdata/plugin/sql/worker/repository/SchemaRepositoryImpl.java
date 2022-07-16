package org.accelerate.tdm.smartdata.plugin.sql.worker.repository;

import org.accelerate.tdm.smartdata.plugin.sql.worker.dao.ISchemaDao;
import org.accelerate.tdm.smartdata.plugin.sql.worker.dao.SchemaDaoImpl;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Schema;

public class SchemaRepositoryImpl implements ISchemaRepository {

    private ISchemaDao schemaDao = new SchemaDaoImpl();
    @Override
    public Schema get() {
       return schemaDao.read();
    }
    
}
