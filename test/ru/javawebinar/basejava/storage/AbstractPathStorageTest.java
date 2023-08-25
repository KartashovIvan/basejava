package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.StreamStorage;

public class AbstractPathStorageTest extends AbstractStorageTest {
    public AbstractPathStorageTest () {
        super(new AbstractPathStorage(STORAGE_DIR.getAbsolutePath(),new StreamStorage()));
    }
}