package org.accelerate.tdm.smartdata.rules.engine.lexer;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class  MultipleArgumentsFunctionTest {

    @Test   
    public void testOneLiteralArgumentFuntion()
    {
        String str = "@function(arg1=foo)";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue("function".equals(function.getFunctionName()));
        Argument arg = function.getArguments().get(0);
        Literal literal1 =(Literal)arg.getChildren().get(0);
        assertTrue("foo".equals(literal1.getValue()));
        assertTrue("arg1".equals(arg.getName()));
    }     
    
    @Test   
    public void testTwoLiteralArgumentFuntion()
    {
        String str = " @function  (  arg1 =foo  , arg2= bar)";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(1);
        assertTrue("function".equals(function.getFunctionName()));
        
        Argument arg1 = function.getArguments().get(0);
        Literal literal1 =(Literal)arg1.getChildren().get(0);
        assertTrue("foo".equals(literal1.getValue()));
        assertTrue("arg1".equals(arg1.getName()));
       
        Argument arg2 = function.getArguments().get(1);
        Literal literal2 =(Literal)arg2.getChildren().get(0);
        assertTrue("bar".equals(literal2.getValue()));
        assertTrue("arg2".equals(arg2.getName()));
    }     
    @Test   
    public void testOneVarialbelArgumentFuntion()
    {
        String str = "@function(arg1=${foo}.bar)";
        Lexer lexer = new Lexer();
        Node root = lexer.lex(str);
        Function function = (Function)root.getChildren().get(0);
        assertTrue("function".equals(function.getFunctionName()));
        
        Argument arg = function.getArguments().get(0);
        Variable variable =(Variable)arg.getChildren().get(0);
        assertTrue("foo".equals(variable.getKeyName()));
        Literal literal =(Literal)arg.getChildren().get(1);
        assertTrue(".bar".equals(literal.getValue()));
    }     
    
}