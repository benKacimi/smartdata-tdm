package org.accelerate.tdm.smartdata.rules.engine.lexer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LexTest {

    @Test
    public void testLexEmptyString()
    {
        String str = "";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        assertTrue(root.getChildren() == null); 
    }

    @Test
    public void testLexNull()
    {
        Lexer lexer = new Lexer();
        Node root = lexer.lex(null);
        assertTrue(root.getChildren() == null);
    }
    @Test
    public void testLexStringWithBackspace()
    {
        String str = "   ";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertTrue("   ".equals(literal.getValue()));
    }
    @Test
    public void testLexASimpleFunction()
    {
        String str = "@function()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue(function.getArguments() == null);
        assertTrue("function".equals(function.getFunctionName()));
        assertTrue("one chhild expected instead of  : " + root.getChildren().size(),  1 == root.getChildren().size());
    }
    @Test
    public void testLexASimpleFunctionWithBlankArgument()
    {
        String str = "@functionTest(   )";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue(function.getArguments() == null);
        assertTrue("functionTest".equals(function.getFunctionName()));
        assertTrue("one chhild expected instead of  : " + root.getChildren().size(),  1 == root.getChildren().size());
    }
    @Test
    public void testLexStringWithNoFunctionName()
    {
        String str = "@()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Literal literal = (Literal)root.getChildren().get(0);
        assertTrue("@()".equals(literal.getValue()));
    }
    @Test
    public void testLexASimpleFunctionWithLiteralParameter()
    {
        String str = "@function(  a parameter  )";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue("function".equals(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal =(Literal)arg.getChildren().get(0);
        assertTrue("a parameter".equals(literal.getValue()));
        assertTrue("one chhild expected instead of  : " + root.getChildren().size(),  1 == root.getChildren().size());
    }
    @Test
    public void testLexASimpleFunctionWithLiteralParameterAndOtherFunction()
    {
        String str = "@function(a parameter)foo@bar@function2(@functionGetParameter())$";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue("function".equals(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal1 =(Literal)arg.getChildren().get(0);
        assertTrue("a parameter".equals(literal1.getValue()));

        Literal literal2 = (Literal)root.getChildren().get(1);
        assertTrue("function name containts an @", "foo@bar".equals(literal2.getValue()));

        assertTrue("4 nodes over : " + root.getChildren().size() , root.getChildren().size() == 4);

        Literal literal3 = (Literal)root.getChildren().get(3);
        assertTrue("$' exepected instead of : "+ literal3.getValue(), "$".equals(literal3.getValue()));

        assertTrue(root==lexer.lex(str));
    }
    @Test
    public void testLexASimpleFunctionWithEmailAdressAndFunction()
    {
        String str = "foo@bar.com@function()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(1);
        assertTrue("function".equals(function.getFunctionName()));
        assertTrue( "Rules Class error : "+ function.getFunctionName(),
                    "org.accelerate.tdm.smartdata.rules.FunctionRuleForTest".
                    equals(function.getFunctionClass()));
    }
    @Test
    public void testLexASimpleFunctionWithEmailAdressAndFunctionWithANumberInFunctionName()
    {
        String str = "foo@bar.com@func23()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(1);
        assertTrue("Function Name should be func23 instead : " + function.getFunctionName(),"func23".equals(function.getFunctionName()));
        assertTrue(root==lexer.lex(str));
    }

    @Test
    public void testLexASimpleFunctionWithBlancInFunctionName()
    {
        String str = "foo@bar.com@func23    ()";
        Lexer lexer = new Lexer();
        Node root = lexer.lex (str);
        Function function = (Function)root.getChildren().get(1);
        assertTrue("Function Name should be func23 instead : " + function.getFunctionName(),"func23".equals(function.getFunctionName()));
    }
    @Test
    public void testLexASimpleVariale()
    {
        String str = "${aVariable}";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Variable aVar = (Variable)root.getChildren().get(0);
        assertTrue("aVariable".equals(aVar.getKeyName()));
    }
    @Test
    public void testLexASimpleVarialeWithBlankCaratere()
    {
        String str = "${ aVariable }";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Variable aVar = (Variable)root.getChildren().get(0);
        assertTrue("Variable Key should be aVariable instead : " + aVar.getKeyName(),"aVariable".equals(aVar.getKeyName()));
    
        assertTrue("aVariable".equals(aVar.getKeyName()));
    }
    @Test
    public void testLexASimpleFunctionPlusAVariable()
    {
        String str = "@function(${aVariable}).bar";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertTrue("aVariable".equals(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertTrue("literal should be .bar instead of : " + literal.getValue(), ".bar".equals(literal.getValue()));
        assertTrue("",function.getFunctionClass() != null);
    }
    @Test
    public void testLexASimpleFunctionWithEqual()
    {
        String str = "@function(foo=${aVariable}).bar";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        Argument arg = function.getArguments().get(0);
        Variable aVar =(Variable)arg.getChildren().get(0);
        assertTrue("Parameter na should be foo instead of : " + arg.getName(), "foo".equals(arg.getName()));
        assertTrue("aVariable".equals(aVar.getKeyName()));
        Literal literal = (Literal)root.getChildren().get(1);
        assertTrue("literal should be .bar instead of : " + literal.getValue(), ".bar".equals(literal.getValue()));
        assertTrue("",function.getFunctionClass() != null);
    }
}