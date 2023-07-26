package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage <Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public void doSave(Resume r, Integer searchKey) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResumeInArray(r, searchKey);
        countResumes++;
    }

    public void doUpdate(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    public void doDelete(Integer searchKey) {
        countResumes--;
        deleteResumeInArray(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage,0,countResumes));
    }

    public int size() {
        return countResumes;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    public abstract void saveResumeInArray(Resume r, Integer index);

    public abstract void deleteResumeInArray(int index);

}
