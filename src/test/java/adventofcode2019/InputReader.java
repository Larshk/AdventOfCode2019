package adventofcode2019;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class InputReader {
    public static String getInput(String file) throws IOException {
        InputStream stream = InputReader.class.getResourceAsStream(file);
        return stream != null
                ? IOUtils.toString(InputReader.class.getResourceAsStream(file),"UTF-8")
                : null;
    }
}