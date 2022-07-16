package org.accelerate.tdm.smartdata.core.security;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DecryptTest {

    @Test
    public void testReadSimpleFile()  {

        String decodedString =  RSASecurityTool.decrypt("private_key.pem",
                                                        "password.encrypt");
        assertTrue("admin expexted instead of : " + decodedString ,"admin".equals(decodedString));
    }
}
