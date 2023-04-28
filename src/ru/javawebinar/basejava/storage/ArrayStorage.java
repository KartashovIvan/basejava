package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        int index = getIndex(r.getUuid());

        if (index != -1) {
            System.out.printf("ERROR: resume %s already exist\n", r.getUuid());
        } else if (countResumes == STORAGE_LIMIT) {
            System.out.printf("Cant add %s. Overload database", r.getUuid());
        } else {
            storage[countResumes] = r;
            countResumes++;
        }
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

    public int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
