package org.accelerate.tdm.smartdata.plugin.sql;

import java.sql.Connection;

public class ThreadContext {
    private static ThreadLocal<Connection> threadDJdbcConnection = new ThreadLocal<Connection>();

    public static void setConnexion(final Connection  jdbcCon){
        threadDJdbcConnection.set(jdbcCon);
    }

    public static Connection  getConnection (){
         return threadDJdbcConnection.get();
    }
    
    public static void remove(){
        threadDJdbcConnection.remove();
    }
}
