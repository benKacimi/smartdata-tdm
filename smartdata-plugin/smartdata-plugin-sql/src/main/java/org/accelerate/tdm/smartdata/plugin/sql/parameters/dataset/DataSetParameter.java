package org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.accelerate.tdm.smartdata.core.configuration.IManagerConfiguration;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataSetParameter implements IManagerConfiguration {
    List<TableParameter> customisedTables;
    List<TableParameter> excludedTables;

//@TODO Changer de nom et de package

    public TableParameter searchIncludedTable(String tableName) {
        return (searchTable(customisedTables, tableName));
    }

    public TableParameter searchExcludedTable(String tableName) {
        return (searchTable(excludedTables, tableName));
    }

    protected TableParameter searchTable(List<TableParameter> tableList, String tableName) {
        if (tableList == null)
            return null;
        if ("".equalsIgnoreCase(tableName) || tableName == null)
            return null;
        for (TableParameter aTable : tableList) {
            if (tableName.equalsIgnoreCase(aTable.getName())) 
            return aTable;
        }
        return null;
    }
}
