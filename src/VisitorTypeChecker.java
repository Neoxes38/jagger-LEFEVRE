package src;

public class VisitorTypeChecker implements Visitor<String> {
    private boolean hasError;
    private String error;

    public VisitorTypeChecker(){
        this.hasError = false;
        this.error = "";
    }

    public boolean hasError(){ return this.hasError; }
    public String getError(){ return this.error; }

    private void buildError(String ...s){
        StringBuilder res = new StringBuilder();
        res.append("Error: Invalid type: ");
        for(String type: s)
            res.append(type).append(" ");
        this.error = res.toString();
    }

    @Override
    public String visit(Num n) {
        return n.getClass().getName();
    }

    @Override
    public String visit(Str s) {
        return s.getClass().getName();
    }

    @Override
    public String visit(BinOp b) {
        String t1, t2;
        t1 = b.lex.accept(this);
        t2 = b.rex.accept(this);
        hasError = !t1.equals(t2);
        if(hasError)
            buildError(t1, t2);
        else return t2;
        return this.error;
    }

    @Override
    public String visit(Relation r) {
        String t1, t2;
        t1 = r.lex.accept(this);
        t2 = r.rex.accept(this);
        System.out.println(t1);
        hasError = !t1.equals(t2);
        if(hasError)
            buildError(t1, t2);
        else return t2;
        return this.error;
    }

    @Override
    public String visit(Not n) {
        return n.ex.accept(this);
    }

    @Override
    public String visit(Print p) {
        return null;
    }

    @Override
    public String visit(TernOp t) {
        String t1, t2, t3, num_type = "src.Num";
        t1 = t.ifEx.accept(this);
        t2 = t.thenEx.accept(this);
        t3 = t.elseEx.accept(this);

        hasError = !t1.equals(num_type) ;
        if(hasError) {
            buildError(num_type, t1);
            return this.error;
        }

        hasError = !t2.equals(t3);
        if(hasError) {
            buildError(t2, t3);
            return this.error;
        }

        else return t3;
    }
}
