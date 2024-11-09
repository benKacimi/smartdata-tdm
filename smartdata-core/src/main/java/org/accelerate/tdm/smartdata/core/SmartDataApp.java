package org.accelerate.tdm.smartdata.core;

import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.core.manager.ISmartDataManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SmartDataApp {

    protected static final String BASE_PACKAGE = "org.accelerate.tdm.smartdata.plugin";
    private String storage;

    public SmartDataApp(final String storage){
        this.storage = storage;
    }

    public void execute() throws TdmException {
        ApplicationContext context = new AnnotationConfigApplicationContext(BASE_PACKAGE + "." + storage);
        Object csv = context.getBean("csv");
        ISmartDataManager exporterManager = (ISmartDataManager) context.getBean(storage);
        exporterManager.run();
       // ((AbstractApplicationContext) context).close();
    }
}
