package com.epicmonstrosity.brainfuckvm.vm;

import java.util.ArrayDeque;
import java.util.Deque;

public class PointerStack {
    private final Deque<Integer> pointers = new ArrayDeque<>();
    public void savePC(int PC) {
        pointers.push(PC);
    }
    public int popPC() {
        return pointers.pop();
    }
}
