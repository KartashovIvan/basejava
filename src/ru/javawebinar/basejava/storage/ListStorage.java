package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage <Integer> {
    protected final List<Resume> resumeStorage = new LinkedList<>();

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    public void saveResume(Resume r, Integer searchKey) {
        resumeStorage.add(r);
    }

    @Override
    public void updateInStorage(Resume r, Integer searchKey) {
        resumeStorage.set(searchKey, r);
    }

    @Override
    public Resume returnResume(Integer searchKey) {
        return resumeStorage.get(searchKey);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        resumeStorage.remove(searchKey.intValue());
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new LinkedList<>(resumeStorage);
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
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }
}
