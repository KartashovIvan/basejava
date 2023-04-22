/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private static int sizeArrayResume = 0;

    void clear() {
        for (int index = 0; index < size(); index++) {
            if (storage[index] != null) {
                storage[index] = null;
            }
        }
        sizeArrayResume = 0;
    }

    void save(Resume r) {
        for (int index = 0; index < storage.length; index++) {
            if (storage[index] == null) {
                storage[index] = r;

                sizeArrayResume++;
            }

            if (r.uuid.equals(storage[index].uuid)) {
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int index = 0; index < sizeArrayResume; index++) {
            if (uuid.equals(storage[index].uuid)) {
                return storage[index];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = 0;

        for (; index < sizeArrayResume; index++) {
            if (uuid.equals(storage[index].uuid)) {
                break;
            }
        }

        for (; index < sizeArrayResume; index++) {
            storage[index] = storage[index + 1];

            sizeArrayResume--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[sizeArrayResume];

        for (int index = 0; index < all.length; index++) {
            all[index] = storage[index];
        }

        return all;
    }

    int size() {
        return sizeArrayResume;
    }
}
