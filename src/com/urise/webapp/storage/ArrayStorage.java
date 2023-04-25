package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10_000;

    private static final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int countResumes;

    public void clear() {
        Arrays.fill(storage,0,countResumes,null);
        countResumes = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.uuid);

        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("ERROR: resume %s not found\n", r.uuid);
        }
    }

    public void save(Resume r) {
        int index = getIndex(r.uuid);

        if (index != -1) {
            System.out.printf("ERROR: resume %s already exist\n", r.uuid);
        } else if (countResumes == storage.length) {
            System.out.println("Overload database");
        } else {
            storage[countResumes] = r;
            countResumes++;
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
            storage[index] = storage[countResumes];
            storage[countResumes] = null;
        } else {
            System.out.printf("ERROR: resume %s not found\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage,0,countResumes);
    }

    public int size() {
        return countResumes;
    }

    public int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }
}
