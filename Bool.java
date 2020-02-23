
/**
 * Write a description of class Boolean here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bool implements Expression
{
    Boolean value;
    
    public Bool(final boolean b){ this.value= b; }
    public Bool(final Boolean b){ this.value = b; }
    public Boolean getValue(){return this.value;}
    public <T> T accept(Visitor<T> v){ return v.visit(this); }
}
