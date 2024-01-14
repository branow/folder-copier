package com.branow.copier.data;

import com.branow.editors.serialization.DTO;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CopierDTO implements DTO<CopierData> {

    public String output;
    public String[] inputs;

    @Override
    public DTO<CopierData> form(CopierData base) {
        output = base.getOutput().toString();
        inputs = base.getInputs().stream().map(Path::toString).toArray(String[]::new);
        return this;
    }

    @Override
    public CopierData form() {
        return new CopierData(Path.of(output), Arrays.stream(inputs).map(Path::of).collect(Collectors.toList()));
    }
}
