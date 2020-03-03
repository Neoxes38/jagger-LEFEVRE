package src;

/**
 * Write a description of class Relation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Relation implements Expression
{
    Expression lex, rex;
    RelationOperator op;
    
    public Relation(final Expression lex, final Expression rex, final RelationOperator op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }
    public <T> T accept(Visitor<T> v){ return v.visit(this); } 
}
