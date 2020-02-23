
/**
 * Write a description of class BoolOp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BoolOp implements Expression
{
    Expression lex, rex;
    BoolOperator op;
    
    public BoolOp(final Expression lex, final Expression rex, final BoolOperator op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }

    public <T> T accept(Visitor<T> v){ return v.visit(this); } 
}
