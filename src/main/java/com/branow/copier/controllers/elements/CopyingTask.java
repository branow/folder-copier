package com.branow.copier.controllers.elements;

import com.branow.copier.data.CopierData;
import com.branow.editors.edit.FilesContentEditor;
import com.branow.editors.edit.FilesEditor;
import com.branow.outfits.checker.ParametersChecker;
import javafx.concurrent.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CopyingTask extends Task<String> {

    private final CopierData data;
    private int doneWork;
    private int totalWork;
    private StringBuilder value = new StringBuilder();

    public CopyingTask(CopierData data) {
        super();
        this.data = data;
        updateMessage("Press 'start' to start copying");
        updateTitle("Ready");
        updateValue("");
    }

    @Override
    protected String call() throws Exception {
        Path output = data.getOutput();
        List<Path> inputs = data.getInputs();

        totalWork = FilesEditor.goInDeep(output).size();
        updateTitle("Copying");
        updateProgress(doneWork, totalWork);

        for (int i=0; i<inputs.size(); i++) {
            Path path = inputs.get(i);
            updateMessage("copying " + path);
            copyFullWithReplacement(path, output);
            updateProgress(i+1, inputs.size());
        }
        updateTitle("Finish");
        updateMessage("Copying was successful");
        return value.toString();
    }

    private Path copyFullWithReplacement(Path src, Path targetDir) throws IOException {
        Path target = copyWithReplacement(src, targetDir);
        if (FilesEditor.isFile(src)) return target;
        updateMessage("deleting excessive files");
        FilesEditor.deleteExcessiveFiles(FilesEditor.getChildren(src), FilesEditor.getChildren(target));
        for (Path path: FilesEditor.getChildren(src)) {
            copyFullWithReplacement(path, target);
        }
        return target;
    }

    private Path copyWithReplacement(Path src, Path targetDir) throws IOException {
        ParametersChecker.isTrueThrow(targetDir, FilesEditor::isFile, "target is not directory" + targetDir);
        Path target = Path.of(targetDir.toAbsolutePath().toString(), src.getFileName().toString());
        if (FilesEditor.exist(target)) {
            updateMessage("equaling files " + src + " and " + target);
            if (FilesEditor.isFile(target) && !FilesContentEditor.equalsFile(src, target)) {
                updateMessage("deleting: " + target);
                FilesEditor.delete(target);
                updateMessage("copying: " + src);
                Files.copy(src, target);
                updateValue(getValue(value, src));
            }
        } else {
            updateMessage("copying: " + target);
            Files.copy(src, target);
            updateValue(getValue(value, src));
        }
        updateProgress(++doneWork, totalWork);
        return target;
    }

    private String getValue(StringBuilder sb, Path newPath) {
        if (!sb.isEmpty())
            sb.append("\n");
        sb.append(newPath);
        return sb.toString();
    }
}
