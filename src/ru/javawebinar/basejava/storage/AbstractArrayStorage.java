package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clearStorage() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public void saveResume(Resume r, int index) {
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            saveResumeInArray(r, index);
            countResumes++;
        }
    }

    public void updateInStorage(Resume r, int index) {
        storage[index] = r;
    }

    public Resume returnResume (int index) {
        return storage[index];
    }

    public void deleteResume(String uuid, int index) {
        countResumes--;
        deleteResumeInArray(uuid, index);
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, countResumes);
    }

    public int size() {
        return countResumes;
    }

    public abstract void saveResumeInArray(Resume r, int index);

    public abstract void deleteResumeInArray(String uuid, int index);

}
