package src;

public interface Visitor
{
    void visit(Num n);
    void visit(Str s);
    void visit(BinOp b);

    void visit(RelOp r);
    void visit(Not n);

    void visit(Print p);
    void visit(TernOp t);

    void visit(VarDecl v);
    void visit(Var v);
    void visit(Scope s);

    void visit(While w);
} // Visitor
