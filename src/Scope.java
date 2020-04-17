package src;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Scope implements Expression {
    private final Map<String, VarDecl> vars;
    private final List<Expression> instrs;

    public Scope() {
        this.vars = new LinkedHashMap<>(0);
        this.instrs = new ArrayList<>(1);
    }

    public Map<String, VarDecl> getVars() {
        return vars;
    }
    public List<Expression> getInstrs() {
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
