package src;

public class While extends Scope{
    private final Expression cond;

    public While(Expression cond){
        super();
        this.cond = cond;
    }

    public Expression getCond() { return cond; }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
} // While
