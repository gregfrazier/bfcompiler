package com.epicmonstrosity.brainfuckvm;

public class AppOptions {
    private String outputFilename;
    private String inputFilename;
    private boolean isByteCodeExecutable;
    private boolean compileInput;

    public String getOutputFilename(){
        return outputFilename;
    }

    public String getInputFilename() {
        return inputFilename;
    }

    public void setInputFilename(String inputFilename) {
        this.inputFilename = inputFilename;
    }

    public void setOutputFilename(String outputFilename) {
        this.outputFilename = outputFilename;
    }

    public boolean isByteCodeExecutable() {
        return isByteCodeExecutable;
    }

    public void setByteCodeExecutable(boolean byteCodeExecutable) {
        isByteCodeExecutable = byteCodeExecutable;
    }

    public boolean isCompileInput() {
        return compileInput;
    }

    public void setCompileInput(boolean compileInput) {
        this.compileInput = compileInput;
    }
}
