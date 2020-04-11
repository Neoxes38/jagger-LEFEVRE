package src;

/**
 * Write a description of class Num here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Num implements Expression{
    private Double value;

    public Num(final Double value){this.value = value;}
    public Double getValue(){return this.value;}
    public void accept(Visitor v){ v.visit(this); }
} // Num
