package org.accelerate.tdm.smartdata.core.tool;

import org.accelerate.tdm.smartdata.core.security.RSASecurityTool;

import java.io.File;
import java.net.URL;

public class FileHelper {

    public static String getAbsolutePath(final String filePath){
        String absolutePath = filePath;
        ClassLoader classLoader = RSASecurityTool.class.getClassLoader();
        URL url = classLoader.getResource(filePath);
        if (url != null) {
            File file = new File(url.getFile());
            absolutePath = file.getAbsolutePath();
        }
        return absolutePath;
    }
}
