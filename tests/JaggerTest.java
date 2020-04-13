package tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import src.Jagger;
import src.ParseException;

import java.io.*;
import java.util.Scanner;


public class JaggerTest extends junit.framework.TestCase{

    protected void setUp() // throws java.lang.Exception
    {
        // Initialisez ici vos engagements
    }
    protected void tearDown() // throws java.lang.Exception
    {
        //Liberez ici les ressources engagees par setUp()
    }

    public JaggerTest() {
        super();
    }

    public JaggerTest(String name) {
        super(name);
    }

    @Override
    public int countTestCases() {
        return super.countTestCases();
    }

    @Override
    protected TestResult createResult() {
        return super.createResult();
    }

    @Override
    public TestResult run() {
        return super.run();
    }

    @Override
    public void run(TestResult result) {
        super.run(result);
    }

    @Override
    public void runBare() throws Throwable {
        super.runBare();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public static void testUnOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Tests
        testOperator("let in print(0) end\n", "LET IN \tprint(0.0)\nEND ", "0.0 ");
        testOperator("let in print(5) end\n", "LET IN \tprint(5.0)\nEND ", "5.0 ");
        testOperator("let in print(-2) end\n", "LET IN \tprint((0.0 MINUS 2.0))\nEND ", "-2.0 ");
        testOperator("let in print(<>1) end\n", "LET IN \tprint((NOT 1.0))\nEND ", "0.0 ");
        testOperator("let in print(<>0) end\n", "LET IN \tprint((NOT 0.0))\nEND ", "1.0 ");

    }

    public static void testBinOp() throws ParseException {
        String s1, res, print;
        double eval;

        InputStream is = new ByteArrayInputStream("".getBytes());
        new Jagger(is);

        // Tests
        testOperator("let in print(2+3) end\n", "LET IN \tprint((2.0 PLUS 3.0))\nEND ", "5.0 ");
        testOperator("let in print(2-3) end\n", "LET IN \tprint((2.0 MINUS 3.0))\nEND ", "-1.0 ");
        testOperator("let in print(3-2) end\n", "LET IN \tprint((3.0 MINUS 2.0))\nEND ", "1.0 ");
        testOperator("let in print(2*3) end\n", "LET IN \tprint((2.0 MULT 3.0))\nEND ", "6.0 ");
        testOperator("let in print(6/3) end\n", "LET IN \tprint((6.0 DIV 3.0))\nEND ", "2.0 ");
        testOperator("let in print(2/3) end\n", "LET IN \tprint((2.0 DIV 3.0))\nEND ", "0.6666666666666666 ");
        testOperator("let in print(2/4) end\n", "LET IN \tprint((2.0 DIV 4.0))\nEND ", "0.5 ");
        testOperator("let in print(1&&1) end\n", "LET IN \tprint((1.0 AND 1.0))\nEND ", "1.0 ");
        testOperator("let in print(1&&0) end\n", "LET IN \tprint((1.0 AND 0.0))\nEND ", "0.0 ");
        testOperator("let in print(0&&0) end\n", "LET IN \tprint((0.0 AND 0.0))\nEND ", "0.0 ");
        testOperator("let in print(1||1) end\n", "LET IN \tprint((1.0 OR 1.0))\nEND ", "1.0 ");
        testOperator("let in print(1||0) end\n", "LET IN \tprint((1.0 OR 0.0))\nEND ", "1.0 ");
        testOperator("let in print(0||0) end\n", "LET IN \tprint((0.0 OR 0.0))\nEND ", "0.0 ");
        testOperator("let var a:=0 in a:=1, print(a) end\n", "LET \t(Var a:=0.0) IN \t(a ASSIGN 1.0)\n\tprint(a)\nEND ", "1.0 ");

    }

    public static void testTernOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Tests
        testOperator("let in if 1 then print(\"true\") else print(\"false\") end\n", "LET IN \tIF 1.0 THEN print(true) ELSE print(false)\nEND ", "true ");
        testOperator("let in if 0 then print(\"true\") else print(\"false\") end\n", "LET IN \tIF 0.0 THEN print(true) ELSE print(false)\nEND ", "false ");
        testOperator("let in print(2+-3) end\n", "LET IN \tprint((2.0 PLUS (0.0 MINUS 3.0)))\nEND ", "-1.0 ");
        testOperator("let in print(2*-3) end\n", "LET IN \tprint((2.0 MULT (0.0 MINUS 3.0)))\nEND ", "-6.0 ");
        testOperator("let in print(6/-3) end\n", "LET IN \tprint((6.0 DIV (0.0 MINUS 3.0)))\nEND ", "-2.0 ");
        /*testOperator("let var a:=1 in a:=2, if a=2 then print(\"true\") else print(\"false\") end\n", "LET \t(Var a:=0.0) IN \t(a ASSIGN 0.0)\n\tprint(a)\nEND ", "0.0 ");*/

    }

    public static void testRelOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Tests
        testOperator("let in print(2=3) end\n", "LET IN \tprint((2.0 EQ 3.0))\nEND ", "0.0 ");
        testOperator("let in print(2=2) end\n", "LET IN \tprint((2.0 EQ 2.0))\nEND ", "1.0 ");

        testOperator("let in print(2<3) end\n", "LET IN \tprint((2.0 INF 3.0))\nEND ", "1.0 ");
        testOperator("let in print(2<2) end\n", "LET IN \tprint((2.0 INF 2.0))\nEND ", "0.0 ");
        testOperator("let in print(3<2) end\n", "LET IN \tprint((3.0 INF 2.0))\nEND ", "0.0 ");

        testOperator("let in print(2>3) end\n", "LET IN \tprint((2.0 SUP 3.0))\nEND ", "0.0 ");
        testOperator("let in print(2>2) end\n", "LET IN \tprint((2.0 SUP 2.0))\nEND ", "0.0 ");
        testOperator("let in print(3>2) end\n", "LET IN \tprint((3.0 SUP 2.0))\nEND ", "1.0 ");

        testOperator("let in print(2<=3) end\n", "LET IN \tprint((2.0 INF_EQ 3.0))\nEND ", "1.0 ");
        testOperator("let in print(2<=2) end\n", "LET IN \tprint((2.0 INF_EQ 2.0))\nEND ", "1.0 ");
        testOperator("let in print(3<=2) end\n", "LET IN \tprint((3.0 INF_EQ 2.0))\nEND ", "0.0 ");

        testOperator("let in print(2>=3) end\n", "LET IN \tprint((2.0 SUP_EQ 3.0))\nEND ", "0.0 ");
        testOperator("let in print(2>=2) end\n", "LET IN \tprint((2.0 SUP_EQ 2.0))\nEND ", "1.0 ");
        testOperator("let in print(3>=2) end\n", "LET IN \tprint((3.0 SUP_EQ 2.0))\nEND ", "1.0 ");

    }

    private static void testOperator(String s1, String expPrint, String expVal) throws ParseException{
        // Create InputStream from String expression
        InputStream is = new ByteArrayInputStream(s1.getBytes());

        // Redirect stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Instantiate parser with our custom stream and run it
        Jagger.ReInit(is);
        Jagger.mainloop();

        // Retrieve pretty print string and eval value with scanner
        String expok = "OK. ";
        String res = baos.toString().replaceAll("\r\n", " ");
        Scanner sc = new Scanner(res).useDelimiter("======================================= ")
                .skip("\n>>> Process code...\n ");
        sc.next();//pretty print
        String print = sc.next();
        assertEquals(expPrint, print);
        sc.next();//binder
        assertEquals(expok, sc.next());//OK
        sc.next();//Type Checker
        assertEquals(expok, sc.next());//OK
        sc.next();//Evaluator

        String eval = sc.next();
        assertEquals(expVal, eval);

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }


}