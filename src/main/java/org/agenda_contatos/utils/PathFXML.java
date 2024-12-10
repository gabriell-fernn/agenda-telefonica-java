package org.agenda_contatos.utils;

import java.nio.file.Paths;

public class PathFXML {

    public static String pathBase() {
        return Paths.get("src/main/java/org/agenda_contatos/view").toAbsolutePath().toString() + "//";
    }
}
