package adventofcode2019.day6;

import adventofcode2019.DailyTask;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import static adventofcode2019.util.InputUtil.inputLines;

/**
 * Day 6: Universal Orbit Map
 * https://adventofcode.com/2019/day/6
 */
public class DaySix implements DailyTask {
    private static final String YOU = "YOU";
    private static final String SANTA = "SAN";

    @Override
    public String solvePartOne(String input) {
        Map<String, String> orbits = inputLines(input, this::mapOrbit).collect(Collectors.toMap(
                SimpleEntry::getKey,
                SimpleEntry::getValue
        ));

        int orbitCount = 0;
        for (String object : orbits.values()) {
            orbitCount++;
            while (orbits.containsKey(object)) {
                object = orbits.get(object);
                orbitCount++;
            }
        }

        // Return the total number of direct and indirect orbits
        return String.valueOf(orbitCount);
    }

    @Override
    public String solvePartTwo(String input) {
        Map<String, String> orbits = inputLines(input, this::mapOrbit).collect(Collectors.toMap(
                SimpleEntry::getKey,
                SimpleEntry::getValue
        ));

        // Build path from YOU to COM
        List<String> youToCom = new ArrayList<>();
        for (String object = YOU; orbits.containsKey(object); object = orbits.get(object)) {
            youToCom.add(object);
        }

        // Build path from SAN to COM
        List<String> santaToCom = new ArrayList<>();
        for (String object = SANTA; orbits.containsKey(object); object = orbits.get(object)) {
            santaToCom.add(object);
        }

        // Remove the common paths to COM
        ListIterator<String> youToComIter = youToCom.listIterator(youToCom.size());
        ListIterator<String> santaToComIter = santaToCom.listIterator(santaToCom.size());

        while (youToComIter.previous().equals(santaToComIter.previous())) {
            youToComIter.remove();
            santaToComIter.remove();
        }

        // Distance is the remaining path elements minus two for YOU and SAN
        return String.valueOf(youToCom.size() + santaToCom.size() - 2);
    }

    private SimpleEntry<String, String> mapOrbit(String s) {
        String[] parts = s.split("\\)");
        return new SimpleEntry<>(parts[1], parts[0]);
    }
}
