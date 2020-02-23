/**
 * Write a description of class VisitorEvaluation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorEvaluation implements Visitor<Double>{
    public Double visit(Num n){
        return n.getValue();
    }

    public Double visit(Binop b){
        double d1, d2;
        d1 = b.lex.accept(this);
        d2 = b.rex.accept(this);
        
        switch(b.op){
            case PLUS:
            return d1 + d2;
            case MINUS:
            return d1 - d2;
            case MULT:
            return d1 * d2;
            case DIV:
            return d1 / d2;
        }
        return -1.0;
    } 
}
