package src;

public enum Types {
    NUM("Num"), STR("Str"), VOID("Void"), VAR("Var");

    private final String t;
    Types(String t) { this.t = t; }

    @Override
    public String toString() {
        return t;
    }
} // Types
