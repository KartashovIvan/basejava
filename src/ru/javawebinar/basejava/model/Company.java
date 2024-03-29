package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Company implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String website;
    private final List<Period> periods = new LinkedList<>();

    public Company(String name, String website, String title, String description, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(name ,"name must not be null");
        this.name = name;
        this.website = website;
        periods.add(new Period(title, description, startDate, endDate));
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (!Objects.equals(name, company.name)) return false;
        if (!Objects.equals(website, company.website)) return false;
        return periods.equals(company.periods);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + ", website='" + website + '\'' +
                ", periods=" + periods +
                '}';
    }


}
