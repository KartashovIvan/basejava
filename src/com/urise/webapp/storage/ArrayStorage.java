package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int countResumes;

    public void clear() {
        for (int i = 0; i < countResumes; i++) {
            storage[i] = null;
        }
        countResumes = 0;
    }

    public void update(Resume r) {
        int index = serchIndex(r.uuid);

        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.printf("ERROR: resume %s not found", r.uuid);
        }
    }

    public void save(Resume r) {
        int index = serchIndex(r.uuid);

        if (index != -1) {
            System.out.printf("ERROR: resume %s already exist", r.uuid);
        } else if (countResumes == storage.length) {
            System.out.println("Overload database");
        } else {
            storage[countResumes] = r;
            countResumes++;
        }
    }

    public Resume get(String uuid) {
        int index = serchIndex(uuid);

        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("ERROR: resume %s not found", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = serchIndex(uuid);

        if (index >= 0) {
            countResumes--;
            storage[index] = storage[countResumes];
            storage[countResumes] = null;
        } else {
            System.out.printf("ERROR: resume %s not found", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] all = new Resume[countResumes];

        for (int i = 0; i < all.length; i++) {
            all[i] = storage[i];
        }
        return all;
    }

    public int size() {
        return countResumes;
    }

    public int serchIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }
}
