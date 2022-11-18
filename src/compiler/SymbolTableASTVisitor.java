package compiler;

import java.util.*;
import java.util.Map;

import compiler.AST.*;
import compiler.lib.*;
import compiler.lib.Node;

// Top down - creo il symbol table e decoro l'albero di natale
// Quando vado bottom-down devo verificare gli errori

public class SymbolTableASTVisitor extends BaseASTVisitor<Void> {
	
	int stErrors=0;
	private List<Map<String, STentry>> symTable = new ArrayList<>();
	private int nestingLevel=0;
	//livello ambiente con dichiarazioni piu' esterno e' 0 (prima posizione ArrayList) invece che 1 (slides)
	//il "fronte" della lista di tabelle e' symTable.get(nestingLevel)

	SymbolTableASTVisitor() {}
	SymbolTableASTVisitor(boolean debug) {super(debug);} // p=true enables print for debugging

	@Override
	public Void visitNode(ProgNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
	}
	
	@Override
	public Void visitNode(IntNode n) {
		if (print) printNode(n, n.val.toString());
		return null;
	}
	
	@Override
	public Void visitNode(PlusNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(TimesNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(EqualNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}
	
	@Override
	public Void visitNode(BoolNode n) {
		if (print) printNode(n, n.val.toString());
		return null;
	}
	
	@Override
	public Void visitNode(IfNode n) {
		if (print) printNode(n);
		visit(n.cond);
		visit(n.th);
		visit(n.el);
		return null;
	}
	
	@Override
	public Void visitNode(PrintNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
	}

//
	@Override
	public Void visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		Map<String, STentry> hm = new HashMap<>();
		symTable.add(hm);

		for (Node dec : n.declist) visit(dec);
		visit(n.exp);

		symTable.remove(0);
		return null;
	}

	@Override
	public Void visitNode(VarNode n) {
		if (print) printNode(n,n.id);
		visit(n.exp);
		// se lo aggiungo prima di visit posso fare e.g. int x = x + 5;
		Map<String, STentry> hm = symTable.get(nestingLevel);

		STentry entry = new STentry(nestingLevel);
		// se true, vuol dire che c'era già-
		if(hm.put(n.id, entry) != null) {
			System.out.println("Var id " + n.id + " at line "+ n.getLine() +" already declared");
			stErrors++;
		}

		return null;
	}

	@Override
	public Void visitNode(FunNode n) {
		if (print) printNode(n,n.id);

		Map<String, STentry> hm = symTable.get(nestingLevel);

		STentry entry = new STentry(nestingLevel);
		// se true, vuol dire che c'era già-
		if(hm.put(n.id, entry) != null) {
			System.out.println("Fun id " + n.id + " at line "+ n.getLine() +" already declared");
			stErrors++;
		}

		// entro in un nuovo scope
		nestingLevel++;
		Map<String, STentry> hmn = new HashMap<>();
		symTable.add(hmn);


		// for (ParNode par : n.parlist) visit(par);
		for (Node dec : n.declist) visit(dec);
		visit(n.exp);

		symTable.remove(nestingLevel--);
		return null;
	}

	private STentry stLookup(String id) {
		int j = nestingLevel;
		STentry entry = null;
		while(j >= nestingLevel && entry == null) {
			entry = symTable.get(j--).get(id);
		}
		return entry;
	}
	
	@Override
	public Void visitNode(IdNode n) {
		if (print) printNode(n);
		STentry entry = stLookup(n.id);
		if(entry == null) {
			System.out.println("Var id " + n.id + " at line "+ n.getLine() +" not declared");
			stErrors++;
		} else {
			n.entry = entry;
		}
		return null;
	}

	@Override
	public Void visitNode(CallNode n) {
		if (print) printNode(n);
		STentry entry = stLookup(n.id);
		if(entry == null) {
			System.out.println("Fun id " + n.id + " at line "+ n.getLine() +" not declared");
			stErrors++;
		} else {
			n.entry = entry;
		}
		// for (Node arg : n.arglist) visit(arg);
		return null;
	}
}

//		Map<String, STentry> hm = new HashMap<>();
//		symTable.add(hm);

//		STentry entry = new STentry(nestingLevel);
//		hm.put(n.id, entry)

//			System.out.println("Var id " + n.id + " at line "+ n.getLine() +" already declared");

//	int j = nestingLevel;

//	STentry entry = null;

//		entry = symTable.get(j--).get(id);