package adventofcode2019.day14;

import adventofcode2019.DailyTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static adventofcode2019.util.InputUtil.inputLines;

/**
 * Day 14: Space Stoichiometry
 * https://adventofcode.com/2019/day/14
 */
public class DayFourteen implements DailyTask {
    private static class Chemical {
        private String name;
        private long quantity;
        private long outputQuantity;
        private Map<Chemical, Long> inputs;

        private Chemical(String name, long outputQuantity) {
            this.name = name;
            this.outputQuantity = outputQuantity;
            this.inputs = new HashMap<>();
        }

        private void addInput(Chemical chemical, long quantity) {
            inputs.put(chemical, quantity);
        }

        private void produce(long amount) {
            if (quantity >= amount || "ORE".equals(name)) {
                quantity -= amount;
            } else {
                // Produce the required amount of each input
                long multiple = (long) Math.ceil((amount - quantity) / (double) outputQuantity);
               inputs.forEach((k, v) -> k.produce(multiple * v));
                quantity += multiple * outputQuantity - amount;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Chemical chemical = (Chemical) o;
            return name.equals(chemical.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    @Override
    public String solvePartOne(String input) {
        Map<String, Chemical> chemicals = parseInput(input);
        return String.valueOf(oreUsedToProduce(chemicals, 1));
    }

    @Override
    public String solvePartTwo(String input) {
        Map<String, Chemical> chemicals = parseInput(input);

        double targetOre = 1E12;

        int low = 0, mid = -1, high = (int) 1E12;
        while (low <= high) {
            mid = (low + high) >>> 1;
            long midVal = oreUsedToProduce(chemicals, mid);
            //System.out.println("Mid " + mid + " ore " + midVal);
            if (midVal < targetOre) {
                low = mid + 1;
            } else if (midVal > targetOre){
                high = mid - 1;
            } else {
                return String.valueOf(mid);
            }
        }
        return String.valueOf(oreUsedToProduce(chemicals, mid) < targetOre ? mid : mid - 1);
    }

    private long oreUsedToProduce(Map<String, Chemical> chemicals, long fuel) {
        chemicals.values().forEach(c -> c.quantity = 0);
        chemicals.get("FUEL").produce(fuel);
        return - chemicals.get("ORE").quantity;
    }

    private Map<String, Chemical> parseInput(String inputText) {
        Map<String, Chemical> chemicals = new HashMap<>();
        Map<String, String[]> chemicalNameToInputs = new HashMap<>();

        Chemical ore = new Chemical("ORE", 1);
        chemicals.put(ore.name, ore);

        inputLines(inputText, line -> line.split("=>"))
                .forEach(parts -> {
                    String[] inputs = parts[0].trim().split(", ");
                    String[] outputParts = parts[1].trim().split(" ");

                    int outputQuantity = Integer.parseInt(outputParts[0].trim());
                    String outputName = outputParts[1].trim();

                    chemicalNameToInputs.put(outputName, inputs);
                    chemicals.put(outputName, new Chemical(outputName, outputQuantity));
                });

        chemicalNameToInputs.forEach((chemicalName, inputs) -> {
            Chemical chemical = chemicals.get(chemicalName);
            for (String input : inputs) {
                String[] inputParts = input.split(" ");
                int quantity = Integer.parseInt(inputParts[0].trim());
                String requiredChemicalName = inputParts[1].trim();
                chemical.addInput(chemicals.get(requiredChemicalName), quantity);
            }
        });

        return chemicals;
    }
}
