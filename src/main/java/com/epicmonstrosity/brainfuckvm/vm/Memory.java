package com.epicmonstrosity.brainfuckvm.vm;

import java.nio.BufferOverflowException;

public class Memory {
    private Byte[] mem;
    private int memSize = 0;
    public Memory(int size) {
        memSize = size;
        mem = new Byte[size];
    }
    public int getValue(int index) {
        if(index < memSize && index > -1)
            return mem[index] == null ? 0 : mem[index];
        throw new BufferOverflowException();
    }
    public int setValue(int index, byte value) {
        if(index < memSize && index > -1) {
            mem[index] = value;
            return value;
        }
        throw new BufferOverflowException();
    }
}
