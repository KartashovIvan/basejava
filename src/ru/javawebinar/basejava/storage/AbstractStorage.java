package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected final Comparator<Resume> RESUME_COMPARATOR = new Resume.ComparatorName().thenComparing(new Resume.ComparatorUuid());

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        updateInStorage(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return returnResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        deleteResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getListResumes();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract void updateInStorage(Resume r, Object searchKey);

    protected abstract Resume returnResume(Object searchKey);

    protected abstract void deleteResume(Object searchKey);

    protected abstract List<Resume> getListResumes();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);
}
