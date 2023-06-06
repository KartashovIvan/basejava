package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapFullNameStorage extends AbstractStorage {
    private final Map<String, Resume> resumeStorage = new HashMap<>();

    protected final Comparator<Resume> RESUME_COMPARATOR_NAME = (o1, o2) -> o1.getFullName().compareTo(o2.getFullName());

    @Override
    public void clear() {
        resumeStorage.clear();
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        resumeStorage.put((String) searchKey, r);
    }

    @Override
    protected void updateInStorage(Resume r, Object searchKey) {
        resumeStorage.put((String) searchKey, r);
    }

    @Override
    protected Resume returnResume(Object searchKey) {
        return resumeStorage.get((String) searchKey);
    }

    @Override
    protected void deleteResume(Object searchKey) {
        resumeStorage.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(Resume resume) {
        return resume.getFullName();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return resumeStorage.containsKey((String) searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allResume = new LinkedList<>(resumeStorage.values());
        allResume.sort(RESUME_COMPARATOR_NAME.thenComparing(RESUME_COMPARATOR_UUID));
        return allResume;
    }

    @Override
    public int size() {
        return resumeStorage.size();
    }
}
