package com.branow.copier;

import com.branow.outfits.checker.ParametersChecker;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;

public class ResourceSearcher {

    public static final ResourceSearcher SEARCHER = new ResourceSearcher(ResourceSearcher.class);

    private static final String FXML = "fxml", JPG = "jpg", PNG = "png", CSS = "css";
    private static final String SEPARATOR = "/";

    private final Class<?> root;

    public ResourceSearcher(Class<?> root) {
        this.root = root;
    }

    public Image png(String name, String... folders) {
        return image(name, PNG, folders);
    }

    public Image jpg(String name, String... folders) {
        return image(name, JPG, folders);
    }


    public URL fxml(String name, String... folders) {
        return search(name, FXML, folders);
    }

    public URL css(String name, String... folders) {
        return search(name, CSS, folders);
    }

    private URL search(String name, String extension, String... folders) {
        String path = String.join(SEPARATOR, folders);
        if (!path.isEmpty()) path += SEPARATOR;
        URL url = root.getResource(path + name + "." + extension);
        ParametersChecker.isNullThrow(url);
        return url;
    }

    private Image image(String name, String extension, String... folders) {
        String path = String.join(SEPARATOR, folders);
        if (!path.isEmpty()) path += SEPARATOR;
        String fullName = path + name + "." + extension;
        return new Image(Objects.requireNonNull(root.getResourceAsStream(fullName)));
    }

}