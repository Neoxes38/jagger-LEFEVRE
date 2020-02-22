/**
 * Write a description of class VisiteurPrettyPrint here.
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
    public String visit(Binop b){
        String s1, s2;
        System.out.print("(");
        s1 = b.lex.accept(this);
        System.out.print(" "+b.op+" ");
        s2 = b.rex.accept(this);
        System.out.print(")");
        return "(" + s1 + b.op + s2 + ")";
    }

    @Override
    public String visit(Unop u){
        String res = "(" + u.op + u.ex.accept(this) + ")";
        System.out.println(res);
        return res;
    }
} // VisiteurPrettyPrint
