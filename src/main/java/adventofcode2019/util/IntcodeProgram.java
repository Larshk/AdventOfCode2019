package adventofcode2019.util;

import java.util.Arrays;

public class IntcodeProgram {
    private int instructionPointer;
    private final int[] originalMemory;
    private final int[] memory;

    private enum Instruction {
        ADD(1, 3),
        MULTIPLY(2, 3),
        STOP(99, 0);

        private final int opcode;
        private final int numberOfParameters;

        Instruction(int opcode, int numberOfParameters) {
            this.opcode = opcode;
            this.numberOfParameters = numberOfParameters;
        }

        public static Instruction fromOpcode(int opcode) {
            return Arrays.stream(values())
                    .filter(i -> i.opcode == opcode)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public static IntcodeProgram parseIntcodeCode(String input) {
        String[] memoryStrings = input.split(",");
        int[] memory = new int[memoryStrings.length];
        int i = 0;
        for (String memoryStr : memoryStrings) {
            memory[i++] = Integer.parseInt(memoryStr);
        }
        return new IntcodeProgram(memory);
    }

    private IntcodeProgram(int[] memory) {
        this.originalMemory = Arrays.copyOf(memory, memory.length);
        this.memory = Arrays.copyOf(memory, memory.length);
    }

    public void setNoun(int noun) {
        memory[1] = noun;
    }

    public void setVerb(int verb) {
        memory[2] = verb;
    }

    public void run() {
        while (memory[instructionPointer] != 99) {
            cycle();
        }
    }

    public void reset() {
        System.arraycopy(originalMemory, 0, memory, 0, originalMemory.length);
        instructionPointer = 0;
    }

    public int peek(int programPointer) {
        return memory[programPointer];
    }

    public int[] getMemoryCopy() {
        return Arrays.copyOf(memory, memory.length);
    }

    private void cycle() {
        int opcode = memory[instructionPointer];
        Instruction instruction = Instruction.fromOpcode(opcode);

        int destination;

        switch (instruction) {
            case ADD:
                destination = memory[instructionPointer + 3];
                memory[destination] = memory[memory[instructionPointer + 1]] + memory[memory[instructionPointer + 2]];
                break;
            case MULTIPLY:
                destination = memory[instructionPointer + 3];
                memory[destination] = memory[memory[instructionPointer + 1]] * memory[memory[instructionPointer + 2]];
                break;
            default:
                throw new IllegalStateException();
        }

        instructionPointer += instruction.numberOfParameters + 1;
    }
}
