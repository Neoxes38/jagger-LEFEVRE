package tests;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import src.Jagger;
import src.ParseException;
import src.RedefineException;

import java.io.*;
import java.util.Scanner;

public class JaggerTest extends TestCase {

    @Test
    public void testUnOp() throws ParseException {
        // Tests
        processCheck("let in print(0) end", "LET IN PRINT(0.0) END ", "0.0 ");
        processCheck("let in print(5) end", "LET IN PRINT(5.0) END ", "5.0 ");
        processCheck("let in print(-2) end", "LET IN PRINT((0.0 MINUS 2.0)) END ", "-2.0 ");
        processCheck("let in print(1--1) end", "LET IN PRINT((1.0 MINUS (0.0 MINUS 1.0))) END ", "2.0 ");
        processCheck("let in print(<>1) end", "LET IN PRINT((NOT 1.0)) END ", "0.0 ");
        processCheck("let in print(<>0) end", "LET IN PRINT((NOT 0.0)) END ", "1.0 ");
    }

    @Test
    public void testBinOp() throws ParseException {
        // BinOp on Nums
        processCheck("let in print(2+3) end", "LET IN PRINT((2.0 PLUS 3.0)) END ", "5.0 ");
        processCheck("let in print(2-3) end", "LET IN PRINT((2.0 MINUS 3.0)) END ", "-1.0 ");
        processCheck("let in print(3-2) end", "LET IN PRINT((3.0 MINUS 2.0)) END ", "1.0 ");
        processCheck("let in print(2*3) end", "LET IN PRINT((2.0 MULT 3.0)) END ", "6.0 ");
        processCheck("let in print(6/3) end", "LET IN PRINT((6.0 DIV 3.0)) END ", "2.0 ");
        processCheck("let in print(2/3) end", "LET IN PRINT((2.0 DIV 3.0)) END ", "0.6666666666666666 ");
        processCheck("let in print(2/4) end", "LET IN PRINT((2.0 DIV 4.0)) END ", "0.5 ");
        processCheck("let in print(1&&1) end", "LET IN PRINT((1.0 AND 1.0)) END ", "1.0 ");
        processCheck("let in print(1&&0) end", "LET IN PRINT((1.0 AND 0.0)) END ", "0.0 ");
        processCheck("let in print(0&&0) end", "LET IN PRINT((0.0 AND 0.0)) END ", "0.0 ");
        processCheck("let in print(1||1) end", "LET IN PRINT((1.0 OR 1.0)) END ", "1.0 ");
        processCheck("let in print(1||0) end", "LET IN PRINT((1.0 OR 0.0)) END ", "1.0 ");
        processCheck("let in print(0||0) end", "LET IN PRINT((0.0 OR 0.0)) END ", "0.0 ");
        processCheck("let var a:=0 in a:=1, print(a) end", "LET VAR a:=0.0 IN (a ASSIGN 1.0) PRINT(a) END ", "1.0 ");
        // Check operators priority
        processCheck("let in print(2*2+1) end", "LET IN PRINT(((2.0 MULT 2.0) PLUS 1.0)) END ", "5.0 ");
        processCheck("let in print(2+2*1) end", "LET IN PRINT((2.0 PLUS (2.0 MULT 1.0))) END ", "4.0 ");

        // BinOp on Strs
        processCheck("let in print(\"a\"+\"b\") end", "LET IN PRINT((\"a\" PLUS \"b\")) END ", "ab ");
        processCheck("let in print(\"a\"-\"b\") end", "LET IN PRINT((\"a\" MINUS \"b\")) END ",
                "Error -> Invalid type: operator \"MINUS\" cannot be applied on \"Str\". ", false, true);
        processCheck("let in print(\"a\"*\"b\") end", "LET IN PRINT((\"a\" MULT \"b\")) END ",
                "Error -> Invalid type: operator \"MULT\" cannot be applied on \"Str\". ", false, true);
        processCheck("let in print(\"a\"/\"b\") end", "LET IN PRINT((\"a\" DIV \"b\")) END ",
                "Error -> Invalid type: operator \"DIV\" cannot be applied on \"Str\". ", false, true);
        processCheck("let var i:=\"i\" in i:= \"o\", print(i) end", "LET VAR i:=\"i\" IN (i ASSIGN \"o\") PRINT(i) END ",
                "o ");
    }

    @Test
    public void testRelOp() throws ParseException {
        // Relations on Nums
        processCheck("let in print(2=3) end", "LET IN PRINT((2.0 EQ 3.0)) END ", "0.0 ");
        processCheck("let in print(2=2) end", "LET IN PRINT((2.0 EQ 2.0)) END ", "1.0 ");

        processCheck("let in print(2<3) end", "LET IN PRINT((2.0 INF 3.0)) END ", "1.0 ");
        processCheck("let in print(2<2) end", "LET IN PRINT((2.0 INF 2.0)) END ", "0.0 ");
        processCheck("let in print(3<2) end", "LET IN PRINT((3.0 INF 2.0)) END ", "0.0 ");

        processCheck("let in print(2>3) end", "LET IN PRINT((2.0 SUP 3.0)) END ", "0.0 ");
        processCheck("let in print(2>2) end", "LET IN PRINT((2.0 SUP 2.0)) END ", "0.0 ");
        processCheck("let in print(3>2) end", "LET IN PRINT((3.0 SUP 2.0)) END ", "1.0 ");

        processCheck("let in print(2<=3) end", "LET IN PRINT((2.0 INF_EQ 3.0)) END ", "1.0 ");
        processCheck("let in print(2<=2) end", "LET IN PRINT((2.0 INF_EQ 2.0)) END ", "1.0 ");
        processCheck("let in print(3<=2) end", "LET IN PRINT((3.0 INF_EQ 2.0)) END ", "0.0 ");

        processCheck("let in print(2>=3) end", "LET IN PRINT((2.0 SUP_EQ 3.0)) END ", "0.0 ");
        processCheck("let in print(2>=2) end", "LET IN PRINT((2.0 SUP_EQ 2.0)) END ", "1.0 ");
        processCheck("let in print(3>=2) end", "LET IN PRINT((3.0 SUP_EQ 2.0)) END ", "1.0 ");

        // Relations on Strs

        processCheck("let in print(\"a\"=\"b\") end", "LET IN PRINT((\"a\" EQ \"b\")) END ", "1.0 ");
        processCheck("let in print(\"a\"=\"bb\") end", "LET IN PRINT((\"a\" EQ \"bb\")) END ", "0.0 ");

        processCheck("let in print(\"a\"<\"b\") end", "LET IN PRINT((\"a\" INF \"b\")) END ", "0.0 ");
        processCheck("let in print(\"aa\"<\"b\") end", "LET IN PRINT((\"aa\" INF \"b\")) END ", "0.0 ");
        processCheck("let in print(\"a\"<\"bb\") end", "LET IN PRINT((\"a\" INF \"bb\")) END ", "1.0 ");

        processCheck("let in print(\"a\">\"b\") end", "LET IN PRINT((\"a\" SUP \"b\")) END ", "0.0 ");
        processCheck("let in print(\"aa\">\"b\") end", "LET IN PRINT((\"aa\" SUP \"b\")) END ", "1.0 ");
        processCheck("let in print(\"a\">\"bb\") end", "LET IN PRINT((\"a\" SUP \"bb\")) END ", "0.0 ");

        processCheck("let in print(\"a\"<=\"b\") end", "LET IN PRINT((\"a\" INF_EQ \"b\")) END ", "1.0 ");
        processCheck("let in print(\"aa\"<=\"b\") end", "LET IN PRINT((\"aa\" INF_EQ \"b\")) END ", "0.0 ");
        processCheck("let in print(\"a\"<=\"bb\") end", "LET IN PRINT((\"a\" INF_EQ \"bb\")) END ", "1.0 ");

        processCheck("let in print(\"a\">=\"b\") end", "LET IN PRINT((\"a\" SUP_EQ \"b\")) END ", "1.0 ");
        processCheck("let in print(\"aa\">=\"b\") end", "LET IN PRINT((\"aa\" SUP_EQ \"b\")) END ", "1.0 ");
        processCheck("let in print(\"a\">=\"bb\") end", "LET IN PRINT((\"a\" SUP_EQ \"bb\")) END ", "0.0 ");

    }

    @Test
    public void testTernOp() throws ParseException {
        // Tests
        processCheck("let in if 1 then (print(\"true\")) else (print(\"false\")) end",
                "LET IN IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END END ", "true ");
        processCheck("let in if 0 then (print(\"true\")) else (print(\"false\")) end",
                "LET IN IF 0.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END END ", "false ");
        processCheck("let in print(2+-3) end",
                "LET IN PRINT((2.0 PLUS (0.0 MINUS 3.0))) END ", "-1.0 ");
        processCheck("let in print(2*-3) end",
                "LET IN PRINT((2.0 MULT (0.0 MINUS 3.0))) END ", "-6.0 ");
        processCheck("let in print(6/-3) end",
                "LET IN PRINT((6.0 DIV (0.0 MINUS 3.0))) END ", "-2.0 ");
        processCheck("let var a:=1 in a:=2, if a=2 then (print(\"true\")) else (print(\"false\")) end",
                "LET VAR a:=1.0 IN (a ASSIGN 2.0) IF (a EQ 2.0) THEN LET IN PRINT(\"true\")" +
                        " END ELSE LET IN PRINT(\"false\") END END ", "true ");
        processCheck("let var a:=\"\" in a:=if true then(\"true\") else (\"else\"), print(a) end",
                "LET VAR a:=\"\" IN (a ASSIGN IF 1.0 THEN LET IN \"true\" END ELSE LET IN \"else\" END) PRINT(a) END ",
                "true ");
    }

    @Test
    public void testScope() throws ParseException {
        // Check if re-declaring a variable actually throws an exception
        try {
            processCheck("let var foo := 1 var bar := 1 var foo := 1 in 1 end  ",
                    "", "");
        } catch (RedefineException re) {
            Assert.assertEquals(re.getMessage(), "Cannot redefine var \"foo\" in this scope");
        }
        // Check if a scope has access to a variable of a scope at the same level as it (which is forbidden)
        processCheck("let in let var a:=1 in print(a) end, let in print(a) end end",
                "LET IN LET VAR a:=1.0 IN PRINT(a) END LET IN PRINT(a) END END ",
                "Error -> Undefined variable: Var \"a\" is not defined. ", true, false);
        // Check if Var actually can't be VOID
        processCheck("let var a := if true then(print(1)) else(print(2)) in a end",
                "LET VAR a:=IF 1.0 THEN LET IN PRINT(1.0) END ELSE LET IN PRINT(2.0) END IN a END ",
                "Error -> Invalid type: \"Void\" is invalid here. ",
                false, true);
    }

    @Test
    public void testLoop() throws ParseException {
        // While
        processCheck(
                "let var i := 1 in while i < 5 do (print(i), i := i + 1) end",
                "LET VAR i:=1.0 IN WHILE (i INF 5.0) DO( PRINT(i) " +
                        "(i ASSIGN (i PLUS 1.0)) ) END ",
                "1.0 2.0 3.0 4.0 ");

        // For
        processCheck(
                "let var i := 1 in " +
                        "for i := 2 to 3 do (print(i)), " +
                        "print(i) end"
                ,
                "LET VAR i:=1.0 IN " +
                        "VAR i:=2.0 WHILE (i INF_EQ 3.0) DO( " +
                        "PRINT(i) (i ASSIGN (i PLUS 1.0)) ) " +
                        "PRINT(i) END "
                , "2.0 3.0 1.0 ");
        processCheck(
                "let in for i := 2 to 1 do( print(i)) end"
                ,
                "LET IN " +
                        "VAR i:=2.0 WHILE (i INF_EQ 1.0) DO( " +
                        "PRINT(i) (i ASSIGN (i PLUS 1.0)) ) END "
                , "");
    }

    @Test
    public void testTypeChecker() throws ParseException {
        processCheck("let var i:= 1+\"1\" in print(i) end",
                "LET VAR i:=(1.0 PLUS \"1\") IN PRINT(i) END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                false, true);
        processCheck("let var i:= if(true) then(print(\"true\")) else(print(\"false\")) in print(i) end",
                "LET VAR i:=IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END IN PRINT(i) END ",
                "Error -> Invalid type: \"Void\" is invalid here. ",
                false, true);
        // Check if WHILE cond can be VOID
        processCheck("let in while if(true) then(print(\"true\")) else(print(\"false\")) do(print(true)) end",
                "LET IN WHILE IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END DO( " +
                        "PRINT(1.0) ) END ",
                "Error -> Invalid type: \"Num\" was expected but \"Void\" was found. ",
                false, true);
        // Check if IF cond can be VOID
        processCheck("let in if if(true) then(print(0)) else(print(0)) then(1) else(print(2)) end",
                "LET IN IF IF 1.0 THEN LET IN PRINT(0.0) END ELSE LET IN PRINT(0.0) END THEN LET IN " +
                        "1.0 END ELSE LET IN PRINT(2.0) END END ",
                "Error -> Invalid type: \"Num\" was expected but \"Void\" was found. ",
                false, true);
        // Left expression of an assignment can only be a Var
        processCheck("let var i:= 1 in 1:=2 end", "LET VAR i:=1.0 IN (1.0 ASSIGN 2.0) END ",
                "Error -> Invalid type: \"Var\" was expected but \"Num\" was found. ",
                false, true);
        // Inconsistency between if bodies types
        processCheck("let in if(true) then(print(0)) else(1) end",
                "LET IN IF 1.0 THEN LET IN PRINT(0.0) END ELSE LET IN 1.0 END END ",
                "Error -> Invalid type: \"Void\" was expected but \"Num\" was found. ",
                false, true);
        // Inconsistency between relation's expressions
        processCheck("let in 1<\"2\" end",
                "LET IN (1.0 INF \"2\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                false, true);
        // Inconsistency between relation's expressions
        processCheck("let in 1+\"2\" end",
                "LET IN (1.0 PLUS \"2\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                false, true);
        processCheck("let in <>\"true\" end",
                "LET IN (NOT \"true\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                false, true);
        // Check if we VOID can actually get printed
        processCheck("let var a:=\"o\" var b:=\"q\" in print(if true then(print(a)) else (print(b))) end",
                "LET VAR a:=\"o\" VAR b:=\"q\" IN PRINT(IF 1.0 THEN LET IN PRINT(a) END ELSE LET IN PRINT(b) END) END ",
                "Error -> Invalid type: \"Void\" is invalid here. ", false, true);
    }

    @Test
    public void testBinder() throws ParseException {
        processCheck("let in print(i) end",
                "LET IN PRINT(i) END ",
                "Error -> Undefined variable: Var \"i\" is not defined. ", true, false);
        processCheck("let var foo:=1 var bar :=1+foo in print(bar) end",
                "LET VAR foo:=1.0 VAR bar:=(1.0 PLUS foo) IN PRINT(bar) END ",
                "2.0 ");
        processCheck("let var i:=3 in print(i) end",
                "LET VAR i:=3.0 IN PRINT(i) END ",
                "3.0 ");
        processCheck("let var i:=3 in let in print(i) end, print(i) end",
                "LET VAR i:=3.0 IN LET IN PRINT(i) END PRINT(i) END ",
                "3.0 3.0 ");
        processCheck("let var i:=3 in let var i:=2 in print(i) end, print(i) end",
                "LET VAR i:=3.0 IN LET VAR i:=2.0 IN PRINT(i) END PRINT(i) END ",
                "2.0 3.0 ");
        processCheck("let in let var i:=2 in print(i) end, print(i) end",
                "LET IN LET VAR i:=2.0 IN PRINT(i) END PRINT(i) END ",
                "Error -> Undefined variable: Var \"i\" is not defined. ", true, false);
    }

    private static void processCheck(String code, String expPrint, String expVal) throws ParseException {
        processCheck(code, expPrint, expVal, false, false);
    }

    /**
     * @param code             code to be interpreted
     * @param expPrint         expected result of pretty printer
     * @param expOut           expected result of evaluation or error message after binding/type checking
     * @param binderBreak      true if the test have to break on binding
     * @param typeCheckerBreak true if the test have to break on type checking
     * @throws ParseException
     */
    private static void processCheck(String code, String expPrint, String expOut, Boolean binderBreak, Boolean typeCheckerBreak) throws ParseException {
        // Create InputStream from String expression
        InputStream is = new ByteArrayInputStream(code.getBytes());

        // Redirect stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Instantiate parser with our custom stream and run it
        try {
            Jagger.ReInit(is);
        } catch (NullPointerException npe) {
            new Jagger(is);
        }
        Jagger.mainloop();

        // Retrieve pretty print string and eval value with scanner
        String expOk = "OK. ";
        String res = baos.toString().replaceAll("\n", " ").replaceAll("\r", "").replaceAll("\t", "");
        Scanner sc = new Scanner(res).useDelimiter("======================================= ");
        sc.next();//process
        sc.next();//process
        String print = sc.next();
        Assert.assertEquals(expPrint, print);

        sc.next();//binder
        if (!binderBreak) Assert.assertEquals(expOk, sc.next());//OK
        else {
            Assert.assertEquals(expOut, sc.next());
            return;
        }
        sc.next();//Type Checker
        if (!typeCheckerBreak) Assert.assertEquals(expOk, sc.next());//OK
        else {
            Assert.assertEquals(expOut, sc.next());
            return;
        }
        sc.next();//Evaluator

        String eval = sc.hasNext() ? sc.next() : "";
        Assert.assertEquals(expOut, eval);

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}