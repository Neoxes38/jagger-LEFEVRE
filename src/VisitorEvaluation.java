package src;

/**
 * Write a description of class VisitorEvaluation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorEvaluation implements Visitor<Double> {
    private Double resNum;
    private String resStr;
    private Types currentType;

    VisitorTypeChecker typeChecker;

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
    public Double visit(Num n) {
        currentType = Types.NUM;
        resNum = n.getValue();
        return n.getValue();
    }

    @Override
    public Double visit(Str s) {
        currentType = Types.STR;
        resStr = s.getValue();
        return 0.0;
    }

    @Override
    public Double visit(BinOp b) {
        String type = b.accept(typeChecker);
        double d1 = 0.0, d2 = 0.0;
        String s1 = "", s2 = "";

        if (type.equals(Types.NUM.toString())) {
            d1 = b.lex.accept(this);
            d2 = b.rex.accept(this);
            this.currentType = Types.NUM;
        } else if (type.equals(Types.STR.toString())) {
            b.lex.accept(this);
            s1 = this.resStr;
            b.rex.accept(this);
            s2 = this.resStr;
            this.currentType = Types.STR;
        }

        switch (b.op) {
            case PLUS:
                if (type.equals(Types.NUM.toString())) {
                    this.resNum = d1 + d2;
                } else if (type.equals(Types.STR.toString())) {
                    this.resStr = s1 + s2;
                }
                break;
            case MINUS:
                this.resNum = d1 - d2;
            case MULT:
                this.resNum = d1 * d2;
            case DIV:
                this.resNum = d1 / d2;
            case AND:
                this.resNum = d1 > 1.0 && d1 > 1.0 ? 1.0 : 0.0;
            case OR:
                this.resNum = d1 > 1.0 || d2 > 1.0 ? 1.0 : 0.0;
        }

        return 0.0;
    }

    @Override
    public Double visit(Relation r) {
        String type = r.accept(typeChecker);
        double d1, d2;
        if (type.equals(Types.NUM.toString())) {
            d1 = r.lex.accept(this);
            d2 = r.rex.accept(this);
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
        return -1.0;
    }

    @Override
    public Double visit(Not n) {
        this.currentType = Types.NUM;
        this.resNum = n.ex.accept(this) != 0.0 ? 1.0 : 0.0;
        return 0.0;
    }

    @Override
    public Double visit(Print p) {
        p.ex.accept(this);
        System.out.println(this.getResult());
        this.currentType = Types.VOID;
        return 0.0;
    }

    @Override
    public Double visit(TernOp t) {
        String type = t.accept(typeChecker);
        double b = t.ifEx.accept(this);
        if (b != 0.0)
            t.thenEx.accept(this);
        else
            t.elseEx.accept(this);

        if (type.equals("src.Num"))
            this.currentType = Types.NUM;
        else if (type.equals("src.Str"))
            this.currentType = Types.STR;
        else
            this.currentType = Types.VOID;

        return 0.0;
    }
}// VisitorEvaluation
