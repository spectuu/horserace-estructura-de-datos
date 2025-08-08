package project.service;

import project.model.Horse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PersistenceService {

    private final Path folder;

    public PersistenceService(Path folder) {
        this.folder = folder;
        try { Files.createDirectories(folder); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    public void saveBinary(String filename, Object obj) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(folder.resolve(filename).toFile()))) {
            oos.writeObject(obj);
        }
    }

    public void dumpReadable(String filename, ArrayList<Horse> horses) throws IOException {
        StringBuilder sb = new StringBuilder("Race snapshot:\n");
        for (Horse h : horses) sb.append(h).append("\n");
        Files.writeString(folder.resolve(filename), sb.toString());
    }

}
