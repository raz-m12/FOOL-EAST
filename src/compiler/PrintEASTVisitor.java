package compiler;

import compiler.AST.*;
import compiler.lib.*;

public class PrintEASTVisitor extends BaseEASTVisitor<Void> {

	PrintEASTVisitor() { super(true); }

	@Override
	public Void visitNode(ProgNode n) {
		printNode(n);
		visit(n.exp);
		return null;
	}
	
	@Override
	public Void visitNode(IntNode n) {
		printNode(n, n.val.toString());
		return null;
	}

	@Override
	public Void visitNode(PlusNode n) {
		printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(TimesNode n) {
		printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(EqualNode n) {
		printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(BoolNode n) {
		printNode(n, n.val.toString());
		return null;
	}


	@Override
	public Void visitNode(IfNode n) {
		printNode(n);
		visit(n.cond);
		visit(n.th);
		visit(n.el);
		return null;
	}
	
	@Override
	public Void visitNode(PrintNode n) {
		printNode(n);
		visit(n.exp);
		return null;
	}

//	
	@Override
	public Void visitNode(ProgLetInNode n) {
		printNode(n);
		for (Node dec : n.declist) visit(dec);
		visit(n.exp);
		return null;
	}

	@Override
	public Void visitNode(BoolTypeNode n) {
		printNode(n);
		return null;
	}

	@Override
	public Void visitNode(IntTypeNode n) {
		printNode(n);
		return null;
	}

	@Override
	public Void visitNode(VarNode n) {
		printNode(n,n.id);
		visit(n.type);
		visit(n.exp);
		return null;
	}

	@Override
	public Void visitNode(FunNode n) {
		printNode(n,n.id);
		visit(n.retType);
		// for (ParNode par : n.parlist) visit(par);
		for (Node dec : n.declist) visit(dec);
		visit(n.exp);
		return null;
	}
	
	@Override
	public Void visitNode(IdNode n) {
		printNode(n,n.id);
		if(n.entry != null) visit(n.entry);
		// visit(n.entry);
		return null;
	}

	@Override
	public Void visitNode(CallNode n) {
		printNode(n,n.id);
		if(n.entry != null) visit(n.entry);
		// for (Node arg : n.arglist) visit(arg);
		return null;
	}

	@Override
	public Void visitSTentry(STentry entry) {
		printSTentry("nestlev "+entry.nl);
		return null;
	}
}



