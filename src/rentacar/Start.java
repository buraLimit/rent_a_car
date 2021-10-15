package rentacar;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Start {
    public static void main (String[] args) {

        String directoryPath = "racuni";

        Path path = Paths.get(directoryPath);
        if (!Files.isDirectory(path)) {
            new File("racuni").mkdir(); //kreiranje direktorijuma za racune
        }

        Filtriranje.glavniMeni();
    }
}
