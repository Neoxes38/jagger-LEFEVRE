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
                "LET VAR a:=1.0 IN (a ASSIGN 2.0) IF (a EQ 2.0) THEN LET IN PRINT(\"true\"git )" +
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

    private static void testOperator(String s1, String expPrint, String expVal) throws ParseException {
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
        assertEquals(expOk, sc.next());//OK
        sc.next();//Type Checker
        assertEquals(expOk, sc.next());//OK
        sc.next();//Evaluator

        String eval = sc.next();
        assertEquals(expVal, eval);

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}