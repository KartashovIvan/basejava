package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        saveResume(r, index);
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());

        if (index >= 0) {
            updateInStorage(r, index);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            return returnResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(uuid, index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    abstract public Resume[] getAll();

    @Override
    abstract public int size();

    protected abstract int getIndex(String uuid);

    protected abstract void clearStorage();

    protected abstract void saveResume(Resume r, int index);

    protected abstract void updateInStorage(Resume r, int index);

    protected abstract Resume returnResume(int index);

    protected abstract void deleteResume(String uuid, int index);
}
