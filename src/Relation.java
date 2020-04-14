package src;

/**
 * Write a description of class Relation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Relation implements Expression{
    private final Expression lex;
    private final Expression rex;
    private final RelationOperator op;
    
    public Relation(final Expression lex, final Expression rex, final RelationOperator op){
        this.lex = lex;
        this.rex = rex;
        this.op = op;
    }

    public Expression getLex() { return lex; }
    public Expression getRex() { return rex; }
    public RelationOperator getOp() { return op; }

    public void accept(Visitor v){ v.visit(this); }
}
