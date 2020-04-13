package src;

/**
 * Write a description of class VisitorEvaluation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorEvaluation implements Visitor {
    private Double resNum;
    private String resStr;
    private Types currentType;
    public VisitorTypeChecker typeChecker;

    public VisitorEvaluation() {
        typeChecker = new VisitorTypeChecker();
        currentType = Types.VOID;
    }

    public String getResult() {
        switch (currentType) {
            case NUM:
                return resNum.toString();
            case STR:
                return resStr;
            case VOID:
                return "";
        }
        return "Error";
    }

    @Override
    public void visit(Num n) {
        currentType = Types.NUM;
        resNum = n.getValue();
    }

    @Override
    public void visit(Str s) {
        currentType = Types.STR;
        resStr = s.getValue();
    }

    @Override
    public void visit(BinOp b) {
        if (b.op == BinarOperator.ASSIGN) {
            Var v = (Var) b.lex;
            b.rex.accept(this);

            switch (currentType) {
                case NUM:
                    v.d.e = new Num(this.resNum);
                    return;
                case STR:
                    v.d.e = new Str(this.resStr);
                    return;
            }
        }

        b.accept(typeChecker);
        double d1 = 0.0, d2 = 0.0;
        String s1 = "", s2 = "";

        if (typeChecker.getType().equals(Types.NUM)) {
            b.lex.accept(this);
            d1 = this.resNum;
            b.rex.accept(this);
            d2 = this.resNum;
            this.currentType = Types.NUM;
        } else if (typeChecker.getType().equals(Types.STR)) {
            b.lex.accept(this);
            s1 = this.resStr;
            b.rex.accept(this);
            s2 = this.resStr;
            this.currentType = Types.STR;
        }

        switch (b.op) {
            case PLUS:
                if (typeChecker.getType().equals(Types.NUM)) {
                    this.resNum = d1 + d2;
                } else if (typeChecker.getType().equals(Types.STR)) {
                    this.resStr = s1 + s2;
                }
                break;
            case MINUS:
                this.resNum = d1 - d2;
                break;
            case MULT:
                this.resNum = d1 * d2;
                break;
            case DIV:
                this.resNum = d1 / d2;
                break;
            case AND:
                this.resNum = d1 >= 1.0 && d2 >= 1.0 ? 1.0 : 0.0;
                break;
            case OR:
                this.resNum = d1 >= 1.0 || d2 >= 1.0 ? 1.0 : 0.0;
                break;
        }
    }

    @Override
    public void visit(Relation r) {
        r.accept(typeChecker);
        double d1, d2;
        if (typeChecker.getType().equals(Types.NUM)) {
            r.lex.accept(this);
            d1 = this.resNum;
            r.rex.accept(this);
            d2 = this.resNum;
        } else {
            r.lex.accept(this);
            d1 = this.resStr.length();
            r.rex.accept(this);
            d2 = this.resStr.length();
        }

        switch (r.op) {
            case EQ:
                this.resNum = d1 == d2 ? 1.0 : 0.0;
                break;
            case INF:
                this.resNum = d1 < d2 ? 1.0 : 0.0;
                break;
            case SUP:
                this.resNum = d1 > d2 ? 1.0 : 0.0;
                break;
            case INF_EQ:
                this.resNum = d1 <= d2 ? 1.0 : 0.0;
                break;
            case SUP_EQ:
                this.resNum = d1 >= d2 ? 1.0 : 0.0;
                break;
        }

        this.currentType = Types.NUM;
    }

    @Override
    public void visit(Not n) {
        this.currentType = Types.NUM;
        n.ex.accept(this);
        this.resNum = this.resNum != 0.0 ? 0.0 : 1.0;
    }

    @Override
    public void visit(Print p) {
        p.ex.accept(this);
        System.out.println(this.getResult());
        this.currentType = Types.VOID;
    }

    @Override
    public void visit(TernOp t) {
        t.accept(typeChecker);
        t.ifEx.accept(this);
        if (this.resNum != 0.0)
            t.thenEx.accept(this);
        else
            t.elseEx.accept(this);

        if (typeChecker.getType().equals(Types.NUM))
            this.currentType = Types.NUM;
        else if (typeChecker.getType().equals(Types.STR))
            this.currentType = Types.STR;
        else
            this.currentType = Types.VOID;
    }

    @Override
    public void visit(VarDecl v) {
        v.accept(this.typeChecker);
        if (typeChecker.getType().equals(Types.NUM))
            v.type = Types.NUM;
        else if (typeChecker.getType().equals(Types.STR))
            v.type = Types.STR;
    }

    @Override
    public void visit(Var v) {
        this.currentType = v.d.type;
        v.d.e.accept(this);
    }

    @Override
    public void visit(Scope s) {
        for (VarDecl v : s.vars.values())
            v.accept(this);

        for (Expression e : s.instrs)
            e.accept(this);
    }

    @Override
    public void visit(While w) {
        w.cond.accept(this);
        while (resNum>0) {
            for(Expression e : w.instrs)
                e.accept(this);
            w.cond.accept(this);
        }
    }
}// VisitorEvaluation
