package org.accelerate.tdm.smartdata.plugin.sql.datasource;

import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;

public class DBUnitDataTypeFactory {
    private static final String MYSQL = "MySQL";

    public static DefaultDataTypeFactory getInstance(final String dataBaseProductName){
        if (MYSQL.equalsIgnoreCase(dataBaseProductName))
            return  new MySqlDataTypeFactory(); // ajouter un worker MySQL par IOC
        return new DefaultDataTypeFactory();
    }
}
