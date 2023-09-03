package ru.javawebinar.basejava.model;

public enum ContactType {
    TELEPHONE_NUMBER ("Номер телефона"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль github"),
    STACKOVERFLOW("Профиль stackoverflow"),
    WEBSITE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
