package org.accelerate.tdm.smartdata.rules.engine.lexer;

import static org.junit.Assert.*;
import org.junit.Test;

public class FunctionCalculateParameterTest {
    
    @Test
    public void testCalculateParamerterWitBlankParemeter()
    {
        String str = "";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateParamerterWitMultipleBackspaceParameter()
    {
        String str = "   ";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateParamerterWitBackspaceParameter()
    {
        String str = " ";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateParamerterWitNullParemeter()
    {
        String parameter = Argument.calculateFunctionParameter(null);
        assertTrue(null == parameter);
    }
    @Test
    public void testCalculateParamerterWithNoArgument()
    {
        String str = "@func()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithBackspaceArgument()
    {
        String str = "@func( )";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("One backspace caractere expected instead of : " + parameter," ".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithSimpleArguments()
    {
        String str = "@func(param=true)";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("param=true".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithEmbedArguments()
    {
        String str = "@func(param=true@func2())";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("param=true@func2()".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithComplexArguments()
    {
        String str = "(a)(b)(c)";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("a".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithComplexEmbedArguments()
    {
        String str = "((foo(bar)john(do)))()func()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("(foo(bar)john(do))".equals(parameter) );
    }
    @Test
    public void testCalculateParamerterWithUnbalancedParenthesis()
    {
        String str = "func(param()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue(null ==parameter);
    }
    @Test
    public void testCalculateParamerterWithUnbalancedParenthesis2()
    {
        String str = ")func(param()";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue(null ==parameter);
    }

    @Test
    public void testCalculateParamerterWithVariableArgument()
    {
        String str = "@function(arg1=${foo})";
        String parameter = Argument.calculateFunctionParameter(str);
        assertTrue("arg1=${foo}".equals(parameter) );
    }
}
