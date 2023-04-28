package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void saveToStorage(Resume r, int index) {
        index = -(getIndex(r.getUuid())) - 1;

        if (index <= -1) {
            System.out.printf("Resume %s already exist\n", r.getUuid());
        } else if (countResumes == STORAGE_LIMIT) {
            System.out.printf("Cant add resume %s. Overload database\n", r.getUuid());
        } else {
            System.arraycopy(storage, index, storage, index + 1, countResumes - index);
            storage[index] = r;
            countResumes++;
        }
    }

    @Override
    public void deleteFromStorage(String uuid, int index) {
        if (index + 1 == countResumes) {
            storage[index] = null;
            countResumes--;
        } else if (index >= 0) {
            countResumes--;
            System.arraycopy(storage, index + 1, storage, index, countResumes - index);
        } else {
            System.out.printf("Resume %s not found\n", uuid);
        }
    }

    @Override
    public int getIndex(String uuid) {
        Resume r = new Resume();
        r.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, r);
    }
}
