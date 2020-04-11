package src;

public abstract class AbstractVisitorError implements Visitor, ErrorHandler {
    private boolean hasError;
    private String errorMessage;

    @Override
    public boolean hasError() { return hasError; }
    @Override
    public String getMessage() { return errorMessage; }

    @Override
    public void setError(String s) {
        hasError = true;
        errorMessage = "Error -> " + s;
    }

    @Override
    public abstract void visit(Num n);

    @Override
    public abstract void visit(Str s);

    @Override
    public abstract void visit(BinOp b);

    @Override
    public abstract void visit(Relation r);

    @Override
    public abstract void visit(Not n);

    @Override
    public abstract void visit(Print p);

    @Override
    public abstract void visit(TernOp t);

    @Override
    public abstract void visit(VarDecl v);

    @Override
    public abstract void visit(Var v);

    @Override
    public abstract void visit(Scope s);
} // AbstractVisitorError
