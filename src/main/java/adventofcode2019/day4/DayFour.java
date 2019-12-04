package adventofcode2019.day4;

import adventofcode2019.DailyTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Day 4: Secure Container
 * https://adventofcode.com/2019/day/4
 */
public class DayFour implements DailyTask {
    private static final Pattern REPETITION_PATTERN = Pattern.compile("([0-9])\\1+");

    @Override
    public String solvePartOne(String input) {
        String[] range = input.split("-");
        int rangeFrom = Integer.parseInt(range[0]);
        int rangeTo = Integer.parseInt(range[1]);

        return String.valueOf(
                IntStream.range(rangeFrom, rangeTo + 1)
                        .filter(password -> matchesCriteria(password, false))
                        .count()
        );
    }

    @Override
    public String solvePartTwo(String input) {
        String[] range = input.split("-");
        int rangeFrom = Integer.parseInt(range[0]);
        int rangeTo = Integer.parseInt(range[1]);

        return String.valueOf(
                IntStream.range(rangeFrom, rangeTo + 1)
                        .filter(password -> matchesCriteria(password, true))
                        .count()
        );
    }

    private static boolean matchesCriteria(int password, boolean requireExactlyTwoRepetitions) {
        String passwordString = String.valueOf(password);

        // It is a six-digit number.
        if (passwordString.length() != 6) {
            return false;
        }

        Matcher m = REPETITION_PATTERN.matcher(passwordString);
        boolean passwordHasRequiredRepetitions = false;
        while (m.find() && !passwordHasRequiredRepetitions) {
            if (requireExactlyTwoRepetitions && m.group().length() == 2) {
                passwordHasRequiredRepetitions = true;
            }
            if (!requireExactlyTwoRepetitions && m.group().length() >= 2) {
                passwordHasRequiredRepetitions = true;
            }
        }

        if (!passwordHasRequiredRepetitions) {
            return false;
        }

        for (int i = 1; i < passwordString.length(); i++) {
            char currentDigit = passwordString.charAt(i);
            char previousDigit = passwordString.charAt(i - 1);
            if (currentDigit < previousDigit) {
                return false;
            }
        }

        return true;
    }
}
