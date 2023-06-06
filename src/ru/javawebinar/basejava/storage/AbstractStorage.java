package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    protected final Comparator<Resume> RESUME_COMPARATOR_UUID = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r);
        saveResume(r, searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r);
        updateInStorage(r, searchKey);
    }

    @Override
    public Resume get(Resume resume) {
        Object searchKey = getExistingSearchKey(resume);
        return returnResume(searchKey);
    }

    @Override
    public void delete(Resume resume) {
        Object searchKey = getExistingSearchKey(resume);
        deleteResume(searchKey);
    }

    private Object getExistingSearchKey(Resume resume) {
        Object searchKey = getSearchKey(resume);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(resume.toString());
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(Resume resume) {
        Object searchKey = getSearchKey(resume);
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.toString());
        }
        return searchKey;
    }

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract void updateInStorage(Resume r, Object searchKey);

    protected abstract Resume returnResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Object getSearchKey(Resume resume);

    protected abstract boolean isExist(Object searchKey);
}
