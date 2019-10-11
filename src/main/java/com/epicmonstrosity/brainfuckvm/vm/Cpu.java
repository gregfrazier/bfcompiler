package com.epicmonstrosity.brainfuckvm.vm;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Cpu {
    private List<Byte> byteCode;
    private int PC = 0;
    private int memPointer = 0;
    private Alu alu;
    private Memory mem;
    private PointerStack pointerStack;

    public Cpu() {
        pointerStack = new PointerStack();
        mem = new Memory(30000);
        alu = new Alu(mem);
    }
    public void reset() {
        PC = 0;
        memPointer = 0;
    }
    public void loadProgram(List<Byte> code) {
        byteCode = code;
        reset();
    }
    public boolean single() {
        int op = byteCode.get(PC);
        switch(op) {
            case 0: memPointer++; break;
            case 1: memPointer--; break;
            case 2: alu.incMemory(memPointer); break;
            case 3: alu.decMemory(memPointer); break;
            case 4: System.out.print( (char) (mem.getValue(memPointer) & 0xFF) ); break;
            case 5: mem.setValue(memPointer, readSingleKey()); break;
            case 6:
                pointerStack.savePC(PC);
                if(alu.zeroCmpMemory(memPointer)) {
                    Deque<Integer> r = new ArrayDeque<>();
                    r.push(1);
                    while(!r.isEmpty()) {
                        final Byte p = byteCode.get(++PC);
                        if(p == 6) r.push(1);
                        if(p == 7) r.pop();
                    }
                    pointerStack.popPC();
                }
                break;
            case 7:
                PC = pointerStack.popPC() - 1;
                break;
        }
        ++PC;
        return byteCode.size() > PC;
    }
    private byte readSingleKey() {
        try {
            return (byte)(System.in.read() & 0xFF);
        } catch(IOException ex) {
            System.out.printf("\nCannot read input\n");
            return 0;
        }
    }
}
