package adventofcode2019.util;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class IntcodeComputer extends Thread {
    private int instructionPointer;
    private final int[] originalMemory;
    private final int[] memory;
    
    private final BlockingQueue<Integer> inputQueue;
    private final BlockingQueue<Integer> outputQueue;
    private Integer lastOutput = null;

    private enum ParameterMode {
        POSITION, IMMEDIATE
    }

    private enum Instruction {
        ADD(1, 3),
        MULTIPLY(2, 3),
        INPUT(3, 1),
        OUTPUT(4, 1),
        JUMP_IF_TRUE(5, 2),
        JUMP_IF_FALSE(6, 2),
        LESS_THAN(7, 3),
        EQUALS(8, 3),
        STOP(99, 0);

        private final int opcode;
        private final int numberOfParameters;

        Instruction(int opcode, int numberOfParameters) {
            this.opcode = opcode;
            this.numberOfParameters = numberOfParameters;
        }

        public static IntcodeComputer.Instruction fromOpcode(int opcode) {
            String opcodeString = String.valueOf(opcode);
            String opcodeOnlyString = opcodeString.length() > 2 ? opcodeString.substring(opcodeString.length() - 2) : opcodeString;
            int opcodeOnly = Integer.parseInt(opcodeOnlyString);

            return Arrays.stream(values())
                    .filter(i -> i.opcode == opcodeOnly)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public IntcodeComputer(
            int[] memory,
            BlockingQueue<Integer> inputQueue,
            BlockingQueue<Integer> outputQueue) {
        this.originalMemory = Arrays.copyOf(memory, memory.length);
        this.memory = Arrays.copyOf(memory, memory.length);

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public static IntcodeComputer parseIntcodeCode(
            String input,
            BlockingQueue<Integer> inputQueue,
            BlockingQueue<Integer> outputQueue) {
        String[] memoryStrings = input.split(",");
        int[] memory = new int[memoryStrings.length];
        int i = 0;
        for (String memoryStr : memoryStrings) {
            memory[i++] = Integer.parseInt(memoryStr);
        }
        return new IntcodeComputer(memory, inputQueue, outputQueue);
    }

    public void setNoun(int noun) {
        memory[1] = noun;
    }

    public void setVerb(int verb) {
        memory[2] = verb;
    }

    @Override
    public void run() {
        try {
            while (memory[instructionPointer] != 99) {
                cycle();
            }
        } catch (InterruptedException e) {
            System.err.println("IntComputer was interrupted");
        }
    }

    public void reset() {
        System.arraycopy(originalMemory, 0, memory, 0, originalMemory.length);
        instructionPointer = 0;
        inputQueue.clear();
        outputQueue.clear();
    }

    private void cycle() throws InterruptedException {
        int opcode = memory[instructionPointer];
        Instruction instruction = Instruction.fromOpcode(opcode);
        int destination;

        int instructionPointerDelta = instruction.numberOfParameters + 1;

        switch (instruction) {
            case ADD:
                destination = memory[instructionPointer + 3];
                memory[destination] = getOperand(opcode, 1) + getOperand(opcode, 2);
                break;
            case MULTIPLY:
                destination = memory[instructionPointer + 3];
                memory[destination] = getOperand(opcode, 1) * getOperand(opcode, 2);
                break;
            case INPUT:
                destination = memory[instructionPointer + 1];
                memory[destination] = inputQueue.take();
                break;
            case OUTPUT:
                int output = getOperand(opcode, 1);
                outputQueue.offer(output);
                lastOutput = output;
                break;
            case JUMP_IF_TRUE:
                if (getOperand(opcode, 1) != 0) {
                    instructionPointer = getOperand(opcode, 2);
                    instructionPointerDelta = 0;
                }
                break;
            case JUMP_IF_FALSE:
                if (getOperand(opcode, 1) == 0) {
                    instructionPointer = getOperand(opcode, 2);
                    instructionPointerDelta = 0;
                }
                break;
            case LESS_THAN:
                destination = memory[instructionPointer + 3];
                memory[destination] = getOperand(opcode, 1) < getOperand(opcode, 2) ? 1 : 0;
                break;
            case EQUALS:
                destination = memory[instructionPointer + 3];
                memory[destination] = getOperand(opcode, 1) == getOperand(opcode, 2) ? 1 : 0;
                break;
            default:
                throw new IllegalStateException("Unsupported opcode: " + instruction + " at instruction pointer " + instructionPointer);
        }

        instructionPointer += instructionPointerDelta;
    }

    public Integer getLastOutput() {
        return lastOutput;
    }

    private int getOperand(int opcode, int i) {
        return parameterMode(opcode, i) == ParameterMode.POSITION
                ? memory[memory[instructionPointer + i]]
                : memory[instructionPointer + i];
    }

    private ParameterMode parameterMode(int opcode, int parameterIndex) {
        String opcodeString = String.valueOf(opcode);
        if (opcodeString.length() < 2) {
            return ParameterMode.POSITION;
        }
        opcodeString = opcodeString.substring(0, opcodeString.length() - 2);

        if (opcodeString.length() - parameterIndex < 0) {
            return ParameterMode.POSITION;
        }

        return ParameterMode.values()[Integer.parseInt("" + opcodeString.charAt(opcodeString.length() - parameterIndex))];
    }
}
