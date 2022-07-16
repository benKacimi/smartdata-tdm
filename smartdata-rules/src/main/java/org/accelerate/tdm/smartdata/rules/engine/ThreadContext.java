package org.accelerate.tdm.smartdata.rules.engine;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {
    private static ThreadLocal<Map<String,String>> threadDataProperties = new ThreadLocal<Map<String, String>>(){
        @Override public Map<String,String> initialValue() {
            return new HashMap<String,String>();
        }
    };
    
    public static void setThreadDataMap(final Map<String, String> properties){
        threadDataProperties.set(properties);
    }

    public static String getVariableValue(final String key){
         return threadDataProperties.get().get(key);
    }
    
    public static void remove(){
        threadDataProperties.get().clear();
        threadDataProperties.remove();
    }
}
