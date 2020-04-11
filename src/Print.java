package src;

public class Print implements Expression{
    Expression ex;

    public Print(Expression ex){ this.ex = ex; }
    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Print
