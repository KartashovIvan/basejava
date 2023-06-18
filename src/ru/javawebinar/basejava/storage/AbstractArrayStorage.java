package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public void saveResume(Resume r, Object searchKey) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        saveResumeInArray(r, (Integer) searchKey);
        countResumes++;
    }

    public void updateInStorage(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    public Resume returnResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    public void deleteResume(Object searchKey) {
        countResumes--;
        deleteResumeInArray((Integer) searchKey);
    }

    @Override
    protected List<Resume> getListResumes() {
        return Arrays.asList(Arrays.copyOfRange(storage,0,countResumes));
    }

    public int size() {
        return countResumes;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    public abstract void saveResumeInArray(Resume r, Integer index);

    public abstract void deleteResumeInArray(int index);

}
