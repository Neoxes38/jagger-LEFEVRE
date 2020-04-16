package tests;

import junit.framework.TestCase;
import org.junit.Test;
import src.Jagger;
import src.ParseException;

import java.io.*;
import java.util.Scanner;

public class JaggerTest extends TestCase {

    @Test
    public void testUnOp() throws ParseException {

        // Tests
        testOperator("let in print(0) end", "LET IN PRINT(0.0) END ", "0.0 ");
        testOperator("let in print(5) end", "LET IN PRINT(5.0) END ", "5.0 ");
        testOperator("let in print(-2) end", "LET IN PRINT((0.0 MINUS 2.0)) END ", "-2.0 ");
        testOperator("let in print(<>1) end", "LET IN PRINT((NOT 1.0)) END ", "0.0 ");
        testOperator("let in print(<>0) end", "LET IN PRINT((NOT 0.0)) END ", "1.0 ");

    }

    @Test
    public void testBinOp() throws ParseException {

        InputStream is = new ByteArrayInputStream("".getBytes());
        new Jagger(is);

        // Tests
        testOperator("let in print(2+3) end", "LET IN PRINT((2.0 PLUS 3.0)) END ", "5.0 ");
        testOperator("let in print(2-3) end", "LET IN PRINT((2.0 MINUS 3.0)) END ", "-1.0 ");
        testOperator("let in print(3-2) end", "LET IN PRINT((3.0 MINUS 2.0)) END ", "1.0 ");
        testOperator("let in print(2*3) end", "LET IN PRINT((2.0 MULT 3.0)) END ", "6.0 ");
        testOperator("let in print(6/3) end", "LET IN PRINT((6.0 DIV 3.0)) END ", "2.0 ");
        testOperator("let in print(2/3) end", "LET IN PRINT((2.0 DIV 3.0)) END ", "0.6666666666666666 ");
        testOperator("let in print(2/4) end", "LET IN PRINT((2.0 DIV 4.0)) END ", "0.5 ");
        testOperator("let in print(1&&1) end", "LET IN PRINT((1.0 AND 1.0)) END ", "1.0 ");
        testOperator("let in print(1&&0) end", "LET IN PRINT((1.0 AND 0.0)) END ", "0.0 ");
        testOperator("let in print(0&&0) end", "LET IN PRINT((0.0 AND 0.0)) END ", "0.0 ");
        testOperator("let in print(1||1) end", "LET IN PRINT((1.0 OR 1.0)) END ", "1.0 ");
        testOperator("let in print(1||0) end", "LET IN PRINT((1.0 OR 0.0)) END ", "1.0 ");
        testOperator("let in print(0||0) end", "LET IN PRINT((0.0 OR 0.0)) END ", "0.0 ");
        testOperator("let var a:=0 in a:=1, print(a) end", "LET VAR a:=0.0 IN (a ASSIGN 1.0) PRINT(a) END ", "1.0 ");
    }

    @Test
    public void testTernOp() throws ParseException {

        // Tests
        testOperator("let in if 1 then (print(\"true\")) else (print(\"false\")) end",
                "LET IN IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END END ", "true ");
        testOperator("let in if 0 then (print(\"true\")) else (print(\"false\")) end",
                "LET IN IF 0.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END END ", "false ");
        testOperator("let in print(2+-3) end",
                "LET IN PRINT((2.0 PLUS (0.0 MINUS 3.0))) END ", "-1.0 ");
        testOperator("let in print(2*-3) end",
                "LET IN PRINT((2.0 MULT (0.0 MINUS 3.0))) END ", "-6.0 ");
        testOperator("let in print(6/-3) end",
                "LET IN PRINT((6.0 DIV (0.0 MINUS 3.0))) END ", "-2.0 ");
        testOperator("let var a:=1 in a:=2, if a=2 then (print(\"true\")) else (print(\"false\")) end",
                "LET VAR a:=1.0 IN (a ASSIGN 2.0) IF (a EQ 2.0) THEN LET IN PRINT(\"true\")" +
                        " END ELSE LET IN PRINT(\"false\") END END ", "true ");
    }

    @Test
    public void testRelOp() throws ParseException {

        // Tests
        testOperator("let in print(2=3) end", "LET IN PRINT((2.0 EQ 3.0)) END ", "0.0 ");
        testOperator("let in print(2=2) end", "LET IN PRINT((2.0 EQ 2.0)) END ", "1.0 ");

        testOperator("let in print(2<3) end", "LET IN PRINT((2.0 INF 3.0)) END ", "1.0 ");
        testOperator("let in print(2<2) end", "LET IN PRINT((2.0 INF 2.0)) END ", "0.0 ");
        testOperator("let in print(3<2) end", "LET IN PRINT((3.0 INF 2.0)) END ", "0.0 ");

        testOperator("let in print(2>3) end", "LET IN PRINT((2.0 SUP 3.0)) END ", "0.0 ");
        testOperator("let in print(2>2) end", "LET IN PRINT((2.0 SUP 2.0)) END ", "0.0 ");
        testOperator("let in print(3>2) end", "LET IN PRINT((3.0 SUP 2.0)) END ", "1.0 ");

        testOperator("let in print(2<=3) end", "LET IN PRINT((2.0 INF_EQ 3.0)) END ", "1.0 ");
        testOperator("let in print(2<=2) end", "LET IN PRINT((2.0 INF_EQ 2.0)) END ", "1.0 ");
        testOperator("let in print(3<=2) end", "LET IN PRINT((3.0 INF_EQ 2.0)) END ", "0.0 ");

        testOperator("let in print(2>=3) end", "LET IN PRINT((2.0 SUP_EQ 3.0)) END ", "0.0 ");
        testOperator("let in print(2>=2) end", "LET IN PRINT((2.0 SUP_EQ 2.0)) END ", "1.0 ");
        testOperator("let in print(3>=2) end", "LET IN PRINT((3.0 SUP_EQ 2.0)) END ", "1.0 ");

    }

    @Test
    public void testLoop() throws ParseException {
        // While
        testOperator(
                "let var i := 1 in while i < 5 do (print(i), i := i + 1) end",
                "LET VAR i:=1.0 IN WHILE (i INF 5.0) DO( PRINT(i) " +
                        "(i ASSIGN (i PLUS 1.0)) ) END ",
                "1.0 2.0 3.0 4.0 ");

        // For
        testOperator(
                "let var i := 1 in " +
                        "for i := 2 to 3 do (print(i)), " +
                        "print(i) end"
                ,
                "LET VAR i:=1.0 IN " +
                        "VAR i:=2.0 WHILE (i INF_EQ 3.0) DO( " +
                        "PRINT(i) (i ASSIGN (i PLUS 1.0)) ) " +
                        "PRINT(i) END "
                , "2.0 3.0 1.0 ");
        testOperator(
                "let in for i := 2 to 1 do( print(i)) end"
                ,
                "LET IN " +
                        "VAR i:=2.0 WHILE (i INF_EQ 1.0) DO( " +
                        "PRINT(i) (i ASSIGN (i PLUS 1.0)) ) END "
                , "");
    }

    @Test
    public void testTypeChecker() throws ParseException {
        testOperator("let var i:= 1+\"1\" in print(i) end",
                "LET VAR i:=(1.0 PLUS \"1\") IN PRINT(i) END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                true, false);
        testOperator("let var i:= if(true) then(print(\"true\")) else(print(\"false\")) in print(i) end",
                "LET VAR i:=IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END IN PRINT(i) END ",
                "Error -> Invalid type: \"Void\" is invalid here. ",
                true, false);
        // Check if WHILE cond can be VOID
        testOperator("let in while if(true) then(print(\"true\")) else(print(\"false\")) do(print(true)) end",
                "LET IN WHILE IF 1.0 THEN LET IN PRINT(\"true\") END ELSE LET IN PRINT(\"false\") END DO( " +
                        "PRINT(1.0) ) END ",
                "Error -> Invalid type: \"Num\" was expected but \"Void\" was found. ",
                true, false);
        // Check if IF cond can be VOID
        testOperator("let in if if(true) then(print(0)) else(print(0)) then(1) else(print(2)) end",
                "LET IN IF IF 1.0 THEN LET IN PRINT(0.0) END ELSE LET IN PRINT(0.0) END THEN LET IN " +
                        "1.0 END ELSE LET IN PRINT(2.0) END END ",
                "Error -> Invalid type: \"Num\" was expected but \"Void\" was found. ",
                true, false);
        // Left expression of an assignment can only be a Var
        testOperator("let var i:= 1 in 1:=2 end", "LET VAR i:=1.0 IN (1.0 ASSIGN 2.0) END ",
                "Error -> Invalid type: \"Var\" was expected but \"Num\" was found. ",
                true, false);
        // Inconsistency between if bodies types
        testOperator("let in if(true) then(print(0)) else(1) end",
                "LET IN IF 1.0 THEN LET IN PRINT(0.0) END ELSE LET IN 1.0 END END ",
                "Error -> Invalid type: \"Void\" was expected but \"Num\" was found. ",
                true, false);
        // Inconsistency between relation's expressions
        testOperator("let in 1<\"2\" end",
                "LET IN (1.0 INF \"2\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                true, false);
        // Inconsistency between relation's expressions
        testOperator("let in 1+\"2\" end",
                "LET IN (1.0 PLUS \"2\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                true, false);
        testOperator("let in <>\"true\" end",
                "LET IN (NOT \"true\") END ",
                "Error -> Invalid type: \"Num\" was expected but \"Str\" was found. ",
                true, false);

    }

    @Test
    public void testBinder() throws ParseException {
        testOperator("let in print(i) end",
                "LET IN PRINT(i) END ",
                "Error -> Undefined variable: Var \"i\" is not defined. ", false, true);
    }

    private static void testOperator(String s1, String expPrint, String expVal) throws ParseException {
        testOperator(s1, expPrint, expVal, true, true);
    }

    private static void testOperator(String s1, String expPrint, String expOut, Boolean binder, Boolean typeChecker) throws ParseException {
        // Create InputStream from String expression
        InputStream is = new ByteArrayInputStream(s1.getBytes());

        // Redirect stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Instantiate parser with our custom stream and run it
        Jagger.ReInit(is);
        Jagger.mainloop();

        // Retrieve pretty print string and eval value with scanner
        String expOk = "OK. ";
        String res = baos.toString().replaceAll("\n", " ").replaceAll("\r", "").replaceAll("\t", "");
        Scanner sc = new Scanner(res).useDelimiter("======================================= ");
        sc.next();//process
        sc.next();//process
        String print = sc.next();
        assertEquals(expPrint, print);

        sc.next();//binder
        if (binder) assertEquals(expOk, sc.next());//OK
        else {
            assertEquals(expOut, sc.next());
            return;
        }
        sc.next();//Type Checker
        if (typeChecker) assertEquals(expOk, sc.next());//OK
        else {
            assertEquals(expOut, sc.next());
            return;
        }
        sc.next();//Evaluator

        String eval = sc.hasNext() ? sc.next() : "";
        assertEquals(expOut, eval);

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}