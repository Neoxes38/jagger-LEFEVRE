package src;

public class VarDecl implements Expression{
    private final String id;
    private Expression ex;
    private Types type;

    public VarDecl(String id, Expression ex){
        this.id = id;
        this.ex = ex;
    }

    public String getId() { return id; }
    public Expression getEx() { return ex; }
    public void setEx(Expression ex) { this.ex = ex; }
    public Types getType() { return type; }
    public void setType(Types type) { this.type = type; }

    @Override
    public void accept(Visitor v) { v.visit(this); }

} // VarDecl
