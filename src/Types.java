package src;

public enum Types {
    NUM("Num"), STR("Str"), VOID("Void"), VAR("Var");

    private String t;
    Types(String t) { this.t = t; }

    @Override
    public String toString() {
        return t;
    }

    public static Types fromObject(Object o) {
        for (Types type : Types.values()) {
            if (type.t.equalsIgnoreCase(o.getClass().getSimpleName())) {
                return type;
            }
        }
        return null;
    }
}
