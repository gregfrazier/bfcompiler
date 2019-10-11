package com.epicmonstrosity.brainfuckvm.compress;

import java.util.ArrayList;
import java.util.List;

public class Shrinker {
    public static List<Byte> shrink(List<Byte> bytes) {
        List<Byte> output = new ArrayList<>();
        for (int i = 0; i < bytes.size(); i += 2) {
            final byte aByte = bytes.get(i);
            final byte bByte = i + 1 < bytes.size() ? bytes.get(i + 1) : 0xF;
            final byte smooched = (byte)(aByte << 4 | bByte);
            output.add(smooched);
        }
        return output;
    }

    public static List<Byte> unshrink(List<Byte> bytes) {
        List<Byte> output = new ArrayList<>();
        for (byte smooched: bytes) {
            byte aByte = (byte)((smooched >> 4) & 0xF);
            byte bByte = (byte)(smooched & 0xF);
            output.add(aByte);
            if(bByte != 0xF)
                output.add(bByte);
        }
        return output;
    }
}
