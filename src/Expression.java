package src;

/**
 * Abstract class Expression - write a description of the class here
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface  Expression
{
    <T> T accept(Visitor<T> v);
} // Expression
