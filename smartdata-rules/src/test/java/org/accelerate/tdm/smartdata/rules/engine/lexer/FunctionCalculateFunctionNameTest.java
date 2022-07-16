package org.accelerate.tdm.smartdata.rules.engine.lexer;

import static org.junit.Assert.*;
import org.junit.Test;

public class FunctionCalculateFunctionNameTest {

    @Test
    public void testCalculateFunctionNameWithBlankParemeter()
    {
        String str = "";
        String parameter = Function.calculateFunctionName(str);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateFunctionNameWithNull()
    {
        String parameter = Function.calculateFunctionName(null);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateFunctionNameWithEmptyName()
    {
        String str = "@()";
        String parameter = Function.calculateFunctionName(str);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateFunctionNameWithSimpleFunctionName()
    {
        String str = "@func()";
        String functionName = Function.calculateFunctionName(str);
        assertTrue("func".equals(functionName));
    }
    @Test
    public void testCalculateFunctionNameWithInvalidFunctionName()
    {
        String str = "@fu<nc()";
        String functionName = Function.calculateFunctionName(str);
        assertTrue(null == functionName);
    }
}
