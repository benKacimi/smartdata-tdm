package org.accelerate.tdm.smartdata.core.worker;

import org.accelerate.tdm.smartdata.core.configuration.IWorkerConfiguration;
import org.accelerate.tdm.smartdata.core.exporter.IExporter;

public interface IWorker extends Runnable{
    public void setConfig(IWorkerConfiguration config);
    public void setExporter(IExporter exporter);
}
