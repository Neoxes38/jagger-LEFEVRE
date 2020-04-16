package src;

/**
 * Write a description of class Binop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BinOp implements Expression
{
    private final Expression lex;
    private final Expression rex;
    private final BinOr op;

    public BinOp(Expression lex, Expression rex, BinOr op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }

    public Expression getLex() { return lex; }
    public Expression getRex() { return rex; }
    public BinOr getOp() { return op; }
    
    public void accept(Visitor v){ v.visit(this); }

} // Binop
