package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
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
    public void clearStorage() {
        storage.clear();
        Assert.assertEquals(0, storage.size(), 0);
    }

    @Test
    public void saveNewResume() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void saveOverflowStorage() {
        try {
            storage.save(RESUME_4);
        } catch (StorageException se) {
            Assert.fail("Overflow happened ahead of time");
        }
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        storage.save(RESUME_1);
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void updateResume() {
        Resume r = new Resume("uuid3");
        storage.update(r);
        Assert.assertNotSame(RESUME_3, r);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        Resume r = new Resume(UUID_4);
        storage.update(r);
    }

    @Test
    public void getResume() {
        Assert.assertSame(RESUME_1, storage.get("uuid1"));
        Assert.assertSame(RESUME_2, storage.get("uuid2"));
        Assert.assertSame(RESUME_3, storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExists() {
        storage.get("Dummy");
    }

    @Test
    public void deleteResume() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistResume() {
        storage.delete(UUID_4);
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] r = Arrays.copyOfRange(storage.getAll(), 0, storage.size());
        Assert.assertArrayEquals(r, storage.getAll());
    }

    @Test
    public void sizeResumeStorage() {
        Assert.assertEquals(3, storage.size(), 3);
    }
}