package src;

/**
 * Write a description of class Binop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BinOp implements Expression
{
    public Expression lex;
    public Expression rex;
    public BinarOperator op;   

    public BinOp(Expression lex, Expression rex, BinarOperator op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }
    
    public void accept(Visitor v){ v.visit(this); }
} // Binop
