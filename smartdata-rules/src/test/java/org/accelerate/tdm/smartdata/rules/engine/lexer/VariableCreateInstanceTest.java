package org.accelerate.tdm.smartdata.rules.engine.lexer;

import static org.junit.Assert.*;
import org.junit.Test;

public class VariableCreateInstanceTest {
    @Test
    public void testCreateInstanceWithNullLexem()
    {
        Variable var = Variable.createInstance(null);
        assertTrue(null == var);
    }    
    
    @Test
    public void testCreateInstanceWithBlankLexem()
    {
        String str = "";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    }
    @Test
    public void testCreateInstanceWithSpace()
    {
        String str = "    ";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    }  
    @Test
    public void testCreateInstanceWithOnlyAAccolade()
    {
        String str = "${";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    }  
    @Test
    public void testCreateInstanceWithNoKeyName()
    {
        String str = "${}";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    } 
    @Test
    public void testCreateInstanceWithSpaceAsKeyName()
    {
        String str = "${ }";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    } 
    @Test
    public void testCreateInstanceWithSpaceBeforeAndAfterKeyName()
    {
        String str = "${ aVar }";
        Variable var = Variable.createInstance(str);
        assertTrue(" aVar ".equals(var.getKeyName()));
    } 
    @Test
    public void testCreateVariableValidInstance()
    {
        String str = "${aVariable}";
        Variable var = Variable.createInstance(str);
        assertTrue("aVariable".equals(var.getKeyName()));
    }
    @Test
    public void testCreateVariableValidInstanceWithPoint()
    {
        String str = "${foo.bar}";
        Variable var = Variable.createInstance(str);
        assertTrue("foo.bar".equals(var.getKeyName()));
    }
    @Test
    public void testCreateVariableInvalidInstanceWithParenthesis()
    {
        String str = "${foo(bar}";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    }   
    @Test
    public void testCreateVariableInvalidInstanceWithSpaceIntoKeyName()
    {
        String str = "${foo bar}";
        Variable var = Variable.createInstance(str);
        assertTrue(null == var);
    }       
}
