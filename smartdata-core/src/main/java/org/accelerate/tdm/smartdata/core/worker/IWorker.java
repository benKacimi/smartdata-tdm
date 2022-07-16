package org.accelerate.tdm.smartdata.core.worker;

import org.accelerate.tdm.smartdata.core.configuration.IWorkerConfiguration;

public interface IWorker extends Runnable{
    public void setConfig(IWorkerConfiguration config);
}
