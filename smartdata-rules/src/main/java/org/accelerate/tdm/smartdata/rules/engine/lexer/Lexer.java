package org.accelerate.tdm.smartdata.rules.engine.lexer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Lexer {

    private static Map<String,Node> compilationCache = new ConcurrentHashMap<String, Node>();
  
    public Node lex(final String lexem){
        String _lexem = lexem;

        if (_lexem == null)
            _lexem = "";
        Node root = compilationCache.get(_lexem);
        if (root == null){
            root = new Node();
            lex (_lexem,root);
            compilationCache.put(_lexem, root);
        }
        return root;
    }

    protected void lex(final String lexem, final Node parent){
        String current = "";
        
        if (lexem != null)
            current = lexem;
        if (!"".equals(current)) {
            switch (current.charAt(0)){
                case ('@'):
                    current = createEvalutedFunctionNode(parent,current);
                    break;
                case ('#'):
                    current = createNotEvalutedFunctionNode(parent,current);
                    break;
                case ('$'):
                    current = createVariableNode(parent,current);
                    break;
                default:
                    current = createLiteralNode(parent,current);
                    break;
            }
            lex(current,parent);
        } 
    }

    private String createNotEvalutedFunctionNode(final Node parent, final String lexem){
        return createFunctionNode(parent,lexem, false);
    }

    private String createEvalutedFunctionNode(final Node parent, final String lexem){
        return createFunctionNode(parent,lexem, true);
    }

    private String createFunctionNode(final Node parent, final String lexem, final boolean isEvaluated){
        Function functionNode = Function.createInstance(lexem);
        
        if (functionNode != null){
            functionNode.setEvaluated(isEvaluated);
            parent.addChild(functionNode);
            int annotationClassNameLenght = 0;
            if (functionNode.getFunctionAnnotationName() != null &&
                !"".equals(functionNode.getFunctionAnnotationName()))
                annotationClassNameLenght = functionNode.getFunctionAnnotationName().length()+1;
            String result = lexem.substring(annotationClassNameLenght+functionNode.getFunctionName().length()+2+functionNode.getParameter().length()+1,lexem.length());
            return result;
        }
        return createLiteralNode(parent, lexem);
    }

    private String createLiteralNode(final Node parent, final String lexem){
        Literal literal = Literal.createInstance(lexem);
        
        if (literal != null){
            parent.addChild(literal);
            return lexem.substring(literal.getValue().length(),lexem.length());
        }
        return null;
    }

    private String createVariableNode(final Node parent, final String lexem){
        Variable var = Variable.createInstance(lexem);
        
        if (var != null){
            parent.addChild(var);
            String result = lexem.substring(var.getKeyName().length()+3,lexem.length());
            var.setKeyName(var.getKeyName().trim());
            return result;
        }
        return createLiteralNode(parent,lexem);
    }
}
