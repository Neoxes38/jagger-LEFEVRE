package src;

import java.util.ArrayList;

public class While implements Expression{
    public Expression cond;
    public ArrayList<Expression> instrs;

    public While(Expression cond){
        this.cond = cond;
        this.instrs = new ArrayList<>();
    }

    public void addInstr(Expression e) {
        this.instrs.add(e);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
