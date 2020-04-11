package src;

public class VisitorTypeChecker implements Visitor {
    private boolean hasError;
    private String error;
    private Types type;

    public VisitorTypeChecker(){
        this.hasError = false;
        this.error = "";
    }

    public boolean hasError(){ return this.hasError; }
    public String getError(){ return this.error; }
    public Types getType(){ return this.type; }

    private void buildError(Types... s) {
        StringBuilder res = new StringBuilder();
        res.append("Error: Invalid type: ");
        for (Types type : s)
            res.append(type).append(" ");
        this.error = res.toString();
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
        b.rex.accept(this);
        hasError = !t1.equals(this.type);
        if(hasError)
            buildError(t1, this.type);
    }

    @Override
    public void visit(Relation r) {
        Types t1;
        r.lex.accept(this);
        t1 = this.type;
        r.rex.accept(this);
        hasError = !t1.equals(this.type);
        if(hasError)
            buildError(t1, this.type);
    }

    @Override
    public void visit(Not n) {
        n.ex.accept(this);
    }

    @Override
    public void visit(Print p) {
        this.type = Types.VOID;
    }

    @Override
    public void visit(TernOp t) {
        Types tmp;
        t.ifEx.accept(this);

        hasError = !this.type.equals(Types.NUM) ;
        if(hasError) {
            buildError(Types.NUM, this.type);
            return;
        }

        t.thenEx.accept(this);
        tmp = this.type;
        t.elseEx.accept(this);

        hasError = !tmp.equals(this.type);
        if(hasError)
            buildError(tmp, this.type);
    }

    @Override
    public void visit(VarDecl v) {
        v.e.accept(this);
        v.type = this.type;
    }

    @Override
    public void visit(Var v) {
        this.type = v.d.type;
    }

    @Override
    public void visit(Scope s) {
        for(VarDecl v : s.vars.values()) {
            v.e.accept(this);
            v.type = this.type;
        }

        for(Expression e : s.instr)
            e.accept(this);
    }
} // VisitorTypeChecker
