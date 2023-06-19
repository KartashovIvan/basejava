package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> resumeStorage = new HashMap<>();

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    protected void saveResume(Resume r, String searchKey) {
        resumeStorage.put(searchKey, r);
    }

    @Override
    protected void updateInStorage(Resume r, String searchKey) {
        resumeStorage.put(searchKey, r);
    }

    @Override
    protected Resume returnResume(String searchKey) {
        return resumeStorage.get(searchKey);
    }

    @Override
    protected void deleteResume(String searchKey) {
        resumeStorage.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return resumeStorage.containsKey(searchKey);
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
