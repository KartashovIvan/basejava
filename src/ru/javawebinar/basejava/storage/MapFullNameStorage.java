package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapFullNameStorage extends AbstractStorage {
    private final Map<String, Resume> resumeStorage = new HashMap<>();

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        resumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, Object searchKey) {
        resumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected Resume returnResume(Object searchKey) {
        return resumeStorage.get(((Resume)searchKey).getUuid());
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return resumeStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeStorage.containsValue(searchKey);
    }

    @Override
    protected List<Resume> getListResumes() {
        return new LinkedList<>(resumeStorage.values());
    }

    @Override
    public int size() {
        return resumeStorage.size();
    }
}
