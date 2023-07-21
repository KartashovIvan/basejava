package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected void updateInStorage(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }
    }

    @Override
    protected Resume returnResume(File file) {
        return doReade(file);
    }

    protected abstract Resume doReade(File file);

    @Override
    protected void deleteResume(File file) {
        if(file.delete()){
            System.out.println(file.getName() + "delete");
        } else {
            throw new StorageException("Error delete file", file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File [] files = directory.listFiles();
        if (files == null){
            throw new StorageException("Error read directory", null);
        }
        List <Resume> resumes = new ArrayList<>(files.length);
        for (File file : files){
            resumes.add(doReade(file));
        }
        return resumes;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        File [] files = directory.listFiles();
        if (files != null) {
            for (File resume : files){
                deleteResume(resume);
            }
        }
    }

    @Override
    public int size() {
        String [] resumes = directory.list();
        if (resumes == null){
            throw new StorageException("Error read directory", null);
        }
        return resumes.length;
    }
}
