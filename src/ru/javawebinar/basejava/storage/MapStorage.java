package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> resumeStorage = new HashMap<>();

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        resumeStorage.put(r.getUuid(),r);
    }

    @Override
    protected void updateInStorage(Resume r, Object searchKey) {
        resumeStorage.put((String) searchKey, r);
    }

    @Override
    protected Resume returnResume(Object searchKey) {
        return resumeStorage.get(searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeStorage.containsKey(searchKey);
    }

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        Collection<Resume> values = resumeStorage.values();
        return values.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeStorage.size();
    }
}
