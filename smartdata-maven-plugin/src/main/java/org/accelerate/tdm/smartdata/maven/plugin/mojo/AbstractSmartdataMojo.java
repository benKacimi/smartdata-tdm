package org.accelerate.tdm.smartdata.maven.plugin.mojo;

import org.accelerate.tdm.smartdata.core.SmartDataApp;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.*;
import org.apache.maven.plugins.annotations.Parameter;
import org.accelerate.tdm.smartdata.core.exception.TdmException;
import java.util.HashMap;

public abstract class AbstractSmartdataMojo extends AbstractMojo {
    @Parameter(property = "storage", required = true)
    protected String storage;

    @Parameter(property = "properties") //represent properties section in the pom.xlm
    HashMap<String, String> properties;

    @Parameter(defaultValue = "${session}")
    protected MavenSession session;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        SmartDataApp app = new SmartDataApp(storage);

        setAllMojoPropertiesInSystemProperties();
        
        try {
            app.execute();
        } catch (TdmException e) {
            this.getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }

    private void setAllMojoPropertiesInSystemProperties(){
        setXMLPomConfigurationProperties();
        setMavenCmdLineProperties();
    }

    private void setMavenCmdLineProperties(){
        session.getRequest().getUserProperties().forEach((key, value) -> System.getProperties().setProperty((String) key, (String) value));
    }

    private void setXMLPomConfigurationProperties() {
        properties.forEach((key,value) ->  System.getProperties().setProperty((String) key, (String) value));
    }
}
