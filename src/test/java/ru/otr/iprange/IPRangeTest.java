package ru.otr.iprange;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class IPRangeTest {

    private static ByteArrayOutputStream expectedOutput = new ByteArrayOutputStream();
    private static OutputStreamWriter osw = new OutputStreamWriter(expectedOutput);

    @BeforeClass
    public static void setUp() throws IOException {
        osw.write("192.168.0.2\n");
        osw.write("192.168.0.3\n");
        osw.write("192.168.0.4\n");
        osw.flush();
    }

    @Test
    public void printRangeSuccess() throws IOException {
        String start = "192.168.0.1";
        String end = "192.168.0.5";

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        IPRange.printRange(start, end);
        actualOutput.flush();

        assertArrayEquals(expectedOutput.toByteArray(), actualOutput.toByteArray());
    }

    @Test
    public void printRangeSwap() throws IOException {
        String start = "192.168.0.5";
        String end = "192.168.0.1";

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(actualOutput));

        IPRange.printRange(start, end);
        actualOutput.flush();

        assertArrayEquals(expectedOutput.toByteArray(), actualOutput.toByteArray());
    }

    @Test
    public void wrongInput() throws IOException {
        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        System.setErr(new PrintStream(actualOutput));

        IPRange.printRange("192.168.256.1", "192.168.0.1");
        actualOutput.flush();
        assertEquals("Введенные ip-адреса некорректны\n", actualOutput.toString());
        actualOutput.reset();

        IPRange.printRange("127.0.0.1", null);
        assertEquals("Введенные ip-адреса некорректны\n", actualOutput.toString());
        actualOutput.reset();

        IPRange.printRange(null, "");
        assertEquals("Введенные ip-адреса некорректны\n", actualOutput.toString());


    }
}