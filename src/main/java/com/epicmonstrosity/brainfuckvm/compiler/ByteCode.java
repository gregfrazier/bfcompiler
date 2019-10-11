package com.epicmonstrosity.brainfuckvm.compiler;

import java.util.EnumMap;
import java.util.Map;

class ByteCode {
    private final Map<Operations, Byte> translations;
    private static ByteCode byteCodeInstance;
    static ByteCode code() {
        if(byteCodeInstance == null)
            byteCodeInstance = new ByteCode();
        return byteCodeInstance;
    }
    private ByteCode() {
        this.translations = new EnumMap<Operations, Byte>(Operations.class);
        translations.put(Operations.RIGHT, (byte)0);
        translations.put(Operations.LEFT, (byte)1);
        translations.put(Operations.INC, (byte)2);
        translations.put(Operations.DEC, (byte)3);
        translations.put(Operations.OUTPUT, (byte)4);
        translations.put(Operations.INPUT, (byte)5);
        translations.put(Operations.LOOP_START, (byte)6);
        translations.put(Operations.LOOP_END, (byte)7);
    }
    Byte fromOperation(Operations op) {
        return translations.get(op);
    }
}
