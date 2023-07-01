package ru.javawebinar.basejava.model;

import java.util.LinkedList;
import java.util.List;

public class ListSection extends AbstractSection{
    private final List<String> contents = new LinkedList<>();

    public void addContent (String content){
        contents.add(content);
    }

    @Override
    public String toString() {
        return contents +"";
    }
}
