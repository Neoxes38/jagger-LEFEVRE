package src;

public class While extends Scope{
    public Expression cond;

    public While(Expression cond){
        super();
        this.cond = cond;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
