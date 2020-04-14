package src;

public class Print implements Expression{
    private final Expression ex;

    public Print(Expression ex){ this.ex = ex; }

    public Expression getEx() { return ex; }

    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Print
