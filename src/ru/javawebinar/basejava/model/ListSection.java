package ru.javawebinar.basejava.model;

import java.util.LinkedList;
import java.util.List;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private final List<String> contents = new LinkedList<>();

    public void addContent(String content) {
        contents.add(content);
    }

    public List<String> getContents() {
        return contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return contents != null ? contents.equals(that.contents) : that.contents == null;
    }

    @Override
    public int hashCode() {
        return contents != null ? contents.hashCode() : 0;
    }

    @Override
    public String toString() {
        return contents + "";
    }
}
