package org.accelerate.tdm.smartdata.plugin.sql.worker;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLWorkerFactory {

    private static final String MYSQL = "MySQL";

    public static IDefaultSQLWorker getWorkerInstance(String dataBaseProductName){
        if (MYSQL.equalsIgnoreCase(dataBaseProductName))
            return  new DefaultSQLWorker(); // ajouter un worker MySQL par IOC
        return new DefaultSQLWorker();
    }
}
