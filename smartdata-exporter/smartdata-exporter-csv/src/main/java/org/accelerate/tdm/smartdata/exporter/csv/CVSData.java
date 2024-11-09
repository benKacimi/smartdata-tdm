package org.accelerate.tdm.smartdata.exporter.csv;

import org.accelerate.tdm.smartdata.core.exporter.IExporterDataWrapper;

public class CVSData implements IExporterDataWrapper {
    /*
     * A CSV file is a flat file with each line corresponding to a piece of data. 
     * An end of line is marked by the '\n' character, 
     * each field of a line is separated by a separator
     */
    protected static final String SEPARATOR = ";";
    
}
