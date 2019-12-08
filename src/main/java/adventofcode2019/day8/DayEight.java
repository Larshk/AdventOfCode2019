package adventofcode2019.day8;

import adventofcode2019.DailyTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Day 8: Space Image Format
 * https://adventofcode.com/2019/day/8
 *
 * A very quick and dirty solution :)
 */
public class DayEight implements DailyTask {
    private static final int LAYER_WIDTH = 25;
    private static final int LAYER_HEIGHT = 6;

    @Override
    public String solvePartOne(String input) {
        List<int[]> layers = new ArrayList<>();
        int layerLength = LAYER_WIDTH * LAYER_HEIGHT;

        for (int i = 0; i < input.length(); i++) {
            int digit = Integer.parseInt("" + input.charAt(i));
            int layerIndex = i / layerLength;

            int[] layer;
            if (layerIndex < layers.size()) {
                layer = layers.get(layerIndex);
            } else {
                layer = new int[10];
                layers.add(layer);
            }

            layer[digit]++;
        }

        int fewestZeroes = Integer.MAX_VALUE;
        int layerWithFewestZeroes = 0;

        for (int i = 0; i < layers.size(); i++) {
            int[] layer = layers.get(i);

            if (layer[0] < fewestZeroes) {
                fewestZeroes = layer[0];
                layerWithFewestZeroes = i;
            }
        }

        return String.valueOf(layers.get(layerWithFewestZeroes)[1] * layers.get(layerWithFewestZeroes)[2]);
    }

    @Override
    public String solvePartTwo(String input) {
        List<int[]> layers = new ArrayList<>();
        int layerLength = LAYER_WIDTH * LAYER_HEIGHT;
        int layerCount = input.length() / layerLength;
        for (int i = 0; i < layerCount; i++) {
            layers.add(new int[layerLength]);
        }

        for (int i = 0; i < input.length(); i++) {
            int[] layer = layers.get(i / layerLength);
            int indexInLayer = i % layerLength;
            layer[indexInLayer] = Integer.parseInt("" + input.charAt(i));
        }

        int[] mergedLayer = Arrays.copyOf(layers.get(layerCount - 1), layerLength);
        for (ListIterator<int[]> iter = layers.listIterator(layers.size() - 1); iter.hasPrevious(); ) {
            int[] layer = iter.previous();
            for (int i = 0; i < layerLength; i++) {
                if (layer[i] == 0 || layer[i] == 1) {
                    mergedLayer[i] = layer[i];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int y = 0; y < LAYER_HEIGHT; y++) {
            for (int x = 0; x < LAYER_WIDTH; x++) {
                int pixel = mergedLayer[i++];
                sb.append(pixel == 0 ? " " : pixel == 1 ? "#" : " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
