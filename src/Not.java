package src;

/**
 * Write a description of class No here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Not implements Expression{
    private final Expression ex;
    
    public Not(final Expression ex){ this.ex = ex; }

    public Expression getEx() { return ex; }

    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Not
