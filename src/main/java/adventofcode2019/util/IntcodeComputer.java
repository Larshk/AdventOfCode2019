package adventofcode2019.util;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class IntcodeComputer extends Thread {
    private int instructionPointer;
    private int relativeBase;
    private final Memory originalMemory;
    private Memory memory;
    
    private final BlockingQueue<Long> inputQueue;
    private final BlockingQueue<Long> outputQueue;
    private Long lastOutput = null;

    private enum ParameterMode {
        POSITION, IMMEDIATE, RELATIVE
    }

    private enum Instruction {
        ADD(1L, 3),
        MULTIPLY(2L, 3),
        INPUT(3L, 1),
        OUTPUT(4L, 1),
        JUMP_IF_TRUE(5L, 2),
        JUMP_IF_FALSE(6L, 2),
        LESS_THAN(7L, 3),
        EQUALS(8L, 3),
        ADJUST_RELATIVE_BASE(9L, 1),
        STOP(99L, 0);

        private final long opcode;
        private final int numberOfParameters;

        Instruction(long opcode, int numberOfParameters) {
            this.opcode = opcode;
            this.numberOfParameters = numberOfParameters;
        }

        public static IntcodeComputer.Instruction fromOpcode(long opcode) {
            String opcodeString = String.valueOf(opcode);
            String opcodeOnlyString = opcodeString.length() > 2 ? opcodeString.substring(opcodeString.length() - 2) : opcodeString;
            long opcodeOnly = Long.parseLong(opcodeOnlyString);

            return Arrays.stream(values())
                    .filter(i -> i.opcode == opcodeOnly)
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    private static class Memory {
        private long[] memory;

        private Memory(long[] initialState) {
            init(initialState);
        }

        private Memory(Memory initialState) {
            init(initialState.memory);
        }

        private void init(long[] memory) {
            this.memory = Arrays.copyOf(memory, memory.length);
        }

        private long read(int address) {
            // Read from memory, or return 0 if outside memory space without expanding
            return address >= memory.length ? 0 : memory[address];
        }

        private int readInt(int address) {
            return (int) read(address);
        }

        private void write(int address, long value) {
            if (address >= memory.length) {
                //System.out.println("Expanding memory to " + (address + 1) + " longs");
                memory = Arrays.copyOf(memory, address + 1);
            }
            memory[address] = value;
        }
    }

    public IntcodeComputer(
            long[] memory,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) {
        this.originalMemory = new Memory(memory);
        this.memory = new Memory(memory);

        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    public static IntcodeComputer parseIntcodeCode(
            String input,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) {
        String[] memoryStrings = input.split(",");
        long[] memory = new long[memoryStrings.length];
        int i = 0;
        for (String memoryStr : memoryStrings) {
            memory[i++] = Long.parseLong(memoryStr);
        }
        return new IntcodeComputer(memory, inputQueue, outputQueue);
    }

    public void setNoun(long noun) {
        memory.write(1, noun);
    }

    public void setVerb(long verb) {
        memory.write(2, verb);
    }

    @Override
    public void run() {
        try {
            while (memory.read(instructionPointer) != Instruction.STOP.opcode) {
                cycle();
            }
        } catch (InterruptedException e) {
            System.err.println("IntComputer was interrupted");
        }
    }

    public void reset() {
        memory = new Memory(originalMemory);
        instructionPointer = 0;
        relativeBase = 0;
        inputQueue.clear();
        outputQueue.clear();
    }

    private void cycle() throws InterruptedException {
        long opcode = memory.read(instructionPointer);
        Instruction instruction = Instruction.fromOpcode(opcode);
        int destination;

        int instructionPointerDelta = instruction.numberOfParameters + 1;

        switch (instruction) {
            case ADD:
                destination = getOperandForWrite(opcode, 3);
                memory.write(destination, getOperand(opcode, 1) + getOperand(opcode, 2));
                break;
            case MULTIPLY:
                destination = getOperandForWrite(opcode, 3);
                memory.write(destination, getOperand(opcode, 1) * getOperand(opcode, 2));
                break;
            case INPUT:
                destination = getOperandForWrite(opcode, 1);
                memory.write(destination, inputQueue.take());
                break;
            case OUTPUT:
                long output = getOperand(opcode, 1);
                outputQueue.offer(output);
                lastOutput = output;
                break;
            case JUMP_IF_TRUE:
                if (getOperand(opcode, 1) != 0) {
                    instructionPointer = (int) getOperand(opcode, 2);
                    instructionPointerDelta = 0;
                }
                break;
            case JUMP_IF_FALSE:
                if (getOperand(opcode, 1) == 0) {
                    instructionPointer = (int) getOperand(opcode, 2);
                    instructionPointerDelta = 0;
                }
                break;
            case LESS_THAN:
                destination = getOperandForWrite(opcode, 3);
                memory.write(destination, getOperand(opcode, 1) < getOperand(opcode, 2) ? 1 : 0);
                break;
            case EQUALS:
                destination = getOperandForWrite(opcode, 3);
                memory.write(destination, getOperand(opcode, 1) == getOperand(opcode, 2) ? 1 : 0);
                break;
            case ADJUST_RELATIVE_BASE:
                relativeBase += getOperand(opcode, 1);
                break;
            default:
                throw new IllegalStateException("Unsupported opcode: " + instruction + " at instruction pointer " + instructionPointer);
        }

        instructionPointer += instructionPointerDelta;
    }

    public Long getLastOutput() {
        return lastOutput;
    }

    private long getOperand(long opcode, int i) {
        switch (parameterMode(opcode, i)) {
            case POSITION:
                return memory.read(memory.readInt(instructionPointer + i));
            case IMMEDIATE:
                return memory.read(instructionPointer + i);
            case RELATIVE:
                return memory.read(memory.readInt(instructionPointer + i) + relativeBase);
            default:
                throw new IllegalStateException("Unknown parameter mode " + parameterMode(opcode, i));
        }
    }

    private int getOperandForWrite(long opcode, int i) {
        switch (parameterMode(opcode, i)) {
            case POSITION:
                return memory.readInt(instructionPointer + i);
            case IMMEDIATE:
                throw new IllegalStateException("Parameter mode IMMEDIATE used to resolve write address");
            case RELATIVE:
                return memory.readInt(instructionPointer + i) + relativeBase;
            default:
                throw new IllegalStateException("Unknown parameter mode " + parameterMode(opcode, i));
        }
    }

    private ParameterMode parameterMode(long opcode, int parameterIndex) {
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
