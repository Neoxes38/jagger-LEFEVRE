package src;

/**
 * Write a description of class VisitorPrettyPrint here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorPrettyPrint implements Visitor<String>
{
    @Override
    public String visit(Num n){
        System.out.print(n.getValue());
        return n.getValue().toString();
    }    
    @Override
    public String visit(BinOp b){
        String s1, s2;
        System.out.print("(");
        s1 = b.lex.accept(this);
        System.out.print(" " + b.op + " ");
        s2 = b.rex.accept(this);
        System.out.print(")");
        return "(" + s1 + b.op + s2 + ")";
    }

    @Override
    public String visit(Relation r){
        String s1, s2;
        System.out.print("(");
        s1 = r.lex.accept(this);
        System.out.print(" " + r.op + " ");
        s2 = r.rex.accept(this);
        System.out.print(")");
        return "(" + s1 + r.op + s2 + ")";
    }
    @Override
    public String visit(Not n){
        String s;
        System.out.print("(NOT ");
        s = n.ex.accept(this);
        System.out.print(")");
        return "(NOT " + s + ")";
    }

    @Override
    public String visit(Print p) {
        System.out.print("print(");
        String s = p.ex.accept(this) ;
        System.out.print(")");
        return "print("+s+")";
    }

    @Override
    public String visit(TernOp t) {
        //TODO: Do we have to check types?
        String s1, s2, s3;
        System.out.print("IF ");
        s1 = t.ifEx.accept(this);
        System.out.print(" THEN ");
        s2 = t.thenEx.accept(this);
        System.out.print(" ELSE ");
        s3 = t.elseEx.accept(this);

        return "IF "+s1+" THEN "+s2+"ELSE"+s3;
    }
} // VisitorPrettyPrint
