package org.accelerate.tdm.smartdata.core.manager;

import org.accelerate.tdm.smartdata.core.exception.TdmException;
import org.accelerate.tdm.smartdata.core.worker.IWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SmartDataManager implements ISmartDataManager {

    protected int nbThread = 10;

    protected static final Logger LOGGER = LoggerFactory.getLogger(SmartDataManager.class);

    public final void run() throws TdmException {

        nbThread = getOptimizeThreadNumber();

        initContext();
        execute();
        end();
    }

    protected abstract IWorker getWorkerInstance();

    public static int getOptimizeThreadNumber(){
        int nbProc = Runtime.getRuntime().availableProcessors();
        if (nbProc > 1)
            nbProc--;
        return nbProc;
    }
}
