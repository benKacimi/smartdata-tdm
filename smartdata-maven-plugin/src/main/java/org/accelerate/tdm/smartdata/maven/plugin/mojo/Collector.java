package org.accelerate.tdm.smartdata.maven.plugin.mojo;


import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "export")
public class Collector extends AbstractSmartdataMojo {
    @Parameter(property = "exportFormat")
    protected String exportFormat;
}
