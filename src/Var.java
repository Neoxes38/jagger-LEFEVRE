package src;

public class Var implements Expression{
    public String id;
    public VarDecl d;

    public Var(String id){
        this.id = id;
    }

    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Var
