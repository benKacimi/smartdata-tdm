package org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.accelerate.tdm.smartdata.core.configuration.IWorkerConfiguration;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TableParameter implements IWorkerConfiguration{
    String name;
    String condition;
    List<ColumnParameter> columns;

    public  Map<String, String> getColumnsListAsAMap(){
        if (columns == null)
            return null;
        Map<String, String> columnsMap = new HashMap<String,String>();
        columns.forEach(parameter -> {
            columnsMap.put(parameter.getName(), parameter.getValue());
        } );
        return columnsMap;
    }
}
