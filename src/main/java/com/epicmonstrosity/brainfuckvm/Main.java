package com.epicmonstrosity.brainfuckvm;

import com.epicmonstrosity.brainfuckvm.compiler.BrainFudgeCompiler;
import com.epicmonstrosity.brainfuckvm.compiler.BrainFudgeLexer;
import com.epicmonstrosity.brainfuckvm.compiler.Operations;
import com.epicmonstrosity.brainfuckvm.compress.Shrinker;
import com.epicmonstrosity.brainfuckvm.vm.Cpu;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    private static Options opts = new Options();
    private static AppOptions appOptions = new AppOptions();

    public static void main(String[] args) throws ParseException {
        populateOptions();
        if(args.length > 0) {
            processOptions(parseArgs(args));
            List<Byte> bytecode;
            if (!appOptions.isByteCodeExecutable()) {
                final List<Operations> operations = BrainFudgeLexer.prepareFile(appOptions.getInputFilename());
                BrainFudgeCompiler compiler = new BrainFudgeCompiler(operations);
                bytecode = compiler.generate();
                if (appOptions.isCompileInput()) {
                    // put bytecode into file
                    Byte[] bytes1 = Shrinker.shrink(bytecode).toArray(new Byte[0]);
                    writeByteCodeFile(ArrayUtils.toPrimitive(bytes1), appOptions.getOutputFilename());
                    System.out.println("Compiled");
                    return;
                }
            } else {
                // Load Bytecode into List
                bytecode = Shrinker.unshrink(Objects.requireNonNull(readByteCodeFile(appOptions.getInputFilename())));
            }
            Cpu cpu = new Cpu();
            cpu.loadProgram(bytecode);
            while (cpu.single()) ;
        } else {
            printHelp();
        }
    }

    private static void populateOptions() {
        opts.addOption(
                Option.builder("c").longOpt("compile").hasArg().argName("OUTPUT_FILE")
                        .desc("Compile to bytecode file; requires output filename").required(false).build()
        );
        opts.addOption(
                Option.builder("e").longOpt("execute").hasArg(false)
                        .desc("Execute compiled bytecode").required(false).build()
        );
        opts.addOption(
                Option.builder("i").longOpt("input").hasArg().argName("INPUT_FILE")
                        .desc("Brainfuck Script; runs by default").required(true).build()
        );
    }

    private static void printHelp() {
        String header = "Brainfudge - Brainfuck Compiler and VM\n\n";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("bfcompile", header, opts, null, true);
    }

    private static CommandLine parseArgs(String[] args) throws ParseException {
        CommandLineParser p = new DefaultParser();
        return p.parse(opts, args);
    }

    private static void processOptions(CommandLine cmd) {
        if(cmd.hasOption("c")){
            String outputFilename = cmd.getOptionValue("c");
            if(outputFilename == null) {
                printHelp();
                return;
            }
            appOptions.setOutputFilename(outputFilename);
            appOptions.setCompileInput(true);
        }
        if(cmd.hasOption("i")){
            String infilename = cmd.getOptionValue("i");
            if(infilename == null) {
                printHelp();
                return;
            }
            appOptions.setInputFilename(infilename);
        }
        if(cmd.hasOption("e"))
            appOptions.setByteCodeExecutable(true);
    }

    private static void writeByteCodeFile(byte[] bFile, String fileDest) {
        try {
            Path path = Paths.get(fileDest);
            Files.write(path, bFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Byte> readByteCodeFile(String fileSrc) {
        final Path path = Paths.get(fileSrc);
        try {
            final byte[] bytes = Files.readAllBytes(path);
            return Arrays.asList(ArrayUtils.toObject(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
