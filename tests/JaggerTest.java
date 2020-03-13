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
        testOperator("0\n", "0.0\r", 0.0);
        testOperator("5\n", "5.0\r", 5.0);
        testOperator("-2\n", "(0.0 MINUS 2.0)\r", -2.0);
        testOperator("!1\n", "(NOT 1.0)\r", 0.0);
        testOperator("!0\n", "(NOT 0.0)\r", 1.0);

    }

    public static void testBinOp() throws ParseException {
        String s1, res, print;
        double eval;

        InputStream is = new ByteArrayInputStream("".getBytes());
        new Jagger(is);

        // Tests
        testOperator("2+3\n", "(2.0 PLUS 3.0)\r", 5.0);
        testOperator("2-3\n", "(2.0 MINUS 3.0)\r", -1.0);
        testOperator("3-2\n", "(3.0 MINUS 2.0)\r", 1.0);
        testOperator("2*3\n", "(2.0 MULT 3.0)\r", 6.0);
        testOperator("6/3\n", "(6.0 DIV 3.0)\r", 2.0);
        testOperator("2/3\n", "(2.0 DIV 3.0)\r", 0.6666666666666666);
        testOperator("2/4\n", "(2.0 DIV 4.0)\r", 0.5);
        testOperator("1&&1\n", "(1.0 AND 1.0)\r", 1.0);
        testOperator("1&&0\n", "(1.0 AND 0.0)\r", 0.0);
        testOperator("0&&0\n", "(0.0 AND 0.0)\r", 0.0);
        testOperator("1||1\n", "(1.0 OR 1.0)\r", 1.0);
        testOperator("1||0\n", "(1.0 OR 0.0)\r", 1.0);
        testOperator("0||0\n", "(0.0 OR 0.0)\r", 0.0);

    }

    public static void testTernOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Tests
        testOperator("if 1 then 1 else 0\n", "IF 1.0 THEN 1.0 ELSE 0.0\r", 1.0);
        testOperator("if 0 then 1 else 0\n", "IF 0.0 THEN 1.0 ELSE 0.0\r", 0.0);
        testOperator("2+-3\n", "(2.0 PLUS (0.0 MINUS 3.0))\r", -1.0);
        testOperator("2*-3\n", "(2.0 MULT (0.0 MINUS 3.0))\r", -6.0);
        testOperator("6/-3\n", "(6.0 DIV (0.0 MINUS 3.0))\r", -2.0);

    }

    public static void testRelOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Tests
        testOperator("2==3\n", "(2.0 EQ 3.0)\r", 0.0);
        testOperator("2==2\n", "(2.0 EQ 2.0)\r", 1.0);

        testOperator("2<3\n", "(2.0 INF 3.0)\r", 1.0);
        testOperator("2<2\n", "(2.0 INF 2.0)\r", 0.0);
        testOperator("3<2\n", "(3.0 INF 2.0)\r", 0.0);

        testOperator("2>3\n", "(2.0 SUP 3.0)\r", 0.0);
        testOperator("2>2\n", "(2.0 SUP 2.0)\r", 0.0);
        testOperator("3>2\n", "(3.0 SUP 2.0)\r", 1.0);

        testOperator("2<=3\n", "(2.0 INF_EQ 3.0)\r", 1.0);
        testOperator("2<=2\n", "(2.0 INF_EQ 2.0)\r", 1.0);
        testOperator("3<=2\n", "(3.0 INF_EQ 2.0)\r", 0.0);

        testOperator("2>=3\n", "(2.0 SUP_EQ 3.0)\r", 0.0);
        testOperator("2>=2\n", "(2.0 SUP_EQ 2.0)\r", 1.0);
        testOperator("3>=2\n", "(3.0 SUP_EQ 2.0)\r", 1.0);

    }

    private static void testOperator(String s1, String expPrint, double expVal) throws ParseException{
        // Create InputStream from String expression
        InputStream is = new ByteArrayInputStream(s1.getBytes());

        // Redirect stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Instantiate parser with our custom stream and run it
        Jagger.ReInit(is);
        Jagger.mainloop();

        // Retrieve pretty print string and eval value with scanner
        String res = baos.toString();
        Scanner sc = new Scanner(res).useDelimiter("\n");

        String print = sc.next();
        double eval = Double.parseDouble(sc.next());
        assertEquals(expPrint, print);
        assertEquals(expVal, eval);

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }


}