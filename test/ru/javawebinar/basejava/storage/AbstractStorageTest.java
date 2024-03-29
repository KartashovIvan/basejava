package ru.javawebinar.basejava.storage;

import org.junit.*;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;


public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("E:\\SharedDIR\\Обучение програмированию\\Проекты на JAVA\\javaops\\basejava\\storage");
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String NAME_1 = "NAME_1";
    private static final Resume RESUME_1 = ResumeTestData.createNewResume(UUID_1, NAME_1);
    private static final String UUID_2 = "uuid2";
    private static final String NAME_2 = "NAME_2";
    private static final Resume RESUME_2 = ResumeTestData.createNewResume(UUID_2, NAME_2);
    private static final String UUID_3 = "uuid3";
    private static final String NAME_3 = "NAME_3";
    private static final Resume RESUME_3 = ResumeTestData.createNewResume(UUID_3, NAME_3);
    private static final String UUID_4 = "uuid4";
    private static final String NAME_4 = "NAME_4";
    private static final Resume RESUME_4 = ResumeTestData.createNewResume(UUID_4, NAME_4);
    private static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";
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
        Assert.assertArrayEquals(expected, storage.getAllSorted().toArray());
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowStorage() {
        Assume.assumeTrue(storage instanceof ArrayStorage || storage instanceof SortedArrayStorage);
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid_" + i, null));
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
        Resume r = new Resume(UUID_3, NAME_3);
        storage.update(r);
        Assert.assertTrue(r.equals(storage.get(UUID_3)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume r = new Resume(UUID_4, null);
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
        storage.delete(UUID_NOT_EXIST);
    }

    @Test
    public void getAll() {
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(expected, storage.getAllSorted().toArray());
    }

    @Test
    public void size() {
        assertSize(3);
    }

    void assertSize(int size) {
        Assert.assertEquals(size, storage.size(), 0);
    }

    void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }
}