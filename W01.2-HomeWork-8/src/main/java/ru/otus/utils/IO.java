package ru.otus.utils;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IO
{
    /**
     * Read all text from a file.
     * Memory utilization
     * The method preserves line breaks, can temporarily require memory several
     * times the size of the file, because for a short time the raw file contents
     * (a byte array), and the decoded characters (each of which is 16 bits even
     * if encoded as 8 bits in the file) reside in memory at once. It is safest
     * to apply to files that you know to be small relative to the available
     * memory.
     *
     * @param path a file path
     * @param encoding Character encoding.
     * @return string from the contents of a file
     * @throws IOException
     */
    public static String readFile(String path, Charset encoding)
    throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String readInputStream(InputStream is, String encoding) throws IOException
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try  {
            br = new BufferedReader(new InputStreamReader(is, encoding));

            String line = br.readLine();

            while (line != null) {
                sb.append(line).append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();
        } catch (IOException e) {
            throw e;
        }
        finally {
            if (br != null) br.close();
            br = null;
        }
    }

    public static void saveResultToTruncatedFile(String result, String path)
    throws URISyntaxException, IOException
    {
        File file = Paths.get(new URI(path)).toFile();

        try (FileChannel fc = new FileOutputStream(file, true).getChannel()) {
            fc.truncate(0);
        }

        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print(result);
        }
    }

    public static String getAbsolutePath(String relativePath)
    {
        return (new File(relativePath)).getAbsolutePath();
    }

    public static String converURIToAbsolutePath(URI uri)
    {
        return Paths.get(uri).toFile().getAbsolutePath();
    }

    public static String converURIToAbsolutePath(String uri) throws URISyntaxException
    {
        return converURIToAbsolutePath(new URI(uri));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
