package src;

import java.util.HashMap;
import java.util.Stack;

public class VisitorBinder extends AbstractVisitorError {
    private final Stack<HashMap<String, VarDecl>> envs;

    public VisitorBinder() {
        this.envs = new Stack<>();
    }

    @Override
    public void visit(Num n) {
    }

    @Override
    public void visit(Str s) {
    }

    @Override
    public void visit(BinOp b) {
        b.getLex().accept(this);
        b.getRex().accept(this);
    }

    @Override
    public void visit(RelOp r) {
        r.getLex().accept(this);
        r.getRex().accept(this);
    }

    @Override
    public void visit(Not n) {
        n.getEx().accept(this);
    }

    @Override
    public void visit(Print p) {
        p.getEx().accept(this);
    }

    @Override
    public void visit(TernOp t) {
        t.getIfEx().accept(this);
        t.getThenEx().accept(this);
        t.getElseEx().accept(this);
    }

    @Override
    public void visit(VarDecl v) {
        v.getEx().accept(this);
        this.envs.peek().put(v.getId(), v);
    }

    @Override
    public void visit(Var v) {
        boolean hit = false;

        for (int i = this.envs.size() - 1; i >= 0; i--)
            if (this.envs.get(i).containsKey(v.getId())) {
                v.setDecl(this.envs.get(i).get(v.getId()));
                hit = true;
                break;
            }

        if (!hit) {
            setError("Undefined variable: Var \"" + v.getId() + "\" is not defined.");
        }
    }

    @Override
    public void visit(Scope s) {
        this.envs.push(new HashMap<>(0));

        for (VarDecl v : s.getVars().values())
            v.accept(this);
        for (Expression e : s.getInstrs())
            e.accept(this);

        this.envs.pop();
    }

    @Override
    public void visit(While w) {
        this.envs.push(new HashMap<>(0));

        for (VarDecl v : w.getVars().values())
            v.accept(this);
        w.getCond().accept(this);
        for (Expression e : w.getInstrs())
            e.accept(this);

        this.envs.pop();
    }
} // VisitorBinder
