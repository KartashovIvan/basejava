package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage <Resume>{
    private final Map<String, Resume> resumeStorage = new HashMap<>();

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    protected void saveResume(Resume r, Resume searchKey) {
        resumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, Resume searchKey) {
        resumeStorage.put(r.getUuid(), r);
    }

    @Override
    protected Resume returnResume(Resume searchKey) {
        return resumeStorage.get(searchKey.getUuid());
    }

    @Override
    protected void deleteResume(Resume searchKey) {
        resumeStorage.remove(searchKey.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return resumeStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
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
