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
        if (b.getOp() == BinarOperator.ASSIGN) {
            Var v = (Var) b.getLex();
            b.getRex().accept(this);
            //TODO: take a decision => evaluate or not
            switch (currentType) {
                case NUM:
                    v.getDecl().setEx(new Num(this.resNum));
                    return;
                case STR:
                    v.getDecl().setEx(new Str(this.resStr));
                    return;
            }
        }

        b.accept(typeChecker);
        double d1 = 0.0, d2 = 0.0;
        String s1 = "", s2 = "";

        if (typeChecker.getType().equals(Types.NUM)) {
            b.getLex().accept(this);
            d1 = this.resNum;
            b.getRex().accept(this);
            d2 = this.resNum;
            this.currentType = Types.NUM;
        } else if (typeChecker.getType().equals(Types.STR)) {
            b.getLex().accept(this);
            s1 = this.resStr;
            b.getRex().accept(this);
            s2 = this.resStr;
            this.currentType = Types.STR;
        }

        switch (b.getOp()) {
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
            r.getLex().accept(this);
            d1 = this.resNum;
            r.getRex().accept(this);
            d2 = this.resNum;
        } else {
            r.getLex().accept(this);
            d1 = this.resStr.length();
            r.getRex().accept(this);
            d2 = this.resStr.length();
        }

        switch (r.getOp()) {
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
        n.getEx().accept(this);
        this.resNum = this.resNum != 0.0 ? 0.0 : 1.0;
    }

    @Override
    public void visit(Print p) {
        p.getEx().accept(this);
        System.out.println(this.getResult());
        this.currentType = Types.VOID;
    }

    @Override
    public void visit(TernOp t) {
        t.accept(typeChecker);
        t.getIfEx().accept(this);
        if (this.resNum != 0.0)
            t.getThenEx().accept(this);
        else
            t.getElseEx().accept(this);

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
            v.setType(Types.NUM);
        else if (typeChecker.getType().equals(Types.STR))
            v.setType(Types.STR);
    }

    @Override
    public void visit(Var v) {
        this.currentType = v.getDecl().getType();
        v.getDecl().getEx().accept(this);
    }

    @Override
    public void visit(Scope s) {
        for (VarDecl v : s.getVars().values())
            v.accept(this);

        for (Expression e : s.getInstrs())
            e.accept(this);
    }

    @Override
    public void visit(While w) {
        for (VarDecl v : w.getVars().values())
            v.accept(this);
        w.getCond().accept(this);
        while (resNum>0) {
            for(Expression e : w.getInstrs())
                e.accept(this);
            w.getCond().accept(this);
        }
    }
}// VisitorEvaluation
