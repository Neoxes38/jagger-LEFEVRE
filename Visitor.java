/**
 * Write a description of interface Visiteur here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Visitor<T>
{
    public T visit(Num n);
    public T visit(Binop b);
    public T visit(Unop u);    
} // Visiteur
