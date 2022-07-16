package org.accelerate.tdm.smartdata.plugin.sql.worker.repository;

import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.TableParameter;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Table;

public interface ITableRepository {
    Table get(final TableParameter filter);
}
