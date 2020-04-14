package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope implements Expression {
    private final HashMap<String, VarDecl> vars;
    private final ArrayList<Expression> instrs;

    public Scope() {
        this.vars = new HashMap<>();
        this.instrs = new ArrayList<>();
    }

    public HashMap<String, VarDecl> getVars() {
        return vars;
    }
    public ArrayList<Expression> getInstrs() {
        return instrs;
    }

    public void addVar(String id, VarDecl d) {
        if (!this.vars.containsKey(id))
            this.vars.put(id, d);
        else
            throw new RedefineException(id);
    }
    public void addInstr(Expression e) {
        this.instrs.add(e);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
} // Scope
