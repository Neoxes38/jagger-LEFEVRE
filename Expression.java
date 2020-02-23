/**
 * Abstract class Expression - write a description of the class here
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface  Expression
{
    public <T> T accept(Visitor<T> v);
} // Expression
