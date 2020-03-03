package src;

public class TernOp implements Expression {
    Expression ifEx, thenEx, elseEx;

    public TernOp(Expression ifEx, Expression thenEx, Expression elseEx) {
        this.ifEx = ifEx;
        this.thenEx = thenEx;
        this.elseEx = elseEx;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}// TernOp
