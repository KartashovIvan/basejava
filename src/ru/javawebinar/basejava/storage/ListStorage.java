package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    protected final List<Resume> resumeStorage = new LinkedList<>();

    @Override
    public void clearStorage() {
        resumeStorage.clear();
    }

    @Override
    public void saveResume(Resume r, int index) {
        if (resumeStorage.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            resumeStorage.add(r);
        }
    }

    @Override
    public void updateInStorage(Resume r, int index) {
        resumeStorage.set(index, r);
    }

    @Override
    public Resume returnResume(int index) {
        return resumeStorage.get(index);
    }

    @Override
    public void deleteResume(String uuid, int index) {
        resumeStorage.remove(get(uuid));
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
    protected int getIndex(String uuid) {
        Resume r = new Resume(uuid);
        return resumeStorage.indexOf(r);
    }
}
