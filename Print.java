public class Print implements Expression{
    Expression ex;

    public Print(Expression ex){ this.ex = ex; }
    @Override
    public <T> T accept(Visitor<T> v) { return v.visit(this); }
}// Print
