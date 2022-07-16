package org.accelerate.tdm.smartdata.plugin.sql.worker.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.TableParameter;
import org.accelerate.tdm.smartdata.plugin.sql.worker.dao.ITableDao;
import org.accelerate.tdm.smartdata.plugin.sql.worker.dao.TableDaoImpl;
import org.accelerate.tdm.smartdata.plugin.sql.worker.domain.Table;
import org.accelerate.tdm.smartdata.rules.engine.RulesEngine;
import org.accelerate.tdm.smartdata.rules.engine.ThreadContext;
import org.dbunit.dataset.Column;

public class TableRepositoryImpl implements ITableRepository {

    private  ITableDao tableDao = new TableDaoImpl();
    
    @Override
    public Table get(final TableParameter filter) {
        Table tableResult = tableDao.read(filter.getName(), filter.getCondition());
        List<Map<Column,String>> rows = tableResult.getRows();
        
        if (rows != null){
            Map<String, String> replacementMap = filter.getColumnsListAsAMap();
            RulesEngine engine = new RulesEngine();
            rows.forEach(aRow ->{aRow.forEach((columnKey, columnValue) -> {
                    if (replacementMap != null){
                        String lexem = replacementMap.get(columnKey.getColumnName());
                        if (lexem != null && !"".equals(lexem)){
                            putRowInThreadContext(aRow);
                            aRow.replace(columnKey,engine.execute(lexem));
                        }
                    }
                });
                ThreadContext.remove();
            });   
        }   
        return tableResult;
    } 

    protected void putRowInThreadContext(final Map<Column,String> aRow){
        if (aRow != null) {
            Map<String, String> resultMpap = new HashMap<String,String>();
            aRow.forEach((columnKey, columnValue) -> {
                resultMpap.put(columnKey.getColumnName(), columnValue);
            });
            ThreadContext.setThreadDataMap(resultMpap);
        }
    }
}
