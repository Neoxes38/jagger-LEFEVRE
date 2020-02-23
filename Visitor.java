/**
 * Write a description of interface Visitor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Visitor<T>
{
    public T visit(Num n);
    public T visit(BinOp b);
    
    public T visit(Relation r);
    public T visit(Not n);
    public T visit(Bool b);
} // Visitor
