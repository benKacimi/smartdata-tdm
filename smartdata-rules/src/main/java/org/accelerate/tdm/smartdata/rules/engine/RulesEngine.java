package org.accelerate.tdm.smartdata.rules.engine;

import org.accelerate.tdm.smartdata.rules.engine.lexer.Lexer;
import org.accelerate.tdm.smartdata.rules.engine.lexer.Node;

public class RulesEngine {
    
    protected Lexer compilator = new Lexer();

    public String execute(final String instructionLine){

        if (instructionLine == null || "".equals(instructionLine))
            return instructionLine;
        
        Node root = compilator.lex(instructionLine);
        return root.apply();
       
    }
}
