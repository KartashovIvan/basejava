package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
    @Override
    public void saveResumeInArray(Resume r, Integer index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, countResumes - index);
        storage[index] = r;
    }

    @Override
    public void deleteResumeInArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes - index);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume r = new Resume(uuid, null);
        return Arrays.binarySearch(storage, 0, countResumes, r , RESUME_COMPARATOR);
    }
}
