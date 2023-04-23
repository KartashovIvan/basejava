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
        boolean equalsResume = true;

        if (countResumes == 0) {
            storage[0] = r;
            countResumes++;
        } else {
            for (int i = 0; i < countResumes; i++) {
                if (r.uuid.equals(storage[i].uuid)) {
                    equalsResume = false;
                    break;
                }
            }
            if (equalsResume) {
                storage[countResumes] = r;
                countResumes++;
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
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].uuid)) {
                countResumes--;
                storage[i] = storage[countResumes];
                storage[countResumes] = null;
            }
        }
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
