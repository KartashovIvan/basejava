package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.LocalDate;


public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("E:\\SharedDIR\\Обучение програмированию\\Проекты на JAVA\\javaops\\basejava\\storage");
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NAME_1 = "NAME_1";
    private static final String NAME_2 = "NAME_2";
    private static final String NAME_3 = "NAME_3";
    private static final String NAME_4 = "NAME_4";
    private static final String UUID_NOT_EXIST = "UUID_NOT_EXIST";


    private static final Resume R_1;
    private static final Resume R_2;
    private static final Resume R_3;
    private static final Resume R_4;

    static {
        R_1 = new Resume(UUID_1, NAME_1);
        R_2 = new Resume(UUID_2, NAME_2);
        R_3 = new Resume(UUID_3, NAME_3);
        R_4 = new Resume(UUID_4, NAME_4);

        R_1.addContact(ContactType.EMAIL, "mail@mail.ru");
//        R_1.addContact(ContactType.TELEPHONE_NUMBER, "11111");
//        R_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
//        R_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
//        R_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Ведение проектов на работе", "Настройка мониторинга", "Ежегодное повышение квалификации"));
//        R_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java 8+", "Git", "SQL"));
        R_1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("СО ЕЭС", "url",
                                new Period("Специалис", "Мониторинг и подержка работы IT инфраструктуры", LocalDate.of(2019, 1, 19), LocalDate.of(2020, 6, 30)))));
        R_1.addSection(SectionType.EXPERIENCE,
                new CompanySection(
                        new Company("ЗАО Раском", "url",
                                new Period("Сопровождение и подержка работы сети", LocalDate.of(2020, 7, 1), LocalDate.of(2021, 12, 23)))));
//        R_1.addSection(SectionType.EXPERIENCE,
//                new CompanySection(
//                        new Company("НРД", "url",
//                                new Period("Главный специалист сопровождения", "2-я линия подержки", LocalDate.of(2021, 12, 24), LocalDate.now()))));
//        R_1.addSection(SectionType.EDUCATION,
//                new CompanySection(
//                        new Company("Stepic", "stepic.org",
//                                new Period("Интерактивный тренажер по SQL", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 3, 1)),
//                                new Period("Java: основы многопоточности", LocalDate.of(2023, 4, 1), LocalDate.now()))));

        R_2.addContact(ContactType.SKYPE, "skype2");
        R_2.addContact(ContactType.TELEPHONE_NUMBER, "22222");
    }




    private static final int STORAGE_LIMIT = 10_000;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
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
        storage.save(R_4);
        assertSize(4);
        assertGet(R_4);
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
        storage.save(R_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        storage.save(R_1);
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
        assertGet(R_1);
        assertGet(R_2);
        assertGet(R_3);
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