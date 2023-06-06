package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    Resume get(Resume resume);

    void delete(Resume resume);

    List<Resume> getAllSorted();

    int size();
}
