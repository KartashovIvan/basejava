package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.MapFullNameStorage;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new MapFullNameStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid3", "Ivan");
        Resume r2 = new Resume("uuid1","Petr");
        Resume r3 = new Resume("uuid2","Bob");
        Resume r4 = new Resume("uuid3", "Ivan");
        Resume dummy = new Resume("dummy","dummy");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get(dummy));

        printAll();
        ARRAY_STORAGE.update(r4);
        System.out.println("After update resume with uuid3");

        printAll();
        ARRAY_STORAGE.delete(r3);
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
