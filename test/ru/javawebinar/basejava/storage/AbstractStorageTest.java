package ru.javawebinar.basejava.storage;

import org.junit.*;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;


public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    private static final String UUID_NOT_EXIST = "Dummy";

    private static final int STORAGE_LIMIT = 10_000;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] expected = {};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowStorage() {
        Assume.assumeFalse(storage instanceof ListStorage || storage instanceof MapStorage);
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid_" + i));
            }
        } catch (StorageException se) {
            Assert.fail("Overflow happened ahead of time");
        }
        storage.save(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume r = new Resume("uuid3");
        storage.update(r);
        Assert.assertSame(r, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume r = new Resume(UUID_4);
        storage.update(r);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        Assert.assertEquals(size, storage.size(), 0);
    }

    void assertGet(Resume resume) {
        Assert.assertSame(resume, storage.get(resume.getUuid()));
    }
}