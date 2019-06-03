package su.svn.rd;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyIntScannerTest
{
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    static final String NL = System.lineSeparator();
    MyIntScanner scanner;

    void read(String[] data, Runnable runnable)
    {
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream((String.join(NL, data) + NL).getBytes()));
            scanner = new MyIntScanner(System.in);
            runnable.run();
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void test00() throws IOException
    {
        String data = "";
        InputStream stdin = System.in;
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            scanner = new MyIntScanner(System.in);
            assertEquals(-1, scanner.read());
            assertFalse(scanner.hasNext());
        } finally {
            System.setIn(stdin);
        }
    }

    @Test
    void test01()
    {
        String data[] = new String[]{""};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertFalse(scanner.hasNext());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test02()
    {
        String data[] = new String[]{"0"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test03()
    {
        String data[] = new String[]{"0123456789012345"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertFalse(scanner.hasNext());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test04()
    {
        String data[] = new String[]{"0123456789abcdef"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertFalse(scanner.hasNext());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test05()
    {
        String data[] = new String[]{"abcdef"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertFalse(scanner.hasNext());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test06()
    {
        String data[] = new String[]{"0"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertEquals(0, scanner.next());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test07()
    {
        String data[] = new String[]{"2147483647"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertEquals(2147483647, scanner.next());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test08()
    {
        String data[] = new String[]{"2147483647", "abcd"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertEquals(2147483647, scanner.next());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test09()
    {
        String data[] = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
                assertEquals(0, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(1, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(2, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(3, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(4, scanner.next());
                assertFalse(scanner.hasNext());
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
                assertEquals(5, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(6, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(7, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(8, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(9, scanner.next());
                assertEquals(-1, scanner.read());
            } catch (IOException ignored) { }
        });
    }

    @Test
    void test10()
    {
        String data[] = new String[]{"10", "2", "12", "4", "0", "15", "6", "7", "8", "19"};
        read(data, () -> {
            try {
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
                assertEquals(10, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(2, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(12, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(4, scanner.next());
                assertFalse(scanner.hasNext());
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
                assertEquals(0, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(15, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(6, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(7, scanner.next());
                assertTrue(scanner.hasNext());
                assertEquals(8, scanner.next());
                assertFalse(scanner.hasNext());
                assertTrue(scanner.read() > 0);
                assertTrue(scanner.hasNext());
                assertEquals(19, scanner.next());
                assertEquals(-1, scanner.read());
            } catch (IOException ignored) { }
        });
    }

    private void useCase()
    {
        try (MyIntScanner scanner = new MyIntScanner(System.in)) {
            if (scanner.read() > 0 && scanner.hasNext()) {
                int n = scanner.next();
                assertEquals(9, n);
                for (int i = 0; i < n; ++i) {
                    if (!scanner.hasNext() && (scanner.read() < 0)) {
                        throw new RuntimeException("Unexpected end!");
                    }
                    int v = scanner.next();
                    assertEquals(i, v);
                }
            }
            else {
                throw new RuntimeException("Unexpected begin!");
            }
        } catch (IOException e) {
            throw new RuntimeException("IO Exception: " + e);
        }
    }

    @Test
    void testUseCase()
    {
        String data[] = new String[]{"9", "0", "1", "2", "3", "4", "5", "6", "7", "8" };
        read(data, this::useCase);
    }
}