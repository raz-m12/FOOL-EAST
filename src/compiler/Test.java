package compiler;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import compiler.lib.*;

public class Test {
    public static void main(String[] args) throws Exception {
   			
        String fileName = "esempio.fool";

        CharStream chars = CharStreams.fromFileName(fileName);
        FOOLLexer lexer = new FOOLLexer(chars);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FOOLParser parser = new FOOLParser(tokens);

        System.out.println("Generating ST via lexer and parser.");
        ParseTree st = parser.prog();
        System.out.println("You had: "+lexer.lexicalErrors+" lexical errors and "
                + parser.getNumberOfSyntaxErrors()+" syntax errors.");

        System.out.println("Generating AST.");
        ASTGenerationSTVisitor visitor = new ASTGenerationSTVisitor(true); // use true to visualize the ST
        Node ast = visitor.visit(st);
        System.out.println("");

        System.out.println("Enriching AST via symbol table.");
        SymbolTableASTVisitor symtableVisitor = new SymbolTableASTVisitor(true); // use true to visualize the AST
        symtableVisitor.visit(ast);
        System.out.println("You had: "+symtableVisitor.stErrors+" symbol table errors.\n");
        
        System.out.println("Visualizing Enriched AST.");
        new PrintEASTVisitor().visit(ast);
    }
}
