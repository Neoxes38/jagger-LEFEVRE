package src;

import java.util.Objects;

public class VisitorTypeChecker extends AbstractVisitorError {
    private Types type;

    public Types getType(){ return this.type; }

    public void buildError(Types expected, Types found) {
        setError("Invalid type: \"" + expected +"\" was expected but \"" + found + "\" was found.");
    }

    public void buildError(Types found) {
        setError("Invalid type: \"" + found + "\" is invalid here.");
    }

    @Override
    public void visit(Num n) {
        this.type = Types.NUM;
    }

    @Override
    public void visit(Str s) {
        this.type = Types.STR;
    }

    @Override
    public void visit(BinOp b) {
        Types t1;
        b.lex.accept(this);
        t1 = this.type;
        if(b.op.equals(BinarOperator.ASSIGN)&&!Objects.equals(Types.fromObject(b.lex), Types.VAR))
            buildError(Types.VAR, t1);
        b.rex.accept(this);
        if(!t1.equals(this.type))
            buildError(t1, this.type);
    }

    @Override
    public void visit(Relation r) {
        Types t1;
        r.lex.accept(this);
        t1 = this.type;
        r.rex.accept(this);
        if(!t1.equals(this.type))
            buildError(t1, this.type);
    }

    @Override
    public void visit(Not n) {
        n.ex.accept(this);
        if(!this.type.equals(Types.NUM))
            buildError(Types.NUM, this.type);
    }

    @Override
    public void visit(Print p) {
        this.type = Types.VOID;
    }

    @Override
    public void visit(TernOp t) {
        Types tmp;
        t.ifEx.accept(this);

        if(!this.type.equals(Types.NUM)) {
            buildError(Types.NUM, this.type);
            return;
        }

        t.thenEx.accept(this);
        tmp = this.type;
        t.elseEx.accept(this);

        if(!tmp.equals(this.type))
            buildError(tmp, this.type);
    }

    @Override
    public void visit(VarDecl v) {
        v.e.accept(this);
        if(this.type.equals(Types.VOID))
            buildError(this.type);
        v.type = this.type;
    }

    @Override
    public void visit(Var v) {
        this.type = v.d.type;
    }

    @Override
    public void visit(Scope s) {
        for(VarDecl v : s.vars.values()) {
            v.accept(this);
        }

        for(Expression e : s.instrs)
            e.accept(this);
    }

    @Override
    public void visit(While w) {
        for(VarDecl v : w.vars.values()) {
            v.accept(this);
        }
        w.cond.accept(this);
        if(!this.type.equals(Types.NUM)) {
            buildError(Types.NUM, this.type);
            return;
        }

        for(Expression e : w.instrs)
            e.accept(this);
    }
} // VisitorTypeChecker
