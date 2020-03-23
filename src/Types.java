package src;

import java.lang.reflect.Type;

public enum Types {
    NUM("src.Num"), STR("src.Str"), VOID(null);

    private String t;
    Types(String t) { this.t = t; }

    @Override
    public String toString() {
        return t;
    }
}
