/**
 * Write a description of class Unop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Unop extends Expression
{
    public Expression ex;
    public String op;

    public Unop(Expression ex, String op){
        this.ex = ex;
        this.op = op;
    }
    
    public <T> T accept(Visitor<T> v){
        return v.visit(this);        
    }
    
    public String getOperator(){ return op; }
} // Unop
