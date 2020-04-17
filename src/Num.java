package src;

public class Num implements Expression{
    private final Double value;

    public Num(final Double value){this.value = value;}
    public Double getValue(){return this.value;}
    public void accept(Visitor v){ v.visit(this); }
} // Num
