/**
 * Abstract class Expression - write a description of the class here
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Expression
{
    public abstract <T> T accept(Visitor<T> v);
} // Expression
