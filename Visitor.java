/**
 * Write a description of interface Visitor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Visitor<T>
{
    T visit(Num n);
    T visit(BinOp b);
    
    T visit(Relation r);
    T visit(Not n);
    T visit(Bool b);

    T visit(Print p);
    T visit(TernOp t);
} // Visitor
