package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.strategy.StreamStorage;

public class AbstractFileStorageTest extends AbstractStorageTest {
    public AbstractFileStorageTest () {
        super(new AbstractFileStorage(STORAGE_DIR,new StreamStorage()));
    }
}