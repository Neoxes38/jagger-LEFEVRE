/**
 * Write a description of class VisitorEvaluation here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class VisitorEvaluation implements Visitor<Double>{
    @Override
    public Double visit(Num n){
        return n.getValue();
    }
    @Override
    public Double visit(BinOp b){
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
            case AND:
            return d1==d2 && d1==1.0 ? 1.0 : 0.0;
            case OR:
            return d1==1.0 || d2==1.0 ? 1.0 : 0.0;
        }
        return -1.0;
    } 
    
    @Override
    public Double visit(Relation r){
        double d1, d2;
        d1 = r.lex.accept(this);
        d2 = r.rex.accept(this);
        
        switch(r.op){
            case EQUAL:
            return d1 == d2 ? 1.0 : 0.0;
            case INF:
            return d1 < d2 ? 1.0 : 0.0;
            case SUP:
            return d1 > d2 ? 1.0 : 0.0;
            case INF_EQ:
            return d1 <= d2 ? 1.0 : 0.0;
            case SUP_EQ:
            return d1 >= d2 ? 1.0 : 0.0;
        }
        return -1.0;
    }
    @Override
    public Double visit(Not n){
        return n.ex.accept(this)==1.0 ? 0.0 : 1.0;
    }
    @Override
    public Double visit(Bool b){
        return b.getValue() ? 1.0 : 0.0;
    }
}// VisitorEvaluation
