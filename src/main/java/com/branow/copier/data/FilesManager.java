package com.branow.copier.data;

import com.branow.editors.edit.FilesEditor;

import java.io.File;
import java.io.IOException;

public class FilesManager {

    public static final File DATA = getDataFile();


    private static File getDataFile() {
        File file = new File("data.xml");
        try {
            FilesEditor.createIfNotExist(file);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return file;
    }


}
