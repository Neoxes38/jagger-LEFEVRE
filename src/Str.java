package src;

public class Str implements Expression {
    private final String value;

    public Str(String s) { this.value = format(s); }

    public String getValue() { return this.value; }

    private static String format(String s) {
        return s.replace("\\n", "\n")
                .replace("\\t", "\t")
                .replace("\\\"", "\"");
    }

    @Override
    public void accept(Visitor v) { v.visit(this); }
} // Str
