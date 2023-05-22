package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> resumeStorage = new LinkedList<>();

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    public void saveResume(Resume r, Object searchKey) {
        resumeStorage.add(r);
    }

    @Override
    public void updateInStorage(Resume r, Object searchKey) {
        resumeStorage.set((Integer) searchKey, r);
    }

    @Override
    public Resume returnResume(Object searchKey) {
        return resumeStorage.get((Integer) searchKey);
    }

    @Override
    public void deleteResume(Object searchKey) {
        resumeStorage.remove((int) searchKey);
    }

    @Override
    public Resume[] getAll() {
        return resumeStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeStorage.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeStorage.size(); i++) {
            if (resumeStorage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }
}
