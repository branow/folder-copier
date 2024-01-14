package com.branow.copier.data;

import com.branow.editors.edit.FileText;
import com.branow.editors.serialization.SerializationException;
import com.branow.editors.serialization.TegSerialization;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CopierDataFile {

    private final CopierData data;
    private final FileText text;

    public CopierDataFile(FileText text) {
        this.text = text;
        data = read();
    }

    public void setOutput(Path output) {
        data.setOutput(output);
        write();
    }

    public void addInput(Path path) {
        data.addInput(path);
        write();
    }

    public void removeInput(Path path) {
        data.removeInput(path);
        write();
    }

    public void removeInputs(List<Path> paths) {
        data.removeInputs(paths);
        write();
    }

    public CopierData getData() {
        return new CopierData(getOutput(), getInputs());
    }

    public Path getOutput() {
        return data.getOutput();
    }

    public List<Path> getInputs() {
        return new ArrayList<>(data.getInputs());
    }


    private CopierData read() {
        if (text.isBlank()) return new CopierData();
        try {
            return ((CopierDTO) new TegSerialization<>(text).deserializate()).form();
        } catch (SerializationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void write() {
        try {
            new TegSerialization<>(text).serializate(new CopierDTO().form(data));
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

}
