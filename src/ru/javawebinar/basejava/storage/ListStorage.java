package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> resumeStorage = new LinkedList<>();

//    private final Comparator <Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

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
    public List<Resume> getAllSorted() {
        List<Resume> allResume = new LinkedList<>(resumeStorage);
        allResume.sort(RESUME_COMPARATOR);
        return allResume;
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
