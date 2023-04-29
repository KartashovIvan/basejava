package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.printf("Resume %s already exist\n", r.getUuid());
        } else if (countResumes == STORAGE_LIMIT) {
            System.out.printf("Cant add resume %s. Overload database\n", r.getUuid());
        } else {
            saveResume(r, index);
            countResumes++;
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());

        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("ERROR: resume %s not found\n", r.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);

        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("ERROR: resume %s not found\n", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            countResumes--;
            deleteResume(uuid, index);
        } else {
            System.out.printf("ERROR: resume %s not found\n", uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, countResumes);
    }

    public int size() {
        return countResumes;
    }

    public abstract void saveResume(Resume r, int index);

    public abstract void deleteResume(String uuid, int index);

    protected abstract int getIndex(String uuid);

}
