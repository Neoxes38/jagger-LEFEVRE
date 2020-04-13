package src;

public class VarDecl implements Expression{
    public String id;
    public Expression e;
    public Types type;

    public VarDecl(String id, Expression e){
        this.id = id;
        this.e = e;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
} // VarDecl
