package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        if (countResumes == 0) {
            storage[countResumes] = r;
            countResumes++;
        } else if (countResumes == STORAGE_LIMIT) {
            System.out.printf("Cant add %s. Overload database", r.getUuid());
        } else {
            int index = -(getIndex(r.getUuid())) - 1;

            if (index >= 0) {
                System.arraycopy(storage, index, storage, index + 1, countResumes - index);
                storage[index] = r;
                countResumes++;
            } else {
                System.out.printf("Resume %s already exist\n", r.getUuid());
            }
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);

        if (index + 1 == countResumes) {
            storage[index] = null;
            countResumes--;
        } else if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, countResumes - index);
            countResumes--;
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
