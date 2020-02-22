/**
 * Write a description of class Binop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Binop extends Expression
{
    public Expression lex;
    public Expression rex;
    public Bop op;   

    public Binop(Expression lex, Expression rex, Bop op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }
    
    public <T> T accept(Visitor<T> v){
        return v.visit(this);        
    }
} // Binop
