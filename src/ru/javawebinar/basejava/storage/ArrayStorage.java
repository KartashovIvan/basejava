package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveResumeInArray(Resume r, Integer index) {
        storage[countResumes] = r;
    }

    @Override
    public void deleteResumeInArray(int index) {
        storage[index] = storage[countResumes];
        storage[countResumes] = null;
    }

    @Override
    protected Object getSearchKey(Resume resume) {
        for (int i = 0; i < countResumes; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
