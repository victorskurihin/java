package com.github.intermon.server;

/*
 * Created by VSkurikhin at Fri Apr 27 12:50:46 MSK 2018.
 */

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public interface DequeBuffer {
    default boolean isNewLine(ByteBuffer buffer, int index) {
        return '\n' == buffer.get(index);
    }

    default StringBuilder createStringBuilder(ByteBuffer buffer, int offset, int index) {
        return new StringBuilder(new String(buffer.array(), offset, index - offset));
    }

    default
    StringBuilder addToStringBuilder(StringBuilder sb, ByteBuffer buff, int offset, int index)
    {
        return sb.append(new String(buff.array(), offset, index - offset));
    }

    default boolean lastNotString(Deque<StringBuilder> deque) {
        String last = deque.getLast().toString();

        return last.length() < 2
            || '\n' != last.charAt(last.length() - 2)
            && '\n' != last.charAt(last.length() - 1);

    }

    default boolean isDoubleNewLine(ByteBuffer buffer, int size, int index) {
        return index < (size - 2)
            && isNewLine(buffer, index)
            && isNewLine(buffer,index + 1);
    }

    default int addBuffer(Deque<StringBuilder> deque, ByteBuffer buffer, int size) {
        int offset = 0, index = 0;

        StringBuilder sb = ! deque.isEmpty() && lastNotString(deque)
                         ? deque.pollLast()
                         : new StringBuilder();

        while (index < size) {
            if (isDoubleNewLine(buffer, size, index)) {
                // System.out.printf("1 index = %d, offset = %d\n", index, offset);
                deque.add(addToStringBuilder(sb, buffer, offset, index).append("\n\n"));
                sb = new StringBuilder();
                offset = index += 2;
            } else
                ++index;
        }

        if (index > offset) {
            // System.out.printf("2 index = %d, offset = %d\n", index, offset);
            deque.add(addToStringBuilder(sb, buffer, offset, index));
        }

        return index;
    }

    default int addBuffer1(Deque<StringBuilder> deque, ByteBuffer buffer, int size) {
        int offset = 0, index = 0;

        if ( ! deque.isEmpty() && lastNotString(deque)) {
            boolean isLine = false;
            StringBuilder sb = deque.pollLast();

            while (index < size) {
                if (isLine = isDoubleNewLine(buffer, size, index))
                    break;
                ++index;
            }

            if (isLine) {
                // System.out.printf("1 index = %d, offset = %d\n", index, offset);
                deque.add(addToStringBuilder(sb, buffer, offset, index).append("\n\n"));
                index += 2;
            } else if (index > offset) {
                // System.out.printf("2 index = %d, offset = %d\n", index, offset);
                deque.add(addToStringBuilder(sb, buffer, offset, index));
            }
            offset = index;
        }

        while (index < size) {
            if (isDoubleNewLine(buffer, size, index)) {
                StringBuilder sb = createStringBuilder(buffer, offset, index);
                // System.out.printf("3 index = %d, offset = %d\n", index, offset);
                deque.add(sb.append('\n').append('\n'));
                index += 2;
                offset = index;
            } else {
                ++index;
            }
        }
        if (index > offset) {
            // System.out.printf("4 index = %d, offset = %d\n", index, offset);
            deque.add(createStringBuilder(buffer, offset, index));
        }

        return index;
    }

    default boolean lineExists(Deque<StringBuilder> deque) {
        if ( ! deque.isEmpty()) {
            StringBuilder sb = deque.peek();
            if (sb.length() > 1)
                return '\n' == sb.charAt(sb.length() - 2)
                    && '\n' == sb.charAt(sb.length() - 1);
        }
        return false;
    }

    default List<String> getStringsFromBuffer(Deque<StringBuilder> deque) {
        List<String> result = new ArrayList<>();

        while (lineExists(deque)) {
            StringBuilder sb = deque.poll();
            sb.delete(sb.length() - 2, sb.length() - 1);
            result.add(sb.toString().trim());
        }
        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
