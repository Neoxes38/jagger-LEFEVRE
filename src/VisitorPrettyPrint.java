package src;

/**
 * Write a description of class VisitorPrettyPrint here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorPrettyPrint implements Visitor {
    private int scopeCnt = 0;

    @Override
    public void visit(Num n) {
        System.out.print(n.getValue());
    }

    @Override
    public void visit(Str s) {
        System.out.print("\""+s.getValue()+"\"");
    }

    @Override
    public void visit(BinOp b) {
        System.out.print("(");
        b.getLex().accept(this);
        System.out.print(" " + b.getOp() + " ");
        b.getRex().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Relation r) {
        System.out.print("(");
        r.getLex().accept(this);
        System.out.print(" " + r.getOp() + " ");
        r.getRex().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Not n) {
        System.out.print("(NOT ");
        n.getEx().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Print p) {
        System.out.print("PRINT(");
        p.getEx().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(TernOp t) {
        System.out.print("IF");
        System.out.println();
        this.scopeCnt++;
        printTab(1);
        t.getIfEx().accept(this);
        System.out.println();
        printTab(0);
        System.out.print("THEN");
        System.out.println();
        printTab(1);
        t.getThenEx().accept(this);
        System.out.println();
        printTab(0);
        System.out.print("ELSE");
        System.out.println();
        printTab(1);
        t.getElseEx().accept(this);
        this.scopeCnt--;
    }

    @Override
    public void visit(VarDecl v) {
        System.out.print("VAR " + v.getId() + ":=");
        v.getEx().accept(this);
    }

    @Override
    public void visit(Var v) {
        System.out.print(v.getId());
    }

    @Override
    public void visit(Scope s) {
        System.out.println("LET");
        this.scopeCnt++;

        for (VarDecl v : s.getVars().values()) {
            printTab(1);
            v.accept(this);
            System.out.println();
        }
        printTab(0);
        System.out.println("IN");

        for (Expression e : s.getInstrs()) {
            printTab(1);
            e.accept(this);
            System.out.print('\n');
        }
        printTab(0);
        System.out.print("END");
        this.scopeCnt--;
        if (this.scopeCnt == 0) System.out.println();
    }

    @Override
    public void visit(While w) {
        for (VarDecl v : w.getVars().values()) {
            printTab(0);
            v.accept(this);
            System.out.println();
        }

        printTab(1);
        System.out.print("WHILE ");
        this.scopeCnt++;
        w.getCond().accept(this);
        System.out.println(" DO(");

        for (Expression e : w.getInstrs()) {
            printTab(1);
            e.accept(this);
            System.out.print('\n');
        }

        printTab(0);
        System.out.print(")");
        this.scopeCnt--;
    }

    private void printTab(final int sup) {
        for (int i = 1; i < this.scopeCnt + sup; i++) {
            System.out.print('\t');
        }
    }
} // VisitorPrettyPrint
