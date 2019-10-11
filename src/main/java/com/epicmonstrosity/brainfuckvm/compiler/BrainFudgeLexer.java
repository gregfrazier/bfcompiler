package com.epicmonstrosity.brainfuckvm.compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BrainFudgeLexer {
    public static List<Operations> prepareFile(String filename) {
        List<Operations> ops = new ArrayList<>();
        try(FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            // Convert into a stream of operations
            while((line = bufferedReader.readLine()) != null)
                ops.addAll(parseLine(line.trim()));
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
            throw new RuntimeException("File not found");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
            throw new RuntimeException("File cannot be read");
        }
        return ops;
    }
    private static List<Operations> parseLine(String line) {
        return line.chars()
            .mapToObj(x -> (char) x)
            .map(c -> {
                switch(c) {
                    case '>': return Operations.RIGHT;
                    case '<': return Operations.LEFT;
                    case '+': return Operations.INC;
                    case '-': return Operations.DEC;
                    case '.': return Operations.OUTPUT;
                    case ',': return Operations.INPUT;
                    case '[': return Operations.LOOP_START;
                    case ']': return Operations.LOOP_END;
                    default: return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
