package adventofcode2019.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.function.Function;
import java.util.stream.Stream;

public class InputUtil {
    public static <T> Stream<T> inputLines(
            String input,
            Function<? super String, ? extends T> mappingFunction) {
        return new BufferedReader(new StringReader(input)).lines().map(mappingFunction);
    }
}
