
/**
 * Write a description of class No here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class No implements Expression
{
    Expression ex;
    
    public No(final Expression ex){ this.ex = ex; }   
    @Override
    public <T> T accept(Visitor<T> v){ return v.visit(this); } 
}
