package su.svn.rd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MyIntScanner implements Closeable
{
    public final static int BYTES_LIMIT = 16;
    private final static int radix = 10;
    private final static Logger LOGGER = LoggerFactory.getLogger(MyIntScanner.class);
    private final BufferedInputStream input;

    private int offset = 0;
    private int bytesRead = 0;
    private byte[] bytes = new byte[BYTES_LIMIT];

    public MyIntScanner(InputStream is)
    {
        input = new BufferedInputStream(is);
    }

    public int read() throws IOException
    {
        bytesRead = input.read(bytes, offset, bytes.length - offset);
        offset += bytesRead;
        LOGGER.debug("bytes readed: {}", bytesRead);
        LOGGER.debug("bytes offset: {}", offset);
        LOGGER.trace(Arrays.toString(bytes));

        return bytesRead;
    }

    static int parseInt(byte[] s, int len) throws NumberFormatException
    {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        int result = 0;
        boolean negative = false;
        int i = 0; //, len = s.length;
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = (char)s[0];
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+')
                    throw new NumberFormatException(Arrays.toString(s));

                if (len == 1) // Cannot have lone "+" or "-"
                    throw new NumberFormatException(Arrays.toString(s));
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s[i++], radix);
                if (digit < 0) {
                    throw new NumberFormatException(Arrays.toString(s));
                }
                if (result < multmin) {
                    throw new NumberFormatException(Arrays.toString(s));
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new NumberFormatException(Arrays.toString(s));
                }
                result -= digit;
            }
        } else {
            throw new NumberFormatException(Arrays.toString(s));
        }
        return negative ? result : -result;
    }

    int findNL()
    {
        for (int i = 0; i < offset - 1; ++i) {
            if (13 == bytes[i] && 10 == bytes[i+1]) return i;
        }
        return -1;
    }

    public boolean hasNext()
    {
        LOGGER.trace("bytes readed: {}", bytesRead);
        if (bytesRead < 1) return false;
        int len = findNL();
        LOGGER.trace("finded new line at: {}", len);
        if (len < 1) return false;
        try {
            parseInt(bytes, len);
            LOGGER.debug("ok parsed.");
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void shiftBytes(int position)
    {
        System.arraycopy(bytes, position, bytes, 0, bytes.length - position);
    }

    private void clearTail(int left, int right)
    {
        for (int i = left; i < right; ++i) {
            bytes[i] = 0;
        }
    }

    public int next()
    {
        if (bytesRead < 1) throw new NumberFormatException(Arrays.toString(bytes));
        int len = findNL();
        if (len < 1) throw new NumberFormatException(Arrays.toString(bytes));
        int result = parseInt(bytes, len);
        shiftBytes(len + 2);
        offset = offset - (len + 2);
        clearTail(offset, bytes.length);
        bytesRead -= (len + 2);
        LOGGER.debug("bytes offset: {}", offset);
        LOGGER.trace(Arrays.toString(bytes));

        return result;
    }

    @Override
    public void close() throws IOException
    {
        input.close();
    }
}
