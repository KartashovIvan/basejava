package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.strategy.SerializableStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializableStrategy serializableStrategy;

    protected AbstractPathStorage(String dir, SerializableStrategy serializableStrategy) {
        directory = Paths.get(dir);
        this.serializableStrategy = serializableStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Could not create " + path, path.getFileName().toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    protected void doUpdate(Resume r, Path path) {
        try {
            serializableStrategy.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Could not write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializableStrategy.doReade(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Could not read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Error delete " + path, path.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        Stream<Path> filesList;
        try {
            filesList = Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Could not read error", null, e);
        }
        return filesList.map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Could not read error", null, e);
        }
    }
}
