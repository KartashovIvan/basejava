package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CompanySection extends AbstractSection{
    private final List<Company>companies = new LinkedList<>();

    public void addCompany (String name, String website, String title, String description, LocalDate startDate, LocalDate endDate) {
        companies.add(new Company(name,website,title,description,startDate,endDate));
    }

    public void addEducation (String name, String website, String title, LocalDate startDate, LocalDate endDate){
        companies.add(new Company(name,website,title,null,startDate,endDate));
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        return companies + "";
    }
}
