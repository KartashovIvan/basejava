/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[3];
    private int countResumes;

    void clear() {
        for (int i = 0; i < countResumes; i++) {
            storage[i] = null;
        }
        countResumes = 0;
    }

    void save(Resume r) {
        for (int index = 0; index < storage.length; index++) {
            if (storage[index] == null) {
                storage[index] = r;
                countResumes++;
            }

            if (r.uuid.equals(storage[index].uuid)) {
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int i = 0;

        for (; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                break;
            }
        }

        for (; i < countResumes; i++) {
            if (i == countResumes-1){
                storage[i] =null;
            } else {
                storage[i] = storage[i + 1];
            }
        }
        countResumes--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[countResumes];

        for (int i = 0; i < all.length; i++) {
            all[i] = storage[i];
        }

        return all;
    }

    int size() {
        return countResumes;
    }
}
