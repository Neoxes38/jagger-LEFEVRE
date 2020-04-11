package src;

public enum Types {
    NUM("Num"), STR("Str"), VOID("Void");

    private String t;
    Types(String t) { this.t = t; }

    @Override
    public String toString() {
        return t;
    }

    public static Types fromString(String st) {
        for (Types type : Types.values()) {
            if (type.t.equalsIgnoreCase(st)) {
                return type;
            }
        }
        return null;
    }
}
