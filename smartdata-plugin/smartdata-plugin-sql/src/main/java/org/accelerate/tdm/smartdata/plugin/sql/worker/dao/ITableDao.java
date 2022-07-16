package org.accelerate.tdm.smartdata.plugin.sql.worker.dao;

import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Table;

public interface ITableDao {
    Table read(final String tableName, final String conditions);
}
