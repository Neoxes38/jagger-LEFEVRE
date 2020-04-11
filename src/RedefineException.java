package src;

public class RedefineException extends RuntimeException{
    public RedefineException(String varId){
        super("Cannot redefine var \"" + varId + "\" in this scope");
    }
}
