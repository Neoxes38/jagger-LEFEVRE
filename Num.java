/**
 * Write a description of class Num here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Num extends Expression
{
    private Double value;

    public Num(final Double value){this.value = value;}
    public Double getValue(){return this.value;}
    public <T> T accept(Visitor<T> v){
        return v.visit(this);       
    }
} // Num
