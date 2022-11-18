package compiler;

import compiler.lib.*;

public class STentry implements Visitable {
	int nl;
	public STentry(int n) {nl = n;}

	@Override
	public <S> S accept(BaseASTVisitor<S> visitor) {
		return ((BaseEASTVisitor<S>) visitor).visitSTentry(this);
	}
}
