package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveResumeInArray(Resume r, int index) {
        storage[countResumes] = r;
    }

    @Override
    public void deleteResumeInArray(String uuid, int index) {
        storage[index] = storage[countResumes];
        storage[countResumes] = null;
    }

    @Override
    public int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
