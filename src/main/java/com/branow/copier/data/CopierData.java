package com.branow.copier.data;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CopierData {

    private Path output;
    private final List<Path> inputs;

    public CopierData() {
        this(Path.of(""), new ArrayList<>());
    }

    public CopierData(Path output, List<Path> inputs) {
        this.output = output;
        this.inputs = inputs;
    }

    public void setOutput(Path output) {
        this.output = output;
    }

    public void addInput(Path path) {
        inputs.add(path);
    }

    public void removeInput(Path path) {
        inputs.remove(path);
    }

    public void removeInputs(List<Path> paths) {
        inputs.removeAll(paths);
    }

    public Path getOutput() {
        return output;
    }

    public List<Path> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return "CopierData{" +
                "output=" + output +
                ", \ninputs=" + inputs +
                '}';
    }
}
