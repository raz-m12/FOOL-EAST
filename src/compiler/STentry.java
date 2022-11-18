package compiler;

import compiler.lib.*;

import compiler.lib.Visitable;

public class STentry implements Visitable {
	int nl;
	public STentry(int n) {nl = n;}

	@Override
	public <S> S accept(BaseASTVisitor<S> visitor) {
		return ((BaseEASTVisitor<S>) visitor).visitSTentry(this);
	}
}













//@Override
//public <S> S accept(BaseASTVisitor<S> visitor) {
//	return ((BaseEASTVisitor<S>) visitor).visitSTentry(this);
//}