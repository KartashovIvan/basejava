package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("ERROR: resume %s not found\n", uuid);
        return null;
    }

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, countResumes);
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());

        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("ERROR: resume %s not found\n", r.getUuid());
        }
    }

    public int size() {
        return countResumes;
    }

    protected abstract int getIndex(String uuid);

}
