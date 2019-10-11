package com.epicmonstrosity.brainfuckvm.vm;

public class Alu {
    private final Memory mem;

    public Alu(Memory mem){
        this.mem = mem;
    }
    void incMemory(int PC) {
        mem.setValue(PC, (byte) (mem.getValue(PC) + 1));
    }
    void decMemory(int PC) {
        mem.setValue(PC, (byte) (mem.getValue(PC) - 1));
    }
    boolean zeroCmpMemory(int PC) {
        return mem.getValue(PC) == 0;
    }
}
