package com.branow.copier;

import com.branow.editors.edit.FilesEditor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.io.File.separator;

public class Copier {

    private static final String DISK_SEPARATOR = ":" + separator;


    private final Path to;

    public Copier(Path to) {
        this.to = to;
    }

//    public void copyFull(Path path) throws IOException {
//        Path targetDir = getTargetDir(path, to);
//        FilesEditor.createIfNotExist(targetDir);
//        FilesEditor.copyFullWithReplacement(path, targetDir);
//    }

    public static List<Path> getAllFilesForCopy(Path path) {
        return FilesEditor.goInDeep(path);
    }

    public void copy(Path path) throws IOException {
        Path targetDir = getTargetDir(path, to);
        FilesEditor.createIfNotExist(targetDir);
        FilesEditor.copyWithReplacement(path, targetDir);
    }

    public void deleteExcessiveFiles(Path origin, Path copy) {
        FilesEditor.deleteExcessiveFiles(FilesEditor.goInDeep(origin), FilesEditor.goInDeep(copy));
    }

    public static Path getTargetDir(Path path, Path to) {
        return Path.of(to.toAbsolutePath() + separator + replaceDiskToFolder(path.getParent().toAbsolutePath().toString()));
    }

    private static String replaceDiskToFolder(String path) {
        return getDiskDirectory(getDisk(path)) + path.substring(3);
    }

    private static char getDisk(String path) {
        return path.charAt(path.indexOf(DISK_SEPARATOR) - 1);
    }

    private static String getDiskDirectory(char disk) {
        return "disk-" + disk + separator;
    }

}
