package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Company {
    private final String name;
    private final String website;
    private final List<Period> periods = new LinkedList();

    public Company(String name, String website, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.website = website;
        periods.add(new Period(title, description, startDate, endDate));
    }

    public Company(String name, String website, String title, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.website = website;
        periods.add(new Period(title, startDate, endDate));
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        return name +
                ", website='" + website + '\'' +
                ", periods=" + periods +
                '}';
    }


}
