package src;

public class RelOp implements Expression{
    private final Expression lex;
    private final Expression rex;
    private final RelOr op;
    
    public RelOp(final Expression lex, final Expression rex, final RelOr op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }

    public Expression getLex() { return lex; }
    public Expression getRex() { return rex; }
    public RelOr getOp() { return op; }

    public void accept(Visitor v){ v.visit(this); }
} // RelOp
