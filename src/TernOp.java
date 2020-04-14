package src;

public class TernOp implements Expression {
    private final Expression ifEx;
    private final Expression thenEx;
    private final Expression elseEx;

    public TernOp(Expression ifEx, Expression thenEx, Expression elseEx) {
        this.ifEx = ifEx;
        this.thenEx = thenEx;
        this.elseEx = elseEx;
    }

    public Expression getIfEx() { return ifEx; }
    public Expression getThenEx() { return thenEx; }
    public Expression getElseEx() { return elseEx; }

    public void accept(Visitor v){ v.visit(this); }
} // TernOp
