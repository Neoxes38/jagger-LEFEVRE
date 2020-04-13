package src;

/**
 * Write a description of class No here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Not implements Expression{
    Expression ex;
    
    public Not(final Expression ex){ this.ex = ex; }

    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Not
