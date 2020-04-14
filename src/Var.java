package src;

public class Var implements Expression{
    private final String id;
    private VarDecl decl;

    public Var(String id){
        this.id = id;
    }

    public String getId() { return id; }
    public VarDecl getDecl() { return decl; }
    public void setDecl(VarDecl decl) { this.decl = decl; }

    @Override
    public void accept(Visitor v){ v.visit(this); }
} // Var
