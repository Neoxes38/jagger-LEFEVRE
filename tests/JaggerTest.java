package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.Jagger;
import src.ParseException;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class JaggerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testBinOp() throws ParseException {
        String s1, res, print;
        double eval;

        // Define expressions to test as String
        s1 = "2*2\n";

        // Create InputStream from String expression
        InputStream is = new ByteArrayInputStream(s1.getBytes());

        // Redirect stdout
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // Instantiate parser with our custom stream and run it
        new Jagger(is);
        Jagger.mainloop();

        // Retrieve pretty print string and eval value with scanner
        res = baos.toString();
        Scanner sc = new Scanner(res).useDelimiter("\n");
        print = sc.next();
        eval = Double.parseDouble(sc.next());

        // Tests
        assertEquals("(2.0 MULT 2.0)", print, "Custom error message");
        assertEquals(4.0, eval, "Custom error message");

        // Revert back to original stdout
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println(res);
    }
}