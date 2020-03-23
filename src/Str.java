package src;

public class Str implements Expression {
    String value;

    public Str(String s){ this.value = s.replace("\"",""); }
    public String getValue(){ return this.value; }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
