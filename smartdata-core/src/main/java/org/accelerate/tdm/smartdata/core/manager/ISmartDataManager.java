package org.accelerate.tdm.smartdata.core.manager;

import org.accelerate.tdm.smartdata.core.exception.TdmException;

public interface ISmartDataManager {
    public void run() throws TdmException;
    public void initContext() throws TdmException;
    public void execute() throws TdmException;
    public void end() throws TdmException;
}
