package src;

/**
 * Write a description of interface Visitor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Visitor
{
    void visit(Num n);
    void visit(Str s);
    void visit(BinOp b);

    void visit(Relation r);
    void visit(Not n);

    void visit(Print p);
    void visit(TernOp t);

    void visit(VarDecl v);
    void visit(Var v);
    void visit(Scope s);
} // Visitor
