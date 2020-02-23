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
    public String visit(BoolOp o){
        String s1, s2;
        System.out.print("(");
        s1 = o.lex.accept(this);
        System.out.print(" " + o.op + " ");
        s2 = o.rex.accept(this);
        System.out.print(")");
        return "(" + s1 + o.op + s2 + ")";
    }
    @Override
    public String visit(No n){
        String s;
        System.out.print("(");
        System.out.print(" NO ");
        s = n.ex.accept(this);
        System.out.print(")");
        return "( NO " + s + ")";
    }
    @Override
    public String visit(Bool b){
        System.out.print(b.getValue().toString());
        return b.getValue().toString();
    }
} // VisitorPrettyPrint
