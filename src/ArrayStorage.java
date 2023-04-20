/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int index =0;index<size();index++){
            if (storage[index] != null){
                storage[index] = null;
            }
        }
    }

    void save(Resume r) {
        int index =0;

        for(;index<storage.length;index++){
            if (storage[index] == null){
                break;
            }
        }

        storage[index] = r;
    }

    Resume get(String uuid) {
        for(int index = 0;index<size();index++){
            if (uuid.equals(storage[index].uuid)){
                return storage[index];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = 0;

        for(;index<size();index++){
            if (uuid.equals(storage[index].uuid)){
                break;
            }
        }

        for (;index<size();index++){
            storage[index] = storage[index+1];
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size()];

        for (int index =0;index< all.length;index++){
            all[index] = storage[index];
        }

        return all;
    }

    int size() {
        int indexSize =0;

        for(;indexSize<storage.length;indexSize++){
            if (storage[indexSize] == null){
                break;
            }
        }

        return indexSize;
    }
}
