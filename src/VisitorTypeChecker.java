package src;

public class VisitorTypeChecker extends AbstractVisitorError {
    private Types type;

    public Types getType(){ return this.type; }

    public void buildError(Types expected, Types found) {
        setError("Invalid type: \"" + expected +"\" was expected but \"" + found + "\" was found.");
    }

    public void buildError(Types found) {
        setError("Invalid type: \"" + found + "\" is invalid here.");
    }

    public void buildError(BinOr operator, Types found) {
        setError("Invalid type: operator \"" + operator + "\" cannot be applied on \"" + found + "\".");
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
        b.getLex().accept(this);
        t1 = this.type;
        if(b.getOp().equals(BinOr.ASSIGN)&&!(b.getLex() instanceof Var))
            buildError(Types.VAR, t1);

        if((t1.equals(Types.STR)&&!(b.getLex() instanceof Var))&&!b.getOp().equals(BinOr.PLUS))
            buildError(b.getOp(), this.type);

        b.getRex().accept(this);
        if(!t1.equals(this.type))
            buildError(t1, this.type);
    }

    @Override
    public void visit(RelOp r) {
        Types t1;
        r.getLex().accept(this);
        t1 = this.type;
        r.getRex().accept(this);
        if(!t1.equals(this.type))
            buildError(t1, this.type);
    }

    @Override
    public void visit(Not n) {
        n.getEx().accept(this);
        if(!this.type.equals(Types.NUM))
            buildError(Types.NUM, this.type);
    }

    @Override
    public void visit(Print p) {
        p.getEx().accept(this);
        if(this.type.equals(Types.VOID))
            buildError(this.type);
        this.type = Types.VOID;
    }

    @Override
    public void visit(TernOp t) {
        Types tmp;
        t.getIfEx().accept(this);

        if(!this.type.equals(Types.NUM)) {
            buildError(Types.NUM, this.type);
            return;
        }

        t.getThenEx().accept(this);
        tmp = this.type;
        t.getElseEx().accept(this);

        if(!tmp.equals(this.type))
            buildError(tmp, this.type);
    }

    @Override
    public void visit(VarDecl v) {
        v.getEx().accept(this);
        if(this.type.equals(Types.VOID))
            buildError(this.type);
        v.setType(this.type);
    }

    @Override
    public void visit(Var v) {
        this.type = v.getDecl().getType();
    }

    @Override
    public void visit(Scope s) {
        for(VarDecl v : s.getVars().values()) {
            v.accept(this);
        }

        for(Expression e : s.getInstrs())
            e.accept(this);
    }

    @Override
    public void visit(While w) {
        for(VarDecl v : w.getVars().values()) {
            v.accept(this);
        }
        w.getCond().accept(this);
        if(!this.type.equals(Types.NUM)) {
            buildError(Types.NUM, this.type);
            return;
        }

        for(Expression e : w.getInstrs())
            e.accept(this);
    }
} // VisitorTypeChecker
