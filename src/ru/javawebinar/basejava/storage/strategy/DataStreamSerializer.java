package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Writer(dos, r.getContacts().entrySet(), write -> {
                dos.writeUTF(write.getKey().name());
                dos.writeUTF(write.getValue());
            });

            Writer(dos, r.getSections().entrySet(), write -> {
                SectionType sectionType = write.getKey();
                AbstractSection abstractSection = write.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) abstractSection).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        Writer(dos, ((ListSection) abstractSection).getContents(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        Writer(dos, ((CompanySection) abstractSection).getCompanies(), writeList -> {
                            dos.writeUTF(writeList.getName());
                            dos.writeUTF(writeList.getWebsite());
                            Writer(dos, writeList.getPeriods(), writePeriods -> {
                                dos.writeUTF(writePeriods.getTitle());
                                dos.writeUTF(writePeriods.getDescription());
                                writeDate(dos, writePeriods.getStartDate());
                                writeDate(dos, writePeriods.getEndDate());
                            });
                        });
                        break;
                }
            });
        }
    }

    private interface Writer<T> {
        void write(T t) throws IOException;
    }

    private <T> void Writer(DataOutputStream dos, Collection<T> collection, Writer<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private static void writeDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
        dos.writeInt(localDate.getDayOfMonth());
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            mapReader(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            mapReader(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, takeSection(dis, sectionType));
            });
            return resume;
        }
    }

    public DataStreamSerializer() {
    }

    private AbstractSection takeSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanySection(
                        readList(dis, () -> new Company(dis.readUTF(), dis.readUTF(),
                                readList(dis, () -> new Period(dis.readUTF(), dis.readUTF(), readDate(dis), readDate(dis))))));
            default:
                throw new NullPointerException();
        }
    }

    private interface Reader {
        void read() throws IOException;
    }

    private interface ListReader<T> {
        T readList() throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, ListReader<T> listReader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(listReader.readList());
        }
        return list;
    }

    private void mapReader(DataInputStream dis, Reader reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private static LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }
}
