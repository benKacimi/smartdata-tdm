package org.accelerate.tdm.smartdata.rules.engine;


import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RulesEngineTest {
    RulesEngine engine = new RulesEngine();
    
    @Test
    public void testRuleEngineWithNullInputParameter()
    {
       String foo = null;
       String result = engine.execute(foo);
       assertTrue("Null expeted instead if : " + result,  result == null);
    }

    @Test
    public void testRuleEngineWithEmptyStringInputParameter()
    {
       String result = engine.execute("");
       assertTrue("Empty String expeted instead of : " + result,  "".equals(result));
    }

    @Test
    public void testRuleEngineALitteral()
    {
       String result = engine.execute("foo bar");
       assertTrue("foo bar  expeted instead of : " + result,  "foo bar".equals(result));
    }

    @Test
    public void testRuleEngineWithAFunction()
    {
       String result = engine.execute("@function()");
       assertTrue("foo expected instead of : " + result,  "foo".equals(result));
    }

    @Test
    public void testRuleEngineWithAFunctionThatDoesntExist()
    {
       String result = engine.execute("@nonExistentFunction()");
       assertTrue("@nonExistentFunction() expected instead of : " + result,  "@nonExistentFunction()".equals(result));
    }
    @Test
    public void testRuleEngineWithAFunctionThatDoesntExistWithAnExistingClass()
    {
       String result = engine.execute("@function.nonExistentFunction()");
       assertTrue("@function.nonExistentFunction() expected instead of : " + result,  "@function.nonExistentFunction()".equals(result));
    }
    @Test
    public void testRuleEngineWithAFunctionThatDoesntExistWithAnNonExistingClass()
    {
       String result = engine.execute("@nonExistentClass.nonExistentFunction()");
       assertTrue("@nonExistentClass.nonExistentFunction() expected instead of : " + result,  "@nonExistentClass.nonExistentFunction()".equals(result));
    }

    @Test
    public void testRuleEngineWithAFunctionWithBackSpaceParameter()
    {
       String result = engine.execute("@function( )");
       assertTrue("foo expected instead of : " + result,  "foo".equals(result));
    }
    @Test
    public void testRuleEngineWithAFunctionThatContaindAWrongNumberOfParameter()
    {
       String result = engine.execute("@function(foo=bar)");
       assertTrue("@function(foo=bar) expected instead of : " + result,  "@function(foo=bar)".equals(result));
    }

    @Test
    public void testRuleEngineWithAFunctionThatDoesntReturnAString()
    {
       String result = engine.execute("@function.functionWithWrongReturnType( )");
       assertTrue("@functionWithWrongReturnType() expected instead of : " + result,  "@function.functionWithWrongReturnType( )".equals(result));
    }

    @Test
    public void testRuleEngineWithAFunctionThatContentAFunctionInParameter()
    {
       String result = engine.execute("@anonymize.anonymiseUUID(${oldId})");
       assertTrue("Result connot be null"  ,result != null);
       assertTrue("Size of the key expected is 38 instead of : " + result.length() ,result.length()== 38);
    }
    @Test
    public void testRuleEngineWithThreadlocalValue()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","foo");
      localProperties.put("var2","bar");
      ThreadContext.setThreadDataMap(localProperties);
       String result = engine.execute("${var2}");
       
       assertTrue("bar expected instead of : " + result ,"bar".equals(result));
       ThreadContext.remove();
    }
    @Test
    public void testRuleEngineWithThreadlocalValueAndSpaceCaratere()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("var1","foo");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("${ var2 }");
       
       assertTrue("bar expected instead of : " + result ,"bar".equals(result));
       ThreadContext.remove();
    }

    @Test
    public void testRuleEngineWithThreadlocalValueAndAParameterInFunction()
    {
      Map<String, String> localProperties = new HashMap<String, String>();
      localProperties.put("customerName","zdzldfsfvklsfcsklfcsfdkl");
      localProperties.put("var2","bar");
      
      ThreadContext.setThreadDataMap(localProperties);
      String result = engine.execute("@encrypt.AESEncrypt($customerName)");
      assertTrue(!"@encrypt.AESEncrypt($customerName)".equals(result));
      ThreadContext.remove();
    }
    
    
}
