package su.svn;

import su.svn.rd.MyIntScanner;

import java.io.IOException;

public class Main
{
    public static void main(String[] args)
    {
        try (MyIntScanner scanner = new MyIntScanner(System.in)) {
            if (scanner.read() > 0 && scanner.hasNext()) {
                int n = scanner.next();
                System.out.println("n = " + n);
                for (int i = 0; i < n; ++i) {
                    if (!scanner.hasNext() && (scanner.read() < 0)) {
                        throw new RuntimeException("Unexpected end!");
                    }
                    int v = scanner.next();
                    System.out.println("v = " + v);
                }
            } else {
                throw new RuntimeException("Unexpected begin!");
            }
        } catch (IOException e) {
            throw new RuntimeException("IO Exception: " + e);
        }
    }
}
