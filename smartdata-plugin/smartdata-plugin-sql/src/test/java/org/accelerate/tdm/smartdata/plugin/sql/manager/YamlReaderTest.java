package org.accelerate.tdm.smartdata.plugin.sql.manager;

import org.accelerate.tdm.smartdata.plugin.sql.configuration.DefaultSQLConfiguration;
import org.accelerate.tdm.smartdata.plugin.sql.parameters.dataset.DataSetParameter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class YamlReaderTest {

    @Test
    public void testReadSimpleFile()  {

        try {
            String resourceName = "org/accelerate/tdm/smartdata/plugin/sql/manager/configTest.yaml";

            SQLDataExporterManager manager = new SQLDataExporterManager();
            DefaultSQLConfiguration parameter = new DefaultSQLConfiguration();
            parameter.setConfigFilePath(resourceName);
            manager.setCmdLineParameters(parameter);

            DataSetParameter conf = manager.readConfigFile();
            Assert.assertTrue(conf.getCustomisedTables().size() == 1);
            Assert.assertTrue(conf.getExcludedTables().size() == 1);

        } catch (IOException e) {
            Assert.assertTrue(e.getLocalizedMessage(), false);
        }
    }
}
