package com.epicmonstrosity.brainfuckvm.compiler;

import java.util.List;
import java.util.stream.Collectors;

public class BrainFudgeCompiler {
    private final List<Operations> src;
    public BrainFudgeCompiler(List<Operations> src) {
        this.src = src;
    }
    public List<Byte> generate() {
        return this.src.stream().map(x -> ByteCode.code().fromOperation(x)).collect(Collectors.toList());
    }
}
